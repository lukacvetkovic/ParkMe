package parkme.projectm.hr.parkme.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import parkme.projectm.hr.parkme.Activities.NewCarActivity;
import parkme.projectm.hr.parkme.Activities.PaymentMenuActivity;
import parkme.projectm.hr.parkme.CustomViewModels.FavoriteCarsArrayAdapter;
import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavouriteCar;
import parkme.projectm.hr.parkme.R;

/**
 * Created by Mihael on 8.4.2015..
 */
public class CarChoosingFragment extends Fragment {

    private Context context;

    private PaymentMenuActivity parentActivity;

    private Button newCarButton;
    private ListView favoriteCarsListView;

    private List<FavouriteCar> favoriteCarList;
    private FavoriteCarsArrayAdapter favoriteCarsArrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_car_choosing, container, false);

        newCarButton = (Button) rootView.findViewById(R.id.btnNewCar);
        favoriteCarsListView = (ListView) rootView.findViewById(R.id.favoriteCarsListView);

        this.context = getActivity();

        DatabaseManager dbManager = DatabaseManager.getInstance();
        favoriteCarList = dbManager.getAllFavouriteCars();

        if(favoriteCarList != null){
            Log.w("PRESS", "FavCarList nije null - number of values : " + favoriteCarList.size());
            favoriteCarsArrayAdapter = new FavoriteCarsArrayAdapter(parentActivity,
                    favoriteCarList.toArray(new FavouriteCar[favoriteCarList.size()]));

        }
        else{
            Log.w("PRESS", "FavCarList je null - nema favs autiju");        // TODO ako dode do ovoga, bacit  popup first time add car
            favoriteCarsArrayAdapter = new FavoriteCarsArrayAdapter(parentActivity, new FavouriteCar[0]);
        }

        favoriteCarsListView.setAdapter(favoriteCarsArrayAdapter);


        favoriteCarsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FavouriteCar favouriteCar = (FavouriteCar) favoriteCarsArrayAdapter.getItem(position);

                // TODO postaviti da je on sad favorite car

                Toast toast = Toast.makeText(context, "Pressed favCar at index " + position
                        + ", car tables -> " + favouriteCar.getCarRegistration(), Toast.LENGTH_LONG);
                toast.show();

            }
        });

        newCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, NewCarActivity.class);
                startActivity(i);
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = (PaymentMenuActivity) activity;
    }
}
