import java.util.Random;
/**
 * Message class controls the random words and check for user input along with validation
 */

public class Message{
    /**
     * String array which has pool of 20 words to generate a random one from it
     */
    private static final String[] MAIN_WORDS = {
    "MICROSOFT","IMAGES","VIDEO","VISUAL","INTERNET","MONITOR","PRINTER","SPEAKER","GAME","MOBILE","LAPTOP","ONLINE","OFFLINE","WINDOWS","APPLE","ONEPLUS","JAVASCRIPT", "ASUS","BEATSAUDIO","SONY"
    };
    private final String secretWord;
    private char userInput;
    private int inputLength;
    private StringBuilder madeOne;


    /**
     * Default constructor to initialize the random word and the empty string using _ for better representation in output JOptionpane
     */
    Message() {
        Random rand = new Random();
        this.secretWord = MAIN_WORDS[rand.nextInt(MAIN_WORDS.length)];
        this.inputLength = secretWord.length();
        this.userInput = ' ';
        this.madeOne = new StringBuilder();

        for(int i=0; i<this.inputLength;i++){
            madeOne.append("_");
        }
    }

    /**
     * returns true of overall word matched with our random word from the pool
     */
    public Boolean solved() {
        int count = 0;
        for(int i=0; i<inputLength; i++){
            if(secretWord.charAt(i) == madeOne.charAt(i)){
                count++;
            }
        }

        if(count == inputLength){
            return true;
        }else {
            return false;
        }
    }

    /**
     * replace the specific index of the stringbuilder array with the character which is matched
     */
    public void updater(char s, int j) {
        madeOne.setCharAt(j, s);
    }

    /**
     * checking if the specific character matched the random word
     */
    public Boolean guess(char c){
        Boolean found = false;
        for(int i=0; i<inputLength; i++){
            if(secretWord.charAt(i) == c){
                found = true;
                updater(c,i);
            }
        }
        return found;
    }
    /**
    * returning the final overloaded string which is made using string builder
    */
    public String toString() {
        return madeOne.toString();
    }
}
