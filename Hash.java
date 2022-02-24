/*=============================================================================
| Implementing a Hash function
|
| Author: Ronald Campos
| Language: Java
+=============================================================================*/
import java.io.File;
import java.util.Scanner;



class UCFxram
{
    UCFxram() {}


    //implements UCFxram pseudocode
    public int hashy(String str, int len)
    {

        int tempData = 0;
        int randVal1 = 0xbcde98ef;
        int randVal2 = 0x7890face;
        int hashVal = 0xfa01bc96;
        int roundedEnd = len & 0xfffffffc;
        int shifty = 0;

        for (int i = 0; i < roundedEnd; i+=4)
        {
            tempData = str.charAt(i) & 0xff;
            tempData |= (str.charAt(i+1) & 0xff) << 8;
            tempData |= (str.charAt(i+2) & 0xff) << 16;
            tempData |= (str.charAt(i+3) & 0xff) << 24;
            tempData *= randVal1;
            tempData = Integer.rotateLeft(tempData, 12);
            tempData *= randVal2;
            hashVal ^= tempData;
            hashVal = Integer.rotateLeft(hashVal, 13);
            hashVal = (hashVal * 5) + 0x46b6456e;
        }
        tempData = 0;

        if ((len & 0x03) == 3)
        {
            tempData = (str.charAt(roundedEnd + 2) & 0xff) << 16;
            len -= 1;
        }
        if ((len & 0x03) == 2)
        {
            tempData |= (str.charAt(roundedEnd + 1) & 0xff) << 8;
            len -= 1;
        }
        if ((len & 0x03) == 1)
        {
            tempData |= str.charAt(roundedEnd) & 0xff;
            tempData *= randVal1;
            tempData = Integer.rotateLeft(tempData, 14);
            tempData *= randVal2;
            hashVal ^= tempData;
        }

        hashVal ^= len;
        hashVal &= 0xb6acbe58;
        hashVal = hashVal ^ hashVal >>> 13;
        hashVal *= 0x53ea2b2c;
        hashVal = hashVal ^ hashVal >>> 16;

        return hashVal;

    }

}


public class Hash {

    public static void main(String[] args) throws Exception
    {
        //instantiate UCFxram and read in file
        UCFxram run = new UCFxram();
        Hash print = new Hash();
        String fileName = args[0];


        Scanner in = new Scanner(new File(fileName));

        //loop, process hash and print as you go along
        while (in.hasNextLine()) {
            String str = in.nextLine();
            int len = str.length();
            len = run.hashy(str, len);
            System.out.format("%10x:%s\n",len, str);
        }

        System.out.println("Input file processed");
        print.complexityIndicator();
        System.exit(0);



    }

   
}


