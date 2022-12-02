import static org.junit.Assert.*;

import org.junit.*;
import java.util.List;
import junit.framework.TestResult;

public class FileSystemTest {
    public String inputText = "C:\\Users\\adria\\Desktop\\Coding Stuff\\CSE 12\\cse12-pa7-BST\\pa7-starter\\input.txt";
    public String inputText2 = "C:\\Users\\adria\\Desktop\\Coding Stuff\\CSE 12\\cse12-pa7-BST\\pa7-starter\\input2.txt";

    @Test
    public void testDuplicate() {
        FileSystem test = new FileSystem(inputText);
        String temp = test.outputDateTree().toString();
      //  System.out.println(temp);

        test.add("mySample.txt", "/home", "2021/02/01");
        String testResult = test.outputDateTree().toString();
       // System.out.println(testResult);
        assertEquals(temp, testResult);
       // System.out.println("------SHOULD BE THE SAME ^^^--------");

        test.add("mySample.txt", "/home", "2021/03/25");
        //System.out.println(test.outputDateTree());
        // System.out.println(test.dateTree.toString(test.dateTree.root));

    }

    @Test
    public void testOutput() {
        FileSystem test = new FileSystem(inputText);
        List<String> testResult = test.outputNameTree();
        //String expected = "[mySample.txt: {Name: mySample.txt, Directory: /home, Modified Date: 2021/02/01}, mySample1.txt: {Name: mySample1.txt, Directory: /root, Modified Date: 2021/02/01},mySample2.txt: {Name: mySample2.txt, Directory: /user, Modified Date: 2021/02/06}]";
        System.out.println(testResult.toString());
        //assertEquals(expected, testResult.toString());
        //["mySample.txt: {Name: mySample.txt, Directory: /home, Modified Date: 2021/02/01}", "mySample1.txt: {Name: mySample1.txt, Directory: /root, Modified Date: 2021/02/01}", "mySample2.txt: {Name: mySample2.txt, Directory: /user, Modified Date: 2021/02/06}"]
        //[mySample.txt: {Name: mySample.txt, Directory: /home, Modified Date: 2021/02/01}, mySample1.txt: {Name: mySample1.txt, Directory: /root, Modified Date: 2021/02/01}, mySample2.txt: {Name: mySample2.txt, Directory: /user, Modified Date: 2021/02/06}]
    
    }

}