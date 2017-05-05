import java.io.*;
import java.util.*;

public class NewCrypto {

    public static void main(String[] d) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader flr = new BufferedReader(new FileReader("lng-2.csv"));
        BufferedWriter flr3 = new BufferedWriter(new FileWriter("newFile.csv"));
        Map<String, ArrayList<Integer>> firstColumn = new HashMap<>();

        Map<String, ArrayList<Integer>> secondColumn = new HashMap<>();

        Map<String, ArrayList<Integer>> thirdColumn = new HashMap<>();

        Set<Integer> treeSet = new TreeSet<>();
        Set<String> set = new HashSet<>();

        ArrayList<String> results = new ArrayList<>();
        ArrayList<Integer> indexes;
        Iterator iterator;
        Queue<Integer> queue = new PriorityQueue<>();
        Group groups;

        String[] mas;
        ArrayList<String[]> massive = new ArrayList<>();
        ArrayList<FinalMassive> finalArray;
        int setSize, howMuch;

        while (flr.ready()) {
            set.add(flr.readLine());
        }
        flr.close();

        iterator = set.iterator();
        while (iterator.hasNext()){
            massive.add(getMasStrings((String) iterator.next()));
        }
        set.removeAll(set);

        setSize = massive.size();

        firstColumn.putAll(getElementsAndIndexes(massive, setSize, 0));
        secondColumn.putAll(getElementsAndIndexes(massive, setSize, 1));
        thirdColumn.putAll(getElementsAndIndexes(massive, setSize, 2));

        for (int i=0; i<setSize; i++){
            mas = massive.get(i);
            if(firstColumn.containsKey(mas[0])) {
                indexes = firstColumn.get(mas[0]);

                queue.addAll(indexes);
                treeSet.addAll(indexes);
                firstColumn.remove(mas[0]);

                while (!queue.isEmpty()) {
                    getGroups(massive, 0, firstColumn, treeSet, queue);
                    getGroups(massive, 1, secondColumn, treeSet, queue);
                    getGroups(massive, 2, thirdColumn, treeSet, queue);

                    queue.remove();
                }

                iterator = treeSet.iterator();
                if (results.size() > 0) {
                    if (!results.get(results.size() - 1).equals("Group")) {
                        results.add("Group");
                    }
                }
                while (iterator.hasNext()) {
                    mas = massive.get((int) iterator.next());
                    results.add(mas[0] + ";" + mas[1] + ";" + mas[2]);
                }
                treeSet.removeAll(treeSet);
            }
        }


        if(secondColumn.size()!=0) {
            for (int i=0; i<setSize; i++){
                mas = massive.get(i);
                if(secondColumn.containsKey(mas[1])) {
                    indexes = secondColumn.get(mas[1]);

                    queue.addAll(indexes);
                    treeSet.addAll(indexes);
                    secondColumn.remove(mas[1]);

                    while (!queue.isEmpty()) {
                        getGroups(massive, 1, secondColumn, treeSet, queue);
                        getGroups(massive, 0, firstColumn, treeSet, queue);
                        getGroups(massive, 2, thirdColumn, treeSet, queue);

                        queue.remove();
                    }

                    iterator = treeSet.iterator();
                    if (results.size() > 0) {
                        if (!results.get(results.size() - 1).equals("Group")) {
                            results.add("Group");
                        }
                    }
                    while (iterator.hasNext()) {
                        mas = massive.get((int) iterator.next());
                        results.add(mas[0] + ";" + mas[1] + ";" + mas[2]);
                    }
                    treeSet.removeAll(treeSet);
                }
            }
        }

        if(thirdColumn.size()!=0) {
            for (int i=0; i<setSize; i++){
                mas = massive.get(i);
                if(thirdColumn.containsKey(mas[2])) {
                    indexes = thirdColumn.get(mas[2]);

                    queue.addAll(indexes);
                    treeSet.addAll(indexes);
                    thirdColumn.remove(mas[2]);

                    while (!queue.isEmpty()) {
                        getGroups(massive, 1, secondColumn, treeSet, queue);
                        getGroups(massive, 2, thirdColumn, treeSet, queue);
                        getGroups(massive, 0, firstColumn, treeSet, queue);

                        queue.remove();
                    }

                    iterator = treeSet.iterator();
                    if (results.size() > 0) {
                        if (!results.get(results.size() - 1).equals("Group")) {
                            results.add("Group");
                        }
                    }
                    while (iterator.hasNext()) {
                        mas = massive.get((int) iterator.next());
                        results.add(mas[0] + ";" + mas[1] + ";" + mas[2]);
                    }
                    treeSet.removeAll(treeSet);
                }
            }
        }

        groups = new Group(results);
        groups.sort();
        finalArray = groups.getFinalArray();
        howMuch = finalArray.size();

        flr3.write("We have got " + howMuch + " elements bigger than single.\n\n");
        for (int i=0; i<howMuch; i++) {
            for (int j = finalArray.get(i).getValue(); j < finalArray.get(i).getValue()+finalArray.get(i).getKey() + 1; j++){
                if(results.get(j).equals("Group")){
                    flr3.write("Group #"+(i+1)+"\n");
                }
                else flr3.write(results.get(j)+"\n");
            }

        }
        flr3.close();

        long finish = System.currentTimeMillis();
        System.out.println("We have got " + howMuch + " elements bigger than single.");
        System.out.println("time is "+(finish-start));

    }

        public static String[] getMasStrings(String str) {

            char[] chars = str.toCharArray();
            String[] mas = new String[3];
            int length = chars.length;
            int fringe = 1;

            for (int i = 0; i < 3; i++)
                mas[i] = "";

            for (int i = 0; i < length; i++) {
                if (chars[i] == ';') {
                    fringe++;

                } else {
                    if (fringe == 1) {
                        mas[0] += chars[i];
                    }
                    if (fringe == 2) {
                        mas[1] += chars[i];
                    }
                    if (fringe == 3 && i != length) {
                        mas[2] += chars[i];
                    }
                }
            }
            return mas;
        }

        public static Map<String, ArrayList<Integer>> getElementsAndIndexes(ArrayList<String[]> massive, int setSize, int column)
        {
            Map<String, ArrayList<Integer>> mapColumn = new HashMap<>();
            Map<String, Integer> checking = new HashMap<>();

            for(int i=0; i<setSize; i++) {
                if (!massive.get(i)[column].equals("") & !massive.get(i)[column].equals("\"\"")) {
                    if (!checking.containsKey(massive.get(i)[column])) {
                        checking.put(massive.get(i)[column], i);
                    } else {
                        if (mapColumn.containsKey(massive.get(i)[column])) {
                            mapColumn.get(massive.get(i)[column]).add(i);
                        }
                        else {
                            mapColumn.put(massive.get(i)[column], new ArrayList<>(Arrays.asList(i)));
                            mapColumn.get(massive.get(i)[column]).add(checking.get(massive.get(i)[column]));
                        }
                    }
                }
            }
            return mapColumn;
        }

        public static void getGroups(ArrayList<String[]> massive, int column, Map<String, ArrayList<Integer>> currentColumnMap, Set treeSet, Queue<Integer> queue){
            String string;
            int temp;
            ArrayList<Integer> indexes;

            if (currentColumnMap.containsKey(massive.get(queue.peek())[column])) {
                string = massive.get(queue.peek())[column];
                indexes = currentColumnMap.get(string);

                for (int j = 0; j < indexes.size(); j++) {
                    temp = indexes.get(j);
                    if (!treeSet.contains(temp)) {
                        queue.add(temp);
                    }
                }
                treeSet.addAll(indexes);
                currentColumnMap.remove(string);
            }
        }
}

class FinalMassive{
    private int key;
    private int Value;

    public int getKey() {
        return key;
    }

    public int getValue() {
        return Value;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setValue(int value) {
        Value = value;
    }

    public FinalMassive(int key, int value) {
        this.key = key;
        Value = value;
    }
}

class Group{
    private ArrayList<String> results;
    private ArrayList<FinalMassive> finalArray = new ArrayList<>();
    private int p = 0;

    public Group(ArrayList<String> arrayList) {
        results = arrayList;
        for (int i=results.size()-1; i>=0; i--)
        {
            if(results.get(i).equals("Group"))
            {
                if(p!=0){
                    finalArray.add(new FinalMassive(p, i));
                    p=0;
                }
            }
            else
            {
                p++;
            }
        }
    }

    public void sort(){
        Collections.sort(finalArray, new Comparator<FinalMassive>() {
            @Override
            public int compare(FinalMassive o1, FinalMassive o2) {
                if(o1.getKey() > o2.getKey())
                    return -1;
                if(o1.getKey() < o2.getKey())
                    return 1;
                return 0;
            }
        });
    }

    public ArrayList<FinalMassive> getFinalArray() {
        return finalArray;
    }
}
