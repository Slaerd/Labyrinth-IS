package model;

import java.util.ArrayList;

public class Laby {
	public static final int SIZE = 10;
	public static final int WALLOBJECTMAXSIZE = 4;
	public static final String SQUARE = "square";
	private static final int[][] MODSQUARE = {	{0,0,0,0,0,0,0,0,0,0},
												{1,1,1,1,1,1,1,1,1,1},
												{0,1,0,0,0,0,0,0,1,0},
												{1,1,1,1,1,1,1,1,1,1},
												{0,0,0,0,0,0,0,0,0,0},
												{1,1,1,1,1,1,1,1,1,1},
												{0,0,0,0,0,0,0,0,0,0},
												{1,1,1,1,1,1,1,1,1,1},
												{0,0,0,0,0,1,0,0,0,0},
												{1,1,1,1,1,1,1,1,1,1}};
	
	public ArrayList<ArrayList<Tile>> labySquare;
	
	public Laby() {
	}
	
	public static ArrayList<ArrayList<Tile>> getSquare() {
		ArrayList<ArrayList<Tile>>labySquare = new ArrayList<ArrayList<Tile>>();
		ArrayList<Tile> columnBuffer;
		Tile tileBuffer;
		
		for(int i = 0; i < SIZE; i++) {
			columnBuffer = new ArrayList<Tile>();
			
			for(int j = 0; j < SIZE; j++) {
				tileBuffer = new Tile(MODSQUARE[j][i],i,j);
				columnBuffer.add(tileBuffer);
			}
			
			labySquare.add(columnBuffer);
		}
		return labySquare;
	}
}
