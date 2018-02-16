package baghi.naeem.com.assignment6.entities;

import org.json.JSONException;
import org.json.JSONObject;

public class Hotel {

    private String id;
    private String name;
    private String address;
    private int imageId;

    public Hotel() {
    }

    public Hotel(String id, String name, String address, int imageId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
