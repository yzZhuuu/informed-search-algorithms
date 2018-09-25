package SearchUSA;

public class Greedy extends SearchAlgorithm {

    public Greedy(){
        super();
    }

    public Greedy(Node srccity, Node destcity, Map map){
        super(srccity, destcity, map);
   }

    @Override
    //priority measurement for Greedy is ESTIMATED REMAINING DISTANCE TO GOAL
    public void UpdatePriority(Node node) throws Exception{
        //set node.G
        if (node.getParent() == null) {
            node.setG(0);
        }
        else{
            node.setG(node.getParent().getG() + node.GetDistanceToParent());
        }

        //set node.Cost
        node.setCost(Node.CalcDistanceHeuri(node, destcity));
    }
}
