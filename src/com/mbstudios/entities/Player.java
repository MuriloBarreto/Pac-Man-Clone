package com.mbstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.mbstudios.main.Game;
import com.mbstudios.world.Camera;
import com.mbstudios.world.World;

public class Player extends Entity{
	
	public boolean right,up,left,down;
	
	public BufferedImage sprite_left; 
	
	public int lastDir = 1;

	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		sprite_left = Game.spritesheet.getSprite(48, 0, 16, 16);
	}
	
	public void tick(){
		depth = 1;
		if(right && World.isFree((int)(x+speed),this.getY())) {
			x+=speed;
			lastDir = 1;
		}
		else if(left && World.isFree((int)(x-speed),this.getY())) {
			x-=speed;
			lastDir = -1;
		}
		if(up && World.isFree(this.getX(),(int)(y-speed))){
			y-=speed;
		}
		else if(down && World.isFree(this.getX(),(int)(y+speed))){
			y+=speed;
		}
		
		verificarPegaFruta();
		
		if(Game.frutas_atual == Game.frutas_contagem) {
			World.restartGame("/level1.png");
			System.out.println("Ganhou");
		}
	}
	
	public void verificarPegaFruta() {
		for(int i = 0; i< Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Fruta) {
				if(Entity.isColidding(this, current)) {
					Game.frutas_atual++;
					Game.entities.remove(i);
					return;
				}
			}
		}
	}

	public void render(Graphics g) {
		if(lastDir == 1) {
			super.render(g);
		}else {
			g.drawImage(sprite_left,this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
	}


}
