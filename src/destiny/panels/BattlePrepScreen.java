package destiny.panels;

import java.awt.Rectangle;

import destiny.assets.Constants;
import destiny.assets.RippleCursor;
import destiny.core.FadeImage;
import destiny.core.PButton;
import destiny.core.Screen;
import destiny.core.ScreenManager;
import processing.core.PApplet;

public class BattlePrepScreen implements Screen {
	
	private FadeImage background;
	private RippleCursor cursor;
	private PButton button, back;
	private FadeImage play, prev;
	private boolean first, second, third, selectFirst, selectSecond, selectThird;
	private int[] revs;
	private PButton[] select;
	private int page;
	private FadeImage[] profile;
	
	@Override
	public void setup(PApplet window) {
		background = new FadeImage("res/battlePrepScreen/nathaniel.PNG");
		play = new FadeImage("res/generalAssets/play.png");
		prev = new FadeImage("res/generalAssets/back.png");
		cursor = RippleCursor.createLowPerformanceCursor();
		first = false;
		second = false;
		third = false;
		selectFirst = true;
		selectSecond = false;
		selectThird = false;
		page = 1;
		revs = new int[3];
		button = new PButton(new Rectangle(Constants.SCREEN_WIDTH-500, Constants.SCREEN_HEIGHT-300, Constants.SCREEN_WIDTH-100, Constants.SCREEN_HEIGHT-100), false);
		back = new PButton(new Rectangle(0, Constants.SCREEN_HEIGHT-200, 200, Constants.SCREEN_HEIGHT), false);
		background.setCoords(0, 0);
		play.resize(400, 200);
		play.setCoords(Constants.SCREEN_WIDTH-500, Constants.SCREEN_HEIGHT-300);
		prev.resize(150, 150);
		prev.setCoords(50, Constants.SCREEN_HEIGHT-200);
		background.resize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		
		select = new PButton[Constants.TOTAL_LEVELS];
		profile = new FadeImage[Constants.TOTAL_LEVELS];

		for(int i = 0; i < Constants.TOTAL_LEVELS; i++) {
			PButton b = new PButton(new Rectangle(250+(i%20%5*300), 100+(i%20/5*200), 250+(i%20%5*250)+200, 100+(i%20/5*200)+ 200), false);
			int id = i+1;
			b.addListener(new Runnable() {
				@Override
				public void run() {
					if(selectFirst) {
						revs[0] = id;
						selectFirst = false;
						selectSecond = true;
						first = true;
					} else if(selectSecond) {
						revs[1] = id;
						selectSecond = false;
						selectThird = true;
						second = true;
					}
					else if(selectThird) {
						revs[2] = id;
						selectThird = false;
						third = true;
					}
					System.out.println(""+revs[0]+revs[1]+revs[2]);
				}
			});
			select[i] = b;
			FadeImage img = new FadeImage("res/generalAssets/obama.png");
			img.setCoords(250+(i%20%5*300), 100+(i%20/5*200));
			img.resize(200, 200);
			profile[i] = img;
		}
	}

	public void draw(PApplet window) {
		background.draw(window);
		if(first&&second&&third)
			play.draw(window);
		prev.draw(window);
		for(int i = 20*(page-1); i < 20*page; i++) {
			profile[i].draw(window);
		}
		if (window.mousePressed) {
			cursor.draw(window);
		} else {
			cursor.clearTrail();
		}
		button.addListener(new Runnable() {
			@Override
			public void run() {
				background.setFadeSpeed(40);
				background.setTint(255);
				background.setTargetTint(0);
				background.fadeWhite(true);
				play.setFadeSpeed(40);
				play.setTint(255);
				play.setTargetTint(0);
				play.fadeWhite(true);
				background.addListener(new Runnable() {

					@Override
					public void run() {
						ScreenManager.setCurrentScreenByName("main", window);
					}
					
				});
			}
		});
		back.addListener(new Runnable() {
			@Override
			public void run() {
				background.setFadeSpeed(40);
				background.setTint(255);
				background.setTargetTint(0);
				background.fadeWhite(true);
				play.setFadeSpeed(40);
				play.setTint(255);
				play.setTargetTint(0);
				play.fadeWhite(true);
				prev.setFadeSpeed(40);
				prev.setTint(255);
				prev.setTargetTint(0);
				prev.fadeWhite(true);
				background.addListener(new Runnable() {

					@Override
					public void run() {
						ScreenManager.setCurrentScreenByName("level", window);
					}
					
				});
			}
		});
}
	

	@Override
	public void dispose() {
		background = null;
		cursor = null;
		button.removeListener();
		button = null;
		prev = null;
	
	}

}
