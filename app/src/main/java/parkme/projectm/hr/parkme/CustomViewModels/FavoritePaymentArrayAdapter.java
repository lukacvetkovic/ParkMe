package parkme.projectm.hr.parkme.CustomViewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import parkme.projectm.hr.parkme.Activities.FragmentMenuActivity;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.FavoritePayment;
import parkme.projectm.hr.parkme.R;

/**
 * Created by Mihael on 7.5.2015..
 */
public class FavoritePaymentArrayAdapter extends ArrayAdapter<FavoritePayment> {
    private final FragmentMenuActivity context;
    private final FavoritePayment[] values;

    public FavoritePaymentArrayAdapter(FragmentMenuActivity context, FavoritePayment[] values) {
        super(context, R.layout.layout_favorite_payment, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View favoritePayment = inflater.inflate(R.layout.layout_favorite_payment, parent, false);

        TextView favoritePaymentCarPlates = (TextView) favoritePayment.findViewById(R.id.txtCarTables);
        TextView favoritePaymentZone = (TextView) favoritePayment.findViewById(R.id.txtZone);
        ImageView favoritePaymentDeleteButton = (ImageView) favoritePayment.findViewById(R.id.imgDelete);

        // TODO - f*** it.. napravit ovo ujutro jer za favPayment ne trebaju tablice..
        favoritePaymentCarPlates.setText(values[position].getFavoritePaymentId());

        return favoritePayment;
    }
}
