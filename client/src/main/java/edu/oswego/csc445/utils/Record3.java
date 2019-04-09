package edu.oswego.csc445.utils;

public class Record3 extends Record {

	private static final int TASK = 3;
	private long stat;

	public Record3(String host, int port, String protocol, long stat, int size) {
		super(host, port, protocol, size);
		this.stat = stat;
	}

	public long getStat() {
		return stat;
	}

	@Override
	public int getTask() {
		return TASK;
	}
}
