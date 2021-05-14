package destiny.panels;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import destiny.assets.Constants;
import destiny.assets.RippleCursor;
import destiny.assets.Character;
import destiny.core.FadeImage;
import destiny.core.PButton;
import destiny.core.Screen;
import destiny.core.ScreenManager;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * BattlePrepScreen is where the user can select which revolutionaries they want to use for battle
 * @author Jay Paek
 * @version 5/7/2021
 *
 */
public class BattleScreen implements Screen {
	
	private FadeImage background;
	private RippleCursor cursor;
	private PButton button, back;
	private int revSelect, enemySelect;
	private Character[] revs, enemies;
	private PButton[] select, selection, enemySelection;
	private boolean win = true, lose = false;;
	private FadeImage victory, defeat;
	
	@Override
	public void setup(PApplet window) {
		background = new FadeImage("res/battlePrepScreen/nathaniel.PNG");
		victory = new FadeImage("res/battleScreen/victory.png");
		defeat = new FadeImage("res/battleScreen/defeat.png");
		cursor = RippleCursor.createLowPerformanceCursor();
		revSelect = 0;
		enemySelect = 0;
		revs = new Character[] {new Character(1),new Character(1),new Character(1)};
		enemies = new Character[] {new Character(1),new Character(1),new Character(1)};
		try {
			button = new PButton(new Rectangle( Constants.SCREEN_WIDTH - Constants.scaleIntToWidth(450), Constants.SCREEN_HEIGHT - Constants.scaleIntToHeight(250), Constants.scaleIntToWidth(400), Constants.scaleIntToWidth(200)),
					new PImage(ImageIO.read(new File("res/generalAssets/play.png"))), false);
			back = new PButton(new Rectangle(Constants.scaleIntToWidth(50), Constants.SCREEN_HEIGHT - Constants.scaleIntToHeight(250), Constants.scaleIntToWidth(200), Constants.scaleIntToHeight(200)),
					new PImage(ImageIO.read(new File("res/generalAssets/back.png"))), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		background.setCoords(0, 0);
		background.resize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		victory.setCoords(0, 0);
		victory.resize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		defeat.setCoords(0, 0);
		defeat.resize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		
		select = new PButton[5];
		selection = new PButton[3]; 
		enemySelection = new PButton[3]; 



		for(int i = 0; i < 5; i++) {
			final PButton b;
			int id = i+1;
			try {
				b = new PButton(new Rectangle(Constants.scaleIntToWidth(100+(i*350)), Constants.SCREEN_HEIGHT - Constants.scaleIntToHeight(300), Constants.scaleIntToWidth(300), Constants.scaleIntToWidth(250)), new PImage(ImageIO.read(new File("res/generalAssets/obama.png"))), false);
				b.addListener(new Runnable() {
					@Override
					public void run() {
						System.out.println(""+revs[0]+revs[1]+revs[2]);
					}
				});
				select[i] = b;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
		for(int i = 0; i < 3; i++) {
			final PButton b;
			try {
				b = new PButton(new Rectangle(Constants.scaleIntToWidth(100+(i*300)), Constants.scaleIntToHeight(200), Constants.scaleIntToWidth(200), Constants.scaleIntToWidth(500)),new PImage(ImageIO.read(new File("res/generalAssets/obama.png"))), false);
				revs[i].setCoords(Constants.scaleIntToWidth(100+(i*300)), Constants.scaleIntToHeight(200));
				int sel = i;
				b.addListener(new Runnable() {
					@Override
					public void run() {
						revSelect = 3 - sel;
						for(PButton rev: selection) {
							rev.setHightlight(false);
						}
						b.setHightlight(true);
					}
				});
				selection[i] = b;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			final PButton b2;
			try {
				b2 = new PButton(new Rectangle(Constants.SCREEN_WIDTH - Constants.scaleIntToWidth(300+(i*300)), Constants.scaleIntToHeight(200), Constants.scaleIntToWidth(200), Constants.scaleIntToWidth(500)),new PImage(ImageIO.read(new File("res/generalAssets/obama.png"))), false);
				enemies[i].setCoords(Constants.scaleIntToWidth(100+(i*300)), Constants.scaleIntToHeight(200));
				int sel = i;
				b2.addListener(new Runnable() {
					@Override
					public void run() {
						for(PButton enemy: enemySelection) {
							enemy.setHightlight(false);
						}
						enemySelect = sel + 1;
					}
				});
				enemySelection[i] = b2;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		button.addListener(new Runnable() {
			@Override
			public void run() {
				background.setFadeSpeed(40);
				background.setTint(255);
				background.setTargetTint(0);
				background.fadeWhite(true);
				background.addListener(new Runnable() {

					@Override
					public void run() {
						ScreenManager.setCurrentScreenByName("battle", window);
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
				background.addListener(new Runnable() {

					@Override
					public void run() {
						ScreenManager.setCurrentScreenByName("level", window);
					}
					
				});
			}
		});
	}

	public void draw(PApplet window) {
		background.draw(window);
		
		for(int i = 0; i < 5; i++) {
			select[i].draw(window);
		}
		for(int i =0; i < 3; i++) {
			selection[i].draw(window);
		}
		
		for(int i =0; i < 3; i++) {
			enemySelection[i].draw(window);
		}
		for(Character rev : revs) {
			rev.draw(window);
		}
		for(Character enemy : enemies) {
			enemy.draw(window);
		}
		if(win) {
			victory.draw(window);
			back.draw(window);
			
		}
		if(lose) {
			defeat.draw(window);
			back.draw(window);
			
		}
		if (window.mousePressed) {
			cursor.draw(window);
		} else {
			cursor.clearTrail();
		}
}
	

	@Override
	public void dispose() {
		background = null;
		cursor = null;
		button.removeListener();
		button = null;
	
	}

}
