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

package reo7sp.mclauncher.ui;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Created by reo7sp on 9/9/13 at 8:06 PM
 */
public abstract class Menu {
	public static final int MENU_WIDTH = MainWindow.MENU_WIDTH;
	public static final int MENU_HEIGHT = MainWindow.MENU_HEIGHT;
	protected JPanel panel = new JPanel();

	public Menu() {
		initPanel();
		buildPanel();
	}

	private void initPanel() {
		panel.setBorder(new EmptyBorder(8, 8, 8, 8));
		panel.setLayout(null);
		panel.setOpaque(false);
	}

	public abstract void buildPanel();

	public boolean hasBackButton() {
		return true;
	}

	public JPanel getPanel() {
		return panel;
	}
}
