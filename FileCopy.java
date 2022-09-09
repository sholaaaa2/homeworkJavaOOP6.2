package part2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class FileCopy implements Runnable {

	private byte[] copyBuffer;
	private boolean turn = false;
	private File in;
	private File out;
	private long length;
	private long available;

	public FileCopy(File in, File out) {
		super();
		this.in = in;
		if (out.isFile()) {
			this.out = out;
		} else {
			this.out = new File(out, in.getName());
		}
		length = in.length();
		available = length;
	}

	private class Copywriter implements Runnable {

		private File file;
		private boolean inOrOut;

		public Copywriter(File file, boolean inOrOut) {
			super();
			this.file = file;
			this.inOrOut = inOrOut;
		}

		private void fileCopy() throws IOException {
			byte[] buffer = new byte[1024 * 1024];
			if (inOrOut) {
				int readByte = 0;
				try (FileInputStream fis = new FileInputStream(file)) {
					for (; (readByte = fis.read(buffer)) > 0;) {
						setCopyBuffer(buffer);
					}
				} catch (IOException e) {
					throw e;
				}
			} else {
				try (FileOutputStream fos = new FileOutputStream(file)) {
					for (; available > 0;) {
						buffer = getCopyBuffer();
						fos.write(buffer, 0, buffer.length);
						available -= buffer.length;
					}
				} catch (IOException e) {
					throw e;
				}
			}
		}

		@Override
		public void run() {
			try {
				fileCopy();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private class Progress implements Runnable {

		public Progress() {
			super();
		}

		private void getProgress() {
			for (; available > 0;) {
				System.out.println(((int) (length - available) / (length / 100)) + "% copied");
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("100% copied");
		}

		@Override
		public void run() {
			getProgress();
		}

	}

	public synchronized byte[] getCopyBuffer() {
		for (; turn == false;) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		byte[] out = copyBuffer;
		turn = !turn;
		notifyAll();
		return out;
	}

	public synchronized void setCopyBuffer(byte[] copyBuffer) {
		for (; turn == true;) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.copyBuffer = copyBuffer;
		turn = !turn;
		notifyAll();
	}

	@Override
	public void run() {
		Progress pg = new Progress();

		Copywriter copy = new Copywriter(in, true);
		Copywriter write = new Copywriter(out, false);

		Thread th1 = new Thread(copy);
		Thread th2 = new Thread(write);
		Thread th3 = new Thread(pg);

		th1.start();
		th2.start();
		th3.start();

	}

}
