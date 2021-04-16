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
	private int k = 3;
	
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
					label = "Survived";
				else
					label = "Died";
				System.out.println("on thing: "+records.get(1));
				System.out.println("Label: "+label);
				
				//pulling class
				String strf1 = records.get(0);
				f1 = Double.parseDouble(strf1);
				System.out.println("f1: "+f1);
				
				//pulling age
				String strf2 = records.get(5);
				f2 = Double.parseDouble(strf2);
				System.out.println("f2: "+f2);
				
				Random rand = new Random();
				double randNum = rand.nextDouble();// 90% of the data is reserved for training
				if (randNum < 0.9) 
					// Set the type of DataPoint as “train” and put into the Collection
					isTest = false; 
				else // Set the type of DataPoint as “test” and put into theCollection
					isTest = true;
				System.out.println(isTest);
				
				//Count the number of each label for train type only.
				if (isTest == false && label == "Survived")
					numSurvive ++;
				else if (isTest == false && label == "Died")
					numNotSurvive ++;
				    
				 // TODO: Store the DataPoint object in a collection
				DataPoint point = new DataPoint(f1,f2,label,isTest);
				System.out.println(point);
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
		//retreive "if test"
		boolean test = data.isTest;
		
		//Find the number of training data:
		int length = numSurvive + numNotSurvive;
		System.out.println(length);
		
		//Declare and allocate a 2dArray
		Double[][] dis2d = new Double[length][2];
		
		//create a count to keep track of the index
		//int c = 0;
		
		System.out.println(dataArr);
		
		//Go through all of the ArrayList
		if(!test) {
			/*for (DataPoint i:dataArr) { 
					double d = doubleGetDistance(data,i);
					for (int y = 0; y<2;y++){
						dis2d[c][0] = d;
						System.out.println(dis2d[c][0]);
						if (i.getLabel().equals("Survived"))
							dis2d[c][1] = 1.0;
						else
							dis2d[c][1]= 0.0;
					}
					c ++;
				}*/
			for (int i = 0; i <length;i++) {
				double d = doubleGetDistance(data,dataArr.get(i));
				dis2d[i][0] = d;
				if (dataArr.get(i).getLabel().equals("Survived"))
					dis2d[i][1] = 1.0;
				else
					dis2d[i][1]= 0.0;
					}
			}
		System.out.println(dis2d);
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
			return "Died";
		else
			return "Survived";
					
	}	
	
	//get accuracy
	//@Override
	//public Double getAccuracy(ArrayList<DataPoint> data) {
		//i.For each test data point, obtain the label by running the test method.
		//for 
		//double label = data.
				
		
		 /* ii.If the label from the test method returns “1” and the label of the DataPoint is “1” 
		 * add 1 to a truePositive variable
		 */		 
		//if(data.)
			
		/* iii.If the label from the test method returns “1” and the label of the DataPoint 
		 * is “0” add 1 to a falsePositive variable
		 * 
		 * iv.If the label from the test method returns “0” and the label of the 
		 * DataPoint is “1” add 1 to a falseNegative variable
		 * 
		 * v. If the label from the test method returns “0” and the label of the 
		 * DataPoint is “0” add 1 to a trueNegative variable
		 
	}
	
	@Override
	public Double getPrecision(ArrayList<DataPoint> data) {
		return 3.33;
	}*/
}
		
			
			
