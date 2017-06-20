/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mshroff
 */
public class BitOperations {
     public static void main(String args[]){
         int a = 60;
         int b = 13; 
         int c = 0; 
         
         c = -a;
         System.out.println("-a = " + c);
         
         c = a&b;
         System.out.println("a&b ==>" + c );
          
         c = a^b;
         System.out.println("a^b ==>" + c);
          
         c = a|b; 
         System.out.println("a|b ==>" + c);
         
         c= a <<2;
         System.out.println("a<<2 ==> " + c);
         
         c = a >> 2;
         System.out.println("a>>2 ==> " + c);
         
         c = a >>>2;
         System.out.println("a>>>2 ==> "+ c);
          
     }
}
