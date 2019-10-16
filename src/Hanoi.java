public class Hanoi{
    private int size;
    private int[][] towers;
    private boolean print;

    public Hanoi(int size, boolean print){
        this.print = print;
        this.size = size;
        towers = new int[3][size];
        for(int i=1;i<=size;i++)
            towers[0][i-1]=size-i+1;
        //print();
    }

    public void start(){
        move(size,0,1,2);
    }

    private int popTower(int towerIndex){
        for(int i=size-1;i>=0;i--)
            if(towers[towerIndex][i]!=0){
                int tmp = towers[towerIndex][i];
                towers[towerIndex][i] = 0;
                return tmp;
            }
        return 0;
    }

    private void pushTower(int towerIndex, int value){
        for(int i=0;i<size;i++)
            if(towers[towerIndex][i]==0){
                towers[towerIndex][i]=value;
                if(print)
                print();
                return;
            }
    }

    private void move(int disks, int src, int mid, int dst){
        if(disks==1){
            int val = popTower(src);
            //System.out.println("Moving "+val+" from tower "+src+" to tower "+dst);
            pushTower(dst,val);
        } else {
            move(disks-1,src,dst,mid);
            int val = popTower(src);
            //System.out.println("Moving "+val+" from tower "+src+" to tower "+dst);
            pushTower(dst,val);
            move(disks-1,mid,src,dst);
        }
    }

    public void print(){
        for(int i=size-1;i>=0;i--)
            System.out.println(towers[0][i]+"\t\t"+towers[1][i]+"\t\t"+towers[2][i]);
        System.out.println();
    }
}
