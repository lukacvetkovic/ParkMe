package parkme.projectm.hr.parkme.Database.OrmliteDb.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Adriano Bacac on 29.3.2015..
 */
@DatabaseTable(tableName = "city")

public class PostCode {

    @DatabaseField(id = true, generatedId = false)
    private int id;
    @DatabaseField
    private int id_city;
    @DatabaseField
    private String post_code;


    public PostCode(){

    }

    public PostCode(int id, int id_city, String post_code) {
        this.id = id;
        this.id_city = id_city;
        this.post_code = post_code;
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

    public void setId_city(int id_city) {
        this.id_city = id_city;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }
}
