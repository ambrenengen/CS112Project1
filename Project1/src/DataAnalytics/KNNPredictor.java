package DataAnalytics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class KNNPredictor extends Predictor{

	//Instance Variables (b) 
	//number of survivors and deaths of training data 
	private int numSurvive = 0;
	private int numNotSurvive = 0;
	
	//K input
	private int k = 3;
	
	//Variables for getPrecision/Accuracy
	double truePositive = 0;
	double falsePositive = 0;
	double trueNegative = 0;
	double falseNegative = 0;
	
	//Take in a value for K and store as private int
	public KNNPredictor(int kParam) {
		this.k = kParam;
	}
	
	//Access k
	public int getK() {
		return this.k;
	}
	
	//Create the ArrayList that will hold all of the points:
	ArrayList<DataPoint> dataArr = new ArrayList<DataPoint>();
	
	// Helper function to split the line by commas
	private List<String> getRecordFromLine(String line) {
			
	// return the values as a Listof String
		List<String> values = new ArrayList<String>(); //why is it a list then an Array List?
			try (Scanner rowScanner = new Scanner(line)) { //do i need to define "line"?
				rowScanner.useDelimiter(",");
				while (rowScanner.hasNext()) {
					values.add(rowScanner.next());
				}
			}
			return values;
		}
	//readData method (c)
	public ArrayList<DataPoint> readData(String filename) {
		
		try (Scanner scanner = new Scanner(new File("src/"+filename));) {
			
			while (scanner.hasNextLine()) {
				
				//List of values in each line 
				List<String> records = getRecordFromLine(scanner.nextLine());
				
				//variables
				double f1 = 0.0; //first var
				double f2 = 0.0; //second var
				String label = null; //survived or not
				boolean isTest = false;//Is this a test data point or not
			    
				//pulling survived
				if (records.get(1).equals("1"))
					label = "1";
				else
					label = "0";
				
				//pulling class
				String strf1 = records.get(0);
				f1 = Double.parseDouble(strf1);
				
				//pulling age
				String strf2 = records.get(5);
				f2 = Double.parseDouble(strf2);

				
				Random rand = new Random();
				double randNum = rand.nextDouble();// 90% of the data is reserved for training
				if (randNum < 0.9) 
					// Set the type of DataPoint as “train” and put into the Collection
					isTest = false; 
				else // Set the type of DataPoint as “test” and put into theCollection
					isTest = true;
				
				//Count the number of each label for train type only.
				if (isTest == false && label == "1")
					numSurvive ++;
				else if (isTest == false && label == "0")
					numNotSurvive ++;
				    
				 // TODO: Store the DataPoint object in a collection
				DataPoint point = new DataPoint(f1,f2,label,isTest);
				dataArr.add(point);  
				}
			
		//Print the number of trainingDataPoint for each type of label
		System.out.println("Number of survivors (in training data): "+numSurvive);
		System.out.println("Number of deaths (in training data): "+numNotSurvive);
			
		}
		catch(FileNotFoundException ex) {
			System.out.println("File not found");
			//file not found exception 
		}
		//Return the arraylist after all lines have been read
		return dataArr;
	}
	
	//Helper function to find the distance between 2 datapoints
	private double doubleGetDistance(DataPoint p1, DataPoint p2) {
		
		//Instance variables pulled from DataPoints 1 and 2:
		double x1 = p1.getF1();//point 1 f1
		double x2 = p2.getF1(); //point 2 f1
		double y1 = p1.getF2(); //point 1 f2
		double y2 = p2.getF2(); //point 2 f2
		
		//Calculate distance
		double distance = Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
		
		//Return Distance
		return distance;
		
	}
	
	//Test Method
	public String test(DataPoint data) {
		
		//Find the number of training data:
		int length = numSurvive + numNotSurvive;
		
		//Declare and allocate a 2dArray
		Double[][] dis2d = new Double[length][2];
		
		//Go through all of the ArrayList
		for (int i = 0; i <length;i++) {
			double d = doubleGetDistance(data,dataArr.get(i));
			dis2d[i][0] = d;
			if (dataArr.get(i).getLabel().equals("1"))
				dis2d[i][1] = 1.0;
			else
				dis2d[i][1]= 0.0;
				}
		
	//Sort distance from highest to lowest
	java.util.Arrays.sort(dis2d,new java.util.Comparator<Double[]>(){
		public int compare(Double[] a,Double[] b){
			return a[0].compareTo(b[0]);
		}
		});
		
		//create counts for deaths vs survived
		int dCount = 0;
		int sCount = 0;
		
		//Pull the first k elements in the 2dArray
		for (int x = 0; x<k;x++) {
			if (dis2d[x][1] == 0)
				dCount ++;
			else if (dis2d[x][1]==1)
				sCount ++;
		}
		
		if(dCount>sCount)
			return "0";
		else
			return "1";
					
	}	
	
	//get accuracy
	public Double getAccuracy(ArrayList<DataPoint> data) {
		
		//i.For each test data point, obtain the label by running the test method.
		for (int ind = 0; ind < data.size(); ind++) {
			DataPoint p = data.get(ind);
			
			if(p.getIsTest()) {
			String l = test(p);
			
				if(l == "1" && p.getLabel() == "1")
					truePositive ++;
				else if(l == "1" && p.getLabel() == "0")
					falsePositive++;
				else if(l == "0" && p.getLabel() == "0")
					trueNegative++;
				else if(l == "0" && p.getLabel() == "1")
					falseNegative++;
			}
		}
		
		return (truePositive+trueNegative)/(truePositive + trueNegative + falsePositive + falseNegative);
		 
	}
	
	public Double getPrecision(ArrayList<DataPoint> data) {
		return  truePositive / (truePositive + falseNegative);
	}
}
		
			
			
