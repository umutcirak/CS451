package cs451;

import java.lang.reflect.Array;
import java.util.*;

public class BFS {

    public static ArrayList<Node> fringe;
    public static ArrayList< ArrayList< ArrayList<Node>  >  > visited;
    public static ArrayList<Node> path;
    public static int depth = 0;
    public static int[] goal_state;
    public double elapsed_time;


    public BFS(int[] goal_state) {
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

    public void search(Node root){

        long startTime = System.currentTimeMillis();

        boolean is_found = false;
        fringe.add(root);
        //view_Fringe();



        while (!fringe.isEmpty() && !is_found){
            //view_Fringe();
            Node node = fringe.get(0);
            fringe.remove(0);
            add_Visited(node);

            node.generateChildren();
            for(Node child: node.children){

                if(child.is_Same_State(goal_state)){
                    is_found = true;
                    elapsed_time = ( System.currentTimeMillis() - startTime) / 1000.0;
                    path_tracker(child);
                    show_Results(elapsed_time);
                    break;
                }
                if(!contains(fringe, child) && !check_Visited(child)){
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
        System.out.println("Match Found!");
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
