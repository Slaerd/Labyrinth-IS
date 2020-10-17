package model;

import java.util.ArrayList;

public class Laby {
	public static final int SIZE = 10;
	public static final String SQUARE = "square";
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
				if(j%2 == 0)
					tileBuffer = new Tile(Tile.FLOOR,i,j);
				else
					tileBuffer = new Tile(Tile.WALL,i,j);
				columnBuffer.add(tileBuffer);
			}
			
			labySquare.add(columnBuffer);
		}
		return labySquare;
	}
}
