package DataAnalytics;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Driver {
	
	/*private static void inItAndShowGUI() {
		
		//Generate 2 sets of random DataPoints
		//Training Data

		DataPoint p1 = new DataPoint(1.6,5.5,"Green",true);
		DataPoint p2 = new DataPoint(3.4,7.8,"Green",true);
		DataPoint p3 = new DataPoint(5.7,8.2,"Green",true);

		//Test Data
		DataPoint tp1 = new DataPoint(7.7,7.8,null,false);
		DataPoint tp2 = new DataPoint(9.0,1.0,null,false);
		DataPoint tp3 = new DataPoint(5.7,3.2,null,false);
		
		//Instanciate DummyPredictor class
		DummyPredictor dummy = new DummyPredictor();
		
		ArrayList<DataPoint> data = dummy.readData("predictorData.txt");
		System.out.println(dummy.test(tp1));
		
		
		// A JFrame is a window.
		JFrame myFrame = new JFrame("precision and accuracy");
		
		//Create container
		Container contentPane = myFrame.getContentPane();

		// The content pane is the meat of the window 
		contentPane.setLayout(new FlowLayout());
		
		// Adding the precision and accuracy as JLabels 
		contentPane.add(new JLabel("The percision is: "+ dummy.getAccuracy(data)));
		contentPane.add(new JLabel("The accuracy is: "+ dummy.getPrecision(data)));


		myFrame.pack();
		myFrame.setVisible(true);
		}*/
	
	public static void main(String[] args) {
        /*SwingUtilities.invokeLater(
                new Runnable() { 
              	  public void run() { 
              		  inItAndShowGUI(); } }
              );*/
		KNNPredictor kp = new KNNPredictor(5);
		System.out.println(kp.getK());
		kp.readData("titanic.csv");
		
		DataPoint dp = new DataPoint(2,30,"Survived",true);
		
		System.out.println(kp.test(dp));
	}
}

