public class Hanoi{
    private int size;
    private int[][] towers;

    public Hanoi(int size){
        this.size = size;
        towers = new int[3][size];
        for(int i=1;i<=size;i++)
            towers[0][i-1]=i;
        print();
    }

    public void start(){
        move(size,0,1,2);
    }

    public int popTower(int towerIndex){
        for(int i=size-1;i>=0;i--)
            if(towers[towerIndex][i]!=0){
                int tmp = towers[towerIndex][i];
                towers[towerIndex][i] = 0;
                return tmp;
            }
        return 0;
    }

    public void pushTower(int towerIndex, int value){
        for(int i=0;i<size;i++)
            if(towers[towerIndex][i]==0){
                towers[towerIndex][i]=value;
                print();
                return;
            }
    }

    public void move(int disks, int src, int mid, int dst){
        //print();
        if(disks==1){
            int val = popTower(src);
            System.out.println("Moving "+val+" from tower "+src+" to tower "+dst);
            pushTower(dst,val);
            //print();
        } else {
            move(disks-1,src,dst,mid);
            int val = popTower(src);
            //print();
            System.out.println("Moving "+val+" from tower "+src+" to tower "+dst);
            pushTower(dst,val);
            move(disks-1,mid,src,dst);
            //print();
        }
        //print();
    }

    public void print(){
        for(int i=size-1;i>=0;i--)
            System.out.println(towers[0][i]+"\t\t"+towers[1][i]+"\t\t"+towers[2][i]);
        System.out.println();
    }
}
