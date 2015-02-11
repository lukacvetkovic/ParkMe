package parkme.projectm.hr.parkme.Helpers;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
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
public class PutRestService extends AsyncTask<Void, Void, String>{

    public String data = null;
    public String adresaNaKojojAzuriram;
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
        HttpPut putRequest = new HttpPut(adresaNaKojojAzuriram);
        putRequest.addHeader("Content-Type", "application/json");
        putRequest.addHeader("Accept", "application/json");
        try{
          input =new StringEntity(noviObjekt.toString());
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        putRequest.setEntity(input);
        String text = null;
        try {
            HttpResponse response = httpClient.execute(putRequest, localContext);
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

    public String getAdresaNaKojojAzuriram() {
        return adresaNaKojojAzuriram;
    }

    public void setAdresaNaKojojAzuriram(String adresaNaKojojAzuriram) {
        this.adresaNaKojojAzuriram = adresaNaKojojAzuriram;
    }
}
