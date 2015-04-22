package parkme.projectm.hr.parkme.Helpers.Rest;

import android.os.AsyncTask;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Cveki on 17.12.2014..
 */
public class PostRestService {
    String url;
    OkHttpClient client;
    String json;
    public static final MediaType JSON
            = MediaType.parse("Content-Type=application/json; charset=unicode");


    public PostRestService(String url, String json) {
        this.url = url;
        this.json = json;
        this.client = new OkHttpClient();
    }

    public String execute() throws ExecutionException, InterruptedException {
        PostWorker postWorker = new PostWorker();
        return postWorker.execute().get();

    }

    private class PostWorker extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .method("POST",body)
                    .addHeader("Content-Type","application/json")
                    .addHeader("charset","unicode")
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response != null) {
                try {
                    return response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;

        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
