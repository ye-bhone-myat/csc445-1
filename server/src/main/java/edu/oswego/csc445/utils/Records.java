package edu.oswego.csc445.utils;

import edu.oswego.csc445.utils.Record1;
import edu.oswego.csc445.utils.Record2;
import edu.oswego.csc445.utils.Record3;

import java.io.*;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;

public class Records {
	private Record1[] tcp1, udp1;
	private Record2[] tcp2, tcp2Server;
	private Record3[] tcp3, udp3;
	private String dir;

	public Records(String dir) {
		this.dir = dir;
	}

	public String getTarget(){
		return dir.substring(dir.indexOf(File.separatorChar) + 1);
	}

	public String getTcp1() {
		String str = Arrays.stream(tcp1)
				.sorted(Comparator.comparingInt(Record::getSize))
				.map(r -> r.getStat()+",").reduce(String::concat).get();
		return "[" + str + "]";
	}

	public String getUdp1() {
		String str = Arrays.stream(udp1)
				.sorted(Comparator.comparingInt(Record::getSize))
				.map(r -> r.getStat()+",").reduce(String::concat).get();
		return "[" + str + "]";
	}

	public String getTcp2() {
		String str = Arrays.stream(tcp2)
				.sorted(Comparator.comparingInt(Record::getSize))
				.map(r -> r.getStat()+",").reduce(String::concat).get();
		return "[" + str + "]";
	}

	public String getTcp2Server() {
		String str = Arrays.stream(tcp2Server)
				.sorted(Comparator.comparingInt(Record::getSize))
				.map(r -> r.getStat()+",").reduce(String::concat).get();
		return "[" + str + "]";
	}

	public String getTcp3() {
		String str = Arrays.stream(tcp3)
				.sorted(Comparator.comparingInt(Record::getSize))
				.map(r -> r.getStat()+",").reduce(String::concat).get();
		return "[" + str + "]";
	}

	public String getUdp3() {
		String str = Arrays.stream(udp3)
				.sorted(Comparator.comparingInt(Record::getSize))
				.map(r -> r.getStat()+",").reduce(String::concat).get();
		return "[" + str + "]";
	}

	public String get1Label() {
		String str = Arrays.stream(tcp1)
				.sorted(Comparator.comparingInt(Record::getSize))
				.map(r -> r.getSize()+",").reduce(String::concat).get();
		return "[" + str + "]";
	}

	public String get2Label() {
		String str = Arrays.stream(tcp2)
				.sorted(Comparator.comparingInt(Record::getSize))
				.map(r -> r.getSize()+",").reduce(String::concat).get();
		return "[" + str + "]";
	}

	public String get3Label() {
		String str = Arrays.stream(tcp3)
				.sorted(Comparator.comparingInt(Record::getSize))
				.map(r -> r.getSize()+",").reduce(String::concat).get();
		return "[" + str + "]";
	}

	public void loadRecords() {
//		File[] recordsDir = new File(dir).listFiles();
		ObjectInputStream objIn;

		File[] recordsDir = new File(dir).listFiles((dir, name) -> name.matches("TCP-1.*\\.ser"));
		tcp1 = new Record1[recordsDir.length];

		for (int i = 0; i < tcp1.length; ++i) {
			try {
				objIn = new ObjectInputStream(new FileInputStream(recordsDir[i]));
				tcp1[i] = (Record1) objIn.readObject();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		recordsDir = new File(dir).listFiles((dir, name) -> name.matches("UDP-1.*\\.ser"));
		udp1 = new Record1[recordsDir.length];

		for (int i = 0; i < udp1.length; ++i) {
			try {
				objIn = new ObjectInputStream(new FileInputStream(recordsDir[i]));
				udp1[i] = (Record1) objIn.readObject();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		recordsDir = new File(dir).listFiles((dir, name) -> name.matches("TCP-2-\\d+\\.ser"));
		tcp2 = new Record2[recordsDir.length];

		for (int i = 0; i < tcp2.length; ++i) {
			try {
				objIn = new ObjectInputStream(new FileInputStream(recordsDir[i]));
				tcp2[i] = (Record2) objIn.readObject();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		recordsDir = new File(dir).listFiles((dir, name) -> name.matches("TCP-2-server\\d+\\.ser"));
		tcp2Server = new Record2[recordsDir.length];

		for (int i = 0; i < tcp2Server.length; ++i) {
			try {
				objIn = new ObjectInputStream(new FileInputStream(recordsDir[i]));
				tcp2Server[i] = (Record2) objIn.readObject();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		recordsDir = new File(dir).listFiles((dir, name) -> name.matches("TCP-3.*\\.ser"));
		tcp3 = new Record3[recordsDir.length];

		for (int i = 0; i < tcp3.length; ++i) {
			try {
				objIn = new ObjectInputStream(new FileInputStream(recordsDir[i]));
				tcp3[i] = (Record3) objIn.readObject();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		recordsDir = new File(dir).listFiles((dir, name) -> name.matches("UDP-3.*\\.ser"));
		udp3 = new Record3[recordsDir.length];

		for (int i = 0; i < udp3.length; ++i) {
			try {
				objIn = new ObjectInputStream(new FileInputStream(recordsDir[i]));
				udp3[i] = (Record3) objIn.readObject();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}


	}
}
