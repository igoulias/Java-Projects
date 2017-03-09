/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ce325.hw1;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author JOHN
 */
public class MainClass {
    
    public static void main(String[] args) {
        
        String input = "";
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("----------- MENU -----------"
            + "\n\t1. Load dictionary from binary file (type: load fromFilepath)"
            + "\n\t2. Save dictionary to binary file (type: save toFilepath)\n\t3. Populate dictionary from txt file (type: read fromTxtFilePath)"
            + "\n\t4. Suggest word (type: suggest wordPhrase)"
            + "\n\t5. Print dictionary inforamtion (type: print)"
            + "\n\t6. Quit (type: quit)");
        
        
        
        
            input = scanner.next();
            String[] inputArray = input.split("\\s+"); 
            
            
            int check = 1;
            do{
                if(inputArray[0].equals("load")){
                    
                ///////////////////////////HERE IS THE LOAD METHOD//////////////////////////////////////
                }
                else if (inputArray[0].equals("save")){
                    ////////////////////////SAVE METHOD/////////////////////////////////
                }
                else if (inputArray[0].equals("read")){


                }
                else if (inputArray[0].equals("suggest")){

                }
                else if (inputArray[0].equals("print")){

                }
                else if (inputArray[0].equals("quit")){
                    System.out.println("tou gamises ti mana");
                    return;

                }
                else {   
                    System.out.println("Please choose a valid option.");
                    input = scanner.next();
                    String[] inputArray = input.split("\\s+"); 
                    check = 0;

                }
            }while(check == 0);
      
        
        

        
    }
    
}
