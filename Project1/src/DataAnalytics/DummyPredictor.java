package DataAnalytics;

import java.util.*;
import java.io.*;

public class DummyPredictor extends Predictor {
	
	public ArrayList<DataPoint> readData(String filename) {
		
		//Declare the ArrayList
		ArrayList<DataPoint> dataArr = new ArrayList<DataPoint>();
		
		try {
			//read in file to scanner
			Scanner fileScanner = new Scanner(new File("src/"+filename));
			
			while (fileScanner.hasNextLine()) {
			
				//read each line (point)
				String point = fileScanner.nextLine();
				
				//
				fileScanner.useDelimiter(",");
				
				//fetch the points
				double x = fileScanner.nextDouble();
				double y = fileScanner.nextDouble();
				
				//Setting Label and Test to Null/False:
				String label = null;
				boolean test = false;
				
				//fetch the label if it exsists
				if (fileScanner.hasNext())
					label = fileScanner.next();
				
				//fetch boolean 
				if (fileScanner.hasNextBoolean())
					 test = fileScanner.nextBoolean();
				
				//Adding to ArrayList 
				dataArr.add(new DataPoint(x,y,label,test));			
			}

		} catch (FileNotFoundException ex) {
				System.out.println("File not found");
		}
		
		return dataArr;
	}
	
	public String test(DataPoint data) {
		double x = data.getF1();
		double y = data.getF2();
		String lab = data.getLabel();
		boolean test = data.getIsTest();
		
		if (test) {
			double avg = Math.abs(x-y);
			if (avg < 5.0) {
				return "close";
			}
			else {
				return "far";
			}
		}
		return "DataPoint is not test data";
	}
	
	public Double getAccuracy(ArrayList<DataPoint> data) {
		return 4.20;
	}
	
	public Double getPrecision(ArrayList<DataPoint> data) {
		return 3.33;
	}
}
            