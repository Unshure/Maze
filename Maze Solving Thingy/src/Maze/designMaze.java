package Maze;

	class Node{
		boolean isVisited = false;
		boolean walls[] = new boolean[4] ;
		int posx, posy;
		
		public Node(boolean left, boolean right, boolean down, boolean up, int x, int y){
			walls[0] = left;
			walls[1] = right;
			walls[2] = down;
			walls[3] = up;
			posx = x;
			posy = y;
		}
		public void setWall(int index, boolean wall){
			walls[index] = wall;
		}
		public boolean getWall(int index){
			return walls[index];
		}
		public void visit(){
			isVisited = true;
		}
		public int getX(){
			return posx;
		}
		public int getY(){
			return posy;
		}
		public boolean isVisited(){
			return isVisited;
		}
		public void unvisit(){
			isVisited = false;
		}
	}

public class designMaze {
	
	Node[][] maze;
	
	public designMaze(int x, int y){
		maze = new Node[x][y];

		for(int i = 0; i < x; i++){
			for(int j = 0; j < y;j++){
				maze[i][j] = new Node(false,false,false,false,i,j);		
			}
		}
		
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y ; j++){
				for(int k = 0; k < 4; k++){
					maze[i][j].setWall(k,false);
				}
				if(i == 0){
					maze[i][j].setWall(0,true);
				}
				if(i == 15){
					maze[i][j].setWall(1,true);
				}
				if(j == 0){
					maze[i][j].setWall(3,true);
				}
				if(j == 15){
					maze[i][j].setWall(2,true);
				}
			}
		}

			
		maze = stepone(maze);
		maze = steponepointfive(maze);
		while(!testMaze()){
		maze = stepsix(maze);
		maze = steptwopointfive(maze);
		maze = steptwo(maze);
		maze = steponepointfive(maze);
		}
	
		maze = stepfive(maze);
		
		unvisit();
		
		
	}
	public Node[][] stepone(Node maze[][]){
		
		for(int i = 0; i < maze.length; i++){
			for(int j = 0; j< maze[0].length; j++){
				for(int k = 0; k < 4; k++){
					if((int)(Math.random() *4) == 0){  // 25% chance
						maze[i][j].setWall(k,true);
					}
				}
			}
		}
		return maze;
	}
	
	public boolean testMaze(){
	dfs(maze[0][0]);
		
	for(int i = 0; i < maze.length; i++){
		for(int j = 0; j< maze[0].length; j++){
			if(maze[i][j].isVisited() == false){
				return false;
			}
		}
	}
	return true;
	}
	
	public Node[][] steponepointfive(Node maze[][]){
		for(int i = 0; i < maze.length; i++){
			for(int j = 0; j< maze[0].length; j++){
				for(int k = 0; k < 4; k++){
					if(!((i == 0 && k == 0) || ( i == 15 && k == 1) || (j == 0 && k == 3) || (j == 15 && k == 2))){
					if(maze[i][j].getWall(k) == true){
						switch(k) {
						case 0:
							maze[i-1][j].setWall(1,true);
							break;
						case 1:
							maze[i+1][j].setWall(0,true);
							break;
						case 2:
							maze[i][j+1].setWall(3,true);
							break;
						case 3:
							maze[i][j-1].setWall(2,true);
							break;
							
							}
						}
					}
				}
			}
		}
		return maze;
	}
	public Node[][] steptwopointfive(Node maze[][]){
		for(int i = 0; i < maze.length; i++){
			for(int j = 0; j< maze[0].length; j++){
				for(int k = 0; k < 4; k++){
					if(!((i == 0 && k == 0) || ( i == 15 && k == 1) || (j == 0 && k == 3) || (j == 15 && k == 2))){
					if(maze[i][j].getWall(k) == false){
						switch(k) {
						case 0:
							maze[i-1][j].setWall(1,false);
							break;
						case 1:
							maze[i+1][j].setWall(0,false);
							break;
						case 2:
							maze[i][j+1].setWall(3,false);
							break;
						case 3:
							maze[i][j-1].setWall(2,false);
							break;
							
							}
						}
					}
				}
			}
		}
		return maze;
	}
	public Node[][] steptwo(Node maze[][]){
		
		for(int i = 0; i < maze.length; i++){
			for(int j = 0; j< maze[0].length; j++){
				if(!(maze[i][j].getWall(1) || maze[i][j].getWall(2) || maze[i + 1][j + 1].getWall(0) || maze[i + 1][j + 1].getWall(3))){
					int R = ((int)(Math.random() * 4));
						switch(R) {
						case 0:
							maze[i][j].setWall(1,true);
							break;
						case 1:
							maze[i][j].setWall(2,true);
							break;
						case 2:
							maze[i+1][j+1].setWall(0,true);
							break;
						case 3:
							maze[i+1][j+1].setWall(3,true);
							break;
					}
				}
			}		
		}
		return maze;
	}
	
	public Node[][] stepfour(Node maze[][]){
		
		dfs(maze[0][0]);
		
		for(int i = 0; i < maze.length; i++){
			for(int j = 0; j< maze[0].length; j++){
				if(maze[i][j].isVisited() == false){
					if(i == 0){
						maze[i][j].setWall(1, false);
						dfs(maze[i][j]);
					}
					else if(i == 15){
						maze[i][j].setWall(0, false);
						dfs(maze[i][j]);
					}
					else if( j == 0){
						maze[i][j].setWall(2, false);
						dfs(maze[i][j]);
					}
					else if( j == 15){
						maze[i][j].setWall(3, false);
						dfs(maze[i][j]);
					}
					else{
						int R = ((int)(Math.random() * 4));
						switch(R) {
						case 0:
							maze[i][j].setWall(0,false);
							break;
						case 1:
							maze[i][j].setWall(1,false);
							break;
						case 2:
							maze[i][j].setWall(2,false);
							break;
						case 3:
							maze[i][j].setWall(3,false);
							break;
					}
						dfs(maze[i][j]);
					}
				}
				
			}
		}
				
		return maze;
	}
	
	boolean finished;
	
	public Node[][] stepsix(Node maze[][]){
		
		do{
			finished = true;
			unvisit();
			dfs(maze[0][0]);
		
		for(int i = 0; i < maze.length; i++){
			for(int j = 0; j< maze[0].length; j++){
				if(maze[i][j].isVisited() == false){
					finished = false;
					newdfs(maze[i][j]);
				}
			}
		}
		maze = steptwopointfive(maze);
		
		
		}while(finished = false);
		
		return maze;
	}
	
	
	public Node newdfs(Node node){
		node.visit();
		
		//left
		if(node.getWall(0) == false){
				if(maze[node.getX()-1][node.getY()].isVisited() == false){
					dfs(maze[node.getX()-1][node.getY()]);
				}
			}
		else if( node.getX() != 0 && maze[node.getX()-1][node.getY()].isVisited() == true){
			node.setWall(0, false);
		}
		
		//right
		if(node.getWall(1) == false){
				if(maze[node.getX()+1][node.getY()].isVisited() == false){
					dfs(maze[node.getX()+1][node.getY()]);
				}
			}
		else if( node.getX() != 15 && maze[node.getX()+ 1][node.getY()].isVisited() == true){
			node.setWall(1, false);
		}
		
		//down
		if(node.getWall(2) == false){
				if(maze[node.getX()][node.getY()+1].isVisited() == false){
					dfs(maze[node.getX()][node.getY()+1]);
				}
			}
		else if( node.getY() != 15 && maze[node.getX()][node.getY()+1].isVisited() == true){
			node.setWall(2, false);
		}

		//up
		if(node.getWall(3) == false){
				if(maze[node.getX()][node.getY()-1].isVisited() == false){
					dfs(maze[node.getX()][node.getY()-1]);
				}
			}
		else if( node.getY() != 0 && maze[node.getX()][node.getY()-1].isVisited() == true){
			node.setWall(3, false);
		}
		
		maze = steptwopointfive(maze);
		return node;
		
	}
	
	
	
	
	
	
	public Node dfs(Node node){
		node.visit();
		
		//left
		if(node.getWall(0) == false){
				if(maze[node.getX()-1][node.getY()].isVisited() == false){
					dfs(maze[node.getX()-1][node.getY()]);
				}
			}
		
		//right
		if(node.getWall(1) == false){
				if(maze[node.getX()+1][node.getY()].isVisited() == false){
					dfs(maze[node.getX()+1][node.getY()]);
				}
			}
		
		//down
		if(node.getWall(2) == false){
				if(maze[node.getX()][node.getY()+1].isVisited() == false){
					dfs(maze[node.getX()][node.getY()+1]);
				}
			}

		//up
		if(node.getWall(3) == false){
				if(maze[node.getX()][node.getY()-1].isVisited() == false){
					dfs(maze[node.getX()][node.getY()-1]);
				}
			}
		return node;
		
	}
	public Node[][] getMaze(){
		return maze;
	}
	public Node[][] stepfive(Node maze[][]){
		
		maze[7][7].setWall(0, true);
		maze[7][7].setWall(3, true);
		maze[7][8].setWall(0, true);
		maze[7][8].setWall(2, true);
		maze[8][7].setWall(1, true);
		maze[8][7].setWall(3, true);
		maze[8][8].setWall(1, true);
		maze[8][8].setWall(2, true);
		maze = steponepointfive(maze);
		
		maze[7][7].setWall(1, false);
		maze[7][7].setWall(2, false);
		maze[7][8].setWall(1, false);
		maze[7][8].setWall(3, false);
		maze[8][7].setWall(0, false);
		maze[8][7].setWall(2, false);
		maze[8][8].setWall(0, false);
		maze[8][8].setWall(3, false);
		
		int R = ((int)(Math.random() * 8));
		switch(R) {
		case 0:
			maze[7][7].setWall(0, false);
			break;
		case 1:
			maze[7][7].setWall(3, false);
			break;
		case 2:
			maze[7][8].setWall(0, false);
			break;
		case 3:
			maze[7][8].setWall(2, false);
			break;
		case 4:
			maze[8][7].setWall(1, false);
			break;
		case 5:
			maze[8][7].setWall(3, false);
			break;
		case 6:
			maze[8][8].setWall(1, false);
			break;
		case 7:
			maze[8][8].setWall(2, false);
			break;
		}	
		maze = steptwopointfive(maze);

		return maze;
	}
	public void unvisit(){
		for(int i = 0; i < maze.length; i++){
			for(int j = 0; j< maze[0].length; j++){
				maze[i][j].unvisit();
			}
		}
	}
	
}
