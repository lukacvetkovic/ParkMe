package parkme.projectm.hr.parkme.Helpers;

import android.os.AsyncTask;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Cveki on 17.12.2014..
 */
public class PostRestService extends AsyncTask<Void, Void, String> {
    String url;
    OkHttpClient client;
    String json;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=unicode");


    public PostRestService(String url,String json) {
        this.url = url;
        this.json=json;
        this.client = new OkHttpClient();
    }

    @Override
    protected String doInBackground(Void... params) {

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response!=null){
            try {
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;

    }


}
