/*  Java Class:	T Sort Tests
    Author:		Kien Nguyen
    Class:		CPE 103
    Date:		12/01/2016
    Description:	Test T Sort

    I certify that the code below is my own work.

	Exception(s): N/A

*/

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.Test;

public class HuffmanTests {
	@Test
	public void test1() throws FileNotFoundException, IOException{
		// "abcd abc ab a"
		Huffman tree = new Huffman("input.txt");
		File file = new File("write.txt");
		
		tree.compress("input.txt", "write.txt");
		Scanner scan = new Scanner(file);
		
//		System.out.println(tree.toString());
		assertEquals(scan.next(),
				"11011011000011011010011010011");
	}
	
	@Test
	public void test2() throws FileNotFoundException, IOException{
		Huffman tree = new Huffman("input.txt");

		tree.decompress("write.txt", "output.txt");
		File file = new File("output.txt");
		Scanner scan = new Scanner(file);
		
		assertEquals(scan.next(),"abcd");
		assertEquals(scan.next(),"abc");
		assertEquals(scan.next(),"ab");
		assertEquals(scan.next(),"a");
	}
	
	@Test
	public void test3()throws FileNotFoundException, IOException{
		// aaabbc
		Huffman tree = new Huffman("textfile.txt");

		assertEquals(tree.toString(), "|acb|");
	}
	
	@Test
	public void test4() throws FileNotFoundException, IOException{
		// "abcd abc ab"
		Huffman tree = new Huffman("input2.txt");
		File file = new File("write.txt");

		tree.compress("input2.txt", "write.txt");
		Scanner scan = new Scanner(file);
		
//		System.out.println(tree.toString());
		assertEquals(scan.next(),
				"1011000100111011000111011");
		
		assertEquals(tree.toString(), "|cd ab|");
	}
/*	
	@Test
	public void test5() throws FileNotFoundException, IOException{
		// aaaaaa
		Huffman tree = new Huffman("input3.txt");
		File file = new File("write.txt");
		
		tree.compress("input3.txt", "write.txt");
//		assertEquals(tree.toString(), "|a|");
		System.out.println(tree.toString());
		
	}
*/
}
