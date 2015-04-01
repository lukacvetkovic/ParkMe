package parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Adriano Bacac on 01.04.15..
 */

public class SimpleMaxDuration {
    private int id;
    private int id_zone;
    private String date_from;
    private String date_to;
    private String max_duration;

    public SimpleMaxDuration(int id, int id_zone, String date_from, String date_to, String max_duration) {
        this.id = id;
        this.id_zone = id_zone;
        this.date_from = date_from;
        this.date_to = date_to;
        this.max_duration = max_duration;
    }

    public int getId() {
        return id;
    }

    public int getIdZone() {
        return id_zone;
    }

    public String getDateFrom() {
        return date_from;
    }

    public String getDateTo() {
        return date_to;
    }

    public String getMaxDuration() {
        return max_duration;
    }

}