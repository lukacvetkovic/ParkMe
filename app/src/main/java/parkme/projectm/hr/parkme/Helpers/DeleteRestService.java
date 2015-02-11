package parkme.projectm.hr.parkme.Helpers;

/**
 * Created by Cveki on 27.11.2014..
 */

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


public class DeleteRestService extends AsyncTask<Void, Void, String> {


    public String data = null;
    public JSONObject json;
    public String adresaSKojeUziamam;


    protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
        InputStream in = entity.getContent();
        StringBuffer out = new StringBuffer();
        int n = 1;
        while (n > 0) {
            byte[] b = new byte[4096];
            n = in.read(b);
            if (n > 0) out.append(new String(b, 0, n));
        }
        return out.toString();
    }

    @Override
    protected String doInBackground(Void... params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpDelete httpDelete = new HttpDelete(adresaSKojeUziamam);
        httpDelete.addHeader("accept", "application/json");
        String text = null;
        try {
            HttpResponse response = httpClient.execute(httpDelete, localContext);
            HttpEntity entity = response.getEntity();
            text = getASCIIContentFromEntity(entity);
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
        return text;
    }

    protected void onPostExecute(String results) {
        if (results != null) {
            data = results;
        }


    }


    public String getData() {
        return data;
    }


    public void obrisi(String URL) {
        adresaSKojeUziamam=URL;
        execute();
    }
}

