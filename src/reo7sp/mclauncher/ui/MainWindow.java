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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import reo7sp.mclauncher.Core;
import reo7sp.mclauncher.ui.elements.FancyButton;
import reo7sp.mclauncher.ui.elements.FancyPanel;
import reo7sp.mclauncher.ui.elements.TransparentPanel;
import reo7sp.mclauncher.ui.menus.MainMenu;

/**
 * Created by reo7sp on 9/9/13 at 6:32 PM
 */
public class MainWindow {
	public static final int FRAME_WIDTH = 800;
	public static final int FRAME_HEIGHT = 600;
	public static final int MENU_WIDTH = 332;
	public static final int MENU_HEIGHT = 400;
	private final JFrame frame = new JFrame();
	private final FancyPanel panel = new FancyPanel();
	private final TransparentPanel menuContainer = new TransparentPanel();
	private final FancyButton backButton = new FancyButton();
	private final Menu rootMenu = new MainMenu();
	private Menu currentMenu = rootMenu;

	public void initFrame() {
		frame.setTitle(Core.getLauncherName() + " by Reo_SP");
		frame.setResizable(false);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		initPanel();
		frame.setContentPane(panel);

		frame.setVisible(true);
	}

	private void initPanel() {
		panel.setBackgroundImage(Core.getImagesFactory().getImage("background"));
		panel.setLayout(null);
		initMenu();
		panel.add(menuContainer);
		updateMenu(null);
	}

	private void initMenu() {
		backButton.setText("Назад");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				returnToRoot();
			}
		});
		menuContainer.setLayout(new BorderLayout());
		menuContainer.setBounds(FRAME_WIDTH / 2 - MENU_WIDTH / 2, FRAME_HEIGHT - MENU_HEIGHT - 20, MENU_WIDTH, MENU_HEIGHT);
		menuContainer.setBorder(new LineBorder(Color.WHITE));
		menuContainer.add(backButton, BorderLayout.SOUTH);
	}

	public void setCurrentMenu(Menu menu) {
		if (menu == null || menu == currentMenu) {
			return;
		}
		Menu oldMenu = currentMenu;
		currentMenu = menu;
		updateMenu(oldMenu);
	}

	public void returnToRoot() {
		setCurrentMenu(rootMenu);
	}

	private void updateMenu(Menu oldMenu) {
		if (oldMenu != null) {
			menuContainer.remove(oldMenu.getPanel());
		}
		menuContainer.add(currentMenu.getPanel());
		menuContainer.repaint();
		menuContainer.revalidate();
		backButton.setVisible(currentMenu.hasBackButton());
	}

	public void hideWindow() {
		frame.setVisible(false);
	}
}
