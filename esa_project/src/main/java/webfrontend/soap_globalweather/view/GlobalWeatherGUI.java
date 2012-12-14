package webfrontend.soap_globalweather.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import webfrontend.soap_globalweather.GlobalWeatherService;

public class GlobalWeatherGUI extends Container {
	
	private WeatherPanel weatherPanel;
	private Container controlContainer;
	private CountrySelectBox countrySelect;
	private CitySelectBox citySelect;
	private GlobalWeatherService service;
	private JButton loadWeatherButton;
	private JButton loadCitiesButton;
	private JLabel label;

	public GlobalWeatherGUI() {
		setLayout(new BorderLayout(5, 5));
		
		service=new GlobalWeatherService(label=new JLabel());
		
		loadWeatherButton=new JButton("Load Weather");
		loadWeatherButton.setEnabled(false);
		loadWeatherButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				weatherPanel.updateWeather(countrySelect.getText(),citySelect.getItemAt(citySelect.getSelectedIndex()));
			}
		});
		
		loadCitiesButton=new JButton("Load Cities");
		loadCitiesButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getCitiesByCountry(countrySelect.getText());
			}
		});
		
		add(weatherPanel=new WeatherPanel(service),BorderLayout.CENTER);
		add(controlContainer=createControlContainer(),BorderLayout.NORTH);
		add(new JLabel("<html><body><table border=0 width=100%><tr><td align=right><font color=#777777 face=verdana size=2>" +
				"Globaler Wetterdienst, nutzt den SOAP Web Service unter http://www.webserviceX.NET. Aufgabe zum 17.12.2012" +
				"</font></td></tr></table></body></html>"),BorderLayout.SOUTH);
	}

	private Container createControlContainer() 
	{
		Container c=new Container();
		
		c.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		
		c.add(new JLabel("Country:"));
		c.add(countrySelect=new CountrySelectBox(this));
		
		c.add(loadCitiesButton);
		
		c.add(new JLabel("City / Airport:"));
		c.add(citySelect=new CitySelectBox(weatherPanel));
		
		c.add(loadWeatherButton);
		
		c.add(label);
		
		return c;
	}

	public void getCitiesByCountry(String country) 
	{
		loadWeatherButton.setEnabled(false);
		
		String[] entries=service.getCitiesByCountry(country).split("<Table>");
		String startTag="<City>",endTag="</City>";
		
		for(int i=0;i<entries.length;i++)
		{
			String city=entries[i];
			if(city.contains(startTag) && city.contains(endTag))
			{
				entries[i]=city.substring(city.indexOf(startTag)+startTag.length(), city.indexOf(endTag));
			}
			else
			{
				entries[i]="";
			}
		}
		
		/*
		 * sort the cities in alphabetical order
		 */
		boolean sorted=false;
		do
		{
			sorted=true;
			for(int i=0;i<entries.length-1;i++)
			{
				if(entries[i].compareTo(entries[i+1])>0)
				{
					sorted=false;
					
					String tmp=entries[i];
					entries[i]=entries[i+1];
					entries[i+1]=tmp;
				}
			}
		}
		while(!sorted);
		
		/*
		 * fill the combo box with the cities
		 */
		citySelect.clear();
		for(String city:entries)
		{
			if(city.length()>0)
				citySelect.addEntry(city);
		}
		
		if(entries.length<1)
		{
			loadWeatherButton.setEnabled(false);
			citySelect.addEntry(citySelect.TIP);
		}
		else
		{
			loadWeatherButton.setEnabled(true);
		}
	}

}
