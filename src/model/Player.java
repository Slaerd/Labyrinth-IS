package model;

public class Player {
	public static final int MOVEMENT = 2;
	public static final int ACTION = 3;
	public static final int NOPLAYER = -1;
	
	private String name;
	private int number;
	protected final int xSpawn; //spawn position
	protected final int ySpawn;
	protected int x;
	protected int y;
	protected final int target; //player to kill
	private int lives = 2;
	private int action = ACTION;
	private int bonusAction = 0;
	private int bonusMovement = 0;
	private boolean trapped = false;
	
	public Player(String name, int x, int y, int number, int target) {
		this.name = name;
		xSpawn = x;
		ySpawn = y;
		
		this.target = target;
		this.x = x;
		this.y = y;
	}
	
	public String getName() {
		return name;
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public void moveTo(int x, int y) {
		if(action != 0) {
			this.x = x;
			this.y = y;
		}
	}
	
	public int getMovement() {
		return MOVEMENT + bonusMovement;
	}
	
	public void setBonusMovement(int n) {
		bonusMovement = n;
	}
	
	public void spendAction() {
		action--;
	}
	
	public int getActions() {
		return action;
	}
	
	public void setBonusAction(int n) {
		bonusAction = n;
	}
	
	public void regainActions() {
		action = ACTION + bonusAction;
	}
	
	public void spendLife() {
		lives--;
	}
	
	public int getLives() {
		return lives;
	}
	
	public boolean equals(Player p) {
		return p.name == this.name;
	}

	public boolean isTrapped() {
		return trapped ;
	}

	public void setTrapped(boolean b) {
		trapped = b;
	}

}

