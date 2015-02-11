package parkme.projectm.hr.parkme.Models;

/**
 * Created by Cveki on 11.2.2015..
 */
public class ParkingZone {
    private int id;
    private String name;
    private String phone_number;
    private int id_city;

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
