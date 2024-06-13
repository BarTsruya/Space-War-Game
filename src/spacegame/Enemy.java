package spacegame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Enemy {

	private int x,y;
	private double valX;
	private int p,life;
	private Random r;
	private Textures tex;
	private Controller c;
	
	public Enemy(Textures tex,Controller c){
		this.tex = tex;
		this.c = c;
		
		r = new Random();
		x = r.nextInt(Game.WIDTH-170-150);
		y = -20;
		valX = 1;
		life = 100;
		
		
	}
	
	public void tick(){
		
		if(y < 100)y++;
		else if(y == 100){
			if(x>Game.WIDTH-180 || x<0) valX*=-1;
			x += (int)valX;
		}
		
		p = r.nextInt(300);
		if(p == 1)Shoot(new Bullet(x+70,y,2,tex));
	}
	
	public void render(Graphics g){
		g.drawImage(tex.Enemy, x, y, null);
	}
	
	public void Shoot(Bullet b){
		c.addBullet(b);
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x,y,160,56);
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
	
	
}