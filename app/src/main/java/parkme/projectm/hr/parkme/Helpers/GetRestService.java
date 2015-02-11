package parkme.projectm.hr.parkme.Helpers;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


public class GetRestService extends AsyncTask<Void, Void, String> {


    public String data = null;
    public JSONObject json;
    public String adresaSKojeUziamam;

    public String getAdresaSKojeUziamam() {
        return adresaSKojeUziamam;
    }

    public void setAdresaSKojeUziamam(String adresaSKojeUziamam) {
        this.adresaSKojeUziamam = adresaSKojeUziamam;
    }


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
        HttpGet httpGet = new HttpGet(adresaSKojeUziamam);
        httpGet.addHeader("accept", "application/json");
        String text = null;
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);
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


    public void dohvati(String URL) {
        adresaSKojeUziamam=URL;
        execute();
    }
}

