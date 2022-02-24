/*=============================================================================
 |  Building a Prim's MST for an input graph
 |
 |  Author:  Josiah James and Ron Campos
 |  Language:  Java
 |
 |
 +=============================================================================*/

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

class Node {

    int destination;
    double key;

    Node() {

    }

    Node(int destination, double key) {

        this.destination = destination;
        this.key = key;

    }

}


class comparator implements Comparator<Node> {

    @Override
    public int compare(Node firstVertex, Node secondVertex)
    {
        int returnVal = 0;
        if (firstVertex.key == secondVertex.key)
            return returnVal;
        if (firstVertex.key > secondVertex.key)
            returnVal = 1;
        if (firstVertex.key < secondVertex.key)
            returnVal = -1;

        return returnVal;
    }
}

class adjGraph {


    int verts;
    ArrayList<Node>[] adjacentNodes;

    adjGraph(int verts, int numEdges) {

        this.verts = verts;
        adjacentNodes = new ArrayList[verts];
        for(int i = 0; i < verts; i++) {

            adjacentNodes[i] = new ArrayList<Node>();

        }

    }

    public void addEdge(int firstVert, int secondVert, double weight) {

        Node edge = new Node(secondVert, weight);
        Node edge2 = new Node(firstVert, weight);
        adjacentNodes[firstVert].add(edge);
        adjacentNodes[secondVert].add(edge2);

    }

    public void primMST(){

        Node[] parent = new Node[verts];
        Node[] nu = new Node[verts];
        boolean[] mst = new boolean[verts];

        for (int i = 0; i < verts; i++) {

            nu[i] = new Node();
            parent[i] = new Node();
            nu[i].key = Integer.MAX_VALUE;
            nu[i].destination = i;
            parent[i].destination = -1;

            mst[i] = false;

        }

        nu[0].key = 0;
        mst[0] = true;

        PriorityQueue<Node> queue = new PriorityQueue<Node>(new comparator());

        for (int j = 0; j < verts; j++){
            queue.add(nu[j]);
        }

        while(!queue.isEmpty()) {

            Node temp = queue.poll();
            mst[temp.destination] = true;

            for (Node itr : adjacentNodes[temp.destination]) {

                if (!mst[itr.destination]) {

                    if(nu[itr.destination].key > itr.key) {

                        queue.remove(nu[itr.destination]);
                        nu[itr.destination].key = itr.key;
                        queue.add(nu[itr.destination]);
                        parent[itr.destination].destination = temp.destination;
                        parent[itr.destination].key = itr.key;

                    }
                }
            }
        }

        print(parent);

    }


    void print(Node[] parent) {
        double weightSum = 0;
        for(int i = 1; i < verts; i++) {
            System.err.println(parent[i].destination + " - " + i + " " + parent[i].key);
            weightSum += parent[i].key;
        }
        System.out.printf("%.5f\n", weightSum);
    }
}



public class Prims {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File(args[0]);
        Scanner scan = new Scanner(file);

        int maxVerts = scan.nextInt();
        int numEdges = scan.nextInt();

        adjGraph graph = new adjGraph(maxVerts, numEdges);
        for(int i = 0; i < numEdges; i++) {

            graph.addEdge(scan.nextInt(), scan.nextInt(), scan.nextDouble());

        }
        graph.primMST();
    }

}
 