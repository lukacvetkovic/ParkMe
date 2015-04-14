package parkme.projectm.hr.parkme.Database.Updater;

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
            updater.updateCity(source.getNewCityRows(lastUpdate));
            updater.updateParkingZone(source.getNewParkingZoneRows(lastUpdate));
            updater.updatePostcode(source.getNewPostcodeRows(lastUpdate));
            updater.updatePaymentMode(source.getNewPaymentModeRows(lastUpdate));
            updater.updateZoneCalendar(source.getNewZoneCalendarRows(lastUpdate));
            updater.updateZoneWorkTime(source.getNewZoneWorkTimeRows(lastUpdate));
            updater.updateZonePrice(source.getNewZonePriceRows(lastUpdate));
            updater.updateMaxDuration(source.getMaxDurationRows(lastUpdate));
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
