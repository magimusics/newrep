package crypto;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Caretka {
    public static void main(String[] d) throws FileNotFoundException, IOException {
        long start = System.currentTimeMillis();
        BufferedReader flr = new BufferedReader(new FileReader("lng.csv"));
        BufferedWriter flr3 = new BufferedWriter(new FileWriter("newFile.csv"));
        File file = new File("Hello3.txt");
        Pattern pat=Pattern.compile(".*;.*;.*");
        Matcher matcher;

        Map<String, String> firstColumn = new HashMap<>();
        ArrayList<String> firstColumnMatch = new ArrayList<>();

        Map<String, String>  secondColumn = new HashMap<>();
        ArrayList<String> secondColumnMatch = new ArrayList<>();

        Map<String, String> thirdColumn = new HashMap<>();
        ArrayList<String> thirdColumnMatch = new ArrayList<>();

        ArrayList<String> results = new ArrayList<>();

        Set<String> set = new TreeSet<>();
        String [] mas = new String[3];
        String [][] theWholeAmount;

        int size, fsize, sSize, thSize, bp, howMuch=0;
        String string = "";

        while (flr.ready()){
            string = flr.readLine();
            matcher=pat.matcher(string);
            if (matcher.find())
                set.add(string);

        }
        flr.close();

        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            mas=((String) iterator.next()).split(";");
            if(!firstColumn.containsKey(mas[0])) {
                firstColumn.put(mas[0], mas[1] + ";" + mas[2]);
            }
            else {
                if(!mas[0].equals("\"\"")) {
                    firstColumnMatch.add(mas[0] + ";" + mas[1] + ";" + mas[2]);
                    firstColumnMatch.add(mas[0] + ";" + firstColumn.get(mas[0]));
                }
            }

            if(!secondColumn.containsKey(mas[1])) {
                secondColumn.put(mas[1], mas[0] + ";" + mas[2]);
            }
            else {
                if(!mas[1].equals("\"\"")) {
                    secondColumnMatch.add(mas[1] + ";" + mas[0] + ";" + mas[2]);
                    secondColumnMatch.add(mas[1] + ";" + secondColumn.get(mas[1]));
                }
            }

            if(!thirdColumn.containsKey(mas[2])) {
                thirdColumn.put(mas[2], mas[0] + ";" + mas[1]);
            }
            else {
                if(!mas[2].equals("\"\"")) {
                    thirdColumnMatch.add(mas[2] + ";" + mas[0] + ";" + mas[1]);
                    thirdColumnMatch.add(mas[2] + ";" + thirdColumn.get(mas[2]));
                }
            }

        }
        set.removeAll(set);
        fsize = firstColumnMatch.size();
        sSize = secondColumnMatch.size();
        thSize = thirdColumnMatch.size();
        size = fsize + sSize + thSize;
        theWholeAmount = new String[size][3];


        for(int i = 0; i<fsize; i++) {
            mas=firstColumnMatch.get(i).split(";");
            theWholeAmount[i][0] = mas[0];
            theWholeAmount[i][1] = mas[1];
            theWholeAmount[i][2] = mas[2];
        }

        for(int i = fsize; i<fsize+sSize; i++) {
            mas=secondColumnMatch.get(i-fsize).split(";");
            theWholeAmount[i][0] = mas[1];
            theWholeAmount[i][1] = mas[0];
            theWholeAmount[i][2] = mas[2];
        }

        for(int i = fsize+sSize; i<size; i++) {
            mas=thirdColumnMatch.get(i-fsize-sSize).split(";");
            theWholeAmount[i][0] = mas[1];
            theWholeAmount[i][1] = mas[2];
            theWholeAmount[i][2] = mas[0];
        }


        for(int i=0; i<size; i++) {
            set.add(theWholeAmount[i][0] + ";" + theWholeAmount[i][1] + ";" + theWholeAmount[i][2]);
        }
        size = set.size();
        Iterator it = set.iterator();
        bp = 0;
        while (it.hasNext()){
            theWholeAmount[bp] = ((String)it.next()).split(";");
            bp++;
        }

        System.out.println("TEST");
        int p=0;
        for(int i=0; i<size; i++){
            for(int j=i+1; j<size; j++) {
                if (!theWholeAmount[j][0].equals("\"\"")) {
                    if (theWholeAmount[j][0].equals(theWholeAmount[i][0])) {
                        results.add("Group");
                        results.add(theWholeAmount[i][0] + ";" + theWholeAmount[i][1] + ";" + theWholeAmount[i][2]);
                        results.add(theWholeAmount[j][0] + ";" + theWholeAmount[j][1] + ";" + theWholeAmount[j][2]);

                        for (int k = 0; k < size; k++) {
                            if (!theWholeAmount[j][1].equals("\"\""))
                                if (theWholeAmount[j][1].equals(theWholeAmount[k][1]) & k != j) {
                                    results.add(theWholeAmount[k][0] + ";" + theWholeAmount[k][1] + ";" + theWholeAmount[k][2]);
                                    if(!theWholeAmount[j][2].equals(theWholeAmount[k][2]))
                                        for (int m = 0; m < size; m++) {
                                            if (!theWholeAmount[k][2].equals("\"\""))
                                                if (theWholeAmount[k][2].equals(theWholeAmount[m][2]) & k != m) {
                                                    results.add(theWholeAmount[m][0] + ";" + theWholeAmount[m][1] + ";" + theWholeAmount[m][2]);
                                                }

                                        }
                                }

                        }

                        for (int k = 0; k < size; k++) {
                            if (!theWholeAmount[j][2].equals("\"\""))
                                if (theWholeAmount[j][2].equals(theWholeAmount[k][2]) & k != j) {
                                    results.add(theWholeAmount[k][0] + ";" + theWholeAmount[k][1] + ";" + theWholeAmount[k][2]);
                                }

                        }
                    }
                }

            }

            for(int j=i+1; j<size; j++) {
                if (!theWholeAmount[j][1].equals("\"\"")) {
                    if (theWholeAmount[j][1].equals(theWholeAmount[i][1])) {
                        results.add("Group");
                        results.add(theWholeAmount[i][0] + ";" + theWholeAmount[i][1] + ";" + theWholeAmount[i][2]);
                        results.add(theWholeAmount[j][0] + ";" + theWholeAmount[j][1] + ";" + theWholeAmount[j][2]);
                        if(!theWholeAmount[j][2].equals(theWholeAmount[i][2]))
                            for (int k = 0; k < size; k++) {
                                if (!theWholeAmount[j][2].equals("\"\""))
                                    if (theWholeAmount[j][2].equals(theWholeAmount[k][2]) & k != j) {
                                        results.add(theWholeAmount[k][0] + ";" + theWholeAmount[k][1] + ";" + theWholeAmount[k][2]);
                                    }

                            }
                    }
                }
            }

            for(int j=i+1; j<size; j++) {
                if (!theWholeAmount[j][2].equals("\"\"")) {
                    if (theWholeAmount[j][2].equals(theWholeAmount[i][2])) {
                        results.add("Group");
                        results.add(theWholeAmount[i][0] + ";" + theWholeAmount[i][1] + ";" + theWholeAmount[i][2]);
                        results.add(theWholeAmount[j][0] + ";" + theWholeAmount[j][1] + ";" + theWholeAmount[j][2]);
                    }
                }
            }
        }


        Map<Integer, Integer> treeMap = new TreeMap<>();

        for (int i=results.size()-1; i>=0; i--)
        {
            if(results.get(i).equals("Group"))
            {
                if(p!=0){
                    treeMap.put(i, p);
                    p=0;
                }
            }
            else
            {
                p++;
            }
        }
        int[][] finalMassive = new int[treeMap.size()][2];
        p=0;
        for(Map.Entry<Integer,Integer> find : treeMap.entrySet()){
            finalMassive[p][0]=find.getKey();
            finalMassive[p][1]=find.getValue();
            p++;
        }

        for(int i=0; i<treeMap.size(); i++){
            if(finalMassive[i][1]>1) howMuch++;
            for (int j=i+1; j<treeMap.size(); j++)
            {
                if(finalMassive[j][1]>finalMassive[i][1])
                {
                    p = finalMassive[j][1];
                    finalMassive[j][1] = finalMassive[i][1];
                    finalMassive[i][1] = p;

                    p = finalMassive[j][0];
                    finalMassive[j][0] = finalMassive[i][0];
                    finalMassive[i][0] = p;
                }
            }
        }

        flr3.write("We have got " + howMuch + "elements biggern than single.\n");
        for (int i=0; i<treeMap.size(); i++) {
            for (int j = finalMassive[i][0]; j < finalMassive[i][0] + finalMassive[i][1] + 1; j++){
                if(results.get(j).equals("Group")){
                    flr3.write("Group #"+(i+1)+"\n");
                }
                else flr3.write(results.get(j)+"\n");
            }

        }


        long finish = System.currentTimeMillis();

        System.out.println(finish-start);

    }
}