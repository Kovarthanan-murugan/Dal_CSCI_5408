package org.example.WriteFile;

import org.example.readfromfile.ReadFromFile;

import java.util.HashMap;
import  java.io.*;
public class WriteDataInFile{

    /** This method creates directory for each keyword records fetched from the api and create a new file for each five records and write it
     * @param titleContent Values of tittle and content of the records fetched from the api.
     * @param keyWord name of the searched record
     */
    public void print(HashMap<String,String> titleContent,String keyWord) throws IOException {
        System.out.println("inside Write");
        String path = "E:/Dalhousie/Class-jan-april/Data-management-CSCI5408/Assignment2/WrittenData/"+keyWord;

        File dir = new File(path);

        boolean success = dir.mkdirs();
        boolean createNewFile = true;
        int filecount =1;
        int articleCount =1;
        File newfile = null;
        String WriteString ="";
        int fileNumberCount =1;
        ReadFromFile readFromFile_obj = new ReadFromFile();
        for (String tittle:titleContent.keySet()){

            System.out.println("kova");
            if (createNewFile==true){
                System.out.println("inside create file>>"+fileNumberCount);

                newfile = new File(path+'/'+keyWord+' '+fileNumberCount+".txt");
                WriteString ="";
                createNewFile = false;
                fileNumberCount++;
            }

            FileWriter fileWrite =  new FileWriter(newfile);

            WriteString+= "{"+tittle+" >> "+titleContent.get(tittle)+"}\n\n";

            fileWrite.write(WriteString);
            fileWrite.close();
            articleCount++;
            filecount++;
            if(articleCount >= 5){
                createNewFile= true;
                articleCount = 0;

            }
           int fileNumber = fileNumberCount-1;

        }
        readFromFile_obj.readFileData(path+'/',keyWord);


    }
}
