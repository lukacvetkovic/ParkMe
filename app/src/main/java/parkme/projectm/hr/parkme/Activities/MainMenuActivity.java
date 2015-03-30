package parkme.projectm.hr.parkme.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.City;
import parkme.projectm.hr.parkme.Database.OrmliteDb.OrmLiteDatabaseHelper;
import parkme.projectm.hr.parkme.Database.Updater.UpdateManager;
import parkme.projectm.hr.parkme.Database.Updater.UrlUpdateSource;
import parkme.projectm.hr.parkme.Helpers.GetRestService;
import parkme.projectm.hr.parkme.R;

/**
 * Created by Cveki on 11.2.2015..
 */
public class MainMenuActivity extends Activity{
    /**
     * Button to go to activity where is swipe menu for choosing favs,cars,...
     */
    Button payment;

    /**
     *
     */
    Button parking;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        DatabaseManager.init(getApplicationContext());
        i = 1;

        payment=(Button) findViewById(R.id.bPay);
        //Goes to PaymentMenuActivity
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EXTRA "update" svega sa neta
                DatabaseManager.init(getApplicationContext());
                UpdateManager um = new UpdateManager(
                        DatabaseManager.getInstance(),
                        new UrlUpdateSource(new GetRestService(""))
                );
                try {
                    um.updateAll(DatabaseManager.dateFormatter.parse("2010-01-01"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //!EXTRA
                Class ourClass = null;
                ourClass = PaymentMenuActivity.class;
                Intent ourIntent = new Intent(MainMenuActivity.this, ourClass);
                startActivity(ourIntent);
            }
        });

        parking=(Button) findViewById(R.id.bPark);
        parking.setOnClickListener(new View.OnClickListener() {
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
