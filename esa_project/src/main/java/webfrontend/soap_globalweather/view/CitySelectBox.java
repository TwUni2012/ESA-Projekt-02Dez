package webfrontend.soap_globalweather.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComboBox;

public class CitySelectBox extends JComboBox<String> implements KeyListener{

	public final String TIP="Enter a country to load the cities";
	private WeatherPanel panel;
	String prevItem="";

	public CitySelectBox(WeatherPanel panel) {
		this.panel=panel;
		addKeyListener(this);
		addItem(TIP);
	}

	public void clear() {
		removeAllItems();
	}

	public void addEntry(String city) {
		addItem(city);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar()>='a' && e.getKeyChar()<='z')
		{
			int i=0;
			while(!getItemAt(i++).toLowerCase().startsWith(""+e.getKeyChar()));
			setSelectedIndex(i);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
