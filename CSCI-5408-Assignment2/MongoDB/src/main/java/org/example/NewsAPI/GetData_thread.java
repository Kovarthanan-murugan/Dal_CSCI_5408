package org.example.NewsAPI;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import org.example.WriteFile.WriteDataInFile;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.HashMap;


public class GetData_thread implements Runnable {

    private final String search;

    private static HashMap<String, String> tittleContent = new HashMap<String, String>();

    public GetData_thread(String search) {
        this.search = search;
    }

    @Override
    public void run() {
        try {
            getResult(this.search);
        }
        catch (InterruptedException | IOException e){


        }
    }

    /** This method connect to the NewsAPI and fetches the record based on the keyword
     * @param search Name of the keyword to be searched and fetched from the API
     */
        public static HashMap<String,String> getResult(String search) throws InterruptedException, IOException {
        NewsApiClient newsApiClient = new NewsApiClient("b5831f23bbac4a878c667c8dc995ecc1");

        CountDownLatch latch = new CountDownLatch(1);
        HashMap<String, String> titleContent = new HashMap<>();

        newsApiClient.getEverything(new EverythingRequest.Builder().q("+"+search).build(), new NewsApiClient.ArticlesResponseCallback() {
            @Override
            public void onSuccess(ArticleResponse response) {
                //System.out.println(response.getArticles().size());
                for (int article=0;article<response.getArticles().size();article++) {
                    titleContent.put(response.getArticles().get(article).getTitle(), response.getArticles().get(article).getContent());
                }
                latch.countDown();
            }

            @Override
            public void onFailure(Throwable throwable) {
                //System.out.println(throwable.getMessage());
                latch.countDown(); // Release the latch to signal that the task is completed
            }
        });

        latch.await(); // Wait for the latch to be released
        WriteDataInFile WriteFile = new WriteDataInFile();
        //System.out.println("titleContent"+titleContent);
        WriteFile.print(titleContent,search);
        return titleContent;
    }




}
