package parkme.projectm.hr.parkme.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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
    Spinner citySpinner;
    String[]cityNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_payment);
        citySpinner=(Spinner)findViewById(R.id.spinnerCity);
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
            Toast toast = Toast.makeText(this, "Neuspjelo dohvačanje podataka", Toast.LENGTH_LONG);
            toast.show();
            finish();
        }

        cityNames=new String[cityList.size()];

        for(int i=0, z=cityList.size();i<z;++i){
            cityNames[i]=cityList.get(i).getName();
        }



        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityNames);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        citySpinner.setAdapter(adapter);



    }
}
