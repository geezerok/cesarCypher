/**
 * 
 */
package com.antonov.decypher;

/**
 * @author OlegAntonov
 *
 */
public abstract class Alphabet {
	
	private static final char[] alphabet = new char[] { 'a', 'b', 'c', 'd',
		   'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
		   'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' ', '.', ',','?', '!','-' };

	public static char[] getAlphabet() {
		return alphabet;
	}

}
