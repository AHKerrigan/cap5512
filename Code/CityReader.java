import java.io.*;
import java.util.*;
import java.text.*;

public class CityReader
{
	//instance variables
	public static int numberCities;
	public static ArrayList<Point> cities = new ArrayList();	
	
	public CityReader(String cityfilename) throws java.io.IOException
	{
		File file = new File(cityfilename);
		Scanner sc = new Scanner(file);
		storing = new ArrayList<String[]>();
		String nextValue = null;
		while(sc.hasNextLine())
		{
			String line = sc.nextLine();
			if(line.contains("DIMENSION"))
			{				
				numberCities = sc.nextInt()
			}
				
			if(line.contains("NODE_COORD_SECTION"))
			{
				while(sc.hasNextLine())
				{
					nextValue = sc.nextLine();
					storing.add(nextValue.trim().split(" "));
				}
			}
				
		}
		
		for(int i=0; i<storing.length; i++)
		{
			Point point = new Point(storing[i][0], Double.valueOF(storing[i][1]).longValue(), Double.valueOF(storing[i][2]).longValue());
			cities.add(point);
		}				
	}