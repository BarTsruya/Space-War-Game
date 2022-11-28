package spacegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player {

	
	private int x,y;
	private int valX,valY;
	private int Health;
	private int NumOfAmmo;
	private Textures tex;
	
	public Player(int x,int y,Textures tex){
		this.x = x;
		this.y = y;
		this.tex = tex;
		
		Health = 250;
		NumOfAmmo = 30;
		valX = 0;
		valY = 0;
		
	}
	
	public void tick(){
		x += valX;
		y += valY; 
		Health = Game.Clamp(Health, 0, 250);
		
		x = Game.Clamp(x, -10, Game.WIDTH-60);
		y = Game.Clamp(y, 0, Game.HEIGHT-100);
	}
	
	public void render(Graphics g){
		g.drawImage(tex.Player, (int)x, (int)y, null);
		g.setColor(Color.WHITE);
		g.drawString("Ammo = "+NumOfAmmo, 30, 100);
	}

	public int getValX() {
		return valX;
	}

	public void setValX(int valX) {
		this.valX = valX;
	}

	public int getValY() {
		return valY;
	}

	public void setValY(int valY) {
		this.valY = valY;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHealth() {
		return Health;
	}

	public void setHealth(int health) {
		Health = health;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x+10,y+10,30,70);
	}

	public int getNumOfAmmo() {
		return NumOfAmmo;
	}

	public void setNumOfAmmo(int numOfAmmo) {
		NumOfAmmo = numOfAmmo;
	}
	
	
	
}