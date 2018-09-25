package SearchUSA;

public class DynamicProgramming extends SearchAlgorithm {

    public DynamicProgramming(){
        super();
    }

    public DynamicProgramming(Node srccity, Node destcity, Map map){
        super(srccity, destcity, map);
    }

    @Override
    //priority measurement for Dynamic Programming is DISTANCE FROM THE START NODE
    public void UpdatePriority(Node node) throws Exception{
        //set node.G
        if (node.getParent() == null) {
            node.setG(0);
        }
        else{
            node.setG(node.getParent().getG() + node.GetDistanceToParent());
        }

        //set node.Cost
        node.setCost(node.getG());
    }
}
