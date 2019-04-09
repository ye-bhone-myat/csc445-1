package edu.oswego.csc445.utils;


import java.io.Serializable;

public abstract class Record implements Serializable {
	private String host, protocol;
	private int port, size;

	public Record(String host, int port, String protocol, int size) {
		this.host = host;
		this.port = port;
		this.protocol = protocol;
		this.size = size;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getProtocol() {
		return protocol;
	}

	public int getSize() {
		return size;
	}

	public abstract int getTask();


}