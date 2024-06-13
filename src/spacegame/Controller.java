package spacegame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Controller {

	private LinkedList<Bullet> b = new LinkedList<Bullet>();
	private LinkedList<Rock> r = new LinkedList<Rock>();
	private LinkedList<Enemy> e = new LinkedList<Enemy>();
	private LinkedList<Loot> l = new LinkedList<Loot>();
	
	private Random rand;
	private Game game;
	private Textures tex;
	private Player p;
	public static int score,endScore;
	
	Bullet tempBullet;
	Rock tempRock;
	Enemy tempEnemy;
	Loot tempLoot;
	
	public Controller(Game game,Textures tex,Player p){
		this.game = game;
		this.tex = tex;
		this.p = p;
		rand = new Random();
		score = 0;
	}
	
	
	public void tick(){
		score++;
		if(score<=4000){
			
			if(score%800 == 0)
				addEnemy(new Enemy(tex,this));
			
		}else if(score <= 15000){
			
			if(score%550 == 0)
				addEnemy(new Enemy(tex,this));
			
		}else if(score < 25000){
			
			if(score%300 == 0)
				addEnemy(new Enemy(tex,this));
			
		}else{
			Game.gameState = Game.STATE.End;
			endScore = score;
		}
					
		
		for(int i=0;i<b.size();i++){
			tempBullet = b.get(i);
			if(tempBullet.getY() < 0){
				removeBullet(tempBullet);
			}
			if(tempBullet.getType() == 2){
				if(tempBullet.getBounds().intersects(p.getBounds())){
					p.setHealth(p.getHealth()-25);
					removeBullet(tempBullet);
				}	
			}
			tempBullet.tick();
		}
		
		for(int i=0;i<r.size();i++){
			tempRock = r.get(i);
			if(tempRock.isOut()){
				removeRock(tempRock);
			}
			for(int j=0;j<b.size();j++){
				tempBullet = b.get(j);
				
				if(tempBullet.getBounds().intersects(tempRock.getBounds())){
					if(tempBullet.getType() == 2){
						tempRock.setIsEnemy(true);
						removeBullet(tempBullet);
						//System.out.println("enemyMissile intersecs rock");
					}else{
						removeRock(tempRock);
						removeBullet(tempBullet);	
					}
				}
				
			}
			
			for(int k=0;k<e.size();k++){
				tempEnemy = e.get(k);
				if(tempEnemy.getLife() <= 0)removeEnemy(tempEnemy);
				for(int l=0;l<b.size();l++){
					tempBullet = b.get(l);
					if(tempBullet.getType() == 1){
						if(tempBullet.getBounds().intersects(tempEnemy.getBounds())){
							removeBullet(tempBullet);
							tempEnemy.setLife(tempEnemy.getLife()-20);
						}
					}
				}
				/*for(int h=0;h<r.size();h++){
					tempRock = r.get(h);
					if(tempEnemy.getBounds().intersects(tempRock.getBounds())){
						removeRock(tempRock);
						tempEnemy.setLife(tempEnemy.getLife()-10);
					}
				}*/
				
				tempEnemy.tick();
			}
			
			if(p.getBounds().intersects(tempRock.getBounds())){
				p.setHealth(p.getHealth()-20);
				removeRock(tempRock);
			}
			
			if(tempRock.getIsEnemy()){
				tempRock.setValX((double)(p.getX()-tempRock.getX())/50.0);
				tempRock.setValY((double)(p.getY()-tempRock.getY())/50.0);
			}
			
			tempRock.tick();
		}
		
		for(int u=0;u<l.size();u++){
			tempLoot = l.get(u);
			if(p.getBounds().intersects(tempLoot.getBounds())){
				if(tempLoot.getType() == 1){
					p.setHealth(p.getHealth()+25);
				}else if(tempLoot.getType() == 2){
					p.setNumOfAmmo(p.getNumOfAmmo()+50);
				}
				removeLoot(tempLoot);
			}
		}
		
		if(p.getHealth() <= 0){
			Game.gameState = Game.STATE.End;
			endScore = score;
		}
	}
	
	public void render(Graphics g){
		for(int i=0;i<l.size();i++){
			tempLoot = l.get(i);
			tempLoot.render(g);
		}
		
		for(int i=0;i<b.size();i++){
			tempBullet = b.get(i);
			tempBullet.render(g);
		}
		
		for(int i=0;i<r.size();i++){
			tempRock = r.get(i);
			tempRock.render(g);
		}
		
		for(int i=0;i<e.size();i++){
			tempEnemy = e.get(i);
			tempEnemy.render(g);
		}
		
		
	}
	
	public void addBullet(Bullet block){
		b.add(block);
	}
	
	public void removeBullet(Bullet block){
		b.remove(block);
	}
	
	
	public void addRock(Rock block){
		r.add(block);
	}
	
	public void removeRock(Rock block){
		int prob;
		if(!block.getIsEnemy()){
			prob = rand.nextInt(100);
			
			if(prob <= 22) l.add(new Loot((int)block.getX()+10,(int)block.getY()+5,1,tex));
			else if(prob >= 93) l.add(new Loot((int)block.getX()+10,(int)block.getY()+5,2,tex));
			
		}
		r.remove(block);
	}
	
	public void addEnemy(Enemy block){
		e.add(block);
	}
	
	public void removeEnemy(Enemy block){
		e.remove(block);
	}
	
	public void addLoot(Loot block){
		l.add(block);
	}
	
	public void removeLoot(Loot block){
		l.remove(block);
	}


	public static int getEndScore() {		
		return endScore;
	}


	public static void setEndScore(int endScore) {
		Controller.endScore = endScore;
	}


	public static int getScore() {
		return score;
	}


	public static void setScore(int score) {
		Controller.score = score;
	}


	
	
	
}