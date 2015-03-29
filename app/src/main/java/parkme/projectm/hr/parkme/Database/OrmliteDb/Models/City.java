package parkme.projectm.hr.parkme.Database.OrmliteDb.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Model of city.
 *
 * Created by Adriano Bacac on 29.03.15..
 */
@DatabaseTable(tableName = "city")
public class City {
    @DatabaseField(id = true, generatedId = false)
    private int id;
    @DatabaseField
    private String name;

    public City(){

    }
    public City(int id, String name) {
        this.id = id;
        this.name = name;
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
}
