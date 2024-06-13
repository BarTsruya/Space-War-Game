package spacegame;

import java.awt.Color;
import java.awt.Graphics;

public class HUD {

	private int HEALTH;
	private Player p;
	private int score;
	
	public HUD(Player p){
		this.p = p;
		HEALTH = p.getHealth();
	}
	
	public void render(Graphics g){
		HEALTH = Game.Clamp(HEALTH, 0, 250);
		g.setColor(Color.GRAY);
		g.fillRect(30, 30, 250, 30);
		g.setColor(new Color(75,HEALTH,0));
		g.fillRect(30, 30, HEALTH, 30);
		g.setColor(Color.WHITE);
		g.drawRect(30, 30, 250, 30);
		
		g.drawString("Score: "+score,30,80);
		
	}
	
	public void tick(){
		HEALTH = p.getHealth();
		HEALTH = Game.Clamp(HEALTH, 0, 250);
		score = Controller.getScore();
	}

	public int getHEALTH() {
		return HEALTH;
	}

	public void setHEALTH(int health) {
		HEALTH = health;
	}
	
	
}