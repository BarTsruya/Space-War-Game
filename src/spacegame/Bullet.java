package spacegame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Bullet {

	
	private int x,y;
	private int type;
	private Textures tex;
	
	public Bullet(int x,int y,int type,Textures tex){
		this.x = x;
		this.y = y;
		this.type = type;
		this.tex = tex;
		
	}
	
	public void tick(){
		
		if(type == 1)y -= 10;
		else if(type == 2)y+=10;
	}
	
	public void render(Graphics g){
		if(type == 1)g.drawImage(tex.missile, x,y,null);
		else if(type == 2)g.drawImage(tex.enemyMissile, x,y,null);
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
	
	public Rectangle getBounds(){
		return new Rectangle(x,y,32,32);
		
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}