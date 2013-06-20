/**
 * 
 */
package com.antonov.decypher;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author OlegAntonov
 * 
 */
public class Helper {

	public static String getUserInput(String prompt) {
		String inputLine = null;
		System.out.print(prompt + "  ");
		try {
			BufferedReader is = new BufferedReader(new InputStreamReader(
					System.in));
			inputLine = is.readLine();
			if (inputLine.length() == 0)
				return null;
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
		return inputLine.toLowerCase();
	}

	/**
	 * Pops up file choosing dialog
	 * @param windowTitle title of dialog window
	 * @return File object associated with chosen file  
	 */
	public static File openFile(String windowTitle) {
		File result;
		JFileChooser chooser = new JFileChooser("./");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"TXT - files", "txt");
		chooser.setFileFilter(filter);
		chooser.setDialogTitle(windowTitle);
		int returnVal = chooser.showOpenDialog(new Frame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			result = chooser.getSelectedFile();
		} else {
			result = null;
		}

		return result;

	}

	/**
	 * Pops up file saving dialog
	 * @param windowTitle title of dialog window
	 * @return File object associated with chosen file  
	 */
	public static File saveFile(String windowTitle) {
		File result;
		JFileChooser chooser = new JFileChooser("./");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"TXT - files", "txt");
		chooser.setFileFilter(filter);
		chooser.setDialogTitle(windowTitle);
		int returnVal = chooser.showSaveDialog(new Frame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    result = chooser.getSelectedFile();
		} else {
			result = null;
		}

		return result;

	}

}
