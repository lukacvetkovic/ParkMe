package parkme.projectm.hr.parkme.CustomViewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import parkme.projectm.hr.parkme.Activities.FragmentMenuActivity;
import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavoritePayment;
import parkme.projectm.hr.parkme.Fragments.PayParkingFragmentCallback;
import parkme.projectm.hr.parkme.R;

/**
 * Created by Mihael on 7.5.2015..
 */
public class FavoritePaymentArrayAdapter extends ArrayAdapter<FavoritePayment> {
    private final FragmentMenuActivity context;
    private final FavoritePayment[] values;
    private DatabaseManager dbManager;

    private FavoritePaymentArrayAdapterCallback callback;

    public interface FavoritePaymentArrayAdapterCallback{
        public void refreshFragment();
    }

    public FavoritePaymentArrayAdapter(FragmentMenuActivity context, FavoritePayment[] values) {
        super(context, R.layout.layout_favorite_payment, values);
        this.context = context;
        this.values = values;
        dbManager = DatabaseManager.getInstance();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View favoritePayment = inflater.inflate(R.layout.layout_favorite_payment, parent, false);

        TextView favoritePaymentCity = (TextView) favoritePayment.findViewById(R.id.txtFavPaymentCity);
        TextView favoritePaymentZone = (TextView) favoritePayment.findViewById(R.id.txtZone);
        ImageView favoritePaymentDeleteButton = (ImageView) favoritePayment.findViewById(R.id.imgDelete);

        favoritePaymentCity.setText(dbManager.getCityNameFromId(values[position].getGradId()));
        favoritePaymentZone.setText(dbManager.getZoneNameFromId(values[position].getZoneID()));

        favoritePaymentDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.removeFavoritePayment(values[position]);
                if(callback != null){
                    callback.refreshFragment();
                }
            }
        });

        return favoritePayment;
    }

    public void setCallback(FavoritePaymentArrayAdapterCallback callback) {
        this.callback = callback;
    }
}
