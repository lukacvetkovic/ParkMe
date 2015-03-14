package parkme.projectm.hr.parkme.Helpers;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class GetRestService {

    String url;
    OkHttpClient client;


    public GetRestService(String url) {
        this.url = url;

        this.client = new OkHttpClient();
    }

    public String execute() throws ExecutionException, InterruptedException {
        GetWorker getWorker= new GetWorker();
        return getWorker.execute().get();

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private class GetWorker extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            Request request = new Request.Builder()
                    .url(url)
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



}

