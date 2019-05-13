package edu.oswego.csc445.utils.Records;

public class Record2 extends Record{

	private static final int TASK = 2;
	private double stat;

	public Record2(String host, int port, double stat, int size) {
		super(host, port, "TCP", size);
		this.stat = stat;
	}

	public double getStat(){
		return stat;
	}

	@Override
	public int getTask() {
		return TASK;
	}
}
