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

package reo7sp.mclauncher.ui.elements;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class TransparentPanel extends JPanel {
	private Color color = new Color(0, 0, 0, 0.75f);

	public TransparentPanel() {
		setOpaque(false);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
