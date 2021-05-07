package DataAnalytics;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class Graph extends JPanel {    
	private static final long serialVersionUID = 1L;    
	private int labelPadding = 40;    
	private Color lineColor = new Color(255, 255, 254);
	
	// Number of grids and the padding width    
	private int numXGridLines = 6;    
	private int numYGridLines = 6;    
	private int padding = 40;    
	private List<DataPoint> data;    
	
	// TODO: Add point colors for each type of data point    
	private Color pointColor = new Color(255, 0, 255);    
	private Color red = new Color(255, 0, 0);    
	private Color gridColor = new Color(200, 200, 200, 200);    
	private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
	
	//More Colors for points
	private Color blue = new Color(0,0,255); //blue.
	private Color cyan = new Color(0, 255, 255); //cyan. 
	private Color yellow = new Color(255,255,0); //yellow
	
	//p and a
	private static double p;
	private static double a;
	
	// TODO: Add a private KNNPredictor variable
	private KNNPredictor kp;
	
	
	// TODO: Change point width as needed    
	private static int pointWidth = 10;
	
	
	//---Constructor method---      
	public Graph(int K, String fileName) {

		
		// TODO: Remove the above logic where random data is generated        
	    // TODO: instantiate the KNNPredictor variable
		//New KNNPredictor
		kp = new KNNPredictor(K);
	    
		// TODO: Run readData using input filename to split the data to test and training
		ArrayList<DataPoint> arr = kp.readData(fileName);
		
	    // TODO: Set this.data as the output of readData 
		this.data = arr;
		
		//get accuracy
		a = kp.getAccuracy(arr);
		System.out.println("Accuracy: "+a);
		
		//print get precision
		p = kp.getPrecision(arr);
		System.out.println("Precision: "+p);
      
    }    
	@Override    
	protected void paintComponent(Graphics g) {        
    	super.paintComponent(g);        
    	Graphics2D g2 = (Graphics2D) g;        
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
    			RenderingHints.VALUE_ANTIALIAS_ON);        
    	
    	double minF1 = getMinF1Data();        
    	double maxF1 = getMaxF1Data();        
    	double minF2 = getMinF2Data();        
    	double maxF2 = getMaxF2Data(); 
    	
    	g2.setColor(Color.WHITE);        
    	g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - 
    			labelPadding, getHeight() - 2 * padding - labelPadding);        
    	g2.setColor(Color.BLUE);        
    	
    	double yGridRatio = (maxF2 - minF2) / numYGridLines;        
    	for (int i = 0; i < numYGridLines + 1; i++) {            
    		int x0 = padding + labelPadding;            
    		int x1 = pointWidth + padding + labelPadding;            
    		int y0 = getHeight() - ((i * (getHeight() - padding * 2 -
    				labelPadding)) / numYGridLines + padding + labelPadding);            
    		int y1 = y0;            
    		if (data.size() > 0) {                
    			g2.setColor(gridColor);                
    			g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth()- padding, y1);                
    			g2.setColor(Color.BLACK);                
    			String yLabel = String.format("%.2f", (minF2 + (i * yGridRatio)));                
    			FontMetrics metrics = g2.getFontMetrics();                
    			int labelWidth = metrics.stringWidth(yLabel);                
    			g2.drawString(yLabel, x0 - labelWidth - 6, y0 + (metrics.getHeight() / 2) - 3);            
    			}            
    		g2.drawLine(x0, y0, x1, y1);        
    		}        
    	
    	double xGridRatio = (maxF1 - minF1) / numXGridLines;        
    	for (int i = 0; i < numXGridLines + 1; i++) {            
    		int y0 = getHeight() - padding - labelPadding;            
    		int y1 = y0 - pointWidth;            
    		int x0 = i * (getWidth() - padding * 2 - labelPadding) / (numXGridLines) + padding + labelPadding;            
    		int x1 = x0;            
    		if (data.size() > 0) {                
    			g2.setColor(gridColor);                
    			g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);                
    			g2.setColor(Color.BLACK);
    			String xLabel = String.format("%.2f", (minF1 + (i * xGridRatio)));                
    			FontMetrics metrics = g2.getFontMetrics();                
    			int labelWidth = metrics.stringWidth(xLabel);                
    			g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight()+ 3);            
    		}            
    		g2.drawLine(x0, y0, x1, y1);        
    	}        
    	// Draw the main axis        
    	g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);        
    	g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, 
		getWidth() -padding, 
		getHeight() - padding - labelPadding);        
    	
    	// Draw the points        
    	paintPoints(g2, minF1, maxF1, minF2, maxF2);    
	}    
	private void paintPoints(Graphics2D g2, double minF1, double maxF1, double minF2, double maxF2) {        
		
		Stroke oldStroke = g2.getStroke();        
		g2.setColor(lineColor);        
		g2.setStroke(GRAPH_STROKE);        
		double xScale = ((double) getWidth() - (3 * padding) - labelPadding) /(maxF1 - minF1);        
		double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (maxF2 - minF2);
		
		g2.setStroke(oldStroke);        
		for (int i = 0; i < data.size(); i++) {            
			int x1 = (int) ((data.get(i).getF1() - minF1) * xScale + padding + labelPadding);            
			int y1 = (int) ((maxF2 - data.get(i).getF2()) * yScale + padding);            
			int x = x1 - pointWidth / 2;            
			int y = y1 - pointWidth / 2;            
			int ovalW = pointWidth;            
			int ovalH = pointWidth;        
			
		// TODO: Depending on the type of data and how it is tested, change color here.            
		// You need to test your data here using the model to obtain the test value             
		// and compare against the true label.            
		// Note that depending on how you implemented "test" method, you may need to             
		// modify KNNPredictor to store the output from readData.            
		// You can also optimize further to compute accuracy and precision in a single            
		// iteration.
		
		DataPoint p = data.get(i);
		String pt = kp.test(p);  

		
		if (p.getIsTest()) {
			if (pt == "1" && p.getLabel() == "1") 
				 pointColor = blue;
			
			else if (pt == "1" && p.getLabel() == "0") 
				pointColor = cyan;
			
			else if (pt == "0" && p.getLabel() == "0") 
				pointColor = red;
			
			else if (pt == "0" && p.getLabel() == "1") 
				pointColor = yellow;
		}  
		// iteration.            
		if (i % 2 == 0) {                
			g2.setColor(pointColor);            
			} 
		else {                
			g2.setColor(red);            
			}  
		
		g2.fillOval(x, y, ovalW, ovalH);        
	}
}
     //@Return the min values        
	private double getMinF1Data() {        
		double minData = Double.MAX_VALUE;        
		for (DataPoint pt : this.data) {            
			minData = Math.min(minData, pt.getF1());        
		}        
		return minData;    
	}    
	private double getMinF2Data() {        
		double minData = Double.MAX_VALUE;        
		for (DataPoint pt : this.data) {            
			minData = Math.min(minData, pt.getF2());        
		}        
		return minData;    
	}        
	 // @Return the max values;    
	    
	private double getMaxF1Data() {        
		double maxData = Double.MIN_VALUE;        
		for (DataPoint pt : this.data) {            
		maxData = Math.max(maxData, pt.getF1());        
		}        
		return maxData;    
	}    
	
	private double getMaxF2Data() {        
		double maxData = Double.MIN_VALUE;        
		for (DataPoint pt : this.data) {            
			maxData = Math.max(maxData, pt.getF2());        
		}        
		return maxData;    
	}    
	
	//Mutator     
	public void setData(List<DataPoint> data) {        
		this.data = data;        
		invalidate();        
		this.repaint();    
	}    
	 //Accessor     
	public List<DataPoint> getData() {        
		return data;    
	} 
	
	public static boolean openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	            return true;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return false;
	}

	public static boolean openWebpage(URL url) {
	    try {
	        return openWebpage(url.toURI());
	    } catch (URISyntaxException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	
	//  Run createAndShowGui in the main method, where we create the frame too and pack it in the panel
	private static void createAndShowGui(int K, String fileName) {
	
		//Main panel       
		Graph mainPanel = new Graph(K, fileName);
		
		//creating the frame        
		JFrame frame = new JFrame("CS 112 Lab Part 3");
		
		//Add a content pane
		Container contentPane = frame.getContentPane();

		
		// Feel free to change the size of the panel        
		mainPanel.setPreferredSize(new Dimension(700, 600)); 
		
		//Create Buttons and change color depending on strength
		JButton aButton = new JButton("Accuracy: " +Double.toString(a));
		if (a <= .666)
			aButton.setBackground(Color.YELLOW);
		else {
			aButton.setBackground(Color.GREEN);
		}
		
		contentPane.add(aButton, BorderLayout.WEST);
		
		JButton pButton = new JButton("Percision: " +Double.toString(p));
		if (p <= .666)
			pButton.setBackground(Color.YELLOW);
		else
			pButton.setBackground(Color.GREEN);
		
		contentPane.add(pButton, BorderLayout.EAST);

		//Add other things   
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
		frame.getContentPane().add(mainPanel);        
		frame.pack();        
		frame.setLocationRelativeTo(null);        
		frame.setVisible(true);   
		
	}
	
	public static void main(String[] args) {        
		//User input for k:
		Scanner kScan = new Scanner(System.in);  // Create a Scanner object
		System.out.println("Enter k: ");
		int K = kScan.nextInt();    
		
		String fileName = "titanic.csv"; // TODO: Change this to titanic.csv        
		SwingUtilities.invokeLater(new Runnable() {            
			public void run() {                
				createAndShowGui(K, fileName);            	
			}        
		});  
		

	}
}


