/*package DataAnalytics;

import javax.swing.SwingUtilities;

public class Driver{
	public static void main(String[] args) {
		//User input for k:
		Scanner kScan = new Scanner(System.in);  // Create a Scanner object
		System.out.println("Enter k: ");
		int k = kScan.nextInt();
	
		//New KNNPredictor
		KNNPredictor kp = new KNNPredictor(k);
		
		//Load Data
		ArrayList<DataPoint> arr = kp.readData("titanic.csv");
		
		//New Point
		DataPoint dp = new DataPoint(1,30,"1",true);
		
		//Print test
		System.out.println("\n"+"testing test(): "+kp.test(dp));
		
		//print get accuracy
		System.out.println("Accuracy: "+kp.getAccuracy(arr));
		
		//print get precision
		System.out.println("Precision: "+kp.getPrecision(arr));
	}
}
	public static void main(String[] args) {        
		int K = 7; // A value of K selected            
		String fileName = "titanic.csv"; // TODO: Change this to titanic.csv        
		SwingUtilities.invokeLater(new Runnable() {            
			public void run() {                
				createAndShowGui(K, fileName);            	
			}        
		});    
	}
}*/

