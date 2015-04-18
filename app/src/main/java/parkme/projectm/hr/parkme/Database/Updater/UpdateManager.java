package parkme.projectm.hr.parkme.Database.Updater;

import android.util.Log;

import java.util.Date;
import java.util.List;

import parkme.projectm.hr.parkme.Database.Tables;

/**
 * Created by Adriano Bacac on 29.03.15..
 */
public class UpdateManager {

    private  Updater updater;
    private UpdateSource source;

    public UpdateManager(Updater updater, UpdateSource source){
        this.updater = updater;
        this.source = source;
    }

    public void updateAll(Date lastUpdate) throws Exception {
        try {
            Log.d("Pocinje"," update");
            updater.updateCity(source.getNewCityRows(lastUpdate));
            Log.d("Gotov update "," City");
            updater.updateParkingZone(source.getNewParkingZoneRows(lastUpdate));
            Log.d("Gotov update "," ParkingZone");
            updater.updatePostcode(source.getNewPostcodeRows(lastUpdate));
            Log.d("Gotov update "," Postcode");
            updater.updatePaymentMode(source.getNewPaymentModeRows(lastUpdate));
            Log.d("Gotov update "," PaymentMode");
            updater.updateZoneCalendar(source.getNewZoneCalendarRows(lastUpdate));
            Log.d("Gotov update "," ZoneCalendar");
            updater.updateZoneWorkTime(source.getNewZoneWorkTimeRows(lastUpdate));
            Log.d("Gotov update "," ZoneWorkTime");
            updater.updateZonePrice(source.getNewZonePriceRows(lastUpdate));
            Log.d("Gotov update "," ZonePrice");
            updater.updateMaxDuration(source.getMaxDurationRows(lastUpdate));
            Log.d("Gotov update "," MaxDuration");
        }catch (NullPointerException e){
            throw new Exception("Update error"); // TODO promjeni lanac exceptiona, ovo bi trebao bit neki UpdateException
        }
    }

    public void setUpdater(Updater updater) {
        this.updater = updater;
    }

    public void setSource(UpdateSource source) {
        this.source = source;
    }

    public Updater getUpdater() {
        return updater;
    }

    public UpdateSource getSource() {
        return source;
    }
}
