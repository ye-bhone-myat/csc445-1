package edu.oswego.csc445.utils;

public class Record1 extends Record{

	private static final int TASK = 1;
	private long stat;

	public Record1(String host, int port, String protocol, long stat, int size) {
		super(host, port, protocol, size);
		this.stat = stat;
	}

	public long getStat(){
		return stat;
	}

	@Override
	public int getTask() {
		return TASK;
	}
}
