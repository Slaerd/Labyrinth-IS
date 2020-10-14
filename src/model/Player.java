package model;

public class Player {
	public static final int MOVEMENT = 2;
	public static final int ACTION = 2;
	
	private String name;
	protected int x;
	protected int y;
	private int lives = 2;
	private int action = 2;
	private int cible;
	private int bonusAction = 0;
	private int bonusMovement = 0;
	
	public Player(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	public void moveTo(int x, int y) {
		if(action != 0) {
			this.x = x;
			this.y = y;
		}
	}
	
	public void spendAction() {
		action--;
	}
	
	public void regainActions() {
		action = ACTION + bonusAction;
	}
	
	public int getMovement() {
		return MOVEMENT + bonusMovement;
	}
}

