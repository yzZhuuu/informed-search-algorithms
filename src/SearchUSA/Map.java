package SearchUSA;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Map {

    ArrayList<Node> nodelist = new ArrayList<>();

    //generate the successors of a node
    public ArrayList<Node> MoveGenerator(Node node){
        ArrayList<String> succnames = new ArrayList<>();
        ArrayList<Node> successors = new ArrayList<>();
        for (Node n : nodelist){
            if(n.getCityname().equals(node.getCityname())){
                for(SubNode sn : n.getLinks()){
                    succnames.add(sn.getCityname());
                }
                break;
            }
        }
        for (String s : succnames){
            for (Node n : nodelist){
                if(n.getCityname().equals(s)){
                    successors.add(n);
                    break;
                }
            }
        }
        return successors;
    }

    public Node GetCityNode(String cityname) {
        for (Node n : nodelist) {
            if (n.getCityname().equals(cityname)) {
                return n;
            }
        }
        return null;
    }

    public void Initialize(){
        InitialMapNodes();
        InitialMapSubNodes();
    }

    public void InitialMapNodes(){
        try {
            InputStream in = Map.class.getResourceAsStream("/SearchUSA/city.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] array = line.split(",");
                if (array[0] != null && array[1] != null && array[2] != null) {
                    String cityname = array[0].substring(array[0].indexOf("(") + 1).trim();
                    double lati = Double.parseDouble(array[1].trim());
                    double longi = Double.parseDouble(array[2].substring(0, array[2].indexOf(")")).trim());
                    nodelist.add(new Node(cityname, lati, longi));
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void InitialMapSubNodes() {
        ArrayList<String[]> roadlist = new ArrayList<>();
        try {
            InputStream in = Map.class.getResourceAsStream("/SearchUSA/road.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] array = line.split(",");
                if (array[0] != null && array[1] != null && array[2] != null) {
                    String city1 = array[0].substring(array[0].indexOf("(") + 1).trim();
                    String city2 = array[1].trim();
                    String distance = array[2].substring(0, array[2].indexOf(")")).trim();
                    roadlist.add(new String[]{city1, city2, distance});
                    roadlist.add(new String[]{city2, city1, distance});
                }
            }
            for (Node n : nodelist){
                ArrayList<SubNode> as = new ArrayList<>();
                for (String[] s : roadlist){
                    if(s[0].equals(n.getCityname())){
                        as.add(new SubNode(s[1], Integer.parseInt(s[2])));
                    }
                }
                n.setLinks(as);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
