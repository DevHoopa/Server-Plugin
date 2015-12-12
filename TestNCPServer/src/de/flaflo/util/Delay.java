package de.flaflo.util;

/**
 * Führt eine eine runnable mit einem Delay aus.
 * @author Flaflo
 *
 */
public abstract class Delay implements Runnable {

	private boolean isRunning;
	
	private Thread runningThread;
	
	/**
	 * Startet den Delay Thread mit dem vorgegebenen delay.
	 * @param delay in millisekunden
	 */
	public void start(long delay) {
		if (this.runningThread == null)
			this.runningThread = new Thread(this);
		
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.isRunning = true;
		this.runningThread.start();
	}
	
	/**
	 * Bricht den Vorgang ab, wenn der Delay<br>
	 * Thread noch nicht gestartet wurde.
	 */
	public void cancel() {
		if (!this.isRunning)
			return;
		
		try {
			this.isRunning = false;
			this.runningThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gibt zurück, ob der Delay Thread läuft.
	 * @return
	 */
	public boolean isRunning() {
		return isRunning;
	}
	
}
