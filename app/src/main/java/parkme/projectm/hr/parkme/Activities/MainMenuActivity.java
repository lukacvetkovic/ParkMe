package parkme.projectm.hr.parkme.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);


        payment=(Button) findViewById(R.id.bPay);
        //Goes to PaymentMenuActivity
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
