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
import java.io.IOException;

import reo7sp.mclauncher.utils.FileUtils;

public class Updater {
	private boolean requestedUpdate;
	private boolean newMcJar;

	public void update() {
		updateJar();
		updateResources();
		updateLibs();
		updateNatives();
		requestedUpdate = true;
	}

	private void updateJar() {
		updateFile("minecraft.jar");
	}

	private void updateLibs() {
		try {
			String[] libList = Core.getDownloadCenter().getTextFile("libList.txt").split("\n");
			for (String lib : libList) {
				updateFile("libs/" + lib);
			}
		} catch (IOException err) {
			Log.s("Updater", err);
		}
	}

	private void updateResources() {
		try {
			String[] resourcesList = Core.getDownloadCenter().getTextFile("resourcesList.txt").split("\n");
			for (String resource : resourcesList) {
				updateFile("resources/" + resource);
			}
		} catch (IOException err) {
			Log.s("Updater", err);
		}
	}

	private void updateNatives() {
		try {
			String[] nativesList = Core.getDownloadCenter().getTextFile("nativesList" + Core.getOS() + ".txt").split("\n");
			for (String nativesFile : nativesList) {
				updateFile("natives" + Core.getOS() + "/" + nativesFile);
			}
		} catch (IOException err) {
			Log.s("Updater", err);
		}
	}

	private void updateFile(String fileName) {
		try {
			if (fileName == null || fileName.isEmpty()) {
				return;
			}

			String dstFileName = fileName.replace("/", File.separator);
			File file = new File(Core.getMinecraftDirLocation() + File.separator + dstFileName);
			boolean isMcJar = fileName.equals("minecraft.jar");
			boolean download = !file.exists() || isMcJar || newMcJar;

			if (file.exists() && download) {
				String md5Net = Core.getDownloadCenter().getTextFile(fileName + ".md5").trim();
				String md5Local = FileUtils.getChecksum(file);

				download = !md5Net.equals(md5Local);

				Log.i("Updater", "File: " + fileName + ". " +
						"Local checksum: " + md5Local + ". " +
						"Net checksum: " + md5Net + ". " +
						(download ? "Not equals" : "Equals"));
			}

			if (download) {
				if (isMcJar) {
					newMcJar = true;
				}
				Core.getDownloadCenter().download(fileName, file);
			}
		} catch (IOException err) {
			Log.s("Updater", err);
		}
	}

	public boolean isRequestedUpdate() {
		return requestedUpdate;
	}
}
