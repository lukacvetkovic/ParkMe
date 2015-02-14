package parkme.projectm.hr.parkme.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

import parkme.projectm.hr.parkme.Helpers.HttpManager;
import parkme.projectm.hr.parkme.Helpers.Pinning;
import parkme.projectm.hr.parkme.R;

/**
 * Created by Cveki on 13.2.2015..
 */
public class Test extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        Pinning pinning= new Pinning(this);
        pinning.execute();

    }
}
