/*
	LinkedList.java
	Author: Max Copeland
	"I pledge my honor that I have abided by the Stevens Honor System"
	Using LinkedList class from Dov Kruger's Github
	Pseudocode for reverse function found on from geeksforgeeks.org
*/
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class LinkedList {
	private static class Node {
		int val;
		Node next;
		public Node(int v, Node n) { val = v; next = n; }
	}
	private Node head;
	private Node tail;

	public LinkedList() {
		head = tail = null;
	}

	public void addStart(int v) {
		head = new Node(v, head);
		if (tail == null)
			tail = head;
	}

	public void addEnd(int v) {
		if (tail == null) {
			// New List
			head = tail = new Node(v, null);
			return;
		}
		tail.next = new Node(v, null);
		tail = tail.next;
	}

	public void insert(int pos, int val) {
		if (head == null) {
			head = tail = new Node(val, null);
			return;
		}
		Node p = head;
		for (; pos > 0; pos--) { p = p.next; }
		p.next = new Node(val, p.next);
	}

	public boolean isEmpty() {
		return head == null;
	}

	public void removeStart() {
		if (head == tail) {
			head = tail = null;
			return;
		}
		head = head.next;
	}

	public void removeEnd() {
		Node p = head;
		if (p == null) { return; } // Empty list --> Do nothing
		if (p.next == null) { // Single element
			p = null;
			return;
		}
		while (p.next != null) { p = p.next; }
		p = null;
	}

	public void remove(int pos) {
		// Remove a node from a specific position
		if (head == null) { return; }
		Node p = head;
		if (pos == 0) {
			removeStart();
			return;
		}
		while (pos > 1) { 
			p = p.next; 
			pos--;
		}
		if (p == tail) { 
			p.next = null;
			return;
		}
		p.next = p.next.next;
	}
	
	public void reverse() {
		Node prev = null; 
		Node next = null;
        Node current = head; 
        while (current != null) {
			next = current.next;
			current.next = prev;
			prev = current;
			current = next;
		}
		head = prev;
	}

	public void print() {
		Node p = head;
		if (p == null) { return; }
		while (p != null) {
			System.out.print(p.val + " ");
			p = p.next;
		}
		System.out.println();
	}

	public static void main(String[] args) throws IOException {
		LinkedList list = new LinkedList();
		// Read in the data
		Scanner data = new Scanner(new File("./linkedlist.dat"));
		int n = data.nextInt();
		data.close();

		// Create the list
		for (int i = 1; i <= n; i++) {
			list.addEnd(i);
		}
		// Now have a complete list from 1 to n
		
		// Print the list
		list.print();
		// Remove nodes whose index mod 3 = 0
		int j = 0;
		for (int i = 0; i < n; i+=3) {
			list.remove(i - (i/3)); // Account for progressive shortening of the list
			j++;
		}
		list.print();

		// Do it again
		n -= j;
		for (int i = 0; i < n; i+=3) {
			list.remove(i - (i/3)); // Account for progressive shortening of the list
			j++;
		}
		list.print();

		// Reverse the list
		list.reverse();
		list.print();
	}
}