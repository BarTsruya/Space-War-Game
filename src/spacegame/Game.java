package spacegame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Game extends Canvas implements Runnable{

	
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 800,HEIGHT = WIDTH/12 * 9;
	
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private static BufferedImage spriteSheet = null;
	private BufferedImage background = null,rock = null,ground = null,menuBack = null;
	
	private static int groundInt = 1200,val = 1;
	public static Thread thread;
	private boolean running = false;
	
	private int col1,col2,col3,colValue = 140,d4 = 2;
	private int d1,d2,d3;
	
	private Textures tex;
	private Player p;
	private Controller c;
	private HUD hud;
	
	private boolean isShooting = false;
	private Random r;
	
	public static enum STATE{
		Menu(),
		Game(),
		End();
	}
	
	public static STATE gameState = STATE.Menu;
	
	public Game(){
		r = new Random();
		col1 = r.nextInt(255);
		col2 = r.nextInt(255);
		col3 = r.nextInt(255);
		d1 = 1;
		d2 = -1;
		d3 = 1;
		new Window(WIDTH,HEIGHT,"Space_Game",this);
	}
	
	public void init(){
		requestFocus();
		BufferedImageLoader loader = new BufferedImageLoader();
		try{
			menuBack = loader.loadImage("menuBack.png");
			spriteSheet = loader.loadImage("sprite_sheet.png");
			background = loader.loadImage("background.png");
			rock = loader.loadImage("rock.png");
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		this.addKeyListener(new KeyInput(this));
		
		tex = new Textures(this);
		p = new Player(WIDTH/2-20,HEIGHT/2-50,tex);
		c = new Controller(this,tex,p);
		hud = new HUD(p);
		ground = background.getSubimage(0, groundInt, 800,600);
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop(){
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		init();
		long lastTime = System.nanoTime();
		double amountOFTicks = 80.0;
		double ns = 1000000000 / amountOFTicks;
		double delta = 0;
		long timer = System.currentTimeMillis(); 
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				delta--;
			}
			if(running)
				render();
			
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(8);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		///////////////////////////////////
		if(gameState == STATE.Menu){
			g.drawImage(menuBack,0,0,WIDTH,HEIGHT,this);
			g.setFont(new Font("ariel",10,80));
			g.setColor(Color.GRAY);
			g.drawString("Space - Game", 137,100);
			g.setFont(new Font("ariel",10,50));
			g.setColor(new Color(col1,col2,col3));
			g.drawString("Press Enter to start!", 185,350);
			
			g.setFont(new Font("ariel",10,20));
			g.setColor(new Color(20, 20, 14));
			g.drawString("CREATED BY BAR TSRUYA", 280, 555);
		}else if(gameState == STATE.Game){
			groundInt -= val;
			if(groundInt > 0)
				ground = background.getSubimage(0, groundInt, 800,600);
			else 
				groundInt = 1200;
			g.drawImage(ground, 0, 0, WIDTH,HEIGHT,this);
			
			p.render(g);
			c.render(g);
			hud.render(g);
			
			g.setFont(new Font("ariel",10,20));
			g.setColor(new Color(10, 10, 4));
			g.drawString("CREATED BY BAR TSRUYA", 280, 555);
		}else if(gameState == STATE.End){
			g.drawImage(menuBack,0,0,WIDTH,HEIGHT,this);
			
			colValue += d4;
			if(colValue <= 140 || colValue >= 240) d4*=-1;
			
			if(Controller.getEndScore() >= 25000){				
				g.setFont(new Font("ariel",10,70));
				g.setColor(new Color(20,colValue,20));
				g.drawString("You saved the universe!", 12,100);
			}else{
				g.setFont(new Font("ariel",10,80));
				g.setColor(new Color(colValue,20,20));
				g.drawString("Game - Over", 160,100);
			}
			
			
			g.setFont(new Font("ariel",10,50));
			g.setColor(Color.WHITE);
			if(Controller.getEndScore() < 100){
				g.drawString("Your Score: " + Controller.getEndScore(), 250, 320);
			}else if(Controller.getEndScore() >= 100 && Controller.getEndScore() < 1000){
				g.drawString("Your Score: " + Controller.getEndScore(), 230, 320);
			}else if(Controller.getEndScore() >= 1000 && Controller.getEndScore() < 10000){
				g.drawString("Your Score: " + Controller.getEndScore(), 210, 320);
			}else{
				g.drawString("Your Score: " + Controller.getEndScore(), 190, 320);
			}
			g.setColor(new Color(col1,col2,col3));
			g.drawString("Press Escape to quit!", 175,450);
			
			g.setFont(new Font("ariel",10,20));
			g.setColor(new Color(10, 10, 4));
			g.drawString("CREATED BY BAR TSRUYA", 280, 555);
		}
		
		///////////////////////////////////
		g.dispose();
		bs.show();
	}
	
	public void tick(){
		
		if(gameState == STATE.Menu){
			
			col1+=d1;
			col2+=d2;
			col3+=d3;
			if(col1 >= 255 || col1 <=0){
				d1*=-1;
				col1 = r.nextInt(255);
			}
			if(col2 >= 255 || col2 <=0){
				d2*=-1;
				col2 = r.nextInt(255);
			}
			if(col3 >= 255 || col3 <=0){
				d3*=-1;
				col3 = r.nextInt(255);
			}
			
		}else if(gameState == STATE.Game){
			p.tick();
			c.tick();
			hud.tick();
			int L = r.nextInt(100);
			if(L < 3) c.addRock(new Rock(tex));
			if(p.getHealth() <= 0) gameState = STATE.End;
		}else if(gameState == STATE.End){
			
			
			col1+=d1;
			col2+=d2;
			col3+=d3;
			if(col1 >= 255 || col1 <=0){
				d1*=-1;
				col1 = r.nextInt(255);
			}
			if(col2 >= 255 || col2 <=0){
				d2*=-1;
				col2 = r.nextInt(255);
			}
			if(col3 >= 255 || col3 <=0){
				d3*=-1;
				col3 = r.nextInt(255);
			}
			
		}
		
		
	}
	
	public static int Clamp(int var,int min,int max){
		if(var >= max) var = max;
		if(min >= var) var = min;
		return var;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(gameState == STATE.Menu){
			if(key == KeyEvent.VK_ENTER) gameState = STATE.Game;
		}else if(gameState == STATE.Game){
			
			if(key == KeyEvent.VK_RIGHT){
				p.setValX(5);
				tex.setPlayer(spriteSheet.getSubimage(195, 64, 60, 96));
			}else if(key == KeyEvent.VK_LEFT){
				p.setValX(-5);
				tex.setPlayer(spriteSheet.getSubimage(192, 160, 60, 90));
			}else if(key == KeyEvent.VK_UP){
				p.setValY(-5);
				val = 1;
			}else if(key == KeyEvent.VK_DOWN){
				p.setValY(5);
			}else if(key == KeyEvent.VK_SPACE && !isShooting){
				isShooting = true;
				if(p.getNumOfAmmo()>0){
					c.addBullet(new Bullet(p.getX()+10,p.getY(),1,tex));
					p.setNumOfAmmo(p.getNumOfAmmo()-1);
				}
			}else if(key == KeyEvent.VK_N){
				p.setNumOfAmmo(p.getNumOfAmmo()+1);
			}
			
		}else if(gameState == STATE.End){
			if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		}
		
	}
	
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_RIGHT){
			p.setValX(0);
			tex.setPlayer(spriteSheet.getSubimage(96,64, 55, 96));
		}else if(key == KeyEvent.VK_LEFT){
			p.setValX(0);
			tex.setPlayer(spriteSheet.getSubimage(96,64, 55, 96));
		}else if(key == KeyEvent.VK_UP){
			p.setValY(0);
			val = 1;
		}else if(key == KeyEvent.VK_DOWN){
			p.setValY(0);
		}else if(key == KeyEvent.VK_SPACE){
			isShooting = false;
		}
	}
	
	
	public static void main(String[] args) {
		new Game(); 
	}
	
	public BufferedImage getSpriteSheet(){
		return spriteSheet;
	}
	
	public BufferedImage getRock(){
		return rock;
	}
	
	


}