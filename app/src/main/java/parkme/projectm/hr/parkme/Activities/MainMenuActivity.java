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

    Button myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        myLocation=(Button) findViewById(R.id.bShow);
        myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class ourClass = null;
                ourClass = MapsActivity.class;
                Intent ourIntent = new Intent(MainMenuActivity.this, ourClass);
                startActivity(ourIntent);
            }
        });
    }
}
