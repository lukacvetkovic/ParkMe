package parkme.projectm.hr.parkme.Helpers;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by Cveki on 17.12.2014..
 */
public class PostRestService extends AsyncTask<Void, Void, String> {

    public String data = null;
    public String adresaNaKojuPostam;
    public JSONObject noviObjekt;




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
        StringEntity input = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost postRequest = new HttpPost(adresaNaKojuPostam);
        postRequest.addHeader("Content-Type", "application/json");
        postRequest.addHeader("Accept", "application/json");
        try{
            input =new StringEntity(noviObjekt.toString());
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        postRequest.setEntity(input);
        String text = null;
        try {
            HttpResponse response = httpClient.execute(postRequest, localContext);
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


    public JSONObject getNoviObjekt() {
        return noviObjekt;
    }

    public void setNoviObjekt(JSONObject noviObjekt) {
        this.noviObjekt = noviObjekt;
    }

    public String getAdresaNaKojuPostam() {
        return adresaNaKojuPostam;
    }

    public void setAdresaNaKojuPostam(String adresaNaKojuPostam) {
        this.adresaNaKojuPostam = adresaNaKojuPostam;
    }
}
