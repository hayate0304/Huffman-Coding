/*  Java Class:	Huffman
    Author:		Kien Nguyen
    Class:		CPE 103
    Date:		11/28/2016
    Description:	 Huff Man

    I certify that the code below is my own work.

	Exception(s): N/A

*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;


public class Huffman {
	private PriorityQueue<Node> tree = new PriorityQueue<Node>();
	private HashMap<Character, Integer>  map = new HashMap<Character, Integer>();
	private HashMap<Character, String>  map2 = new HashMap<Character, String>();
	
	public Huffman(String fileName) throws FileNotFoundException, IOException {

		BufferedReader fr = new BufferedReader(new FileReader(fileName));	
		int c = 0;
		
		// Map keep track of occurs
		while ((c = fr.read()) != -1){
			if (map.containsKey((char) c)){
				int m = map.get((char) c) + 1;
				
				map.put((char) c, m);
			}
			else {
//				System.out.println((char) c);
				map.put((char) c, 1);
			}
		}

		// Create new Node 
		for (char key : map.keySet()){
			
			Node newN = new Node(key, map.get(key));	
			tree.add(newN);

//			System.out.println("Key: " + key);
//			System.out.println("Value: " +  map.get(key));

//			System.out.println(tree.peek().minASCII);
		}
		
		// Create a Complete Tree
		while (tree.size() > 1){
			Node n1 = tree.poll();
	//		System.out.println(n1.minASCII);
			Node n2 = tree.poll();
	//		System.out.println(n2.minASCII);

			// New node has occurs of total n1 + n2
			Node newN = new Node((n1.occur + n2.occur));
			
			// The lesser goes to the left
/*			if (n1.compareTo(n2) < 0){
				newN.left = n1;

				newN.right = n2;

			}
			else{
				newN.left = n2;

				newN.right = n1;

			}
*/
			newN.left = n1;
			newN.right = n2;
			
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

	// Decompress
	public void decompress(String infileName,String outfileName) throws FileNotFoundException,IOException{
		BufferedReader fr = new BufferedReader(new FileReader(infileName));
		BufferedWriter wr = new BufferedWriter(new FileWriter(outfileName));
		
		StringBuilder s = new StringBuilder();
		Node temp = tree.peek();
		
		int c = 0;
		
		while ((c = fr.read()) != -1){
//			System.out.println((char) c);
			if ((char) c == '0'){
				temp = temp.left;
//				System.out.println((char) c);
			}
			else if ((char) c == '1'){
				temp = temp.right;
//				System.out.println((char) c);
			}
			
			if (temp.left == null && temp.right == null){
//				System.out.println(temp.element);
				s.append(temp.element);
//				System.out.println(s);
				temp = tree.peek();
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
		BufferedWriter wr = new BufferedWriter(new FileWriter(outfileName));
		
		StringBuilder s = new StringBuilder();
		
		int c = 0;	
		
		// Read from file
		while ((c = fr.read()) != -1){
			
			s.append(map2.get((char) c));
		}
		
		// Write to file
		wr.write(s.toString());
		
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
