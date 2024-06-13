package spacegame;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Loot {
	
	private int x,y,type;
	private Textures tex;

	public Loot(int x,int y,int type,Textures tex){
		this.type = type;
		this.tex = tex;
		this.x = x;
		this.y = y;
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		if(type == 1)g.drawImage(tex.Medcit, x,y,null);
		else if(type == 2)g.drawImage(tex.Ammo, x,y,null);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x,y,32,32);
	}
	
}