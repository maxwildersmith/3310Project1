import java.util.*;

public class Matrix {
    private int[][] mat;
    private int n;

    public Matrix(int size){
        n = size;
        mat = new int[n][n];
    }

    public Matrix(int[][] values){
        n = values.length;
        mat = values;
    }

    public void print(){
        for(int[] row: mat) {
            for (int i : row)
                System.out.print(i + "\t");
            System.out.println();
        }
    }

    public int[][] subsection(int startRow, int startCol, int endRow, int endCol){
        if(startRow<0||startCol<0||endRow>n||endCol>n)
            return null;
        int[][] out = new int[endRow-startRow][endCol-startCol];
        for(int i=0;i<out.length;i++)
            for(int j=0;j<out[0].length;j++)
                out[i][j] = mat[startRow+i][startCol+j];
            return out;
    }

    public int getSize(){
        return n;
    }

    public int get(int i, int j){
        return mat[i][j];
    }

    public void set(int i, int j, int val){
        mat[i][j]=val;
    }

    public static Matrix classicMultiplication(Matrix mat1, Matrix mat2) {
        if(mat1.getSize()!=mat2.getSize())
            return null;
            //throw new Exception("Mismatched Matrix sizes!");
        Matrix prod = new Matrix(mat1.getSize());
        for(int i=0;i<mat1.getSize();i++)
            for(int j=0;j<mat1.getSize();j++){
                prod.set(i,j,0);
                for(int k=0;k<mat1.getSize();k++)
                    prod.set(i,j,prod.get(i,j)+mat1.get(i,k)*mat2.get(k,j));
            }
        prod.print();
            return prod;
    }

    public static Matrix strassenMultiplication(Matrix mat1, Matrix mat2){
        Matrix prod = new Matrix(mat1.getSize());
        strassenMultiplicationRecursive(mat1.getSize(),mat1,mat2,prod);
        return prod;
    }

    public int[][] getData(){
        return mat;
    }

    public void setValues(Matrix mat1){
        mat = mat1.getData();
    }

    public static void strassenMultiplicationRecursive(int n, Matrix mat1, Matrix mat2, Matrix prod){
        if(n<=2){
            prod.set(0,0,mat1.get(0,0)*mat2.get(0,0)+mat1.get(0,1)*mat2.get(1,0));
            prod.set(0,1,mat1.get(0,0)*mat2.get(0,1)+mat1.get(0,1)*mat2.get(1,1));
            prod.set(1,0,mat1.get(1,0)*mat2.get(0,0)+mat1.get(1,1)*mat2.get(1,0));
            prod.set(1,1,mat1.get(1,0)*mat2.get(0,1)+mat1.get(1,1)*mat2.get(1,1));
        }
        else{
            Matrix[] mat1Split = split(mat1);
            Matrix[] mat2Split = split(mat2);
            Matrix p = new Matrix(n/2);
            Matrix q = new Matrix(n/2);
            Matrix r = new Matrix(n/2);
            Matrix s = new Matrix(n/2);
            Matrix t = new Matrix(n/2);
            Matrix u = new Matrix(n/2);
            Matrix v = new Matrix(n/2);

            strassenMultiplicationRecursive(n/2,add(mat1Split[0],mat1Split[3]),add(mat2Split[0],mat2Split[3]),p);
            strassenMultiplicationRecursive(n/2,add(mat1Split[2],mat1Split[3]),mat2Split[0],q);
            strassenMultiplicationRecursive(n/2,mat1Split[0],subtract(mat2Split[1],mat2Split[3]),r);
            strassenMultiplicationRecursive(n/2,mat1Split[3],subtract(mat2Split[2],mat2Split[0]),s);
            strassenMultiplicationRecursive(n/2,add(mat1Split[0],mat1Split[1]),mat2Split[3],t);
            strassenMultiplicationRecursive(n/2,subtract(mat1Split[2],mat1Split[0]),add(mat2Split[0],mat2Split[1]),u);
            strassenMultiplicationRecursive(n/2,subtract(mat1Split[1],mat1Split[3]),add(mat2Split[2],mat2Split[3]),v);

            prod.setValues(Matrix.merge(add(subtract(add(p,s),t),v),add(r,t),add(q,s),add(subtract(add(p,r),q),u)));
        }
    }

    public static Matrix[] split(Matrix mat){
        Matrix[] mats = new Matrix[4];
        mats[0] = new Matrix(mat.subsection(0,0,mat.getSize()/2,mat.getSize()/2));
        mats[1] = new Matrix(mat.subsection(0,mat.getSize()/2,mat.getSize()/2,mat.getSize()));
        mats[2] = new Matrix(mat.subsection(mat.getSize()/2,0,mat.getSize(),mat.getSize()/2));
        mats[3] = new Matrix(mat.subsection(mat.getSize()/2,mat.getSize()/2,mat.getSize(),mat.getSize()));
        return mats;
    }


    public static Matrix subtract(Matrix mat1, Matrix mat2){
        int[][] diff = new int[mat1.getSize()][mat1.getSize()];
        for(int i=0;i<mat1.getSize();i++)
            for(int j=0;j<mat1.getSize();j++)
                diff[i][j]=mat1.get(i,j)-mat2.get(i,j);
            return new Matrix(diff);
    }

    public static Matrix add(Matrix mat1, Matrix mat2){
        int[][] sum = new int[mat1.getSize()][mat1.getSize()];
        for(int i=0;i<mat1.getSize();i++)
            for(int j=0;j<mat1.getSize();j++)
                sum[i][j]=mat1.get(i,j)+mat2.get(i,j);
        return new Matrix(sum);
    }

    public static Matrix merge(Matrix mat11, Matrix mat12, Matrix mat21, Matrix mat22){
        int[][] merged = new int[mat11.getSize()*2][mat11.getSize()*2];
        for(int i=0;i<mat11.getSize();i++)
            for(int j=0;j<mat11.getSize();j++)
                merged[i][j]=mat11.get(i,j);

        for(int i=0;i<mat12.getSize();i++)
            for(int j=0;j<mat12.getSize();j++)
                merged[i][mat11.getSize()+j]=mat12.get(i,j);

        for(int i=0;i<mat21.getSize();i++)
            for(int j=0;j<mat21.getSize();j++)
                merged[mat11.getSize()+i][j]=mat21.get(i,j);

        for(int i=0;i<mat22.getSize();i++)
            for(int j=0;j<mat22.getSize();j++)
                merged[mat11.getSize()+i][mat11.getSize()+j]=mat22.get(i,j);

            return new Matrix(merged);
    }

    public void generateSequentialValues(){
        Stack<Integer> values = new Stack<>();
        for(int i=0;i<n*n;i++)
            values.push(i+1);
        Collections.reverse(values);
        for(int i=0;i<mat.length;i++)
            for(int j=0;j<mat[0].length;j++)
                mat[i][j]=values.pop();
    }

    public void generateRandomValues(){
        Stack<Integer> values = new Stack<>();
        for(int i=0;i<n*n;i++)
            values.push(i+1);
        Collections.shuffle(values);
        for(int i=0;i<mat.length;i++)
            for(int j=0;j<mat[0].length;j++)
                mat[i][j]=values.pop();
    }

    public boolean equals(Matrix obj) {
        if(n!=obj.getSize())
            return false;
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                if(obj.get(i,j)!=get(i,j))
                    return false;
                return true;
    }
}
