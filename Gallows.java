/**
 * Gallows class controlls the hangman body following the game conditions
 */

public class Gallows{
    /**
     * We have our hangman stored in string array
     */
        private final String[] hangman = {
            "|",
            "(-_-)",
            "|",
            "\\ | /",
            "|",
            "/ \\"
        };

    /**
     *initializing the maximum number of wrong possibilities for the player to do and wordguess counter
     */
    
        private int wrongGuessCounter;
        private final int maxWrong = 6;         
    
        Gallows() {
            this.wrongGuessCounter = 0;         //using default constructor to  initialize the player guess to zero
        }
    
        public void incrementHangman() {            //function to increase the hangman body counter
            this.wrongGuessCounter = this.wrongGuessCounter + 1;
            System.out.println(wrongGuessCounter);
        }
    
        public Boolean isDead() {                   //checking if hangman is dead and returning true and false for it
            if(this.wrongGuessCounter > maxWrong){
                return true;
            }
            return false;
        }
    
        public String toString() {   //I have made loop for doign  the string overloading here
            StringBuilder status = new StringBuilder();
                    status.append("<html><pre>_________________\n")
                          .append("                |               \n")
                          .append("              __|__             \n");
            //status.append("")
            for(int i = 0; i<wrongGuessCounter; i++){       //using for loop for arranging the spacing for printing hangman
                if(i == 0 || i ==4){
                    status.append("                "+hangman[i]+"        \n");
                }
                if(i==1){
                    status.append("              "+hangman[i]+"        \n");
                }
                if(i==2){
                    status.append("                "+hangman[i]+"        \n");
                }
                if(i == 3){
                    status.append("              "+hangman[i]+"        \n");
                }
                if(i==5) {
                    status.append("               "+hangman[i]+"        </pre></html>\n");
                }

            }
            return status.toString();   //returning our final string from gallows class which has our hangman condition        
        }
    
}