/*  Java Class:	Huffman
    Author:		Kien Nguyen
    Class:		CPE 103
    Date:		11/28/2016
    Description:	 Huff Man

    I certify that the code below is my own work.

	Exception(s): N/A

*/

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class HuffmanExtra {
	private PriorityQueue<Node> tree = new PriorityQueue<Node>();
	private HashMap<Character, Integer>  map = new HashMap<Character, Integer>();
	private HashMap<Character, String>  map2 = new HashMap<Character, String>();
	
	public HuffmanExtra(String fileName) throws FileNotFoundException, IOException {
		BufferedReader fr = new BufferedReader(new FileReader(fileName));
		
		int c = 0;
		
		// Map keep track of occurs
		while ((c = fr.read()) != -1){
			if (map.containsKey((char) c)){
				int m = map.get((char) c) + 1;
				
				map.put((char) c, m);
			}
			else {

				map.put((char) c, 1);
			}
		}

		// Create new Node 
		for (char key : map.keySet()){
			Node newN = new Node(key, map.get(key));
			System.out.print(key);
			System.out.println(map.get(key));

			tree.add(newN);
//			System.out.println(tree.peek().minASCII);
		}
		
		Node EOT = new Node((char) 4, 1);
		tree.add(EOT);
		
		// Create a Complete Tree
		while (tree.size() > 1){
			Node n1 = (Node) tree.poll();
			Node n2 = (Node) tree.poll();
			
			// New node has occurs of total n1 + n2
			Node newN = new Node((n1.occur + n2.occur));
			
			// The lesser goes to the left
			if (n1.compareTo(n2) < 0){
				newN.left = n1;

				newN.right = n2;

			}
			else{
				newN.left = n2;

				newN.right = n1;

			}
			
			// Keep track of the minASCII
			if (n1.minASCII < n2.minASCII){
				newN.minASCII = n1.minASCII;
			}
			else{
				newN.minASCII = n2.minASCII;
			}			
			tree.add(newN);
		}		
		
		translate("", tree.peek());
		fr.close();
	}

	// Decompress
	public void decompress(String infileName,String outfileName) throws FileNotFoundException,IOException{
		InputStream inStream = new FileInputStream(infileName);
		BufferedInputStream fr = new BufferedInputStream(inStream);
		
		BufferedWriter wr = new BufferedWriter(new FileWriter(outfileName));
		
		StringBuilder s = new StringBuilder();
		Node temp = tree.peek();
		
		int c = 0;
		
		// String was: "010101111100" . Now is "WA" with "0101011111000000"
		while ((c = fr.read()) != -1){
//			System.out.println(c);
			for (int i = 7; i >= 0; i--){
				// Right shift i bits
				int newVal = (c >> i) & 1;
				
//				System.out.println(newVal);
				
				if (newVal == 0){
					temp = temp.left;
					System.out.println(c);
				}
				else if (newVal == 1){
					temp = temp.right;
					System.out.println(c);
				}
				
				if (temp.left == null && temp.right == null){
					System.out.println(temp.element);
					if (temp.element == (char) 4){
						break;
					}
					
					s.append(temp.element);
	//				System.out.println(s);
					temp = tree.peek();
				}
			}
		}
		
		// Write to file
		wr.write(s.toString());
		
		fr.close();
		wr.close();
	}
		
	// Compress
	public void compress(String infileName, String outfileName)throws FileNotFoundException,IOException{
		BufferedReader fr = new BufferedReader(new FileReader(infileName));
		
		OutputStream outStream = new FileOutputStream(outfileName);
		BufferedOutputStream wr = new BufferedOutputStream(outStream);
		
		StringBuilder s = new StringBuilder();
		int value = 0;
		int count = 0; // keep track of 32 bits
		int c = 0;	
		
		// Read from file
		while ((c = fr.read()) != -1){
			s.append(map2.get((char) c));
		}

		s.append(map2.get((char) 4));
		
//		System.out.println(s);
//		System.out.println("Length: " + s.length());
		
		// String right now is: "010101111100"
		for (int i = 0; i < s.length(); i++){
//			System.out.println(i);
			count++;
			
			if (s.charAt(i) == '0'){
				value = value | 0;
//				System.out.println(value);
			}			
			else {
				value = value | 1;
//				System.out.println(value);

			}
			
			if (count == 8){
//				System.out.println("Fill:" + value);
				wr.write(value);
				
				value = 0;
				count = 0;
			}
			
			value = value << 1;
//			System.out.println(value);

			if (i == s.length()-1){

				// Set the rest of the byte to be 0, push the bits up front
				for (int j = count; j < 7; j++)
					value = value << 1;
				
				value = value | 0;
//				System.out.println(value);
				wr.write(value);
				
				break;
			}									
		}
			
		fr.close();
		wr.close();
	}
	
	// Private helper recursive for Compress
	private void translate(String str, Node node){
		if (node.left == null && node.right == null){
			map2.put(node.element, str );
		}
		
		if (node.left != null){
			translate(str + "0", node.left);
		}
		
		if (node.right != null){
			translate(str + "1", node.right);
		}
	}
	
	
	// ToString method
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append("|");
		helper(s,tree.peek());
		s.append("|");
		return s.toString();
	}
	
	private void helper(StringBuilder s, Node node){
		if (node.left == null && node.right == null){
			s.append(node.element);
		}
		
		if (node.left != null){
			helper(s, node.left);
		}
		
		if (node.right != null){
			helper(s, node.right);
		}
	}
	
	
	// Node class
	private class Node implements Comparable<Node>{
		Character element;
		Node left;
		Node right;
		Integer occur; 		// Hold the occurrences
		Character minASCII;
		
		public Node(char e, int occurrences){
			this.element = e;
			this.occur = occurrences;
			minASCII = e;
			left = null;
			right = null;
		}
		
		public Node(int occurrences){
			this.occur = occurrences;
			minASCII = null;
			left = null;
			right = null;
		}
		
		// Override
		public int compareTo(Node node) {
			// If occurrences is equal, then compare the minASCII
			if (this.occur.equals(node.occur)){

				return this.minASCII.compareTo(node.minASCII);				
			}		
			// When occurs is not equal
			else {
				return this.occur.compareTo(node.occur);
			}
			
		}
	}
}
