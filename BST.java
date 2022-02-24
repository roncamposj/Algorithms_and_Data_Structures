/*=============================================================================
| Building and managing a BST
|
| Author: Ronald Campos
+=============================================================================*/
import java.io.File;
import java.util.Scanner;


public class BST extends Node
{
    public Node root;

    public BST()
    {
        super();
        this.root = null;
    }


    public boolean search(Node root, int key)  {
        //if root is null,then return false
        if(root==null)
            return false;

        if(root.key == key)
            return true;

         search(root.left,key);
         search(root.right,key);

         return false;
    }



    //insert a node in appropriate position
    public Node insert(Node root, int key)
    {
        if (root == null)
        {
            root = new Node(key);
            return root;
        }

        if (key < root.key)
            root.left = insert(root.left, key);

        else if (key > root.key)
            root.right = insert(root.right, key);

        return root;
    }

    //helper function when deleting a node and replacing it with it's lowest element on right.
    public int findMin(Node root)
    {

        int min = root.key;

        while (root.left != null)
        {
            min = root.left.key;
            root = root.right;
        }

        return min;
    }

    public void deleteNode(int key)
    {
        root = delete(root, key);
    }

    //delete a node
    public Node delete(Node root, int key)
    {
        if (root == null)
            return root;

            if (key < root.key)
                root.left = delete(root.left, key);

            else if (key > root.key)
                root.right = delete(root.right, key);

            else
            {
                if (root.left == null)
                    return root.right;

                else if (root.right == null)
                    return root.left;

                root.key = findMin(root.right);
                root.right = delete(root.right, root.key);
            }
            return root;

    }

    //print inorder traversal
    public void print(Node root)
    {
        if (root != null)
        {
            print(root.left);
            System.out.print(" " + root.key);
            print(root.right);
        }
    }

    public int countSubtree(Node root)
    {
        //base case for when last leafs are found
        if(root==null)
            return 0;

        return countSubtree(root.left) + countSubtree(root.right) +1;
    }

    public void countChildren()
    {
        int leftChildren = countSubtree(this.root.left);
        int rightChildren = countSubtree(this.root.right);
        int leftDepth = getDepth(this.root.left);
        int rightDepth = getDepth(this.root.right);

        System.out.println("left children: \t" + leftChildren);
        System.out.println("left depth: \t" + leftDepth);
        System.out.println("right children: \t" +  rightChildren);
        System.out.println("right depth: \t" + rightDepth);
    }

    public int getDepth(Node node)
    {

        if(node==null)
            return 0;

        return Math.max(getDepth(node.left),getDepth(node.right))+1;
    }

    //denotes info and how hard program was
    public void complexityIndicator()
    {
        String nid = "ro834336";
        double difficulty = 3;
        double hoursSpent = 8.0;
        System.err.println(nid + "; " + difficulty + "; " + hoursSpent);
    }


    public static void main(String[] args) throws Exception
    {
        BST tree = new BST();

        String file = args[0];
            //open the file
            Scanner print = new Scanner(new File(file));
            Scanner in = new Scanner(new File(file));

        while (print.hasNextLine()) {
            System.out.println(print.nextLine());
            }
            print.close();

            while (in.hasNextLine()) {
                String line = in.nextLine();

                //separate commands and numbers
                String[] input = line.split(" ");
                int inputLength = input.length;

                if ((input[0].equals("i") || input[0].equals("d") || input[0].equals("s")) && inputLength != 2)
                    System.out.println("Command-> i missing numeric parameter");

                switch(input[0]) {
                    //insert
                    case "i":

                        tree.root = tree.insert(tree.root, Integer.parseInt(input[1]));
                    break;
                    //delete
                    case "d":
                        if (tree.search(tree.root,Integer.parseInt(input[1])))
                        tree.root = tree.delete(tree.root, Integer.parseInt(input[1]));
                        else
                            System.out.println(Integer.parseInt(input[1]) + ": NOT found - NOT deleted");
                    break;
                    //search
                    case "s":
                        if (tree.search(tree.root, Integer.parseInt(input[1])))
                            System.out.println(Integer.parseInt(input[1]) + ": found");
                        else
                            System.out.println(Integer.parseInt(input[1]) + ": NOT found");
                    break;
                    //print
                    case "p":

                        tree.print(tree.root);
                        System.out.println();
                    break;
                    //quit
                    case "q":
                        break;
                }
            }

            //close scanner
            in.close();

        //print the children count and the complexity.
        tree.countChildren();
        tree.complexityIndicator();

    }


}
//class for nodes.  Not public so it can be in the same file.
class Node
{
    public Node() { }
    public int key;

    public Node left, right;

    public Node(int key)
    {
        this.key = key;
        this.left = this.right = null;
    }
}

