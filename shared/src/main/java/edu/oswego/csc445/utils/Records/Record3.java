package edu.oswego.csc445.utils.Records;

public class Record3 extends Record {

	private static final int TASK = 3;
	private float stat;

	public Record3(String host, int port, String protocol, float stat, int size) {
		super(host, port, protocol, size);
		this.stat = stat;
	}

	public float getStat() {
		return stat;
	}

	@Override
	public int getTask() {
		return TASK;
	}
}
