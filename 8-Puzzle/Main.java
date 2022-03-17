package cs451;
import java.util.Scanner;
public class Main {
    // Initial State = 3,5,6,1,2,4,0,7,8
    // Goal State    = 0,1,2,3,4,5,6,7,8

    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);
        System.out.println("Enter the grid size:");
        int size = scn.nextInt();
        scn.nextLine();

        System.out.println("Type 1D nxn array as a string, exm : 8,1,0,2,3,4,5,7,6" + "\n" +
                        "Enter the initial state:" );
        String initial_s = scn.nextLine();

        System.out.println("Type 1D nxn array as a string, exm : 0,1,2,3,4,5,6,7,8"+ "\n" +
                "Enter the goal state:" );
        String goal_s = scn.nextLine();

        int[] initial_state = new int[size+1];
        int i = 0;
        for(String s : initial_s.split(",")){
            initial_state[i] = Integer.parseInt(s);
            i++;
        }

        int[] goal_state = new int[size+1];
        i = 0;
        for(String s : goal_s.split(",")){
            goal_state[i] = Integer.parseInt(s);
            i++;
        }

        Node initial_node = new Node(initial_state,size+1,0);




        System.out.println("\n-------Menu-------\n"+
                "1. BFS\n"+
                "2. DFS\n"+
                "3. UCS\n"+
                "4. A*\n"+
                "5. Exit\n");
        int choice = scn.nextInt();

        if(choice == 1){
            BFS bfs_search = new BFS(goal_state);
            bfs_search.search(initial_node);
        }else if(choice == 2){
            DFS dfs = new DFS(goal_state);
            dfs.search(initial_node);
        }else if(choice == 3){
            UCS ucs = new UCS(goal_state);
            ucs.search(initial_node);
        }else if(choice == 4){
            AStar astar = new AStar(goal_state);
            astar.search(initial_node);
        }else if(choice == 5){
            System.out.println("Exit!");

        }else{
            System.out.println("Please enter valid search algorithm !");
        }




    }




}
