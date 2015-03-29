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
    private int idCity;
    @DatabaseField
    private String postCode;


    public PostCode(){

    }

    public PostCode(int id, int idCity, String postCode) {
        this.id = id;
        this.idCity = idCity;
        this.postCode = postCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
