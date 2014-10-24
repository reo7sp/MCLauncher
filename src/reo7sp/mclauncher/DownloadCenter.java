/*
 Copyright 2014 Reo_SP

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 	http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
*/

package reo7sp.mclauncher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by reo7sp on 9/15/13 at 4:57 PM
 */
public class DownloadCenter extends Thread {
	private Queue<Download> downloads = new ConcurrentLinkedQueue<Download>();
	private Download current;

	public DownloadCenter() {
		super("DownloadCenter");
		start();
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	@Override
	public void run() {
		try {
			while (!isInterrupted()) {
				current = downloads.poll();

				if (current == null) {
					Thread.sleep(100);
					continue;
				}

				try {
					Log.i("DownloadCenter", "Downloading " + current.getFileToLoad());
					current.download();
				} catch (Exception err) {
					Log.w("DownloadCenter", "Can't download file " + current.getFileToLoad());
					err.printStackTrace();
					current.getFile().delete();
				}
				current = null;
			}
		} catch (InterruptedException ignored) {
		}
	}

	public void download(String fileName, File file) {
		if (fileName == null || file == null) {
			return;
		}
		Log.i("DownloadCenter", "Added " + fileName + " to download queue");
		downloads.add(new Download(fileName, file));
	}

	public boolean isBusy() {
		return !downloads.isEmpty() && current != null;
	}

	public String getTextFile(String fileName) throws IOException {
		URL url;
		ReadableByteChannel in;
		try {
			url = new URL(Core.getRepoURL() + fileName);
			in = Channels.newChannel(url.openStream());
		} catch (IOException err) {
			url = new URL(Core.getSecondRepoURL() + fileName);
			in = Channels.newChannel(url.openStream());
		}

		StringBuilder result = new StringBuilder();
		ByteBuffer buffer = ByteBuffer.allocate(32);
		while (in.read(buffer) > 0) {
			buffer.flip();
			for (int i = 0; i < buffer.limit(); i++) {
				result.append((char) buffer.get());
			}
			buffer.clear();
		}
		return result.toString().replace("\n\n", "\n");
	}

	private class Download {
		private File file;
		private String fileToLoad;

		public Download(String fileToLoad, File file) {
			this.fileToLoad = fileToLoad;
			this.file = file;
		}

		public void download() throws Exception {
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				if (!file.createNewFile()) {
					throw new IOException("Can't create file");
				}
			}

			URL url;
			ReadableByteChannel inFromNet;
			try {
				url = new URL(Core.getRepoURL() + fileToLoad.replace(" ", "%20"));
				inFromNet = Channels.newChannel(url.openStream());
			} catch (IOException err) {
				url = new URL(Core.getSecondRepoURL() + fileToLoad.replace(" ", "%20"));
				inFromNet = Channels.newChannel(url.openStream());
			}
			FileOutputStream outToFile = new FileOutputStream(file);
			outToFile.getChannel().transferFrom(inFromNet, 0, Long.MAX_VALUE);
			outToFile.close();
			inFromNet.close();
		}

		public String getFileToLoad() {
			return fileToLoad;
		}

		public File getFile() {
			return file;
		}
	}
}
