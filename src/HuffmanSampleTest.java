/**
 * Trivial JUnit test for Huffman coding.
 *
 * @author Brian Jones
 * @version 11/11/2016 Developed for CPE 103 Program 6
 */
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.runners.MethodSorters;
import java.io.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HuffmanSampleTest {
   // This test has no assertions because it's not testing correctness.  Feel
   // free to modify this test to assert correctness.  Storing the results is
   // done for compile time type checking of your return types.
   @Test
   public void test01_correctMethodNames() throws FileNotFoundException,
          IOException {
      // You'll need textfile.txt in the same directory
      Huffman h = new Huffman("textfile.txt");
      h.compress("textfile.txt", "compressed.txt");
      h.decompress("compressed.txt", "textfile_copy.txt");
      String s = h.toString();
   }
} 