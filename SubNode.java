package SearchUSA;

public class SubNode {

    private String cityName;
    private int distance;

    public SubNode(String cityName, int distance){
        super();
        this.cityName = cityName;
        this.distance = distance;
    }

    public String getCityname() {
        return cityName;
    }

    public void setCityName(String cityname) {
        this.cityName = cityname;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}