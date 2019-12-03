/*
	TrieHashmap.java
	Author: Max Copeland
	"I pledge my honor that I have abided by the Stevens Honor System"
	Help from Brian Bazergui in understanding what's kind of going on
	This doesn't really work but I tried
*/

import java.io.IOException;
import java.io.File;
import java.lang.Character;
import java.util.Scanner;

public class TrieHashMap {
	// Private objects
    private class Node {
		// Node class
		public char letter;
		public Node parent;
		public Node[] children = new Node[26];
		public boolean isEnd = false;

		public Node(char l, Node p) {
			// Constructor for Node
			letter = l;
			parent = p;
		}
	}
	private Node root;
	private String word = "";

	// Public objects
	public TrieHashMap() {
		root = null;
	}

	public void add(String word) {
		// Add a word into the Trie
		Node p = root;
		char letter;
		int index;

		if (p == null) {
			// First word in the Trie
			root = new Node ('R', null);
			p = root;
		}

		for (int i = 0; i < word.length(); i++) {
			letter = word.charAt(i);
			index = letter - 'a';
			if (p.children[index] == null) {
				p.children[index] = new Node(letter, p);
			}
			p = p.children[index];
		}
		p.isEnd = true;
	}

	public boolean search(String word) {
		// Search for a word in the Trie
		Node p = root;
		char letter;
		int index;

		for (int i = 0; i < word.length(); i++) {
			letter = word.charAt(i);
			index = letter - 'a';
			if (p.children[index] == null) {
				return false;
			}
			p = p.children[index];
		}
		return p.isEnd;
	}

	public void display() {
		// Display the Trie
		Node p = root;
		char letter;

		for (int i = 0; i < 26; i++) {
			if (p.children[i] == null) {
				// Skip it
				continue;
			}
			else {
				if (p.children[i].isEnd) {
					// Print the word
					letter = p.children[i].letter;
					word += letter;
					System.out.println(word);
					word = ""; // Clear word
				}
				else {
					display(p.children[i]);
				}
			}
			word = ""; // Clear word
		}
	}

	public void display(Node start) {
		// Helper function for display
		Node p = start;
		char letter;

		for (int i = 0; i < 26; i++) {
			if (p.children[i] == null) {
				// Skip it
				continue;
			}
			else {
				letter = p.children[i].parent.letter;
				word += letter;
				if (p.children[i].isEnd) {
					letter = p.children[i].letter;
					word += letter;
					System.out.println(word);
				}
				else {
					display(p.children[i]);
				}
			}
			word = word.substring(0,word.length()-1); // Remove last character
		}
	}

	public static void main(String[] args) throws IOException {
		TrieHashMap t = new TrieHashMap();
		String temp;
		String found = "";

		// Load Dictionary
		Scanner dict = new Scanner(new File("./dictionary.txt"));
		while (dict.hasNextLine()) {
			t.add(dict.nextLine());
		}
		dict.close();

		System.out.println("Scanned Dictionary");
		// Check words
		Scanner words = new Scanner(new File("./words.txt"));
		while (words.hasNextLine()) {
			temp = words.nextLine();
			if (!t.search(temp)) {
				// Word is not in the dictionary
				System.out.println(temp + " was not found");
			}
		}
		words.close();
		System.out.println("Scanned Words");
		// Display dictionary
		System.out.println("Dictionary:");
		t.display();
	}
}