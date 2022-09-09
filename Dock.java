package part2;

import java.util.concurrent.atomic.AtomicInteger;

public class Dock {
	private AtomicInteger emptyDocks = new AtomicInteger(2);
	
	public Dock() {
		super();
	}
	
	public synchronized void unloadInDock() {
		
		for (;emptyDocks.intValue() <= 0;) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		emptyDocks.getAndDecrement();
		
		try {
			Thread.sleep(500);
			emptyDocks.getAndIncrement();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		notify();
	}
}
