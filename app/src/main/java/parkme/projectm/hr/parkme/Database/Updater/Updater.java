package parkme.projectm.hr.parkme.Database.Updater;

import java.util.List;
import java.util.Objects;

import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.City;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.MaxDuration;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingZone;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PaymentMode;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PostCode;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZoneCalendar;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZonePrice;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ZoneWorkTime;

/**
 * Universal updatable interface.
 * Updates database with new values.
 *
 * Created by Adriano Bacac on 29.03.15..
 */
public interface Updater {

    /**
     * Updates city table with new rows.
     * If row exists changes values, inserts new row otherwise.
     *
     * @param cities List of changed or added rows.
     */
    public void updateCity(List<City> cities);

    /**
     * Updates parking zone table with new rows.
     * If row exists changes values, inserts new row otherwise.
     *
     * @param parkingZones List of changed or added rows.
     */
    public void updateParkingZone(List<ParkingZone> parkingZones);

    /**
     * Updates postcode table with new rows.
     * If row exists changes values, inserts new row otherwise.
     *
     * @param postcodes List of changed or added rows.
     */
    public void updatePostcode(List<PostCode> postcodes);

    /**
     * Updates payment mode table with new rows.
     * If row exists changes values, inserts new row otherwise.
     *
     * @param paymentModes List of changed or added rows.
     */
    public void updatePaymentMode(List<PaymentMode> paymentModes);

    /**
     * Updates zone calendar table with new rows.
     * If row exists changes values, inserts new row otherwise.
     *
     * @param zoneCalendars List of changed or added rows.
     */
    public void updateZoneCalendar(List<ZoneCalendar> zoneCalendars);

    /**
     * Updates zone work time table with new rows.
     * If row exists changes values, inserts new row otherwise.
     *
     * @param zoneWorkTimes List of changed or added rows.
     */
    public void updateZoneWorkTime(List<ZoneWorkTime> zoneWorkTimes);


    /**
     * Updates zone price table with new rows.
     * If row exists changes values, inserts new row otherwise.
     *
     * @param zonePrices List of changed or added rows.
     */
    public void updateZonePrice(List<ZonePrice> zonePrices);


    /**
     * Updates max duration table with new rows.
     * If row exists changes values, inserts new row otherwise.
     *
     * @param maxDurations List of changed or added rows.
     */
    public void updateMaxDuration(List<MaxDuration> maxDurations);
}
