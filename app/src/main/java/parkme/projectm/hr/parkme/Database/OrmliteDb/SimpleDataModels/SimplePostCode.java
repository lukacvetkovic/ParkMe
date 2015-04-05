package parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Adriano Bacac on 29.3.2015..
 */
public class SimplePostCode {

    private int id;
    private int id_city;
    private String post_code;

    public SimplePostCode(int id, int idCity, String postCode) {
        this.id = id;
        this.id_city = idCity;
        this.post_code = postCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCity() {
        return id_city;
    }

    public void setIdCity(int idCity) {
        this.id_city = id_city;
    }

    public String getPostCode() {
        return post_code;
    }

    public void setPostCode(String postCode) {
        this.post_code = postCode;
    }
}
