package SearchUSA;

import java.util.ArrayList;
import java.util.Collections;

public class Experiments {
    public static void main(String[] args) {
        Map map = new Map();
        map.Initialize();
        Astar_Experiment(map);
        Astar_Study_Output(map);
        Compare_Three_Search_Methods(map);
    }

    public static void Astar_Experiment(Map map) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            for (int j = i + 1; j < map.nodelist.size(); j++) {
                Astar astar = new Astar();
                ArrayList<String> outputForw = astar.Search_Experiment(map.nodelist.get(i), map.nodelist.get(j), map);
                ArrayList<String> outputBackw = astar.Search_Experiment(map.nodelist.get(j), map.nodelist.get(i), map);
                //CompareOutputs(outputForw, outputBackw);
                if (!outputForw.get(0).equals(outputBackw.get(0))) {
                    result.add(String.format("(%s, %s): the total expanded node number from %s to %s is %s whereas from %s to %s is %s",
                            map.nodelist.get(i).getCityname(), map.nodelist.get(j).getCityname(),
                            map.nodelist.get(i).getCityname(), map.nodelist.get(j).getCityname(), outputForw.get(0),
                            map.nodelist.get(j).getCityname(), map.nodelist.get(i).getCityname(), outputBackw.get(0)));
                }
                outputForw.remove(0);
                Collections.reverse(outputForw);
                outputBackw.remove(0);
                if (!outputForw.equals(outputBackw)) {
                    StringBuffer forwPath = new StringBuffer();
                    for (String s : outputForw) {
                        forwPath.append(s).append(" ,");
                    }
                    forwPath.deleteCharAt(forwPath.length() - 1);
                    StringBuffer backwPath = new StringBuffer();
                    Collections.reverse(outputBackw);
                    for (String s : outputBackw) {
                        backwPath.append(s).append(" ,");
                    }
                    backwPath.deleteCharAt(backwPath.length() - 1);
                    result.add(String.format("(%s, %s): the path from %s to %s is [%s] whereas from %s to %s is [%s]",
                            map.nodelist.get(i).getCityname(), map.nodelist.get(j).getCityname(),
                            map.nodelist.get(i).getCityname(), map.nodelist.get(j).getCityname(), forwPath,
                            map.nodelist.get(j).getCityname(), map.nodelist.get(i).getCityname(), backwPath));
                }
            }
        }
        if (result.size() != 0) {
            for (String s : result)
                System.out.println(s);
        } else
            System.out.println("no such pair!");
    }

    public static void Astar_Study_Output(Map map) {
        for (int i = 0; i < 1; i++) {
            for (int j = i + 1; j < map.nodelist.size(); j++) {
                Astar astar = new Astar(map.nodelist.get(i), map.nodelist.get(j), map);
                astar.SearchPath();
            }
        }
    }

    public static void Compare_Three_Search_Methods(Map map) {
        for (int i = 0; i < 1; i++) {
            for (int j = i + 1; j < map.nodelist.size(); j++) {
                System.out.println("Astar:");
                new Astar(map.nodelist.get(i), map.nodelist.get(j), map).SearchPath();
                System.out.println("Greedy:");
                new Greedy(map.nodelist.get(i), map.nodelist.get(j), map).SearchPath();
                System.out.println("Dynamic Programming:");
                new DynamicProgramming(map.nodelist.get(i), map.nodelist.get(j), map).SearchPath();

            }
        }
    }
}
