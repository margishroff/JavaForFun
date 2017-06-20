/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//mshroff
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

//To list out the frequency of all the works in a movie. Few Good Men one of my favorites.
public class MovieScriptWordCount {
    
    public static void main(String[] args) {
        try {
            File f = new File("C:\\data\\movie_fewgoodmen.txt");
            Scanner sc;
            sc = new Scanner(f);
            // sc.useDelimiter("[^a-zA-Z']+");
            
            Map<String, Integer> wordCount = new TreeMap<>();
            
            while(sc.hasNext()) {
                String word = sc.next();
                if(!wordCount.containsKey(word))
                    wordCount.put(word, 1);
                else
                    wordCount.put(word, wordCount.get(word) + 1);
            }
            
            // show results
            for(String word : wordCount.keySet())
                System.out.println(word + " " + wordCount.get(word));
            System.out.println(wordCount.size());
            
        }
        catch(IOException e) {
            System.out.println("Unable to read from file.");
        }
    }
}