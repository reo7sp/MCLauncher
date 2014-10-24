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

package reo7sp.mclauncher.ui.menus;

import java.awt.Font;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import reo7sp.mclauncher.Core;
import reo7sp.mclauncher.ui.Menu;
import reo7sp.mclauncher.ui.elements.FancyLabel;

/**
 * Created by reo7sp on 9/15/13 at 7:22 PM
 */
public class StartMinecraftMenu extends Menu {
	public StartMinecraftMenu() {
		new Thread("MinecraftUpdate") {
			@Override
			public void run() {
				Core.getMinecraftController().prepareMinecraft();
			}
		}.start();

		new Thread("MinecraftStart") {
			@Override
			public void run() {
				try {
					while (!isInterrupted()) {
						Thread.sleep(1000);

						// minecraft
						if (Core.getUpdater().isRequestedUpdate() && !Core.getDownloadCenter().isBusy()) {
							// starting minecraft
							try {
								Core.getMinecraftController().startMinecraft();
							} catch (Exception err) {
								JOptionPane.showMessageDialog(StartMinecraftMenu.this.panel, "Can't start Minecraft! " + err, "Error", JOptionPane.ERROR_MESSAGE);
								err.printStackTrace();
							}

							// saving conf
							try {
								Core.getConfiguration().save();
							} catch (IOException err) {
								err.printStackTrace();
							}

							// closing launcher
							if (Core.getMinecraftController().isStarted()) {
								Core.getMainWindow().hideWindow();
							} else {
								Core.getMainWindow().returnToRoot();
							}
							break;
						}
					}
				} catch (InterruptedException ignored) {
				}
			}
		}.start();
	}

	@Override
	public void buildPanel() {
		FancyLabel updateLabel = new FancyLabel();
		updateLabel.setBounds(0, 64, MENU_WIDTH, 24);
		updateLabel.setText("Updating");
		updateLabel.setFont(new Font(null, Font.BOLD, 16));
		panel.add(updateLabel);

		FancyLabel loadingAnimLabel = new FancyLabel();
		loadingAnimLabel.setIcon(new ImageIcon(Core.getImagesFactory().getImage("loading.gif")));
		loadingAnimLabel.setBounds(0, 100, MENU_WIDTH, 24);
		panel.add(loadingAnimLabel);
	}

	@Override
	public boolean hasBackButton() {
		return false;
	}
}
