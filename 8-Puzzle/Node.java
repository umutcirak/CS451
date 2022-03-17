package cs451;

import java.util.ArrayList;

public class Node {

    public static int size = 0;
    public Node parent = null;
    public int[] state;
    public ArrayList<Node> children;
    public int index_of_zero;
    public static int id_k = 64;
    public char id;
    public int cost_g;
    public int cost_h;


    public Node(int[] state, int size, int cost_g) {
        this.children = new ArrayList<Node>();
        this.state = state;
        this.index_of_zero = index_setter();
        this.size = size;
        this.cost_g = cost_g;

        setID();
    }

    public Node(int[] state, int index_of_zero, Node parent, int cost_g) {

        this.children = new ArrayList<Node>();
        this.state = state;
        this.index_of_zero = index_of_zero;
        this.parent = parent;
        this.cost_g = cost_g;
        setID();
    }

    public void setID(){
        id_k++;
        this.id = (char)id_k;
    }

    public void view(){

        System.out.println("Node ID: " + this.id);
        int one_line = (int) Math.sqrt(state.length);
        for (int i = 0; i < state.length; i++) {
            System.out.print(state[i] + " ");
            if (i == one_line-1){
                System.out.println();
                one_line += one_line;
            }
        }
        System.out.println();

    }

    public void swap(int[] state, int index_1 , int index_2 ){
        int tmp = state[index_1];
        state[index_1] = state[index_2];
        state[index_2] = tmp;
    }

    public void generateChildren(){

        int sqrt = (int) Math.sqrt(size);

        // Shift Left
        if( index_of_zero % sqrt > 0 ){
            int[] child_state = copyArray(this.state);
            swap(child_state, index_of_zero, index_of_zero-1);
            Node n = new Node(child_state,this.index_of_zero-1,this,this.cost_g+1);
            this.children.add(n);
        }
        // Shift Right
        if( index_of_zero % sqrt < (sqrt-1)  ){
            int[] child_state = copyArray(this.state);
            swap(child_state, index_of_zero, index_of_zero+1);
            Node n = new Node(child_state,this.index_of_zero+1,this,this.cost_g+1);
            this.children.add(n);
        }
        // Shift Up
        if(index_of_zero - sqrt >= 0){
            int[] child_state = copyArray(this.state);
            swap(child_state, index_of_zero, index_of_zero-sqrt);
            Node n = new Node(child_state,this.index_of_zero-sqrt,this,this.cost_g+1);
            this.children.add(n);
        }
        // Shift Down
        if(index_of_zero + sqrt < size){
            int[] child_state = copyArray(this.state);
            swap(child_state, index_of_zero, index_of_zero+sqrt);
            Node n = new Node(child_state,this.index_of_zero+sqrt,this,this.cost_g+1);
            this.children.add(n);
        }
    }

    public int[] copyArray(int[] arr){
        int[] new_arr = new int[arr.length];
        for (int i = 0; i < arr.length ; i++) {
            new_arr[i] = arr[i];
        }
        return new_arr;
    }

    public void view_children(){
        System.out.println("Children of " + this.id+ " are: ");
        System.out.println("\n------------------------");
        for (Node n : this.children){
            n.view();
        }
        System.out.println("\n------------------------");
    }

    public int index_setter(){
        for (int i = 0; i < state.length; i++) {
            if(state[i] == 0)
                return i;
        }
        return 0;
    }

    public boolean is_Same_State(int[] s){
        boolean is_Same = true;
        for (int i = 0; i < state.length; i++) {
            if( !(this.state[i] == s[i]) ) {
                is_Same = false;
                break;
            }
        }
        return is_Same;
    }




}
