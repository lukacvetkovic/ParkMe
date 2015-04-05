package parkme.projectm.hr.parkme.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.City;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavouriteCar;
import parkme.projectm.hr.parkme.Database.OrmliteDb.OrmLiteDatabaseHelper;
import parkme.projectm.hr.parkme.Database.Updater.UpdateManager;
import parkme.projectm.hr.parkme.Database.Updater.UrlUpdateSource;
import parkme.projectm.hr.parkme.Helpers.GetRestService;
import parkme.projectm.hr.parkme.R;

/**
 * Created by Cveki on 11.2.2015..
 */
public class MainMenuActivity extends Activity{

    private static final String TAG = "MainMenu Activity";
    /**
     * Button to go to activity where is swipe menu for choosing favs,cars,...
     */
    private ImageButton findParkingButton;
    private ImageButton payParkingButton;

    Button update;
    /**
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        DatabaseManager.init(getApplicationContext());

        payParkingButton = ( ImageButton ) findViewById(R.id.imgBtnPayment);
        //Goes to PaymentMenuActivity
        payParkingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //!EXTRA
                Class ourClass = null;
                ourClass = PaymentMenuActivity.class;
                Intent ourIntent = new Intent(MainMenuActivity.this, ourClass);
                startActivity(ourIntent);
            }
        });

        update=(Button)findViewById(R.id.bUpdate);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EXTRA "update" svega sa neta
                DatabaseManager.init(getApplicationContext());
                UpdateManager um = new UpdateManager(
                        DatabaseManager.getInstance(),
                        new UrlUpdateSource(new GetRestService(""))
                );
                try {
                    um.updateAll(DatabaseManager.dateFormatter.parse("2010-01-01"));        // TODO - ubacit ovo u asyncTask ako nije vezano uz placanje
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        findParkingButton = ( ImageButton ) findViewById(R.id.imgBtnFindParking);
        findParkingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class ourClass = null;
                ourClass =ParkingMenuActivity.class;
                Intent ourIntent = new Intent(MainMenuActivity.this, ourClass);
                startActivity(ourIntent);
            }
        });

    }
}
