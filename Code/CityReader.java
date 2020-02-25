import java.io.*;
import java.util.*;
import java.text.*;

public class CityReader
{
	//instance variables
	public static int numberCities;
	public static List<Point> cities = new ArrayList();	
	
	public CityReader(String cityfilename) throws java.io.IOException
	{
		cities = new ArrayList<Point>();
		File file = new File(cityfilename);
		Scanner sc = new Scanner(file);	
		ArrayList<String[]> storing = new ArrayList<String[]>();
		String nextValue = null;
		String tmp = null;
		while(sc.hasNextLine())
		{
			String line = sc.nextLine();
			if(line.contains("DIMENSION"))
			{				
				tmp = line;
				numberCities = Integer.parseInt(line.substring(line.indexOf(":") + 2));
			}
				
			if(line.contains("NODE_COORD_SECTION"))
			{
				while(sc.hasNextLine())
				{
					nextValue = sc.nextLine();
					if(!nextValue.contains("EOF"))
						storing.add(nextValue.trim().split(" "));
				}
			}
				
		}
		
		sc.close();
		
		for(int i=0; i<storing.size(); i++)
		{
			cities.add(new Point(Integer.parseInt(storing.get(i)[0]), Double.valueOf(storing.get(i)[1]).longValue(), Double.valueOf(storing.get(i)[2]).longValue()));
		}				
		
		sc.close();
	}
}