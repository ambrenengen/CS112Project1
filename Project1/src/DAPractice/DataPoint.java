package DAPractice;

public class DataPoint {
	private Double f1;
	private String label;

	public DataPoint(Double f, String label) {
		this.f1 = f;
		this.label = label;
	}

	public DataPoint(Double f) {
		this.f1 =f;
		this.label = null;

	}
	public Double getf1() {
		return this.f1;
	}
	public String getLabel() {
		return this.label;
	}
	public void setF1(Double f) {
		//add restrictions
		if (f<0) {
			return;
		}
		this.f1 = f;
	}
	public void setLabel(String label) {
		if(!(label.equals("Green")||label.equals("Blue"))) {
			return;
		}
	}
}
