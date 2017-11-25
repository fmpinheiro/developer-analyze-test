package br.com.segware.util;

public class Message {
	
	private Message() {
		new AssertionError("This class must not be instantiate!");
	}
	
	public static void print(final String message) {
		System.out.println(message);
	}
	
	public static void printError(final String message) {
		System.err.println(message);
	}
	
}
