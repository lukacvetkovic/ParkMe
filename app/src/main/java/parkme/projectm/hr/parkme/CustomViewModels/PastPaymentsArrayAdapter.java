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
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PastParkingPayment;
import parkme.projectm.hr.parkme.R;

/**
 * Created by Mihael on 6.5.2015..
 */
public class PastPaymentsArrayAdapter extends ArrayAdapter<PastParkingPayment> {
    private final FragmentMenuActivity context;
    private final PastParkingPayment[] values;

    public PastPaymentsArrayAdapter(FragmentMenuActivity context, PastParkingPayment[] values) {
        super(context, R.layout.layout_favorite_car , values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View pastPaymentView = inflater.inflate(R.layout.layout_past_payment, parent, false);

        ImageView pastPaymentCarIcon = (ImageView) pastPaymentView.findViewById(R.id.imgViewPastPaymentCarIcon);
        TextView pastPaymentCarTables = (TextView) pastPaymentView.findViewById(R.id.txtCarTables);
        TextView pastpaymentDate = (TextView) pastPaymentView.findViewById(R.id.txtDateOfPayment);

        pastPaymentCarIcon.setImageResource(values[position].getCarIcon());
        pastPaymentCarTables.setText(values[position].getCapPlates());
        pastpaymentDate.setText(values[position].getStartOfPayment());

        return pastPaymentView;
    }
}
