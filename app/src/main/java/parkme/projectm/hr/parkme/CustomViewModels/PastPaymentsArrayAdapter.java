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
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PastParkingPayment;
import parkme.projectm.hr.parkme.R;

public class PastPaymentsArrayAdapter extends ArrayAdapter<PastParkingPayment> {
    private final FragmentMenuActivity context;
    private final PastParkingPayment[] values;

    private PastPaymentArrayAdapterCallback callback;
    private DatabaseManager dbManager;

    public interface PastPaymentArrayAdapterCallback{
        public void refreshFragment();
    }

    public PastPaymentsArrayAdapter(FragmentMenuActivity context, PastParkingPayment[] values) {
        super(context, R.layout.layout_favorite_car , values);
        this.context = context;
        this.values = values;
        dbManager = DatabaseManager.getInstance();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View pastPaymentView = inflater.inflate(R.layout.layout_past_payment, parent, false);

        ImageView pastPaymentCarIcon = (ImageView) pastPaymentView.findViewById(R.id.imgViewPastPaymentCarIcon);
        TextView pastPaymentCarTables = (TextView) pastPaymentView.findViewById(R.id.txtCarTables);
        TextView pastpaymentDate = (TextView) pastPaymentView.findViewById(R.id.txtDateOfPayment);

        ImageView deleteImage = (ImageView) pastPaymentView.findViewById(R.id.imgDelete);
        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.removePastParkingPayment(values[position]);
                if(callback != null){
                    callback.refreshFragment();
                }
            }
        });

        pastPaymentCarIcon.setImageResource(values[position].getGreenBackgroundCarIcon());
        pastPaymentCarTables.setText(values[position].getCapPlates());
        pastpaymentDate.setText(values[position].getStartOfPayment());

        return pastPaymentView;
    }

    public void setCallback(PastPaymentArrayAdapterCallback callback) {
        this.callback = callback;
    }
}
