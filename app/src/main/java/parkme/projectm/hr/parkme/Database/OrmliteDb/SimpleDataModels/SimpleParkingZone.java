package parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Model of parking zone.
 *
 * Created by Adriano Bacac on 29.03.15..
 */

public class SimpleParkingZone {
    private int id;
    private String name;
    private String phone_number;
    private int id_city;


    public SimpleParkingZone(int id, String name, String phone_number, int id_city) {
        this.id = id;
        this.name = name;
        this.phone_number = phone_number;
        this.id_city = id_city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phone_number = phoneNumber;
    }

    public int getIdCity() {
        return id_city;
    }

    public void setIdCity(int idCity) {
        this.id_city = idCity;
    }
}
