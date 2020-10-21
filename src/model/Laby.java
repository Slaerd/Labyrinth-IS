package model;

import java.util.ArrayList;

/**
 * 
 * @author Kevin Ratovo
 * Lists preset labyrinths
 */
public class Laby {
	//public static final int SIZE = 10;
	public static final int WALLOBJECTMAXSIZE = 6;
	public static final String SQUARE = "square";
	public static final String RECTANGLE = "rectangle";
	public static final String CROSS = "cross";
	
	private static final boolean[][] TRAP1 = {	{false,false,false,false},
												{false,true ,true ,false},
												{false,true ,true ,false},
												{false,false,false,false}};
	
	private static final boolean[][] TRAP2 = {	{false,false,false,false},
												{false,true ,true ,true },
												{false,false,false,false},
												{false,true ,true ,true },
												{false,false,false,false}};
	
	private static final boolean[][] TRAP3 = {	{false,true ,true ,true ,false},
												{false,false,true ,false,false},
												{false,true ,false,true ,false},
												{false,true ,true ,true ,false}};
	
	private static final int[][] SQU = {		{1,0,0,4,0,0,4,0,0,1},
												{0,2,2,0,2,2,0,2,2,0},
												{0,2,2,0,2,2,0,2,2,0},
												{4,0,0,4,0,0,4,0,0,4},
												{2,2,2,0,2,2,0,2,2,2},
												{2,2,2,0,2,2,0,2,2,2},
												{4,0,0,4,0,0,4,0,0,4},
												{0,2,2,0,2,2,0,2,2,0},
												{0,2,2,0,2,2,0,2,2,0},
												{1,0,0,4,0,0,4,0,0,1}};

	private static final int[][] REC = {		{1,0,0,0,0,2,0,0,2,2,0,1},
												{2,2,0,0,2,2,0,0,2,0,0,0},
												{0,0,2,0,0,0,2,2,0,0,2,2},
												{0,0,2,2,0,0,0,2,0,0,0,2},
												{2,2,0,0,2,2,0,0,2,2,0,0},
												{0,2,0,0,2,0,0,0,0,2,0,0},
												{0,0,0,2,0,0,0,2,0,0,2,2},
												{1,0,2,2,0,0,2,2,0,0,0,1},
	};
	
	private static final int[][] CRO = {		{1,2,0,0,0,0,0,0,2,1},
												{2,2,2,0,0,4,0,2,2,2},
												{0,2,4,0,0,0,0,4,2,0},
												{0,0,0,0,3,3,0,0,0,4},
												{0,4,0,3,3,3,3,0,0,0},
												{0,0,0,3,3,3,3,0,4,0},
												{4,0,0,0,3,3,0,0,0,0},
												{0,2,4,0,0,0,0,4,2,0},
												{2,2,2,0,4,0,0,2,2,2},
												{1,2,0,0,0,0,0,0,2,1}};
	
	public ArrayList<ArrayList<Tile>> labySquare;
	
	public Laby() {
	}
	
	public static ArrayList<ArrayList<Tile>> getLaby(String labyName) {
		ArrayList<ArrayList<Tile>> laby = new ArrayList<ArrayList<Tile>>();
		ArrayList<Tile> columnBuffer;
		Tile tileBuffer;
		
		switch(labyName) {
			case Laby.SQUARE:
				for(int j = 0; j < SQU[0].length; j++) {			

					columnBuffer = new ArrayList<Tile>();
					
					for(int i = 0; i < SQU.length; i++) {
						if(SQU[i][j] == 4)
							tileBuffer = new Tile(0,j,i,Tile.TRAP);
						else
							tileBuffer = new Tile(SQU[i][j],j,i);
						columnBuffer.add(tileBuffer);
					}
					
					laby.add(columnBuffer);
				}
				return laby;
				
			case Laby.RECTANGLE:
				for(int j = 0; j < REC[0].length; j++) {			

					columnBuffer = new ArrayList<Tile>();
					
					for(int i = 0; i < REC.length; i++) {
						if(REC[i][j] == 4)
							tileBuffer = new Tile(0,j,i,Tile.TRAP);
						else
							tileBuffer = new Tile(REC[i][j],j,i);
						columnBuffer.add(tileBuffer);
					}
					
					laby.add(columnBuffer);
				}
				return laby;
				
			case Laby.CROSS:
				for(int j = 0; j < CRO[0].length; j++) {			

					columnBuffer = new ArrayList<Tile>();
					
					for(int i = 0; i < CRO.length; i++) {
						if(CRO[i][j] == 4)
							tileBuffer = new Tile(0,j,i,Tile.TRAP);
						else
							tileBuffer = new Tile(CRO[i][j],j,i);
						columnBuffer.add(tileBuffer);
					}
					
					laby.add(columnBuffer);
				}
				return laby;
		}
		return null;
	}
	
	public static ArrayList<ArrayList<Boolean>> getTrap(int n){
		
		ArrayList<ArrayList<Boolean>> trap = new ArrayList<ArrayList<Boolean>>();
		ArrayList<Boolean> columnBuffer;
		switch(n) {
			case 0:
				for(int j = 0; j < TRAP1[0].length; j++) {			
					
					columnBuffer = new ArrayList<Boolean>();
					
						for(int i = 0; i < TRAP1.length; i++) {
							columnBuffer.add(TRAP1[i][j]);
						}
					
					trap.add(columnBuffer);
				}
					return trap;
					
			case 1:
				for(int j = 0; j < TRAP2[0].length; j++) {			
				
					columnBuffer = new ArrayList<Boolean>();
				
						for(int i = 0; i < TRAP2.length; i++) {
							columnBuffer.add(TRAP2[i][j]);
						}
				
					trap.add(columnBuffer);
				}
				return trap;
				
			case 2:
				for(int j = 0; j < TRAP3[0].length; j++) {			
					
					columnBuffer = new ArrayList<Boolean>();
				
						for(int i = 0; i < TRAP3.length; i++) {
							columnBuffer.add(TRAP3[i][j]);
						}
				
					trap.add(columnBuffer);
			}
				return trap;
		}
		return null;

	}
}
