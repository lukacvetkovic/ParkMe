package parkme.projectm.hr.parkme.Database.OrmliteDb.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.ParseException;
import java.util.Date;

import parkme.projectm.hr.parkme.Database.OrmliteDb.DatabaseManager;
import parkme.projectm.hr.parkme.Database.OrmliteDb.SimpleDataModels.SimpleMaxDuration;

/**
 * Created by Adriano Bacac on 01.04.15..
 */
@DatabaseTable(tableName = "maxDuration")
public class MaxDuration {
    @DatabaseField(id = true, generatedId = false)
    private int id;
    @DatabaseField
    private int idZone;
    @DatabaseField
    private Date maxDuration;
    @DatabaseField
    private Date dateFrom;
    @DatabaseField
    private Date dateTo;

    public MaxDuration() {

    }
    public MaxDuration(SimpleMaxDuration sMaxDuration) {
        this.id = sMaxDuration.getId();
        this.idZone = sMaxDuration.getIdZone();
        try {
            this.maxDuration = DatabaseManager.timeFormatter.parse(sMaxDuration.getMaxDuration());
            this.dateFrom = DatabaseManager.dateFormatter.parse(sMaxDuration.getDateFrom());
            this.dateTo = DatabaseManager.dateFormatter.parse(sMaxDuration.getDateTo());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public MaxDuration(int id, int id_zone, Date date_from, Date date_to, Date max_duration) {
        this.id = id;
        this.idZone = id_zone;
        this.dateFrom = date_from;
        this.dateTo = date_to;
        this.maxDuration = max_duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdZone() {
        return idZone;
    }

    public void setIdZone(int idZone) {
        this.idZone = idZone;
    }

    public Date getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(Date maxDuration) {
        this.maxDuration = maxDuration;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
}