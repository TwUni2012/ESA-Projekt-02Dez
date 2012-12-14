package webfrontend.soap_globalweather.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class CountrySelectBox extends JTextField implements KeyListener{
	
	private GlobalWeatherGUI gui;

	public CountrySelectBox(GlobalWeatherGUI gui) {
		this.gui=gui;
		addKeyListener(this);
		setColumns(20);
		setText("Germany");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==10)
		{
			System.out.println("Requesting cities for '"+getText()+"'");
			gui.getCitiesByCountry(getText());
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
