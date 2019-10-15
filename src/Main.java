import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;

public class Main {

    private static long timeout = (long)(60*1000 * 8), startSize=10000, maxSize=200000;
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
        Hanoi tower = new Hanoi(4);
        tower.start();
    }

    private static void task3(){
        System.out.println("\n###########################################\nTask #3 - Matrix Multiplication\n");
        Hanoi tower = new Hanoi(4);
        tower.start();
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
            Sort.createCSV(getName()+".csv",sizes,times);
            System.out.println("Finished "+getName()+" iteration "+runNum++ +" of "+maxRuns);
            if(runNum<=maxRuns)
                run();
        }
    }
}
