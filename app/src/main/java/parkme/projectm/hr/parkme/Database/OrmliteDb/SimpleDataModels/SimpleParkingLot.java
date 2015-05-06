package parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels;

/**
 * Created by Adriano Bacac on 06.05.15..
 */
public class SimpleParkingLot {
    private int id;
    private String name;
    private String description;
    private String adress;
    private String google_place_id;
    private int relativity;
    private double lat;
    private double lng;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAdress() {
        return adress;
    }

    public String getGooglePlaceId() {
        return google_place_id;
    }

    public int getRelativity() {
        return relativity;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
