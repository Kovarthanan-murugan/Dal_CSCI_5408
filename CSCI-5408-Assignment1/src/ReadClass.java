import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadClass {
    /**
     * This method is used to read the existing files with contains tables.
     * @param filename name of the file.
     */
   static String read(String filename) {
       String resultString = "";
        try {
            File myObj = new File(filename+".txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                resultString += data;
                //System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    return  resultString;
   }
}