package org.example;

import java.io.IOException;

import org.example.NewsAPI.GetData_thread;

public class API {

    /** This method fetches the record from the api and write it into the file and read it and transform it without any special characters*/
    public static void main( String[] args ) throws InterruptedException, IOException {

        String[] keyWord = {"halifax","canada","university","dalhousie","Canada Education","Moncton","hockey","Fredericton","celebration"};
        for(String i:keyWord) {
            Thread thread = new Thread(new GetData_thread(i));
            thread.start();
        }

    }
}