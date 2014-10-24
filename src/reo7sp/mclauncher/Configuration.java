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
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by reo7sp on 9/9/13 at 7:40 PM

 */
public class Configuration {
	private final File file = new File(Core.getLauncherDirLocation() + File.separator + "config.txt");
	private String nick = "Player";
	private int ram = 512;

	public Configuration() {
	}

	public void load() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String s;
			while ((s = reader.readLine()) != null) {
				String[] parts = s.split("=");

				if (parts[0].equals("nick")) {
					nick = parts[1];
				} else if (parts[0].equals("ram")) {
					try {
						ram = Integer.parseInt(parts[1]);
					} catch (Exception ignored) {
					}
				}
			}
			reader.close();
		} catch (IOException ignored) {
		}
		Log.i("Configuration", "Config loaded! Nick: " + nick + ". Ram: " + ram);
	}

	public void save() throws IOException {
		if (!file.exists()) {
			if (!file.getParentFile().mkdirs() && !file.createNewFile()) {
				throw new IOException("Can't create file");
			}
		}

		PrintWriter writer = new PrintWriter(file);
		writer.write("nick=" + nick + "\n");
		writer.write("ram=" + ram + "\n");
		writer.close();
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
		try {
			save();
		} catch (IOException err) {
			err.printStackTrace();
		}
	}

	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
		try {
			save();
		} catch (IOException err) {
			err.printStackTrace();
		}
	}
}
