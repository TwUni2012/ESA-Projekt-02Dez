package webfrontend.soap_globalweather.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

import webfrontend.soap_globalweather.GlobalWeatherService;

public class WeatherPanel extends JPanel {

	private GlobalWeatherService service;
	/*
	 * values used to paint the thermometer etc
	 */
	private Dimension size;
	private final int margin=15,degreesMinus=30,degreesPlus=40,thermHeight=20,barHeight=8,textdist=3;
	private int thermWidth,x,y,compassSize;
	private double degreeWidth;
	private int markerWidth;
	private int arrowLength;
	private double radiant;
	private int centerX;
	private int centerY;
	private int arrowAngle;
	private int angle;
	private FontMetrics metrics;
	private final Font headlineFont=Font.decode("verdana-bold-13"),textFont=Font.decode("verdana-11"),labelFont=Font.decode("verdana-9");
	private String text;
	private final String[] directionLabels={"N","NE","E","SE","S","SW","W","NW"};

	/*
	 * replace with combobox
	 */
	public WeatherPanel(GlobalWeatherService service) {
		this.service=service;
	}

	public void updateWeather(String country, String city)
	{
		try {
			service.updateWeather(country,city);
			repaint();
			//			System.out.println("Last Update: "+service.getDay()+"."+service.getMonth()+"."+service.getYear()+" "+service.getHour()+":"+service.getMinute());
			//			System.out.println("Temperature: "+service.getTemperature()+" °C");
			//			System.out.println("Wind: "+service.getWindspeed()+" km/h, "+service.getWindDirection()+" °");
			//			System.out.println("Sky: "+service.getCondition());
		} catch (Exception e) {
		}
	}

	@Override
	public void paint(Graphics g) 
	{
		size=getSize();

		try
		{
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, size.width, size.height);

			/*
			 * draw the thermometer
			 */
			thermWidth=size.width-size.height-(3*margin);
			degreeWidth=(double)((double)(thermWidth)/(double)(degreesMinus+degreesPlus));

			g.setFont(labelFont);
			metrics=g.getFontMetrics();

			for(int i=-degreesMinus;i<degreesPlus;i++)
			{
				x=(int)((i+degreesMinus)*degreeWidth)+margin;
				y=size.height-margin-thermHeight;
				g.drawLine(x, y, x, y+thermHeight);

				if((i)%5==0)
				{
					g.setColor(Color.BLACK);
					g.drawLine(x, y-thermHeight/2, x, y+thermHeight);
					g.drawString((i)+" °C", x+1, y-thermHeight/2);
					g.setColor(Color.DARK_GRAY);
				}
			}

			g.setColor(service.getTemperature()<0?Color.BLUE:Color.RED);
			x=margin;
			y+=(thermHeight-barHeight)/2;

			g.fillRoundRect(
					x, 
					y, 
					(int)((degreesMinus+service.getTemperature())*degreeWidth), 
					barHeight, 
					barHeight/2, 
					barHeight/2);

			g.setColor(Color.BLACK);
			g.drawRoundRect(
					margin, 
					size.height-margin-thermHeight, 
					thermWidth, 
					thermHeight, 
					thermHeight/4, 
					thermHeight/4);

			/*
			 * draw the wind compass
			 */

			x=thermWidth+3*margin;
			y=margin;
			compassSize=size.height-2*margin;

			g.setColor(Color.LIGHT_GRAY);
			g.fillOval(x, y, compassSize, compassSize);

			centerX=x+compassSize/2;
			centerY=y+compassSize/2;
			markerWidth=compassSize/15;

			g.setColor(Color.BLACK);
			for(int i=0;i<8;i++)
			{
				radiant=Math.toRadians(i*45);
				g.drawLine(
						centerX, 
						centerY, 
						centerX+(int)(Math.sin(radiant)*compassSize/2), 
						centerY-(int)(Math.cos(radiant)*compassSize/2));

				/*
				 * draw the labels
				 */
				x=centerX+(int)(Math.sin(radiant)*(compassSize+margin)/2);
				x-=metrics.stringWidth(directionLabels[i])/2;

				y=centerY-(int)(Math.cos(radiant)*(compassSize+margin)/2);
				y+=metrics.getHeight()/2;

				g.drawString(directionLabels[i], x, y);
			}

			x=thermWidth+3*margin;
			y=margin;

			g.setColor(Color.LIGHT_GRAY);
			g.fillOval(
					x+markerWidth, 
					y+markerWidth, 
					compassSize-2*markerWidth, 
					compassSize-2*markerWidth);

			g.setColor(Color.BLACK);
			g.drawOval(x, y, compassSize, compassSize);

			arrowLength=(compassSize-3*markerWidth)/2;
			arrowAngle=20;

			angle=service.getWindDirection()-180;
			angle=angle%360;
			radiant=Math.toRadians(angle);

			x=-(int)(Math.sin(radiant)*arrowLength);
			y=(int)(Math.cos(radiant)*arrowLength);
			g.drawLine(
					centerX-x, 
					centerY-y, 
					centerX+x/2, 
					centerY+y/2);

			radiant=Math.toRadians(angle+arrowAngle);
			g.drawLine(
					centerX-x, 
					centerY-y, 
					centerX-(int)(Math.sin(radiant)*arrowLength), 
					centerY+(int)(Math.cos(radiant)*arrowLength));
			g.drawLine(
					centerX+x/2, 
					centerY+y/2, 
					centerX-(int)(Math.sin(radiant)*arrowLength), 
					centerY+(int)(Math.cos(radiant)*arrowLength));

			radiant=Math.toRadians(angle-arrowAngle);
			g.drawLine(
					centerX-x, 
					centerY-y, 
					centerX-(int)(Math.sin(radiant)*arrowLength), 
					centerY+(int)(Math.cos(radiant)*arrowLength));
			g.drawLine(
					centerX+x/2, 
					centerY+y/2, 
					centerX-(int)(Math.sin(radiant)*arrowLength), 
					centerY+(int)(Math.cos(radiant)*arrowLength));

			/*
			 * draw the headline
			 */
			g.setFont(headlineFont);
			text="Weather in "+service.getCity()+", "+service.getCountry();
			metrics=g.getFontMetrics();
			x=margin;
			y=margin+metrics.getHeight();
			g.drawString(text,x,y);

			/*
			 * draw date and time
			 */
			g.setFont(textFont);
			text="Last Update: "+service.getYear()+"-"+service.getMonth()+"-"+service.getDay()+
					" "+service.getHour()+":"+(service.getMinute()<10?"0":"")+service.getMinute()+" GMT";
			metrics=g.getFontMetrics();
			y+=metrics.getHeight()+textdist;
			g.setColor(Color.GRAY);
			g.drawString(text,x,y);

			/*
			 * draw the wind speed
			 */
			g.setColor(Color.BLACK);
			text="Wind Speed: "+(int)service.getWindspeed()+" km/h";
			x=size.width-3*margin-compassSize-metrics.stringWidth(text);
			g.drawString(text,x,y);

//			y+=metrics.getHeight();
//			text="Direction: "+service.getWindDirection()+"°";
//			x=size.width-3*margin-compassSize-metrics.stringWidth(text);
//			g.drawString(text,x,y);

			/*
			 * draw the weather conditions
			 */
			g.setFont(headlineFont);
			metrics=g.getFontMetrics();
			text=service.getTemperature()+" °C, "+service.getCondition();
			x=(thermWidth+2*margin-metrics.stringWidth(text))/2;
			y=(size.height-2*margin)/2;
			g.drawString(text,x,y);
		}
		catch(Exception e){

			g.setColor(Color.WHITE);
			g.fillRect(0, 0, size.width, size.height);

		}
	}

}
