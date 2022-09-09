package part2;

import java.io.File;

public class FileSearcher {
	private int count = 1;
	private int check;

	public FileSearcher() {
		super();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCheck() {
		return check;
	}

	public void setCheck(int check) {
		this.check = check;
	}

	public void fileSearcher(File startDir, String soughtName) {
		if (startDir.isDirectory() == true) {
			File[] fileArr = startDir.listFiles();
			for (File file : fileArr) {
				if (file.isDirectory() == true) {
					SearchTask st = new SearchTask(this, file, soughtName);
					count += 1;
					synchronized (this) {
						for (; count > 2;) {
							try {
								System.out.println(st.getThr().getName() + " is waiting.");
								wait();
								System.out.println(st.getThr().getName() + " not waiting.");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				String name = file.getName();
				if (name.equals(soughtName)) {
					check += 1;
					System.out.println("Sought file founded: " + file.getAbsolutePath());
				}
			}
		} else {
			System.out.println("Entered file is not a directory.");
		}
		if (check < 1) {
			System.out.println("No files with such name in " + startDir.getAbsolutePath());
		}
		count -= 1;
		synchronized (this) {
			notifyAll();
		}
		System.out.println("Number of threads currently working is: " + count);
	}

}
