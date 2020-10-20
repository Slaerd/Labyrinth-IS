package view;

import controller.Controller;
import event.Listener;
import javafx.scene.control.Label;

public abstract class ListenerLabel extends Label implements Listener{
	protected Controller controller;
	
	public ListenerLabel(String text,Controller controller) {
		super(text);
		this.controller = controller;
		controller.addListener(this);
	}
	
	public abstract void update();
}
