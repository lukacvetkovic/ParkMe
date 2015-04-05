package parkme.projectm.hr.parkme.Database.OrmliteDb.Models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Mihael on 5.4.2015..
 */
@DatabaseTable(tableName = "favourite_cars")
public class FavouriteCar {

    public final static String column_car_registration = "car_registration";
    public final static String column_car_icon = "car_icon";

    @DatabaseField(columnName = column_car_registration, id = true)
    private String carRegistration;

    @DatabaseField(columnName = column_car_icon)        // holds int for a resource R.drawable.carIcon
    private int carIcon;

    /**
     *
     * @param carRegistration - String representation of a cars registration plates
     * @param carIcon - int reference to drawable resource, i.e.: R.drawable.icon
     */
    public FavouriteCar(String carRegistration, int carIcon) {
        this.carRegistration = carRegistration;
        this.carIcon = carIcon;
    }

    public FavouriteCar() {
    }

    public String getCarRegistration() {
        return carRegistration;
    }

    public void setCarRegistration(String carRegistration) {
        this.carRegistration = carRegistration;
    }

    public int getCarIcon() {
        return carIcon;
    }

    public void setCarIcon(int carIcon) {
        this.carIcon = carIcon;
    }
}
