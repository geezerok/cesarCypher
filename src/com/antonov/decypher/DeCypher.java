package com.antonov.decypher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * main goal of this task is to find a key to decode message
 * 
 * @author OlegAntonov
 * 
 */
public class DeCypher {

	/**
	 * Default constructor that initializes fields
	 */
	public DeCypher() {
		knownWords = new TreeSet<String>();
		linesFromMessage = new ArrayList<String>(0);
		keyToStrings = new HashMap<Integer, ArrayList<String>>(0);
	}

	/**
	 * Does all logic for read and decode the message
	 * 
	 */
	void deCypherFromFiles() {

		knownWords = readManuscriptData(Helper.openFile("Open Manuscript"));
		linesFromMessage = readMessageData(Helper.openFile("Open encoded file"));
		keyToStrings = generateMapOfEncodedLines(linesFromMessage);

		int finalKey = findKeyToEncodeMessage();

		printResult(finalKey);

		saveOutputIntoFile(Helper.saveFile("Select output file"), keyToStrings,
				finalKey);

	}

	/**
	 * Finds matches for each possible keys
	 * 
	 * @return key with max matches
	 */
	private int findKeyToEncodeMessage() {
		int maxMatches = 0;
		int result = 0;

		for (int i = 0; i < Alphabet.getAlphabet().length; i++) {
			int matches = 0;
			System.out.println("processing key = " + i);
			matches = getAmountOfMatchesInListOfStrings(keyToStrings.get(i));

			if (matches > maxMatches) {
				maxMatches = matches;
				result = i;
			}
		}
		return result;
	}

	/**
	 * Prints key to console
	 * 
	 * @param finalKey
	 */
	private void printResult(int finalKey) {
		System.out.println("key found, key is " + finalKey);
	}

	/**
	 * Reads words from source-file and composes vocabulary
	 * 
	 * @param file
	 *            - document with known data
	 * 
	 * @return TreeSet<String> sorted set of words with 3 or more letters
	 */
	private Set<String> readManuscriptData(File file) {
		Set<String> result = new TreeSet<String>();

		try {
			File manuscript = file;
			FileReader fr = new FileReader(manuscript);
			BufferedReader br = new BufferedReader(fr);
			String s;

			while ((s = br.readLine()) != null) {
				String[] words = s.split("\\b\\s*\\.*\\!*\\?*[--]*(\\s\\.)*");
				// System.out.println(words[0]);
				for (String string : words) {
					if (string.length() > 2) {
						result.add(string);
					}

				}
			}
			
			br.close();

		} catch (IOException e) {

		}

		return result;

	}

	/**
	 * Reads data from encoded file and puts it into list of strings
	 * 
	 * @param file
	 *            - document with encoded data
	 * 
	 * @return List with lines from file
	 */
	private List<String> readMessageData(File file) {
		List<String> result = new ArrayList<String>(0);
		try {
			File message = file;
			FileReader fr = new FileReader(message);
			BufferedReader br = new BufferedReader(fr);
			String s;

			while ((s = br.readLine()) != null) {
				result.add(s);
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * Saves result for final key into the file
	 * 
	 * @param f
	 *            - output file
	 * @param keyToStrings
	 *            - map with strings
	 * @param finalKey
	 */
	private void saveOutputIntoFile(File f,
			Map<Integer, ArrayList<String>> keyToStrings, int finalKey) {

		try {
			File newFile = f;

			FileWriter fw = new FileWriter(newFile);
			PrintWriter pw = new PrintWriter(fw);

			for (String string : keyToStrings.get(finalKey)) {

				pw.print(string);
				pw.print("\n");

			}

			pw.flush();
			pw.close();

		} catch (IOException e) {

		}
	}

	/**
	 * Generates map with lists of lines for different keys
	 * 
	 * @param sourceListOfLines
	 *            List of lines from encoded file
	 * @return map with "keys" for keys and lists of lines for elements
	 */
	private Map<Integer, ArrayList<String>> generateMapOfEncodedLines(
			List<String> sourceListOfLines) {

		Map<Integer, ArrayList<String>> result = new HashMap<Integer, ArrayList<String>>(
				0);
		int index = 0;
		char[] lineTochar;
		char[] alphabet = Alphabet.getAlphabet();
		int maxKey = alphabet.length;
		char[] newLine;
		String s;

		for (int key = 0; key < maxKey; key++) {
			result.put(key, new ArrayList<String>());
			for (String string : sourceListOfLines) {
				lineTochar = string.toCharArray();
				newLine = new char[lineTochar.length];
				// iterate through line and generate new line with shift on key
				for (int i = 0; i < lineTochar.length; i++) {
					for (int j = 0; j < maxKey; j++) {
						if (lineTochar[i] == alphabet[j]) {
							index = (((j + key) >= maxKey) ? (j + key - maxKey)
									: (j + key));
							newLine[i] = alphabet[index];
							break;
						}
					}
				}
				s = String.copyValueOf(newLine);

				result.get(key).add(s);

			}

		}
		return result;
	}

	/**
	 * Iterates through List of strings and counts matches for words from
	 * vocabulary
	 * 
	 * @param source
	 *            - List of strings
	 * @return amount of matches
	 */
	private int getAmountOfMatchesInListOfStrings(ArrayList<String> source) {
		Pattern p;
		Matcher m;
		int result = 0;

		for (String line : source) {
			for (String words : knownWords) {
				p = Pattern.compile(words);
				m = p.matcher(line);
				while (m.find()) {
					result++;
				}

			}
		}

		return result;

	}

	private Set<String> knownWords;
	private List<String> linesFromMessage;
	private Map<Integer, ArrayList<String>> keyToStrings;

	

}


