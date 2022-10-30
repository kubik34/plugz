package com.codeine.plugz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class MainActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    AdView mAdView;
    private InterstitialAd mInterstitialAd;
    boolean intCanShow = true;
    boolean bannerCanShow = false;
    boolean rewardedAdCanShow = false;
    boolean mustDestroyBanner = false;
    private RewardedAd rewardedAd;

    boolean ads = false;
    BillingProcessor bp;
    SharedPreferences sharedpreferences;

    WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        browser = findViewById(R.id.webView);
        browser.setWebChromeClient(new WebChromeClient());
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setDomStorageEnabled(true);
        browser.getSettings().setAllowFileAccessFromFileURLs(true);
        browser.getSettings().setAllowUniversalAccessFromFileURLs(true);
        browser.addJavascriptInterface(new WebAppInterface(this), "Android");
        browser.loadUrl("file:///android_asset/js/game.html");
        browser.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView viewx, String urlx) {
                browser.setVisibility(View.GONE);
                if(!urlx.contains("http")){
                    viewx.loadUrl(urlx);
                    return false;
                }else{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlx));
                    startActivity(intent);
                    return true;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url){
                browser.setVisibility(View.VISIBLE);
                if(ads){
                    browser.loadUrl("javascript:integration.itsads(1)");
                }
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap facIcon) {
            }

        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdOpened() {
            }

            @Override
            public void onAdClicked() {
            }

            @Override
            public void onAdLeftApplication() {
            }

            @Override
            public void onAdClosed() {
            }
        });

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdOpened() {
            }

            @Override
            public void onAdClicked() {
            }

            @Override
            public void onAdLeftApplication() {
            }

            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

        initRewardedAd();

        destroyBanner();

        bp = new BillingProcessor(MainActivity.this, "", this);
        bp.initialize();

        sharedpreferences = getApplicationContext().getSharedPreferences(getPackageName(), MODE_PRIVATE);
        if(sharedpreferences.contains("ads")) {
            ads = sharedpreferences.getBoolean("ads", false);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!ads){
                    initAd();
                    destroyBanner();
                    browser.loadUrl("javascript:integration.itsads(0)");
                }else{
                    browser.loadUrl("javascript:integration.itsads(1)");
                }

            }
        }, 5000);
    }


    public void initAd(){
        if(intCanShow){
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }
        intCanShow = false;

        if(bannerCanShow){
            showBanner();
        }
        bannerCanShow = false;
        if(mustDestroyBanner){
            destroyBanner();
        }
        mustDestroyBanner = false;

        if(rewardedAdCanShow){
            loadRewardedVideoAd();
        }
        rewardedAdCanShow = false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initAd();
            }
        }, 1000);
    }

    public void showBanner(){
        mAdView.loadAd(new AdRequest.Builder().build());
        mAdView.setVisibility(View.VISIBLE);
    }

    public void destroyBanner(){
        mAdView.setVisibility(View.GONE);
    }

    private void initRewardedAd(){
        rewardedAd = new RewardedAd(this,"");
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
            }
            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
    }

    private void loadRewardedVideoAd() {
        if (rewardedAd.isLoaded()) {
            Activity activityContext = MainActivity.this;
            RewardedAdCallback adCallback = new RewardedAdCallback() {
                @Override
                public void onRewardedAdOpened() {
                }

                @Override
                public void onRewardedAdClosed() {
                }

                @Override
                public void onUserEarnedReward(@NonNull RewardItem reward) {
                    browser.loadUrl("javascript:integration.rewarded()");
                }

                @Override
                public void onRewardedAdFailedToShow(AdError adError) {
                }
            };
            rewardedAd.show(activityContext, adCallback);

        } else {
        }
        initRewardedAd();
    }

    public void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }

    public void launchSharer(String msg, String titlemsg) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, titlemsg);
            String shareMessage = msg+"\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + getPackageName() +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Choose one"));
        } catch(Exception e) {
        }
    }

    public void launchTextSharer(String msg, String titlemsg){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, titlemsg);
            String shareMessage = msg;
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Choose one"));
        } catch(Exception e) {
        }
    }

    public class WebAppInterface {

        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void showAd(){
            intCanShow = true;
        }

        @JavascriptInterface
        public void showAndroidBanner() {
            bannerCanShow = true;
        }

        @JavascriptInterface
        public void hideAndroidBanner() {
            mustDestroyBanner = true;
        }

        @JavascriptInterface
        public void removeAds() {
            orderRemoveAds();
        }

        @JavascriptInterface
        public void showRewardedAd(){
            rewardedAdCanShow = true;
        }

        @JavascriptInterface
        public void showAlert(String alerttext){
            Toast.makeText(getApplicationContext(), alerttext, Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void portrait() {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


        @JavascriptInterface
        public void landscape() {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        @JavascriptInterface
        public void rateThisApp(){
            launchMarket();
        }

        @JavascriptInterface
        public void shareText(String txt, String title){
            launchTextSharer(txt, title);
        }

        @JavascriptInterface
        public void shareThisApp(String txt, String title){
            launchSharer(txt, title);
        }

    }

    @Override
    public void onBillingInitialized() {
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        if(productId.equals("noads")){
            bp.consumePurchase("noads");
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean("ads", true);
            editor.apply();
            ads = true;
            destroyBanner();
            browser.loadUrl("javascript:integration.itsads(1)");
        }
    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
    }

    @Override
    public void onPurchaseHistoryRestored() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void orderRemoveAds(){
        boolean isAvailable = BillingProcessor.isIabServiceAvailable(MainActivity.this);
        if(isAvailable) {
            bp.purchase(MainActivity.this, "noads");
        }
    }

    @Override
    protected void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        WebView browser = findViewById(R.id.webView);
        if ((keyCode == KeyEvent.KEYCODE_BACK) && browser.canGoBack()) {
            browser.goBack();
            return true;
        }
        else
        {}
        return super.onKeyDown(keyCode, event);
    }


}
