package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public class ImageLib {
	
	public Image head1;
	public Image head2;
	public Image body1;
	public Image body2;
	public Image legs1;
	public Image legs2;
	
	public ImageLib() throws FileNotFoundException {
		head1 = new Image(new FileInputStream("images/head1.png"));
		head2 = new Image(new FileInputStream("images/head2.png"));
		body1 = new Image(new FileInputStream("images/body1.png"));
		body2 = new Image(new FileInputStream("images/body2.png"));
		legs1 = new Image(new FileInputStream("images/legs1.png"));
		legs2 = new Image(new FileInputStream("images/legs2.png"));
	}
}
