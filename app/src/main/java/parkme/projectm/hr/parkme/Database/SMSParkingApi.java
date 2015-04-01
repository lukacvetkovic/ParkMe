package parkme.projectm.hr.parkme.Database;

import java.util.Date;
import java.util.List;

import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.City;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.MaxDuration;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.ParkingZone;
import parkme.projectm.hr.parkme.Database.OrmliteDb.Models.PaymentMode;

/**
 * Created by Cveki on 29.3.2015..
 *
 * API for database functionality in sms payment.
 */
public interface SMSParkingApi {

    /**
     * Gets all cities.
     * @return List of all cities in table city.
     */
    public List<City> getAllCities();

    /**
     * Return zones of a single city.
     * @param idCity City to which zones belong to.
     * @return Zones of selected city.
     */
    public List<ParkingZone>getAllParkingZonesFromCity(int idCity);

    /**
     * Returns payment modes for single zone.
     * Payment mode example: Park for 1h, for a maximum of 3h.
     * @param idParkingZone Zone to which payment mode belongs to.
     * @return List of payment modes for zone.
     */
    public List<PaymentMode>getAllPaymentModesFromParkingZone(int idParkingZone);

    /**
     * Calculates price for payment mode.
     * @param date Date and time.
     * @param idPaymentMode Payment mode id.
     * @return Price for selected payment mode at single moment.
     */
    public float getPrice(Date date, int idPaymentMode);

    /**
     * Returns max parking duration for selected date in zone.
     * @param date Date and time.
     * @param idParkingZone Parking zone id.
     * @return Max parking duration for selected date in zone.
     */
    public MaxDuration getMaxDuration(Date date, int idParkingZone);


}
