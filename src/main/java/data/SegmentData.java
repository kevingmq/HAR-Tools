package data;

public class SegmentData implements ISegmentDataWithLabel{

	private double label = 0;
	private double[][] data;
	
	public SegmentData(double[][] data, double label) {
		this.data = data;
		this.label = label;
	}
	

	@Override
	public double getLabel() {
	
		return this.label;
	}

	@Override
	public void setLabel(double label) {
		this.label = label;
		
	}

	@Override
	public double[][] getData() {

		return this.data;
	}

	@Override
	public int getDimensionOfData() {
		
		if(this.data != null) {
		return this.data.length;
		}
		return 0;
	}

	@Override
	public int lenghtOfData() {

		if(this.getDimensionOfData() > 0) {
			return this.data[0].length;
		}
		return 0; 
	}

}
