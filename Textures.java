package spacegame;

import java.awt.image.BufferedImage;

public class Textures {
	
	
	private SpriteSheet ss;
	public BufferedImage Player,missile,Enemy,enemyMissile,rock,enemyRock,Medcit,Ammo;
	
	public Textures(Game game){
		
		ss = new SpriteSheet(game.getSpriteSheet());
		
		getTextures();
	}
	
	private void getTextures(){
		Player = ss.grabImage(4, 3, 55, 96);
		missile = ss.grabImage(3, 1, 32, 32);
		Enemy = ss.grabImage(1, 6, 157, 70);
		enemyMissile = ss.grabImage(3, 2, 32, 32);
		rock = ss.grabImage(4, 1, 64, 60);
		enemyRock = ss.grabImage(6, 1, 80, 58);
		Medcit = ss.grabImage(2, 1, 32, 32);
		Ammo = ss.grabImage(2, 2, 32, 32);
	}

	public BufferedImage getPlayer() {
		return Player;
	}

	public void setPlayer(BufferedImage player) {
		Player = player;
	}
	
	

}