/*@version 1.0                         [ Assignment 1: Word Search Generator ]
 *                                             Creator:Nick Williams
 *                                            Instructor:Ryan Parsons
 *                                                -> 1/30/2019 <-
 *
 *                                  This program takes in words from a user which
 *                                  are either entered manually 1 by 1 or read in
 *                                  from an existing file.
 *
 *
 * */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class WordSearchMain {
 public static void main(String[] args) throws FileNotFoundException {

     Scanner input = new Scanner(System.in);
     Scanner readFile;
     WordSearch wordSearch = new WordSearch();
     String menuSelection;
     boolean getFile;

     printIntro();
     boolean isGenerated = false;

     // Will keep user menu active until the user enters 'q'
     do {
         userMenu();
         menuSelection = input.next();
         if (menuSelection.equalsIgnoreCase("g")){

             // get File will check if the user would like to input words
             // from a file or enter manually.
             getFile = userPrompt(input);
             if (getFile){
                 String inFile = getFileName(input);
                 readFile = new Scanner(new File(inFile));
                 ArrayList<String> wordsFromFile = new ArrayList<>();
                 while (readFile.hasNext()){
                     wordsFromFile.add(readFile.next());
                 }
                     // We create array words to hold the words input from file
                     String[] words = new String[wordsFromFile.size()];
                     wordsFromFile.toArray(words);
                     // We then generate a new word search with the words and set
                     // isGenerate to true.
                     wordSearch.generate(words);
                     isGenerated = true;
             } else {

                 // Here we take words from the user 1 by 1 and input them into an
                 // array before generating the puzzle.
                 ArrayList<String> manualEntry = new ArrayList<>();
                 System.out.print("How many words would you like to add to your list? : ");
                 int numOfWords = input.nextInt();
                 String wordsIn;
                 for (int i = 1; i <= numOfWords; i++){
                     System.out.print("Word #" + i + ": ");
                     wordsIn = input.next();
                     manualEntry.add(wordsIn);
                 }
                 String[] userWords = new String[manualEntry.size()];
                 manualEntry.toArray(userWords);
                 wordSearch.generate(userWords);
                 isGenerated = true;
             }
         }else if (menuSelection.equalsIgnoreCase("p")){
                if (isGenerated){
                   print(wordSearch);
                } else {
                    System.out.println();
                    System.out.println("You must generate a puzzle before you can print! ");
                }
         }else if(menuSelection.equalsIgnoreCase("s")){
                if (isGenerated) {
                    showSolution(wordSearch);
                } else {
                    System.out.println("You must generate a puzzle before a solution is available!");
                }

                // Allows a user to save a puzzle to a specified file
         } else if (menuSelection.equalsIgnoreCase("sf")){
             if (isGenerated){
                 System.out.println("[Save your puzzle]");
                 System.out.println();
                 System.out.print("Please enter the file you wish to save to: ");
                 String outFile = input.next();
                 wordSearch.toFile(new File(outFile));
                 System.out.println();
                 System.out.println("File: '"  +  outFile + "'" + " was successfully saved!");
             }
         }
     } while (!menuSelection.equalsIgnoreCase("q"));
     System.out.println("Now exiting The Word Search Generator!");
    }

    // Takes the file name input by the user and checks
    // to see if the file exists, if it does, returns the
    // name of the checked file. Parameter inFile represents
    // the name of the file to be validated.
    public static String getFileName(Scanner inFile){
     System.out.print("Please enter the file you wish to read from: ");
     String verifyFile = inFile.next();
     File file = new File(verifyFile);
     while (!file.exists()){
         System.out.println("That file doesn't exits!");
         System.out.println("Enter the file you wish to read from: ");
         verifyFile = inFile.next();
         file = new File(verifyFile);
     }
        return verifyFile;
    }

    // Checks if the user would like to enter words manually or have them
    // read in from a file. Scanner input represents the users menu input and
    // returns the response of of Y or N.
    public static boolean userPrompt(Scanner input){
        System.out.println();
        System.out.println("Welcome to the generator!");
        System.out.println("Here you can choose to generate a new puzzle from");
        System.out.println("scratch, or you can read in words from a .txt file...");
        System.out.println();
        System.out.print("Would you like to read from a .txt?(Y or N): ");
        String response = input.next();
        return response.equalsIgnoreCase("Y") || response.startsWith("y");
    }

    // Prints out our word search. Gets passed in wordSearch
    // which represents our word search object.
   public static void print(WordSearch wordSearch){
       System.out.println(wordSearch);
   }

    // Displays user menu that will be visible to user until
    // they enter 'q'.
   public static void userMenu(){
       System.out.println("\t------------[ User Menu ]-----------");
       System.out.println();

       System.out.println("\tGenerate a new word search:   ( g )");
       System.out.println("\tPrint out your word search:   ( p )");
       System.out.println("\tShow the solution:            ( s )");
       System.out.println("\tSave generated puzzle:        ( sf )");
       System.out.println("\tQuit the program:             ( q )");
       System.out.println();
       System.out.print("\tEnter Selection: ");
   }

    // Prints opening intro for the user.
    public static void printIntro(){
        System.out.println();
        System.out.println("\tWelcome to: WORD SEARCH GENERATOR");
        System.out.println();
        System.out.println("\tThis program will allow you to generate");
        System.out.println("\tyour own word search, or read from a file");
        System.out.println("\tto generate a puzzle.");
        System.out.println();
        System.out.println();
    }

    // Prints out the solution for the user.
    public static void showSolution(WordSearch wordSearch){
        System.out.println("[ -> Puzzle Solution <- ]");
        System.out.println(wordSearch.printSolution());
    }

}
