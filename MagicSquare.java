
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mshroff
 */
public class MagicSquare {
    
    public static void main(String[] args) throws IOException{
        
        //use the following code to fetch input from console 
     	String line; 
     	BufferedReader inp = new BufferedReader (new InputStreamReader(System.in)); 
     	line=inp.readLine();
 
        int arraySize = Integer.valueOf(line); 
        int[][] square = new int[arraySize][arraySize]; 
        for( int i = 0 ; i <  arraySize; i++){
              inp = new BufferedReader (new InputStreamReader(System.in)); 
              line=inp.readLine();
              String[] inputNumbers = line.split(" ");
              int counter = 0 ; 
              for( String num : inputNumbers){
                  square[i][counter] = Integer.valueOf(num);
                  counter++;
              }
        }
    
         
        System.out.println("Input ::::");
        for(int i = 0; i < square.length; i++){
            for(int j=0; j < square[0].length;j++){
                System.out.println(square[i][j]);
            }
        } 
        
        int magicSq = getMagicSquare(square);
        if(magicSq == 0){
             System.out.println(" Magic Square not found..."); 
        }
        else{
             System.out.println(" magicSq = " + magicSq);
        } 
        
        System.out.println( magicSq);
    }
    public static int getMagicSquare(int[][] square) {

        int maxSize = square.length;
        for (int x = maxSize; x >= 2; x--) {
            System.out.println(" matrix size == " + x ); 
            for (int y = 0; y < x -1; y++) {
                System.out.println(" y  == " + y ); 
                int leftdiagonal = sumLeftDiagonal(square, 0, y, x-y+1);
                System.out.println("Left diagonal: " + leftdiagonal);

                int rightdiagonal = sumRightDiagonal(square, 0, x-1, y);
                System.out.println("Right diagonal: " + rightdiagonal);

                if (leftdiagonal == rightdiagonal) {
                    System.out.println("Found the magic square of size: " + x);
                    return x;
                }
            } 
        }
        return 0;
    }
    
    public static int sumLeftDiagonal(int[][] square, int rowIndex, int columnIndex , int size){
        int sum = 0 ;
        for(int i=rowIndex,j=columnIndex; i < size && j < size ; i++, j ++){
           
            sum += square[i][j]; 
             System.out.println( " square[" + i +"][" + j + "] = " +  square[i][j] +  " , sum = " + sum);
         }
        return sum;
    }
    
    public static int sumRightDiagonal(int[][] square, int rowIndex, int columnIndex , int size ){
        int sum = 0 ;
        for(int i=rowIndex,j=columnIndex; i <= columnIndex  - size && j >=0  ; i++, j--){
           
            sum += square[i][j]; 
           System.out.println( " square[" + i +"][" + j + "] = " +  square[i][j] +  " , sum = " + sum);
         }
        return sum;
    }
}
