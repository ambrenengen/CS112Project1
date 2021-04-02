package DAPractice;

import java.lang.Math;

public class Predictor1D {
	
	private Double greenAvg;
	private Double blueAvg;
	
	//[] becuase it's an array
	public void readData(DataPoint[] data) {
		
		//Variables
		Double greenAvg = 0.0;
		Double blueAvg = 0.0;
		
		Double totalBlue = 0.0;
		Double totalGreen = 0.0;
		
		int greenCount = 0;
		int blueCount = 0;
		
		//Reads data, counts the number of fs for each label, and sums total
		//of each color.
		for (int i =0;i<data.length; i++) {
			DataPoint d = data[i];
			Double f = d.getf1();
			
			if (d.getLabel() == "Green") {
				totalGreen = totalGreen + f;
				greenCount++;
			}
			else if (d.getLabel() == "Blue") {
				totalBlue = totalBlue + f;
				blueCount++;
			}	
		}
		//Calculates Averages
		this.greenAvg = totalGreen/greenCount;
		this.blueAvg = totalBlue/blueCount;
		System.out.println(this.blueAvg);
		System.out.println(this.greenAvg);
	}

	public String test(DataPoint testData) {
		//return blue/green depending on how close it 
		//is to computed average
		
		//Calculates distance from averages
		double g = Math.abs(testData.getf1()-this.greenAvg);
		double b = Math.abs(testData.getf1()-this.blueAvg);
		
		//returning the color with the shortest distance
		if (g>b) 
			return "Green"; 
		else if (g<b) 
			return "Blue";
		else 
			return "error";
	}
	
	public static void main(String[] args) {
		// create predictor
		Predictor1D predictor = new Predictor1D();
		
		//Data
		DataPoint[] data = new DataPoint[4];
		data[0] = new DataPoint(2.0, "Green");
		data[1] = new DataPoint(4.0,"Green");
		data[2] = new DataPoint(9.7, "Blue");
		data[3] = new DataPoint(11.8,"Blue");
		//..
		
		//Create testData
		DataPoint testData = new DataPoint(9.0);
		
		//Do the thing
		predictor.readData(data);
		System.out.println(predictor.test(testData));
	}

}
