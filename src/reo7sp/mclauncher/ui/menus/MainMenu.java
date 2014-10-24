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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import reo7sp.mclauncher.Core;
import reo7sp.mclauncher.ui.Menu;
import reo7sp.mclauncher.ui.elements.FancyButton;
import reo7sp.mclauncher.ui.elements.FancyTextBox;

/**
 * Created by reo7sp on 9/9/13 at 8:23 PM
 */
public class MainMenu extends Menu {
	@Override
	public void buildPanel() {
		final FancyTextBox nickField = new FancyTextBox();
		nickField.setBounds(MENU_WIDTH / 2 - 100, 64, 200, 32);
		nickField.setText(Core.getConfiguration().getNick());
		panel.add(nickField);

		FancyButton playButton = new FancyButton();
		playButton.setText("Play!");
		playButton.setBounds(MENU_WIDTH / 2 - 50, 125, 100, 32);
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Core.getConfiguration().setNick(nickField.getText());
				Core.getMainWindow().setCurrentMenu(new StartMinecraftMenu());
			}
		});
		panel.add(playButton);
	}

	@Override
	public boolean hasBackButton() {
		return false;
	}
}
