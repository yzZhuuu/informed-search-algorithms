package SearchUSA;

import java.util.ArrayList;

public class Node {

    private String cityname;
    private double latitude;
    private double longitude;
    private ArrayList<SubNode> links = null;
    private int g = 0;
    private double cost = 0;
    private Node parent = null;

    public Node(){
        super();
    }

    public Node(String cityname, double latitude, double longitude){
        super();
        this.cityname = cityname;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCityname() {
        return cityname;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public ArrayList<SubNode> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<SubNode> links) {
        this.links = links;
    }

    public int getG() { return g; }

    public void setG(int g) {
        this.g = g;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public static Boolean IsNodeNameinList(Node n, ArrayList<Node> list){
        for(Node l : list){
            if(l.getCityname().equals(n.getCityname()))
                return true;
        }
        return false;
    }

    public int GetDistanceToParent() throws Exception{
        if(parent == null)
            throw new Exception(String.format("Cannot find parent of node:%s.", cityname));
        for(SubNode n : links){
            if(n.getCityname().equals(parent.getCityname()))
                return n.getDistance();
        }
        throw new Exception(String.format("Error when getting distance from node:%s to its parent.", cityname));
    }

    public static double CalcDistanceHeuri(Node a, Node b){
        double pi = 3.141593;
        return Math.sqrt((Math.pow((69.5 * (a.getLatitude() - b.getLatitude())), 2) +
                Math.pow((69.5 * Math.cos((a.getLatitude() + b.getLatitude())/360 * pi) * (a.getLongitude() - b.getLongitude())), 2)));
    }
}
