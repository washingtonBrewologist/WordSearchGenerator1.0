/*@version 1.0                         [ Assignment 1: Word Search Generator ]
 *                                             Creator:Nick Williams
 *                                            Instructor:Ryan Parsons
 *                                                -> 1/30/2019 <-
 *
 *                              WordSearch Class takes input words from user and
 *                              arranges them into a WordSearch puzzle.
 * */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;

public class WordSearch {

    // Words input by user
    private String[] inputWords;

    // A boolean representation of the solved puzzle
    private boolean[][] solvedPuzzle;

    // The character array containing our words as characters
    private char[][] wordToChar;

    // post: takes input words from string array and makes them lower case,
    // Creates char array, places word characters into char array, and fills
    // remaining empty spots.
    public void generate(String[] inputWords){
        for (int i = 0; i < inputWords.length; i++){
            inputWords[i] = inputWords[i].toLowerCase();
        }
        this.inputWords = inputWords;
        char[][] wordsToCharArray = buildPuzzle();
        for (int i = 0; i < wordsToCharArray.length; i++){
            placeWords(wordsToCharArray, i);
        }
        fillEmptySpots();
    }

    // post: Returns a string representation of the Word Search
    // solution for the client.
    public String printSolution(){
        String newLine = "\n";
        String appender = "";
        for (int i = 0; i < wordToChar.length; i++){
            for (int j = 0; j < wordToChar[i].length; j++){
                if (solvedPuzzle[i][j]){
                    appender+= " " + wordToChar[i][j] + " ";
                } else {
                    appender += " - ";
                }
            }
            appender += newLine;
        }
        return appender;
    }

    // post: Places a word into our character array. Determines the direction
    // and attempts to find a place to fit the word.
    public void placeWords(char[][] wordChar, int iteration){
        Random random = new Random();
        int wordDirection = random.nextInt(3);

        // used to determine position within character array
        int[] position = {0,0};

        // wordDirection == 0 will look for 'horizontal' placement
        if (wordDirection == 0) {
            // Determines if the word has been placed into our array
            boolean placed = hasBeenPlaced(position, random, wordChar, iteration);

            // if placed update char array and solved puzzle array to reflect addition
            if (placed){
                for (int i = 0; i < wordChar[iteration].length; i++){
                    wordToChar[position[0]][position[1]]= wordChar[iteration][i];
                    solvedPuzzle[position[0]][position[1]] = true;
                    // update position
                    position[0]++;
                }
            }

            // wordDirection == 1 will look for 'vertical' placement
        } else if (wordDirection == 1){
            boolean placed = hasBeenPlaced(position, random, wordChar, iteration);
            if (placed){
                for (int i = 0; i < wordChar[iteration].length; i++){
                    wordToChar[position[0]][position[1]]= wordChar[iteration][i];
                    solvedPuzzle[position[0]][position[1]] = true;
                    position[1]++;
                }
            }
            // wordDirection == 2 will look for 'diagonal' placement
        } else if (wordDirection == 2){
            boolean placed = hasBeenPlaced(position, random, wordChar, iteration);
            if (placed) {
                for (int i = 0; i < wordChar[iteration].length; i++) {
                    wordToChar[position[0]][position[1]] = wordChar[iteration][i];
                    solvedPuzzle[position[0]][position[1]] = true;
                    position[1]++;
                    position[0]++;
                }
            }
        }
    }

    // post: Returns true or false based on if the word was placed into the array
    public boolean hasBeenPlaced(int[] position, Random random, char[][] wordChars, int generatorIndex){
        int attempts = 0;
        boolean placed = false;
        // Has 100 tries to attempt to fit a word into the array
        while(!placed && attempts <= 100){
            position[0] = random.nextInt((wordToChar.length -1) - wordChars[generatorIndex].length);
            position[1] = random.nextInt((wordToChar.length -1) - wordChars[generatorIndex].length);
            placed = true;
            for (int i = 0; i < wordChars[generatorIndex].length; i++){
                if (wordToChar[position[0] + i][position[1]] != '\u0000' && wordToChar[position[0] + i][position[1]]
                        != wordToChar[generatorIndex][i]){
                    placed = false;
                }
            }
            attempts++;
        }
        return placed;
    }

    // post: Determines how big to make the puzzle based on the longest word
    // input by the user. Sets the current initialized puzzle to the correct
    // size to handle words of varying sizes.
    public char[][] buildPuzzle(){
        char[][] wordToChar = new char[inputWords.length][];
        int longest = 8;
        for (int i = 0; i < inputWords.length; i++){
            wordToChar[i] = inputWords[i].toCharArray();
            if (wordToChar[i].length > longest){
                longest = wordToChar[i].length;
            }
        }
        if (inputWords.length > longest){
            longest = inputWords.length;
        }
        this.wordToChar = new char[longest + 4][longest + 4];
        this.solvedPuzzle = new boolean[longest + 4][longest + 4];
        return wordToChar;
    }

    //post: Returns a string representation of the Word Search
    //for client.
    public String toString() {
        String newLine = "\n";
        String appender = "";
        for (int i = 0; i < wordToChar.length; i++) {
            for (int j = 0; j < wordToChar[i].length; j++) {
                appender += " " + wordToChar[i][j] + " ";
            }
            appender += newLine;
        }
        return appender;
    }

    // post: Fills remaining null or empty spaces in character array
    // with random characters.
    public void fillEmptySpots(){
        Random letter = new Random();
        for (int i = 0; i < wordToChar.length; i++){
            for (int j = 0; j < wordToChar[i].length; j++){
                if (wordToChar[i][j] == '\u0000'){
                    wordToChar[i][j] = (char)(letter.nextInt(26) + 97);
                }
            }
        }
    }

    // post: Allows for saving of Word Search to user indicated
    // file.
    public void toFile(File file) throws FileNotFoundException{
        PrintStream wsOut = new PrintStream(file);
        wsOut.println();
        wsOut.println(" [ Word Search ]");
        wsOut.println(this);
        wsOut.println();
        wsOut.println("[ Word Search Solution ]");
        wsOut.println(this.printSolution());
    }

}