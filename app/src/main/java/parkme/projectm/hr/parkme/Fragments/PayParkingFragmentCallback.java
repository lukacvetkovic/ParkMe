package parkme.projectm.hr.parkme.Fragments;

import parkme.projectm.hr.parkme.Dialogs.ConfirmPaymentDialog;
import parkme.projectm.hr.parkme.Dialogs.PayParkingDialog;

/**
 * Created by Mihael on 17.4.2015..
 */
public interface PayParkingFragmentCallback {

    public void refreshActivity();

    public void swipeToChooseCarFragment();

    public void displayPayParkingDialog(PayParkingDialog payParkingDialog);

    public void dismissPayParkingDialog(PayParkingDialog payParkingDialog);

    public void displayConfirmPaymentDialog(ConfirmPaymentDialog confirmPaymentDialog);

    public void dismissConfirmPaymentDialog(ConfirmPaymentDialog confirmPaymentDialog);
}
