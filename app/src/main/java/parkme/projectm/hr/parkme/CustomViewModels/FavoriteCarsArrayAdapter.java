package parkme.projectm.hr.parkme.CustomViewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import parkme.projectm.hr.parkme.Activities.FragmentMenuActivity;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavouriteCar;
import parkme.projectm.hr.parkme.R;

/**
 * Created by Mihael on 8.4.2015..
 */
public class FavoriteCarsArrayAdapter extends ArrayAdapter<FavouriteCar> {
    private final FragmentMenuActivity context;
    private final FavouriteCar[] values;

    public FavoriteCarsArrayAdapter(FragmentMenuActivity context, FavouriteCar[] values) {
        super(context, R.layout.layout_favorite_car , values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View favoriteCarView = inflater.inflate(R.layout.layout_favorite_car, parent, false);

        ImageView imgCarIcon = (ImageView) favoriteCarView.findViewById(R.id.imgViewFavCar);
        TextView txtCarPlates = (TextView) favoriteCarView.findViewById(R.id.txtCarTables);

        imgCarIcon.setImageResource(values[position].getGreenBackgroundCarIcon());
        txtCarPlates.setText(values[position].getCarRegistration());

        return favoriteCarView;
    }
}
