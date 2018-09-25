package SearchUSA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public abstract class SearchAlgorithm {
    Map map;
    Node srccity;
    Node destcity;

    public SearchAlgorithm(){
        super();
    }

    public SearchAlgorithm(Node srccity, Node destcity, Map map){
        super();

        this.srccity = srccity;
        this.destcity = destcity;
        this.map = map;
    }

    //for every subclass, override this method according to specific priority measurement.
    //Set node.G with path cost from start node. This is used to calculate total distance in solution path.
    //set node.Cost with its value of the specific priority measurement.
    public abstract void UpdatePriority(Node node) throws Exception;

    public void SearchPath(){
        try {
            //Initialize Frontier and Explored, add source city into Frontier
            ArrayList<Node> frontier = new ArrayList<Node>();
            ArrayList<Node> explored = new ArrayList<Node>();
            UpdatePriority(srccity);
            frontier.add(srccity);

            while (frontier.size() != 0){
                //remove highest priority node N from Frontier
                Node currN = RemvHighestPriorNode(frontier);

                //if N is destination, print results
                if(currN.getCityname().equals(destcity.getCityname())){
                    PrintPathInfo(currN, explored);
                    return;
                }

                //if N is not destination, add N into Explored and get its successors
                explored.add(currN);
                ArrayList<Node> successors = map.MoveGenerator(currN);

                //foreach successor, compute cost, set parent node, add into Frontier
                for(Node s : successors) {
                    //for the successor which is actually the parent of N, do not recalculate cost or set parent
                    if (currN.getParent() != null && s.getCityname().equals(currN.getParent().getCityname()))
                        continue;
                    //set parent of the successor and compute cost
                    s.setParent(currN);
                    UpdatePriority(s);


                    /*The reason use IsNodeNameinList instead of List.Contains is that the s in list successors may has
                    different priority from its counterpart in Frontier
                     */
                    //if the successor is in Explored, do nothing
                    if (Node.IsNodeNameinList(s, explored))
                        continue;
                        //if the successor isn't in Explored and isn't Frontier, add into Frontier
                    else if (!Node.IsNodeNameinList(s, frontier))
                        frontier.add(s);
                        //if the successor isn't in Explored but already in Frontier, keep the higher priority one
                    else {
                        ChallengeNodeinList(s, frontier);
                    }
                }
            }
            System.out.println("failed to find a path!");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Node RemvHighestPriorNode(ArrayList<Node> list) {
        //sort the Frontier list with low to high total cost
        Collections.sort(list, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return (int)o1.getCost() - (int)o2.getCost();
            }
        });

        //remove the highest priority node from the Frontier
        Node highestprionode = list.get(0);
        list.remove(highestprionode);
        return highestprionode;
    }

    public void ChallengeNodeinList(Node n, ArrayList<Node> list){
        for (Node l : list){
            if(l.getCityname().equals(n.getCityname())){
                if(n.getCost() < l.getCost()){
                    list.remove(l);
                    list.add(n);
                }
                break;
            }
        }
    }

    public void PrintPathInfo(Node currN, ArrayList<Node> explored){
        StringBuffer expanded = new StringBuffer();
        for (Node n : explored){
            expanded.append(n.getCityname()).append(", ");
        }
        expanded = expanded.deleteCharAt(expanded.length() - 2);
        System.out.println("expanded nodes: " + expanded);
        System.out.println("number of nodes expanded: " + explored.size());

        StringBuffer path = new StringBuffer();
        ArrayList<String> pathArray = GetSolutionPath(currN);
        for (String s : pathArray){
            path.append(s).append(", ");
        }
        System.out.println("solution path: " + path.deleteCharAt(path.length() - 2));
        System.out.println("number of nodes in the path: " + pathArray.size());
        System.out.println("total distance: " + currN.getG());
    }

    public ArrayList<String> GetSolutionPath(Node n){
        ArrayList<String> pathArray = new ArrayList<>();
        pathArray.add(n.getCityname());
        while (n.getParent() != null){
            pathArray.add(n.getParent().getCityname());
            n = n.getParent();
        }
        Collections.reverse(pathArray);
        return pathArray;
    }
}
