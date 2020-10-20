package controller;

import event.Listener;

public interface Controller {
	
	public void addListener(Listener listener);
	public void removeListener(Listener listener);
	
}
