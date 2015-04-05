package parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels;

/**
 * Created by Mihael on 5.4.2015..
 */
public class SimpleFavouriteCar {

    private String carRegistration;
    private int carIcon;

    /**
     *
     * @param carRegistration - String representation of a cars registration plates
     * @param carIcon - int reference to drawable resource, i.e.: R.drawable.icon
     */
    public SimpleFavouriteCar(String carRegistration, int carIcon) {
        this.carRegistration = carRegistration;
        this.carIcon = carIcon;
    }

    public SimpleFavouriteCar() {
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
