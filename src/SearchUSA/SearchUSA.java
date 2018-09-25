package SearchUSA;

public class SearchUSA {
    //cd the outer folder of SearchUSA.java
    //java SearchUSA.SearchUSA searchtype srccityname destcityname
    public static void main(String[] args){
        //parse arg numbers
        if(args.length != 3){
            System.out.println("Invalid parameter number!");
            return;
        }

        String searchtype = args[0].trim().toUpperCase();
        String srccityname = args[1].trim();
        String destcityname = args[2].trim();

        //load data of cities and roads into map
        Map map = new Map();
        map.Initialize();

        //parse args of cities
        if(srccityname.equals(destcityname))
            System.out.println("The source city and the destination city are the same!");
        Node srccity = map.GetCityNode(srccityname);
        if(srccity == null){
            System.out.println("Source city error! No such city.");
            return;
        }
        Node destcity = map.GetCityNode(destcityname);
        if(destcity == null){
            System.out.println("Destination city error! No such city.");
            return;
        }

        //parse args of search type and run search
        if(searchtype.equals("ASTAR"))
            new Astar(srccity, destcity, map).SearchPath();
        else if(searchtype.equals("GREEDY"))
            new Greedy(srccity, destcity, map).SearchPath();
        else if(searchtype.equals("DYNAMICPROGRAMMING"))
            new DynamicProgramming(srccity, destcity, map).SearchPath();
        else
            System.out.println("No such search type!");
    }
}
