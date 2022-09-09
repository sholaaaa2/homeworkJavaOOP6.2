package part2;

public class Ship implements Runnable {
	private String name;
	private int box = 10;
	private Dock dock;

	public Ship(String name, Dock dock) {
		super();
		this.name = name;
		this.dock = dock;
	}

	public Ship() {
		super();
	}

	public int getBox() {
		return box;
	}
	
	private void unloadBox() {
		for (; box > 0;) {
			dock.unloadInDock();
			box = box - 1;
			System.out.println(name + " ship unload 1 box");
		}
		System.out.println(name + " ship is empty");
	}

	@Override
	public void run() {
		unloadBox();
	}
	
}
