main={

create:function(){
	game.add.image(0,0,'bgmain');
	text_title=game.add.image(320,230,'title');
		text_title.anchor.setTo(0.5,0.5);
		game.add.tween(text_title).from({y:50},2500,'Elastic',false);
		
	buttons_play=game.add.sprite(320,700,'button_play');
		buttons_play.anchor.setTo(0.5,0.5);
	button_play=game.add.sprite(320,690,'buttonplay');
		button_play.anchor.setTo(0.5,0.5);
		button_play.inputEnabled=true;
		button_play.events.onInputDown.add(this.clickplay,this);
	text_play=game.add.text(320,700,'',{font:"50px cuyubrab",fill:"white"});
		text_play.anchor.setTo(0.5,0.5);

		
},

createselect:function(){
	buttons_easy=game.add.sprite(320,405,'button_s');
		buttons_easy.anchor.setTo(0.5,0.5);
	button_easy=game.add.sprite(320,400,'button');
		button_easy.anchor.setTo(0.5,0.5);
		button_easy.inputEnabled=true;
		button_easy.events.onInputDown.add(this.clickeasy,this);
	text_easy=game.add.text(320,400,'Easy',{font:"40px cuyubrab",fill:"white"});
		text_easy.anchor.setTo(0.5,0.5);
		
	buttons_hard=game.add.sprite(320,605,'button_s');
		buttons_hard.anchor.setTo(0.5,0.5);
	button_hard=game.add.sprite(320,600,'button');
		button_hard.anchor.setTo(0.5,0.5);
		button_hard.inputEnabled=true;
		button_hard.events.onInputDown.add(this.clickhard,this);
	text_hard=game.add.text(320,600,'Hard',{font:"40px cuyubrab",fill:"white"});
		text_hard.anchor.setTo(0.5,0.5);
},

clickplay:function(){
	tween_button=game.add.tween(button_play).from({y:700},200,'Linear',true);
	tween_button.onComplete.add(function(){
		game.global.box[0]="Easy";
		game.global.box[2]=1;
		game.state.start('game0');
	},this);
},

clickeasy:function(){
	tween_button=game.add.tween(button_easy).from({y:405},200,'Linear',true);
	tween_button.onComplete.add(function(){
		game.global.box[0]="Easy";
		game.global.box[2]=1;
		bgmusic.stop();
		game.state.start('game0');
	},this);
	    integration.showBanner();
},



clickhard:function(){
	tween_button=game.add.tween(button_hard).from({y:605},200,'Linear',true);
	tween_button.onComplete.add(function(){
		game.global.box[0]="Hard";
		game.global.box[2]=3;
		bgmusic.stop();
		game.state.start('game1');
	},this);
},



};