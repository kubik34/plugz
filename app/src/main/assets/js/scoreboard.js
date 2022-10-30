scoreboard1={

create:function(){
	game.add.image(0,0,'gameover');
	game.stage.backgroundColor="#4d3458";
	report=game.add.image(0,0,'');
		report.anchor.setTo(0,0);
		tween_scale=game.add.tween(report.scale).from({x:1.5,y:1.5},1000,'Linear',true);
	sound_score=game.add.audio('score');
		sound_score.play();
	
	tween_scale.onComplete.add(function(){
		
	if(localStorage.getItem('easy_score')==null){
		localStorage.setItem('easy_score',game.global.score);
	}
	else if(game.global.score>localStorage.getItem('easy_score')){
		localStorage.setItem('easy_score',game.global.score);
	}
	
	bestscore=localStorage.getItem('easy_score');
		
	text_report=game.add.text(330,156,'',{font:"30px cuyubrab",fill:"white"});
		text_report.anchor.setTo(0.5,0.5);
	text_level=game.add.text(330,300,'Правильных ответов: '+game.global.box[1]+'',{font:"30px cuyubrab",fill:"white"});
		text_level.anchor.setTo(0.5,0.5);
	text_scoretext=game.add.text(330,550,"Счет:",{font:"50px cuyubrab",fill:"white"});
		text_scoretext.anchor.setTo(0.5,0.5);
	text_score=game.add.text(330,618,""+game.global.score+"",{font:"80px cuyubrab",fill:"white"});
		text_score.anchor.setTo(0.5,0.5);
	text_besttext=game.add.text(330,400,"Лучший счет:",{font:"50px cuyubrab",fill:"white"});
		text_besttext.anchor.setTo(0.5,0.5);
	text_best=game.add.text(330,448,""+bestscore+"",{font:"50px cuyubrab",fill:"white"});
		text_best.anchor.setTo(0.5,0.5);
	},this);
	
	//buttons for home and restart
	buttons_left=game.add.sprite(160,830,'button_s');
		buttons_left.anchor.setTo(0.5,0.5);
	button_left=game.add.sprite(160,820,'buttonhome');
		button_left.anchor.setTo(0.5,0.5);
	
	buttons_right=game.add.sprite(480,830,'button_s');
		buttons_right.anchor.setTo(0.5,0.5);
	button_right=game.add.sprite(480,820,'buttonretry');
		button_right.anchor.setTo(0.5,0.5);
	text_home=game.add.text(160,820,'',{font:"50px cuyubrab",fill:"white"});
		text_home.anchor.setTo(0.5,0.5);
	text_restart=game.add.text(480,820,'',{font:"50px cuyubrab",fill:"white"});
		text_restart.anchor.setTo(0.5,0.5);
	
	game.time.events.add(1000,function(){
		button_left.inputEnabled=true;
		button_left.events.onInputDown.add(this.clickhome,this);
		button_right.inputEnabled=true;
		button_right.events.onInputDown.add(this.clickrestart,this);
		
			
	
	
	},this);
},

clickhome:function(){
	tween_button=game.add.tween(button_left).from({y:830},500,'Linear',true);
	tween_button.onComplete.add(function(){
		game.state.start('main');
	},this);
},

clickrestart:function(){
	tween_button=game.add.tween(button_right).from({y:830},500,'Linear',true);
	tween_button.onComplete.add(function(){
		game.state.start('game0');
	},this);
},


};

scoreboard2={

create:function(){
	game.stage.backgroundColor="#4d3458";
	report=game.add.image(0,0,'');
		report.anchor.setTo(0,0);
		tween_scale=game.add.tween(report.scale).from({x:1.5,y:1.5},1000,'Linear',true);
	sound_score=game.add.audio('score');
		sound_score.play();
	
	tween_scale.onComplete.add(function(){
		
	if(localStorage.getItem('easy_score')==null){
		localStorage.setItem('easy_score',game.global.score);
	}
	else if(game.global.score>localStorage.getItem('easy_score')){
		localStorage.setItem('easy_score',game.global.score);
	}
	
	bestscore=localStorage.getItem('easy_score');
		
	text_report=game.add.text(330,160,'',{font:"30px cuyubrab",fill:"white"});
		text_report.anchor.setTo(0.5,0.5);
	text_level=game.add.text(330,300,'Правильных ответов: '+game.global.box[1]+'',{font:"30px cuyubrab",fill:"white"});
		text_level.anchor.setTo(0.5,0.5);
	text_scoretext=game.add.text(330,550,"Счет:",{font:"50px cuyubrab",fill:"white"});
		text_scoretext.anchor.setTo(0.5,0.5);
	text_score=game.add.text(330,618,""+game.global.score+"",{font:"80px cuyubrab",fill:"white"});
		text_score.anchor.setTo(0.5,0.5);
	text_besttext=game.add.text(330,400,"Лучший счет:",{font:"50px cuyubrab",fill:"white"});
		text_besttext.anchor.setTo(0.5,0.5);
	text_best=game.add.text(330,448,""+bestscore+"",{font:"50px cuyubrab",fill:"white"});
		text_best.anchor.setTo(0.5,0.5);
	},this);
	
	buttons_left=game.add.sprite(160,830,'button_s');
		buttons_left.anchor.setTo(0.5,0.5);
	button_left=game.add.sprite(160,820,'button');
		button_left.anchor.setTo(0.5,0.5);
	
	buttons_right=game.add.sprite(480,830,'button_s');
		buttons_right.anchor.setTo(0.5,0.5);
	button_right=game.add.sprite(480,820,'button');
		button_right.anchor.setTo(0.5,0.5);
	text_home=game.add.text(160,820,'Home',{font:"50px cuyubrab",fill:"white"});
		text_home.anchor.setTo(0.5,0.5);
	text_restart=game.add.text(480,820,'Restart',{font:"50px cuyubrab",fill:"white"});
		text_restart.anchor.setTo(0.5,0.5);
	
	game.time.events.add(1000,function(){
		button_left.inputEnabled=true;
		button_left.events.onInputDown.add(this.clickhome,this);
		button_right.inputEnabled=true;
		button_right.events.onInputDown.add(this.clickrestart,this);
		
			
	
	
	},this);
},

clickhome:function(){
	tween_button=game.add.tween(button_left).from({y:830},500,'Linear',true);
	tween_button.onComplete.add(function(){
		game.state.start('main');
	},this);
},

clickrestart:function(){
	tween_button=game.add.tween(button_right).from({y:830},500,'Linear',true);
	tween_button.onComplete.add(function(){
		game.state.start('game0');
	},this);
},


};