package parkme.projectm.hr.parkme.Database.OrmliteDb.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Adriano Bacac on 29.3.2015..
 */
@DatabaseTable(tableName = "post_code")

public class PostCode {

    @DatabaseField(id = true, generatedId = false)
    private int id;
    @DatabaseField
    private int id_city;
    @DatabaseField
    private String post_code;


    public PostCode(){

    }

    public PostCode(int id, int idCity, String postCode) {
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

    public int getId_city() {
        return id_city;
    }

    public void setId_city(int idCity) {
        this.id_city = id_city;
    }

    public String getPostCode() {
        return post_code;
    }

    public void setPostCode(String postCode) {
        this.post_code = postCode;
    }
}
