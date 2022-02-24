///*=============================================================================
//| Building and managing a Skiplist
//|
//| Author: Ronald Campos
//| Language: Java
//|
//+=============================================================================*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


class Node
{
    public int key;
    public Node next;

    public Node(int key)
    {
        this.key = key;
        this.next = null;
    }

}

class SkipList
{
    private final int NEG_INF = Integer.MIN_VALUE;
    private final int POS_INF = Integer.MAX_VALUE;
    public Random rand = new Random();
    public Node head = null;
    public Node tail = null;

    public int insert(SkipList[] levels, int key) {
        Node curr;

        int i = 0;
        curr = levels[i].head;

        while(curr.next != null)
        {
            if (curr.key == key) {
                return ++i;
            }
            curr = curr.next;
        }

        levels[i].promote(key);

        while((rand.nextInt()%2+2)%2 == 1)
        {
            i++;
            levels[i].promote(key);
        }
        return ++i;
    }


    public void promote(int key)
    {
        Node temp = new Node(key);
        Node curr = head;

        if(head == null)
        {
            head = temp;
            tail = temp;
        }

        else if(head.key > key)
        {
            temp.next = head;
            head = temp;
        }

        else if(tail.key < key)
        {
            tail.next = temp;
            tail = temp;
        }

        else if (head.key < key && tail.key > key)
        {
            while(curr.next.key <= key)
                curr = curr.next;

            temp.next = curr.next;
            curr.next = temp;
        }

    }

    //delete the node from list
    public boolean delete(SkipList[] list, int level, int key)
    {
        int flag = 0;
        while(level > 0)
        {
            level--;

            if(list[level].deleteNode(key))
            {
                flag = 1;
            }
        }

        if(flag == 1)
        {
            return true;
        }

        return false;
    }

    public Boolean deleteNode(int key)
    {
        Node tempHead = head;
        Node tempTail = tail;

        if(tempHead == null)
        {
            return false;
        }

        else if(tempHead == tempTail) {
            if (tempHead.key == key) {
                tempHead = null;
                tempTail = null;
                return true;
            }
        }
        else if(tempHead.key == key)
        {
            tempHead = tempHead.next;
            return true;
        }
        else
        {
            while (tempHead.next != null) {
                if (tempHead.next.key == key) {
                    tempHead.next = tempHead.next.next;
                    return true;
                }
                tempHead = tempHead.next;
            }
        }
        return false;
    }

    //search through the list
    public boolean search(SkipList[] list, int level, int key)
    {
        int flag = 0;

        while (flag == 0) {
            while(level >= 0){
                level--;

                if (list[level].searchNode(key))
                    flag = 1;

            }
        }

        if(flag == 1)
        {
            return true;
        }

        return false;
    }

    public Boolean searchNode(int key)
    {
        Node temp = head;

        if(temp == null)
        {
            return false;
        }
        else if(temp == tail)
        {
            if (temp.key != key)
                return false;

            return true;
        }

        else
        {
            while(temp.next != null) {
                if (temp.next.key == key) {
                    return true;
                }
                temp = temp.next;
            }
        }
        return false;
    }


    // func that creates a predetermined number of levels for the skiplist and attaches all
    // head and tail ptrs to neg and pos infinity.
    public SkipList[] addEndpoints()
    {
        int numOfLevs = 0;
        SkipList[] levels = new SkipList[30];
        while(numOfLevs < 30)
        {
            levels[numOfLevs] = new SkipList();
            levels[numOfLevs].promote(NEG_INF);
            levels[numOfLevs].promote(POS_INF);
            numOfLevs++;
        }
        return levels;
    }

    //print
    public void printAll(SkipList[] level, int heightOfSL, int inputLength)
    {
        int index;
        int i;
        int j;
        String[] printArr = new String[inputLength];
        for(i = 0; i < heightOfSL; i++)
        {
            Node curr = level[i].head;
            index = 0;
            String compare;

            //add repetitions of key to that arr element if heads on coin flip
            if (i > 0) {
                while(curr != null)
                {
                    index = 0;
                    compare = " " + curr.key + "; ";
                    if(curr.key != Integer.MAX_VALUE && curr.key != Integer.MIN_VALUE)
                    {
                        j = 1;
                        while (j < i)
                        {
                        compare = compare + " " + curr.key + "; ";
                        j++;
                        }
                        while(!compare.equals(printArr[index]))
                        {
                            index++;
                        }
                        printArr[index] = printArr[index] + " " + curr.key + "; ";
                    }
                    curr = curr.next;
                }
            }

            //otherwise just add one
            else
            {
                while(curr != null)
                {
                    printArr[index] =" " + curr.key + "; ";
                    index++;
                    inputLength = index;
                    curr = curr.next;
                }
            }

        }

        //actual printing
        for(i = 0; i < inputLength; i++)
            if (!printArr[i].equals(" " + NEG_INF+ "; ")|| !printArr[i].equals(" " + POS_INF+ "; "))
                System.out.println(printArr[i]);
            else {
                if (printArr[i].equals(" " + NEG_INF + "; "))
                    System.out.println("+++infinity");
                else if (printArr[i].equals(" " + POS_INF + "; "))
                    System.out.println("---infinity");
            }
        System.out.println("---End of Skip List---");
    }


}

public class SkipList
{
    public static String[] commands;

    public SkipList(String inputFile) throws FileNotFoundException
    {
        int numOfLines = 0;

        commands = new String[100000];
        Scanner in = new Scanner(new File(inputFile));
        while(in.hasNext())
        {
            commands[numOfLines] = in.next();
            numOfLines++;
        }

    }

    public static void main(String[] args) throws Exception
    {
        int i = 0;
        int level = 1;
        int temp;
        String fileName = args[0];
        String typeOfSeed;
        int rInput;

        if(1 < args.length)
        {
            rInput = Integer.parseInt(args[1]);
            typeOfSeed = "With the RNG seeded with " + rInput + ",";
        }

        else
        {
            rInput = 42;
            typeOfSeed = "With the RNG unseeded,";
        }

        SkipList skip = new SkipList(fileName);

        SkipList newSkipList = new SkipList();
        newSkipList.rand.setSeed(rInput);
        SkipList[] heightOfSL = newSkipList.addEndpoints();
        boolean wasItFound = false;
        boolean wasItDeleted = false;

        for(i = 0; i < commands.length; i++)
        {
            String[] input = commands[i].split(" ");

                switch (input[0]) {
                    case "i":
                        i++;
                        temp = newSkipList.insert(heightOfSL, Integer.parseInt(commands[i]));
                        if (level < temp) {
                            level = temp;
                        }
                    break;
                    //delete
                    case "d":
                        i++;
                        wasItDeleted = newSkipList.delete(heightOfSL, level, Integer.parseInt(commands[i]));
                        if (wasItDeleted)
                            System.out.println(Integer.parseInt(commands[i]) + " deleted");
                        else
                            System.out.println(Integer.parseInt(commands[i]) + " integer not found " + "- delete not successful");


                        break;
                    //search
                    case "s":
                        i++;
                        wasItFound = newSkipList.search(heightOfSL, level, Integer.parseInt(commands[i]));
                        if (wasItFound)
                            System.out.println(Integer.parseInt(commands[i]) + " found");
                        else
                            System.out.println(Integer.parseInt(commands[i]) + " NOT FOUND");
                    break;
                    //print
                    case "p":
                        skip.complexityIndicator();
                        System.out.println("For the input file named " + fileName);
                        System.out.println(typeOfSeed);
                        newSkipList.printAll(heightOfSL, level, commands.length);
                    break;
                    //quit
                    case "q":
                        System.exit(0);
                    default:
                    break;
                }
        }
    }

}
