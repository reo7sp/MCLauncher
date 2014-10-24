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

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/**
 * Created by reo7sp on 9/9/13 at 7:21 PM
 */
public class ImagesFactory {
	private Map<String, Image> images = new HashMap<String, Image>();

	public Image getImage(String name) {
		String key = name + (name.contains(".") ? "" : ".png");
		Image image = images.get(key);
		if (image == null) {
			image = new ImageIcon(ImagesFactory.class.getResource("/reo7sp/mclauncher/res/" + key)).getImage();
			images.put(key, image);
		}
		return image;
	}
}
