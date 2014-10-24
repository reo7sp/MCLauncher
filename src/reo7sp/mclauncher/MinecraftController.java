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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by reo7sp on 9/9/13 at 6:44 PM
 */
public class MinecraftController {
	private boolean started;

	public void prepareMinecraft() {
		Core.getUpdater().update();
	}

	public void startMinecraft() throws Exception {
		// finding latest version
		String jar = Core.getMinecraftDirLocation() + File.separator + "minecraft.jar";

		// finding all libraries
		StringBuilder cp = new StringBuilder();
		File[] libs = new File(Core.getMinecraftDirLocation() + File.separator + "libs").listFiles();
		for (File lib : libs) {
			cp.append(lib.getAbsolutePath());
			cp.append(File.pathSeparatorChar);
		}
		cp.append(jar);

		// staring process
		ProcessBuilder processBuilder = new ProcessBuilder(
				"java",
				"-cp",
				cp.toString(),
				"-Djava.library.path=" + Core.getMinecraftDirLocation() + File.separator + "natives" + Core.getOS(),
				"-Xmx" + Math.max(Core.getConfiguration().getRam(), 256) + "M",
				"-Xms" + Math.min(Core.getConfiguration().getRam(), 256) + "M",
				"net.minecraft.client.main.Main",
				"--username",
				Core.getConfiguration().getNick(),
				"--gameDir",
				Core.getMinecraftDirLocation(),
				"--version",
				"0",
				"--accessToken",
				"0"
		);
		processBuilder.redirectErrorStream(true);
		processBuilder.directory(new File(Core.getMinecraftDirLocation()));

		final Process process = processBuilder.start();

		started = true;

		if (process != null) {
			new Thread("Minecraft") {
				@Override
				public void run() {
					try {
						Log.i("MinecraftController", "Minecraft started!");
						BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
						String line;
						while ((line = input.readLine()) != null) {
							System.out.println(line);
						}
						try {
							process.waitFor();
						} catch (InterruptedException err) {
							err.printStackTrace();
						}
						Log.i("MinecraftController", "Minecraft exited with status " + process.exitValue());
					} catch (IOException err) {
						err.printStackTrace();
					}

					System.exit(0);
				}
			}.start();
		}
	}

	public boolean isStarted() {
		return started;
	}
}
