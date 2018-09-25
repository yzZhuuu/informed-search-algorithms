package SearchUSA;

import java.util.ArrayList;

public class Astar extends SearchAlgorithm{

    public Astar(){
        super();
    }

    public Astar(Node srccity, Node destcity, Map map){
        super(srccity, destcity, map);
    }

    @Override
    //priority measurement for A* is the SUM OF PATH COST FROM START NODE (g) AND ESTIMATED REMAINING DISTANCE TO GOAL (h-hat)
    public void UpdatePriority(Node node) throws Exception {
        //set node.G
        if (node.getParent() == null) {
            node.setG(0);
        }
        else {
            node.setG(node.getParent().getG() + node.GetDistanceToParent());
        }

        //set node.Cost
        node.setCost(node.getG() + Node.CalcDistanceHeuri(node, destcity));
    }

    //#region Experiment
    public ArrayList<String> Search_Experiment(Node srccity, Node destcity, Map map){
        try {
            ClearAllParent(map);

            ArrayList<Node> frontier = new ArrayList<Node>();
            ArrayList<Node> explored = new ArrayList<Node>();
            UpdatePriority_Experiment(srccity, destcity);
            frontier.add(srccity);

            while (frontier.size() != 0){
                Node currN = RemvHighestPriorNode(frontier);

                if(currN.getCityname().equals(destcity.getCityname())){
                    ArrayList<String> output = new ArrayList<>();
                    output.add(String.valueOf(explored.size()));
                    output.add(currN.getCityname());
                    while (currN.getParent() != null){
                            output.add(currN.getParent().getCityname());
                        currN = currN.getParent();
                    }
                    return output;
                }

                explored.add(currN);
                ArrayList<Node> successors = map.MoveGenerator(currN);

                for(Node s : successors) {
                    if (currN.getParent() != null && s.getCityname().equals(currN.getParent().getCityname()))
                        continue;
                    s.setParent(currN);
                    UpdatePriority_Experiment(s, destcity);


                    if (Node.IsNodeNameinList(s, explored))
                        continue;
                    else if (!Node.IsNodeNameinList(s, frontier))
                        frontier.add(s);
                    else {
                        ChallengeNodeinList(s, frontier);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void UpdatePriority_Experiment(Node node, Node destcity) throws Exception {
        if (node.getParent() == null) {
            node.setG(0);
        }
        else {
            node.setG(node.getParent().getG() + node.GetDistanceToParent());
        }

        node.setCost(node.getG() + Node.CalcDistanceHeuri(node, destcity));
    }

    public void ClearAllParent(Map map){
        for(Node n : map.nodelist){
            n.setParent(null);
        }
    }

    //#endregion
}
