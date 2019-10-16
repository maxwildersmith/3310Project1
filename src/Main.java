import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.function.Function;

public class Main {

    private static long timeout = (long)(60*1000 * 1), startSize=10000, maxSize=200000;
    private static double growthRate=1.2;


    public static void main(String[] args) {
        System.out.println("\n###########################################\nCS3310 Project #1\nTannaz Damavandi\nby Maximum Wilder-Smith");

        task2();
    }

    private static void task1(){
        System.out.println("\n###########################################\nTask #1 - Sorting Algorithms\n");
        Sort<Integer> sorter = new Sort<>();

        Sorter merge = new Sorter(6,"Merge", sorter::mergeSort);
        merge.run();

        Sorter quick = new Sorter(6,"Quick", sorter::quickSort);
        quick.run();
    }

    private static void task2(){
        System.out.println("\n###########################################\nTask #2 - Tower of Hanoi\n");
        ArrayList<Long> sizes = new ArrayList<>();
        ArrayList<Long> times = new ArrayList<>();
        long time = 0;
        int size = 2;
        while(time<timeout&&size<=maxSize){
            Hanoi tower = new Hanoi(size,false);
            long startT = System.currentTimeMillis();
            tower.start();
            time = System.currentTimeMillis()-startT;
            times.add(time);
            sizes.add((long)size);
            size*=2;
            System.out.println("Hanoi: \t"+size+" disks took \t"+time);
        }
        createCSV("Hanoi.csv",sizes,times);
        System.out.println("Finished Tower of Hanoi up to "+Math.sqrt(size)+" disks");
    }

    private static void task3(){
        System.out.println("\n###########################################\nTask #3 - Matrix Multiplication\n");
        Matrix mat = new Matrix(16);
        mat.generateSequentialValues();
        Matrix mat2 = new Matrix(16);
        mat2.generateSequentialValues();
        mat.print();

        Matrix m = Matrix.strassenMultiplication(mat,mat2);
        m.print();
    }


    /**
     * A helper method to generate a randomized Integer array of a given size.
     * @param size The size of the array to create.
     * @return The randomly shuffled array of a given size.
     */
    public static Integer[] randomSeqArr(long size){
        ArrayList<Integer> tmp = new ArrayList<>();
        for(int i=1;i<=size;i++)
            tmp.add(i);
        Collections.shuffle(tmp);
        return tmp.toArray(new Integer[0]);
    }

    /**
     * A helper class that allows the methods to be run and tested in individual threads.
     */
    public static class Sorter extends Thread{
        Function<Integer[], Long> method;
        int maxRuns, runNum;

        /**
         * Constructor that takes the name of the algorithm to test, and a method reference to the algorithm.
         * @param runCount The number of trials to be run for this algorithm.
         * @param name The name of the algorithm that is being tested.
         * @param method The reference to the method that runs the algorithm.
         */
        public Sorter(int runCount,String name, Function<Integer[], Long> method){
            maxRuns = runCount;
            this.method = method;
            this.setName(name);
            runNum=1;
        }

        /**
         * A custom run method from the Thread class that is called when the new thread is created.
         */
        public void run(){
            ArrayList<Long> sizes = new ArrayList<>();
            ArrayList<Long> times = new ArrayList<>();
            long last=0;
            long size=startSize;
            Integer[] data =randomSeqArr(size);

            while(maxSize==0?last<timeout/growthRate:size<maxSize){
                last = method.apply(data);
                times.add(last);
                sizes.add(size);
                size*=growthRate;
                data=randomSeqArr(size);
            }
            createCSV(getName()+".csv",sizes,times);
            System.out.println("Finished "+getName()+" iteration "+runNum++ +" of "+maxRuns);
            if(runNum<=maxRuns)
                run();
        }
    }

    /**
     * Static helper method to export a CSV file for a given set of algorithm times and sizes. If the CSV file
     * already exists, it will append to the file, and add additional array size values if needed.
     * @param filename The name of the file to create.
     * @param sizes An ArrayList of the dataset sizes.
     * @param times An ArrayList of the time to perform a given operation.
     * @return True if successfully created the CSV, False if there was some IO error.
     */
    public static boolean createCSV(String filename, ArrayList<Long> sizes, ArrayList<Long> times){
        File csv = new File(""+filename);
        try {
            if(csv.createNewFile()) {
                PrintWriter file = new PrintWriter(new FileWriter(csv));
                file.print("Size (n)");
                for (int i = 0; i < sizes.size(); i++)
                    file.print(","+sizes.get(i));
                file.print("\nTimes (ms)");
                for(Long time:times)
                    file.print(","+time);
                file.println();
                file.close();

            } else {
                Scanner fileReader = new Scanner(csv);
                String currentSizes = fileReader.nextLine();
                int size=currentSizes.split(",").length-1;
                if(size<sizes.size()){
                    for(int i=size;i<sizes.size();i++)
                        currentSizes+=","+sizes.get(i);
                    currentSizes+="\n";
                    while(fileReader.hasNextLine())
                        currentSizes+=fileReader.nextLine()+"\n";
                    FileWriter writer = new FileWriter(csv);
                    writer.write(currentSizes);
                    writer.close();
                }

                PrintWriter file = new PrintWriter(new FileWriter(csv,true));
                file.print("Times (ms)");
                for(Long time:times)
                    file.print(","+time);
                file.println();
                file.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
