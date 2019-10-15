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

    public static void strassenMultiplicationRecursive(int n, Matrix mat1, Matrix mat2, Matrix prod){
        if(n<=2){

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
