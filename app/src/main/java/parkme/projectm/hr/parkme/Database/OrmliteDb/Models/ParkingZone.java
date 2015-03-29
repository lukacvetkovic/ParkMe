package parkme.projectm.hr.parkme.Database.OrmliteDb.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Model of parking zone.
 *
 * Created by Adriano Bacac on 29.03.15..
 */
@DatabaseTable(tableName = "parking_zone")
public class ParkingZone {
    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String phone_number;
    @DatabaseField
    private int id_city;

    public ParkingZone(){

    }

    public ParkingZone(int id, String name, String phone_number, int id_city) {
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getId_city() {
        return id_city;
    }

    public void setId_city(int id_city) {
        this.id_city = id_city;
    }
}
