package spacegame;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Rock {
	
	private Random r;
	private Textures tex;
	
	private double x,y;
	private double valX,valY;
	private Boolean isEnemy = false;
	
	public Rock(Textures tex){
		this.tex = tex;
		r = new Random();
		x = r.nextInt(Game.WIDTH-30);
		y = -64;
		valX = -3+r.nextInt(6);
		valY = 2+r.nextInt(3);
		while(valX == 0 && valY == 0){
			valX = -3+r.nextInt(6);
			valY = -3+r.nextInt(6);
		}
		
	}
	
	public void tick(){
		x += valX;
		y += valY;
		
	}
	
	public void render(Graphics g){
		if(!isEnemy)g.drawImage(tex.rock, (int)x,(int)y,null);
		else g.drawImage(tex.enemyRock, (int)x,(int)y,null);
	}
	
	public boolean isOut(){
		if(x<-64 || x>Game.WIDTH || y<-64 || y>Game.HEIGHT) return true;
		else return false;
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x,(int)y,64,64);
	}

	public Boolean getIsEnemy() {
		return isEnemy;
	}

	public void setIsEnemy(Boolean isEnemy) {
		this.isEnemy = isEnemy;
	}

	public double getValX() {
		return valX;
	}

	public void setValX(double valX) {
		this.valX = valX;
	}

	public double getValY() {
		return valY;
	}

	public void setValY(double valY) {
		this.valY = valY;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	
	
	
}
