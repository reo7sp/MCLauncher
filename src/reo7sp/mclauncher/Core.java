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

import reo7sp.mclauncher.ui.ImagesFactory;
import reo7sp.mclauncher.ui.MainWindow;

/**
 * Created by reo7sp on 9/9/13 at 6:32 PM
 */
public class Core {
	private static Core instance;
	private final ImagesFactory imagesFactory = new ImagesFactory();
	private final String LAUNCHER_NAME = "MCLauncher";
	private final String repoURL = "https://dl.dropboxusercontent.com/u/28393540/MCLauncherRepo/";
	private final String secondRepoURL = "https://dl.dropboxusercontent.com/u/28393540/MCLauncherSecondRepo/";
	private final Updater updater = new Updater();
	private final DownloadCenter downloadCenter = new DownloadCenter();
	private final MinecraftController minecraftController = new MinecraftController();
	private MainWindow mainWindow;
	private Configuration configuration;
	private OS os;
	private String launcherDirLocation;
	private String minecraftDirLocation;

	private Core() {
		instance = this;

		findSystemDependedValues();
		configuration = new Configuration();
		configuration.load();
		mainWindow = new MainWindow();
		mainWindow.initFrame();
	}

	public static void main(String[] args) {
		instance = new Core();
	}

	public static MainWindow getMainWindow() {
		return instance.mainWindow;
	}

	public static String getLauncherDirLocation() {
		return instance.launcherDirLocation;
	}

	public static String getMinecraftDirLocation() {
		return instance.minecraftDirLocation;
	}

	public static String getLauncherName() {
		return instance.LAUNCHER_NAME;
	}

	public static Configuration getConfiguration() {
		return instance.configuration;
	}

	public static ImagesFactory getImagesFactory() {
		return instance.imagesFactory;
	}

	public static Updater getUpdater() {
		return instance.updater;
	}

	public static DownloadCenter getDownloadCenter() {
		return instance.downloadCenter;
	}

	public static String getRepoURL() {
		return instance.repoURL;
	}

	public static String getSecondRepoURL() {
		return instance.secondRepoURL;
	}

	public static OS getOS() {
		return instance.os;
	}

	public static MinecraftController getMinecraftController() {
		return instance.minecraftController;
	}

	private void findSystemDependedValues() {
		// finding OS id
		String systemName = System.getProperty("os.name").toLowerCase();
		if (systemName.contains("linux")) {
			os = OS.LINUX;
		} else if (systemName.contains("win")) {
			os = OS.WINDOWS;
		} else if (systemName.contains("mac")) {
			os = OS.MAC;
		} else if (systemName.contains("bsd")) {
			os = OS.FREEBSD;
		} else if (systemName.contains("solaris")) {
			os = OS.SOLARIS;
		}

		// finding files location
		String filesLocation;
		switch (os) {
			case WINDOWS:
				filesLocation = System.getenv("APPDATA");
				launcherDirLocation = filesLocation + File.separator + LAUNCHER_NAME;
				break;

			default:
				filesLocation = System.getProperty("user.home");
				launcherDirLocation = filesLocation + File.separator + "." + LAUNCHER_NAME;
				break;
		}
		minecraftDirLocation = launcherDirLocation + File.separator + "mc";
	}
}
