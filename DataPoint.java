package DataAnalytics;

public class DataPoint {
	
	//Instance Variables:
	//Should the doubles be reference types?
	double f1; //first var
	double f2; //second var
	String label; //survived or not
	boolean isTest;//Is this a test data point or not

	//4 Arg Constructor
	public DataPoint(double f1Param, double f2Param, String lParam, boolean tParam ) {
		this.f1 = f1Param;
		this.f2 = f2Param;
		this.label = lParam;
		this.isTest = tParam;
		//Do we need validation? (test data == null)
	}
	
	//no-arg default constructor
	public DataPoint() {
		this.f1 = 0;
		this.f2 = 0;
		this.label = null;
		this.isTest = false;
	}
	
	//Accessors
	public double getF1() {
		return this.f1;
	}
	public double getF2() {
		return this.f2;
	}
	public String getLabel() {
		return this.label;
	}
	public boolean getIsTest() {
		return this.isTest;
	}
	
	//mutators
	public void setF1(double f1Param) {
		this.f1 = f1Param;
	}
	public void setF2(double f2Param) {
		this.f2 = f2Param;
	}
	public void setLabel(String lParam) {
		this.label = lParam;
	}
	public void setIsTest(boolean tParam) {
		this.isTest = tParam;
	}
	
	//toString method
	public String toString() {
		return "label = "+this.label;
	}
	
	
}


