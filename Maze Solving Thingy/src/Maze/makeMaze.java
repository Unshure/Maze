package Maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.Stack;


public class makeMaze extends JPanel 
	implements ActionListener {

	private Timer timer;
	private JLabel statusbar;
	private boolean isPaused = false;
	private boolean isMoved = true;
	boolean startDFS = true;
	boolean backtrack = false;
	private int cx = 0, cy = 0, ncx = 0, ncy = 0;
	int n = 41;
	int mSize = 16;
	designMaze maze;
	makeMaze parent = this;
	Stack stkgo = new Stack<Node>();
	Stack stkback = new Stack<Node>();

	public makeMaze(RunMaze parent) {

		initMaze(parent);
	}

	private void initMaze(RunMaze parent) {
    
		maze = new designMaze(mSize,mSize);
		
	    setFocusable(true);
		
		timer = new Timer(50, this);
		timer.start(); 
		
		
		statusbar =  parent.getStatusBar();
		
		addKeyListener(new TAdapter());
		
       
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(startDFS){
			search(maze.getMaze()[cx][cy]);
			travel();
			System.out.println("Am doing things");
		}
	}
	
	
	public void search(Node node){
		node.visit();
		
		System.out.println("VISITED");
		//left
		if(node.getWall(0) == false){
			if(maze.getMaze()[node.getX()-1][node.getY()].isVisited() == false){
				stkgo.push(maze.getMaze()[node.getX()-1][node.getY()]);
				System.out.println("push left");
				}
		}
				//up
		if(node.getWall(3) == false){
			if(maze.getMaze()[node.getX()][node.getY()-1].isVisited() == false){
				stkgo.push(maze.getMaze()[node.getX()][node.getY()-1]);
				System.out.println("push up");
				}
		}
		//right
		if(node.getWall(1) == false){
			if(maze.getMaze()[node.getX()+1][node.getY()].isVisited() == false){
				stkgo.push(maze.getMaze()[node.getX()+1][node.getY()]);
				System.out.println("push right");
				}
		}
		//down
		if(node.getWall(2) == false){
			if(maze.getMaze()[node.getX()][node.getY()+1].isVisited() == false){
				stkgo.push(maze.getMaze()[node.getX()][node.getY()+1]);
				System.out.println("push down");
				}
		}
		
	}

	
	public void travel(){
		Node current = maze.getMaze()[cx][cy];
		if(stkgo.empty()){
			Node temp = (Node) stkback.pop();
			moveTo(current,temp);
		}		
		else if(canTravelTo(current,(Node) stkgo.peek())){

			Node temp = (Node)stkgo.pop();
			System.out.println("Can travel to");
			stkback.push(current);
			moveTo(current, temp);
		}
		else{
			Node temp = (Node) stkback.pop();
			moveTo(current,temp);

		}

	}
	public boolean canTravelTo(Node start, Node end){
		System.out.print((start.getX() - end.getX()) + "   " + (start.getY() - end.getY()));
		if((Math.abs(start.getX() - end.getX()) == 1 || Math.abs(start.getY() - end.getY()) == 1) && Math.abs(start.getY() - end.getY()) != Math.abs(start.getX() - end.getX())){
			System.out.println("GOT THORUGH");
			int xDiff = start.getX() - end.getX();
			int yDiff = start.getY() - end.getY();
			if(xDiff == 1){
				if(!start.getWall(0)){
					return true;
				}
				
			}
			if(xDiff == -1){
				if(!start.getWall(1)){
					return true;
				}
				
			}
			if(yDiff == 1){
				if(!start.getWall(3)){
					return true;
				}
				
			}
			if(yDiff == -1){
				if(!start.getWall(2)){
					return true;
				}
				
			}
		}
		return false;
	}
	public void moveTo(Node start, Node end){
		if(start.getX() - end.getX() == 1){
			move("left");
		}
		if(start.getX() - end.getX() == -1){
			move("right");
		}
		if(start.getY() - end.getY() == 1){
			move("up");
		}
		if(start.getY() - end.getY() == -1){
			move("down");
		}
	}
	
	
	
	
	


	public void start()  {

		if (isPaused)
			return;

		timer.start();
	}

	private void pause()  {
		
		isPaused = !isPaused;
		
		if (isPaused) {
    
			timer.stop();
			statusbar.setText("paused");
		} else {
			
			timer.start();
		}
		
		repaint();
	}
	
	private void doDrawing(Graphics g) {
		
		
		 Color colors[] = { new Color(255, 0, 0), new Color(0, 0, 255), 
		            new Color(0, 255, 0), new Color(0,0,0), new Color(255,255,255)};
		g.setColor(colors[3]);
		g.drawLine(0, 0, 0, 657);
		g.drawLine(0, 0, 657, 0);
		g.drawLine(0, 657, 657, 657);
		g.drawLine(657, 0, 657, 657);
		
		
		for(int x = 0; x < mSize; x++){
			for(int y = 0; y < mSize; y++){
				
				for(int k = 0; k <4; k++){
				if(maze.getMaze()[x][y].walls[k]){
					 g.setColor(colors[1]);
				}
				else{
					g.setColor(colors[4]);
				}
				switch(k) {
				case 0:
				g.drawLine((x * n)+1, (y*n)+1, (x * n)+ 1, (y*n)+ n);
					break;
				case 1:
				g.drawLine((x * n)+ n, (y*n)+ 1, (x * n)+ n, (y*n)+ n);	
					break;
				case 2:
				g.drawLine((x * n)+ 1, (y*n)+ n, (x * n)+  n, (y*n)+ n);
					break;
				case 3:
				g.drawLine((x * n)+1, (y*n)+1, (x * n)+ n, (y*n)+ 1);
					break;
					
					}
			}
		}
		if(isMoved){
		drawMove(g,colors);
		}
		}
		System.out.println("CX: " + cx + " CY: " + cy);
	}
	

	public void drawMove(Graphics g, Color colors[]){
		
		if(ncy <0 || ncx <0 || ncx > 15 || ncy > 15){
			ncx = cx;
			ncy = cy;
		}
		
		
		g.setColor(colors[2]);
		g.fillRect((ncx * n)+11, (ncy * n)+11, 20, 20);
		g.setColor(colors[3]);
		g.drawRect((ncx * n)+11, (ncy * n)+11, 20, 20);
		cx = ncx;
		cy = ncy;
		isMoved = !isMoved;
		
	}
	
	@Override
	public void paintComponent(Graphics g) { 
		
		super.paintComponent(g);
		doDrawing(g);

	}
	
	public void move(String m){
		if(m.equals("left")){
			if(!maze.getMaze()[cx][cy].getWall(0)){
				ncx = cx - 1;
				System.out.println("LEFT");
			}
		}
		if(m.equals("right")){
			if(!maze.getMaze()[cx][cy].getWall(1)){
				ncx = cx + 1;
				System.out.println("RIGHT");
			}
		}
		if(m.equals("down")){
			if(!maze.getMaze()[cx][cy].getWall(2)){
				ncy = cy +1;
				System.out.println("DOWN");
			}
		}
		if(m.equals("up")){
			if(!maze.getMaze()[cx][cy].getWall(3)){
				ncy = cy - 1;
				System.out.println("UP");
			}
		}
		isMoved = true;
		repaint();
	}

	
	class TAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			
			int keycode = e.getKeyCode();
			
			if (keycode == 'p' || keycode == 'P') {
				pause();
				return;
			}

			if (isPaused)
				return;

			switch (keycode) {
         
			case KeyEvent.VK_LEFT:
				move("left");
				break;
         
			case KeyEvent.VK_RIGHT:
				move("right");
				break;
         
			case KeyEvent.VK_DOWN:
				move("down");
				break;
         
			case KeyEvent.VK_UP:
				move("up");
				break;
         
			case KeyEvent.VK_SPACE:
				startDFS = !startDFS;
				break;
         
			case 'd':

				break;
         
			case 'D':

				break;
			}
		}
	}
	}