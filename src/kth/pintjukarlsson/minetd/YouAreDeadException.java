package kth.pintjukarlsson.minetd;

import java.io.PrintStream;
import java.io.PrintWriter;

public class YouAreDeadException extends IllegalStateException {

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Illigal Player State: You are Dead!";
	}

	

	@Override
	public void printStackTrace(PrintStream s) {
		// TODO Auto-generated method stub
		super.printStackTrace(s);
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		// TODO Auto-generated method stub
		super.printStackTrace(s);
	}
	
}
