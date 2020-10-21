package view;

import controller.Controller;
import event.Listener;
import javafx.scene.control.Button;

/**
 * 
 * @author Kevin Ratovo
 * abstract class for lone buttons that don't deserve their own class
 */
public abstract class ListenerButton extends Button implements Listener {
	
	protected Controller controller;
	
	public ListenerButton(String label,Controller controller) {
		super(label);
		this.controller = controller;
		controller.addListener(this);
	}
	
	public ListenerButton(Controller controller) {
		super();
		this.controller = controller;
		controller.addListener(this);
	}
	
	@Override
	public abstract void update();

}
