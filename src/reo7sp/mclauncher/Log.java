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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

/**
 * Created by reo7sp on 9/20/13 at 3:55 PM
 */
public class Log {
	private static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

	public static void log(Level level, Object tag, Object message) {
		System.out.println("[" + format.format(new Date()) + "] [" + level + "] [" + tag + "] " + message);
	}

	public static void info(Object tag, Object message) {
		log(Level.INFO, tag, message);
	}

	public static void warning(Object tag, Object message) {
		log(Level.WARNING, tag, message);
	}

	public static void severe(Object tag, Object message) {
		log(Level.SEVERE, tag, message);
	}

	public static void l(Level level, Object tag, Object message) {
		log(level, tag, message);
	}

	public static void i(Object tag, Object message) {
		log(Level.INFO, tag, message);
	}

	public static void w(Object tag, Object message) {
		log(Level.WARNING, tag, message);
	}

	public static void s(Object tag, Object message) {
		log(Level.SEVERE, tag, message);
	}
}
