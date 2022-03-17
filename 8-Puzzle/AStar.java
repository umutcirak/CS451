package cs451;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class AStar {

    public static ArrayList<Node> fringe;
    public static ArrayList< ArrayList< ArrayList<Node>  >  > visited;
    public static ArrayList<Node> path;
    public static int depth = 0;
    public static int[] goal_state;
    public double elapsed_time;



    public AStar(int[] goal_state) {
        this.fringe =  new ArrayList<Node>();
        this.goal_state = goal_state;
        this.path = new ArrayList<Node>();
        set_visited();
    }

    public void set_visited(){
        this.visited = new  ArrayList< ArrayList< ArrayList<Node>  >  >();
        int size = goal_state.length;
        for (int i = 0; i < size; i++) {
            visited.add(new ArrayList<ArrayList<Node>  >());
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j <(size) ; j++) {
                visited.get(i).add(new ArrayList<Node>());
            }
        }


    }

    public void search(Node root) {

        long startTime = System.currentTimeMillis();

        boolean is_found = false;
        fringe.add(root);

        Node node = null;

        while (!fringe.isEmpty() && !is_found){

            node = low_cost_Node();
            add_Visited(node);
            //view_Fringe();
            // node.view();

            if(node.is_Same_State(goal_state)){
                is_found = true;
                elapsed_time = ( System.currentTimeMillis() - startTime) / 1000.0;
                path_tracker(node);
                System.out.println("Match Found!");
                show_Results(elapsed_time);
                break;
            }

            node.generateChildren();

            for(Node child: node.children){

                if(!contains(fringe, child) && !check_Visited(child)){
                    child.cost_h = manhattan_distance(child);
                    this.fringe.add(child);

                }

            }

        }

        if(!is_found){
            System.out.println("MATCH NOT FOUND!");
            elapsed_time = ( System.currentTimeMillis() - startTime) / 1000.0;
            show_Results(elapsed_time);

        }


    }


    public Node low_cost_Node(){

        Node node = null;
        Node min = fringe.get(0);
        int min_cost = min.cost_g + min.cost_h;

        for (int i = 1; i < fringe.size(); i++) {
            node = fringe.get(i);
            if( (node.cost_g + node.cost_h) < min_cost ){
                min = node;
                min_cost = node.cost_g + node.cost_h;
            }
        }

        fringe.remove(min);
        return min;
    }



    public int manhattan_distance(Node n){

        int heuristic = 0;

        for (int i = 0; i < goal_state.length ; i++) {

            for (int j = 0; i < goal_state.length ; j++) {
                if(n.state[j] == i){
                    heuristic += Math.abs(i - j);
                    break;
                }
            }

        }

        return heuristic;
    }

    public void path_tracker(Node n){
        int count = 0;
        Node tmp = n ;
        this.path.add(tmp);
        while(tmp.parent != null){
            tmp = tmp.parent;
            this.path.add(tmp);
            count++;
        }
        depth = count;
    }

    public void add_Visited(Node node){
        int first_element = node.state[0];
        int second_element = node.state[1];

        visited.get(first_element).get(second_element).add(node);
    }

    public boolean check_Visited(Node node){
        int first_element = node.state[0];
        int second_element = node.state[1];
        boolean is_visited = false;

        for(Node n : visited.get(first_element).get(second_element)){
            if(node.is_Same_State(n.state))
                return true;
        }
        return is_visited;
    }


    public boolean contains(ArrayList<Node> list , Node n){
        boolean contains = false;
        for (int i = 0; i < list.size(); i++) {
            if( list.get(i).is_Same_State(n.state)  ){
                contains = true;
                break;
            }
        }
        return contains;
    }

    public int total_Visited_Nodes(){
        int count = 0;
        for (ArrayList<ArrayList<Node>> arr : visited){
            for(ArrayList<Node> arr2: arr){
                count += arr2.size();
            }
        }
        return count;
    }

    public void show_Results(double elapsed_time){
        System.out.println("Number of visited nodes: " + total_Visited_Nodes());
        System.out.println("Depth of graph: " + depth);
        System.out.println("Elapsed Time: " + elapsed_time);
        System.out.println("The path: ");

        for (int i = path.size() -1; i >= 0 ; i--) {
            path.get(i).view();
        }

    }

    public void view_Fringe(){
        String s = "Fringe: ";
        for(Node n: fringe){
            s = s + " " + n.id + " ";
        }
        System.out.println(s);
    }





}
