package org.example.readfromfile;

import org.example.mongodb.MongoDbSendData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadFromFile {
    /** This method read the data from the file to update it in the mongodb database.
     * @param fileName path of the file to be read
     * @param keyWord name of the searched record
     */
    public void  readFileData(String fileName,String keyWord){



        try {
            File myObj1 = new File(fileName);

            for (int i = 1; i <= myObj1.list().length; i++) {
                String filename =  fileName + keyWord+" " + i + ".txt";
                File myObj = new File(filename);

                Scanner myReader = new Scanner(myObj);

                String patternString = "\\{([^{}]+)\\}";

                Pattern pattern = Pattern.compile(patternString);

                try {
                    String input = new String(Files.readAllBytes(Paths.get(filename)));
                    Matcher matcher = pattern.matcher(input);
                    while (matcher.find()) {
                        System.out.println(fileName+"--"+i);
                        String fileReadData = matcher.group(1);
                        String output = fileReadData.replaceAll("[{}]|\\s*\\[\\+\\d+ chars\\]|[^a-zA-Z0-9\\s]|:[)(DpP/\\|\\[\\{\\]<3]|[https?://\\\\S+\\\\b]", "");
                        String[] spitData = output.split(">>");
                        MongoDbSendData.updateDataBase(spitData,keyWord);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();

        }


    }
}
