package de.flaflo.util;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public abstract class Delay implements Runnable {

	
	public void start(long delay) {
		new ScheduledThreadPoolExecutor(1).schedule(this, delay, TimeUnit.MILLISECONDS);
	}
	
	
	public void start(long delay, TimeUnit measure) {
		new ScheduledThreadPoolExecutor(1).schedule(this, delay, measure);
	}

}
