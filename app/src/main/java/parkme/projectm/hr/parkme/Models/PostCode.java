package parkme.projectm.hr.parkme.Models;

/**
 * Created by Cveki on 29.3.2015..
 */
public class PostCode {
    private int id;
    private int id_city;
    private String post_code;

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
