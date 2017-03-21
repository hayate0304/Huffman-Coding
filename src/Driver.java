import java.io.FileNotFoundException;
import java.io.IOException;

public class Driver {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		Huffman tree = new Huffman("write2.txt");
		
		tree.compress("write2.txt", "extraout.txt");
		System.out.println(tree.toString());
		
//		tree.decompress("extraout.txt", "extraout2.txt");
	}

}
