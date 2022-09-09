package part2;

import java.io.File;

public class Main {

	public static void main(String[] args) {
//		Ships and Docks
		Dock docks = new Dock();
		
		Ship a = new Ship("a", docks);
		Ship b = new Ship("b", docks);
		Ship c = new Ship("c", docks);
		
		Thread th1 = new Thread(a);
		Thread th2 = new Thread(b);
		Thread th3 = new Thread(c);
		
		th1.start();
		th2.start();
		th3.start();
		
//		Copy Files with progress
		File file = new File("JavaScript_Podrobnoe_rukovodstvo_6-e_izdanie.pdf");
		File folder = new File("b");
		
		FileCopy fc = new FileCopy(file, folder);
		
		Thread thCopy = new Thread(fc);
		thCopy.start();
		
		
//		File Searcher
		File direct = new File(".");
		String name = "ClockThread.java";

		FileSearcher fs = new FileSearcher();
		SearchTask st = new SearchTask(fs, direct, name);


	}

}
