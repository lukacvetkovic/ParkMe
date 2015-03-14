package parkme.projectm.hr.parkme.Helpers;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class GetRestService extends AsyncTask<Void, Void, String> {

    String url;
    OkHttpClient client;


    public GetRestService(String url) {
        this.url = url;

        this.client = new OkHttpClient();
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}

