package edu.oswego.csc445.utils.Records;

public class Record1 extends Record{

	private static final int TASK = 1;
	private float stat;

	public Record1(String host, int port, String protocol, float stat, int size) {
		super(host, port, protocol, size);
		this.stat = stat;
	}

	public float getStat(){
		return stat;
	}

	@Override
	public int getTask() {
		return TASK;
	}
}
