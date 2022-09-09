package part2;

import java.io.File;

public class SearchTask implements Runnable {

	private FileSearcher fs;
	private Thread thr;
	private File startDir;
	private String soughtFileName;

	public SearchTask(FileSearcher mfs, File startDir, String soughtFileName) {
		super();
		this.fs = mfs;
		this.startDir = startDir;
		this.soughtFileName = soughtFileName;
		thr = new Thread(this);
		thr.start();
	}

	public Thread getThr() {
		return thr;
	}

	public void setThr(Thread thr) {
		this.thr = thr;
	}

	@Override
	public void run() {
		System.out.println(thr.getName() + " start.");
		fs.fileSearcher(startDir, soughtFileName);
	}

}


