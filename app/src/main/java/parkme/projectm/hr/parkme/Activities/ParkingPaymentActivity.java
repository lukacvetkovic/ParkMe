package parkme.projectm.hr.parkme.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;

import java.util.List;
import java.util.concurrent.ExecutionException;

import parkme.projectm.hr.parkme.Helpers.Constants;
import parkme.projectm.hr.parkme.Helpers.GetRestService;
import parkme.projectm.hr.parkme.Helpers.JavaJsonHelper;
import parkme.projectm.hr.parkme.Models.City;
import parkme.projectm.hr.parkme.R;

public class ParkingPaymentActivity extends Activity {

    GetRestService getRestService;
    String response;
    List<City> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_payment);
        getRestService = new GetRestService(Constants.dohvatiSveGradove);

        try {
            response = getRestService.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (response != null) {
            try {
                cityList = JavaJsonHelper.fromStringToCityList(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("NIJE USPJELO", "!!!!!!!");
        }



    }
}
