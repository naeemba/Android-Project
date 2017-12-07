package baghi.naeem.com.assignment4;

public class Hotel {

    private String name;
    private String address;
    private int imageId;

    public Hotel() {
    }

    public Hotel(String name, String address, int imageId) {
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
}
