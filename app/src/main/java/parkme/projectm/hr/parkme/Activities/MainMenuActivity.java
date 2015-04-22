package parkme.projectm.hr.parkme.Activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.Updater.UpdateManager;
import parkme.projectm.hr.parkme.Database.Updater.UrlUpdateSource;
import parkme.projectm.hr.parkme.Dialogs.AddCarDialog;
import parkme.projectm.hr.parkme.Helpers.PrefsHelper;
import parkme.projectm.hr.parkme.Helpers.Rest.DeleteRestService;
import parkme.projectm.hr.parkme.Helpers.Rest.GetRestService;
import parkme.projectm.hr.parkme.Helpers.Rest.PostRestService;
import parkme.projectm.hr.parkme.Helpers.Rest.PutRestService;
import parkme.projectm.hr.parkme.PomocPOST.marker;
import parkme.projectm.hr.parkme.R;
import parkme.projectm.hr.parkme.Receivers.IncomingSms;

/**
 * Created by Cveki on 11.2.2015..
 */
public class MainMenuActivity extends Activity {

    private static final String TAG = "MainMenu Activity";
    /**
     * Button to go to activity where is swipe menu for choosing favs,cars,...
     */
    private ImageView findParkingButton;
    private ImageView payParkingButton;

    private RelativeLayout rootRelativeView;

    private AddCarDialog addCarDialog;
    private PrefsHelper prefsHelper;

    private Button update;
    private Button post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        DatabaseManager.init(getApplicationContext());


        post = (Button) findViewById(R.id.bPost);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marker m = new marker(1, 1.09, 1.04568, 6);
                Gson g = new Gson();
                String json = g.toJson(m);
                DeleteRestService postRestService = new DeleteRestService("https://lumipex.me/ParkMe/api/data/marker/49.json", json);
                try {
                    String a = postRestService.execute();
                    Log.d("RESP-->", a);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });

        payParkingButton = (ImageButton) findViewById(R.id.imgBtnPayment);

        payParkingButton = (ImageView) findViewById(R.id.imgBtnPayment);

        //Goes to PaymentMenuActivity
        payParkingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //!EXTRA
                Class ourClass = null;
                ourClass = FragmentMenuActivity.class;
                Intent ourIntent = new Intent(MainMenuActivity.this, ourClass);
                startActivity(ourIntent);
            }
        });

        update = (Button) findViewById(R.id.bUpdate);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        try {
                            //EXTRA "update" svega sa neta
                            DatabaseManager.init(getApplicationContext());
                            UpdateManager um = new UpdateManager(
                                    DatabaseManager.getInstance(),
                                    new UrlUpdateSource(new GetRestService(""))
                            );


                            Calendar cal = Calendar.getInstance();
                            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                            String formatted = format1.format(cal.getTime());


                            String lastUpdate = prefsHelper.getString(PrefsHelper.LastUpdate, "NULL");

                            Log.d(lastUpdate, "---> last update");
                            if (lastUpdate == "NULL") {
                                lastUpdate = "2010-01-01";
                            }

                            Log.d("UPDATE FROM", lastUpdate);
                            boolean updated = true;

                            try {
                                um.updateAll(DatabaseManager.dateFormatter.parse(lastUpdate));
                                Log.d("Update", " done");
                            } catch (Exception e) {
                                updated = false;
                            }
                            if (updated) {
                                prefsHelper.putString(PrefsHelper.LastUpdate, formatted);
                                Log.d("LAST UPDATE -->", " " + formatted);
                            }

                        } catch (Exception e) {
                        }

                    }
                });

                thread.start();
            }


        });

        findParkingButton = (ImageView) findViewById(R.id.imgBtnFindParking);
        findParkingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class ourClass = null;
                ourClass = FindParkingActivity.class;
                Intent ourIntent = new Intent(MainMenuActivity.this, ourClass);
                startActivity(ourIntent);
            }
        });

        prefsHelper = new PrefsHelper(this);
        if (!prefsHelper.prefsContains(PrefsHelper.ActiveCarPlates) || prefsHelper.getString(PrefsHelper.ActiveCarPlates, null) == null) {
            Log.w(TAG, "Prefs does not contain ActiveCarPlates !");
            rootRelativeView = (RelativeLayout) findViewById(R.id.rootRelativeView);
            addCarDialog = new AddCarDialog(this);
            addCarDialog.setDismissCallback(new AddCarDialog.FirstTimeAddCarCallback() {
                @Override
                public void dismissThisDialog() {
                    rootRelativeView.removeView(addCarDialog);
                    addCarDialog.setVisibility(View.GONE);
                }
            });
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            rootRelativeView.addView(addCarDialog, params);
        }

    }


}


