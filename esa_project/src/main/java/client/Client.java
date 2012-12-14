package client;

import webfrontend.soap_globalweather.GlobalWeatherService;

public class Client {

	private static GlobalWeatherService service;

	private static String value;
	private static String[] parts;

	private static int temperature,wind;
	private static double windspeed;
	private static String condition;
	private static int year,month,day,hour,minute;

//	public static void main(final String[] args) 
//	{
//		String city="Berlin-Schoenefeld",country="Germany";
//		
//		service=new GlobalWeatherService();
//		
//		String xml;
//		try {
//			xml=service.updateWeather(country,city);
//			
//			/*
//			 * date and time
//			 */
//			value=getValueFromXMLTag(xml,"Time");
//			parts=value.split(" ");
//			
//			String[] dateParts=parts[parts.length-3].split("\\.");
//			year=Integer.parseInt(dateParts[0]);
//			month=Integer.parseInt(dateParts[1]);
//			day=Integer.parseInt(dateParts[2]);
//			System.out.println("Datum:\t\t"+day+". "+month+". "+year);
//			
//			hour=(Integer.parseInt(parts[parts.length-2])/100)+1;
//			minute=Integer.parseInt(parts[parts.length-2])%100;
//			System.out.println("Uhrzeit:\t"+hour+":"+minute);
//			
//			/*
//			 * temperature
//			 */
//			value=getValueFromXMLTag(xml, "Temperature");
//			temperature=Integer.parseInt(value.substring(value.indexOf("(")+1, value.indexOf(" C)")));
//			System.out.println("Temperatur:\t"+temperature+" °C");
//			
//			/*
//			 * wind direction and speed
//			 */
//			value=getValueFromXMLTag(xml, "Wind");
//			wind=Integer.parseInt(value.substring(value.indexOf("(")+1, value.indexOf(" degrees)")));
//			System.out.println("Windrichtung:\t"+wind+" °");
//			
//			windspeed=Double.parseDouble(value.substring(value.indexOf("at ")+3, value.indexOf(" MPH")))/0.62;
//			windspeed=round(windspeed,2);
//			System.out.println("Windgeschwindigkeit:\t"+windspeed+" km/h");
//			
//			/*
//			 * sky conditions
//			 */
//			value=getValueFromXMLTag(xml, "SkyConditions");
//			condition=value.trim();
//			System.out.println("Bewölkung:\t"+condition);
//			
//		} catch (Exception e) {
//		}
//	}
//
//	private static double round(double value, int digits) 
//	{
//		return (double)((int)value+(Math.round(Math.pow(10,digits)*(value-(int)value)))/(Math.pow(10,digits)));
//	}
//
//	private static String getValueFromXMLTag(String line,String tag) 
//	{
//		String startTag="<"+tag+">",endTag="</"+tag+">";
//		String value=null;
//
//		if(!line.contains(startTag) || !line.contains(endTag))
//			return null;
//
//		int start=line.indexOf(startTag)+startTag.length();
//		int end=line.indexOf(endTag);
//
//		if(start>=end)
//			return null;
//
//		value=line.substring(start, end);
//
//		return value;
//	}
//
}
