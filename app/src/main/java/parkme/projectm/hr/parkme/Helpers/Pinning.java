package parkme.projectm.hr.parkme.Helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import parkme.projectm.hr.parkme.R;

import static org.apache.http.conn.ssl.SSLSocketFactory.getSocketFactory;


/**
 * Created by martin on 02/06/14.
 */
public class Pinning extends AsyncTask<Void, Void, String> {

    Context context;
    public static String TRUST_STORE_PASSWORD = "lumipex";
    private static final String ENDPOINT = "https://178.62.239.175:8000/ParkMe/api/data/zone.json";

    public Pinning(Context c) {
        this.context = c;
    }

    private SSLSocketFactory getPinnedCertSslSocketFactory(Context context) {
        try {
            KeyStore trusted = KeyStore.getInstance("BKS");
            InputStream in = context.getResources().openRawResource(R.raw.apptruststore);
            trusted.load(in, TRUST_STORE_PASSWORD.toCharArray());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trusted);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            Log.e("MyApp", e.getMessage(), e);
        }
        return null;

    }

    @Override
    protected String doInBackground(Void... params) {
        /*Response response = null;
        try {
            OkHttpClient client = new OkHttpClient();
            client.setSslSocketFactory(getPinnedCertSslSocketFactory(context));

            Request request = new Request.Builder()
                    .url(ENDPOINT)
                    .build();

            response = client.newCall(request).execute();

            Log.d("MyApp", response.body().string());

        } catch (Exception e) {
            Log.e("MyApp", e.getMessage(), e);

        }

        try {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;*/

        HttpGet httpGet = new HttpGet("https://178.62.239.175:8000/ParkMe/api/data/zone.json");
        httpGet.addHeader("accept", "application/json");
        String rez=null;

        try {
            rez=HttpManager.execute(httpGet).toString();
            Log.d("MOLI TE RADI",rez.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  rez;

    }


}
