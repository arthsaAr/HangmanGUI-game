import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;
import javax.swing.*;

/**
 * Main hangman class which calls objects from message and gallows class and arranged them to print on the specific optionpane and make game playable
 */

public class Hangman implements ActionListener{

    /**
    * Function to replay the game, this function resets the components of the frame and different panels that we included in the first run of the game
    */
    private void gameRestart() {            //function to restart the game, i have initialized two new gallows and message members
        frame.getContentPane().removeAll(); //clearing the frame and making it empty
        frame.remove(panel);
        frame.remove(gallowsPanel);
        frame.remove(messagePanel);
        frame.remove(button2);
        frame.remove(textfield);
        gallows = new Gallows();
        message = new Message();            //clearing the input that we stored to keep track of words in message class
        allInputHold = "";
        loopTracker = 0;                    //resetting teh loop tracker

        
        frame.revalidate();
        frame.repaint();                    //resetting back the frame


        actionPerformed(new ActionEvent(button, ActionEvent.ACTION_PERFORMED, "Restart"));       //calling the actionperformed event which is the main beggining of our game after user clicks START GAME button in the main menu dialogue
    }



    private Message message;
    private Gallows gallows;
    private final int FRAME_WIDTH = 400;
    private final int FRAME_HEIGHT = 150;
    private JFrame frame;
    private static JLabel label;
    private static JLabel gallows_Label;
    private static JLabel message_Label;
    private JButton button;
    private JButton button1;
    private JButton button2;
    private static JPanel panel;
    private static JPanel gallowsPanel;
    private static JPanel messagePanel;
    private JTextField textfield;
    private String allInputHold= "";
    private int loopTracker = 0;
    
    /**
    * Default constructor which maked our basic start window with play game option
    */
    public Hangman(){
            this.gallows = new Gallows();
            this.message = new Message();
            frame = new JFrame();

            button = new JButton("Start the Game!");
            button.setFont(new Font("Tahoma", Font.BOLD, 12));
            button.setMaximumSize(new Dimension(200,30));
            button.addActionListener(this);

            button1 = new JButton("Quit");
            button1.setFont(new Font("Tahoma", Font.BOLD, 12));
            button1.setMaximumSize(new Dimension(200,30));
            button1.addActionListener(this);

            panel = new JPanel();
            panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

            panel.add(button);
            panel.add(button1);

            frame.add(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Hangman Menu");
            frame.setLocation(100, 100);
            frame.pack();
            frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
            frame.setVisible(true);
    
    }

        public static void main(String[] args) {
            new Hangman();      //calling our constructor inside the main
    }

        /**
        * This is our main event handles which is triggred after the start game button is clicked by the user from the first window
        */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == button){
                    panel.remove(button);
                    panel.remove(button1);
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                    if(loopTracker == 1){
                        panel.remove(label);
                        panel.remove(textfield);
                        panel.remove(button2);
                    }

                    messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));       //panel for storing informations from message class
                    message_Label = new JLabel("<html>"+message.toString().replace("", " ")+"</html>");
                    message_Label.setFont(new Font("Tahoma", Font.BOLD, 20));
                    message_Label.setAlignmentX(Component.CENTER_ALIGNMENT);
                    messagePanel.add(message_Label);

                    gallowsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));           //panel for storing information/outputs from gallows panel
                    gallows_Label = new JLabel("<html>"+gallows.toString().replace("\n", "<br>")+"</html>");
                    gallows_Label.setFont(new Font("Tahoma", Font.BOLD, 15));
                    gallows_Label.setAlignmentX(Component.RIGHT_ALIGNMENT);
                    gallowsPanel.add(gallows_Label);

                    gallowsPanel.add(Box.createRigidArea(new Dimension(0, 20))); //distance between than hangman print and the input field/buttons

                    label = new JLabel("Guess the correct letters!");
                    label.setFont(new Font("Tahoma", Font.BOLD, 20));
                    label.setAlignmentX(Component.CENTER_ALIGNMENT);
                    textfield = new JTextField(10);
                    textfield.setPreferredSize(new Dimension(250,20));
                    textfield.setMaximumSize(new Dimension(250,20));

                    button = new JButton("OK");                                 //creating a default ok button which will be pressed by user each time after a guess
                    button.setFont(new Font("Tahoma", Font.BOLD, 12));
                    button.setMaximumSize(new Dimension(200,30));
                    button.addActionListener(new ActionListener() {                 //main logic of our input will be processed after okk button is pressed. We will validate the guesses and also work on our hangman if we have to increment it on appropriate places
                        public void actionPerformed(ActionEvent e){
                            String userGave = textfield.getText();
                            char transfer;
                            if(userGave.length() > 1){          //if user tries to give multiple letters, reseting the textfield and notifying the user about the mistake usng optionpane
                                JOptionPane.showMessageDialog(null, "Your guess must be a letter!", "Input Error", JOptionPane.INFORMATION_MESSAGE);
                                textfield.setText("");
                            }else if (allInputHold.contains(userGave.toUpperCase()) && allInputHold != "" && !message.guess(userGave.toUpperCase().charAt(0))){     //this condition checks if the user given character is already chosen before, and it doesnot exists in the real secret word.
                                gallows.incrementHangman();
                                JOptionPane.showMessageDialog(null, "PENALTY: For entering the same wrong guess again!", "Busted", JOptionPane.INFORMATION_MESSAGE);
                                if(gallows.isDead()){
                                    int choice = JOptionPane.showConfirmDialog(null, "Your man is Hanged!"+"\n"+"Play again?", "GAME OVER!",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                                            if(choice == JOptionPane.YES_OPTION) {
                                                frame.getContentPane().removeAll();                            
                                                panel.remove(label);
                                                panel.remove(textfield);
                                                panel.remove(button);
                                                panel.remove(button2);
                                                gameRestart();
                                            }else if(choice == JOptionPane.NO_OPTION){
                                                System.out.println("Thanks for playing the game");
                                                frame.setVisible(false);
                                                System.exit(0);
                                            }
                                }
                                //panel.remove(gallows_Label);
                                gallows_Label.setText("<html>"+gallows.toString().replace("\n", "<br>")+"</html>");
                                gallows_Label.setAlignmentX(Component.RIGHT_ALIGNMENT);
                                gallowsPanel.add(gallows_Label);
                            }
                            else{       //this is the main condition which runs after all the condiitons are checked, here we will update our message if the character matches and also increment the hangman if the character guessed is wrong
                                if(userGave.matches("[a-z]") || userGave.matches("[A-Z]")){
                                    userGave = userGave.toUpperCase();
                                    transfer = userGave.charAt(0);
                                    allInputHold = allInputHold + transfer;
                                    if(message.guess(transfer)){
                                        message_Label.setText("<html>"+message.toString().replace("", " ")+"</html>");
                                        message_Label.setAlignmentX(Component.CENTER_ALIGNMENT);
                                        messagePanel.add(message_Label);
                                        if(message.solved()){       //regularly checking if the word is already solved to avoid repeatedly asking the user for guesses
                                            frame.setVisible(false);
                                            frame.remove(panel);
                                            
                                            frame.remove(gallowsPanel);
                                            frame.remove(messagePanel);
                                            frame = new JFrame();
                                            int choice = JOptionPane.showConfirmDialog(null, "Congrats, You saved the man!"+"\n"+"Play again?", "Won!",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                                            if(choice == JOptionPane.YES_OPTION) {
                                                frame.getContentPane().removeAll();                            
                                                panel.remove(label);
                                                panel.remove(textfield);
                                                panel.remove(button);
                                                panel.remove(button2);
                                                gameRestart();
                                            }else if(choice == JOptionPane.NO_OPTION){
                                                System.out.println("Thanks for playing the game");
                                                frame.setVisible(false);
                                                System.exit(0);
                                            }
                                            
                                        }
                                    }else{      //incrementing the hangman if the guess was incorrect and also checking if all the tries are finished
                                        gallows.incrementHangman();
                                        if(gallows.isDead()){
                                            int choice = JOptionPane.showConfirmDialog(null, "Your man is Hanged!"+"\n"+"Play again?", "GAME OVER!",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                                            if(choice == JOptionPane.YES_OPTION) {
                                                frame.getContentPane().removeAll();                            
                                                panel.remove(label);
                                                panel.remove(textfield);
                                                panel.remove(button);
                                                panel.remove(button2);
                                                gameRestart();
                                            }else if(choice == JOptionPane.NO_OPTION){
                                                System.out.println("Thanks for playing the game");
                                                frame.setVisible(false);
                                                System.exit(0);
                                            }
                                        }
                                        gallows_Label.setText("<html>"+gallows.toString().replace("\n", "<br>")+"</html>");
                                        gallows_Label.setAlignmentX(Component.CENTER_ALIGNMENT);
                                        gallowsPanel.add(gallows_Label);

                                    }
                                }
                            }
                        }
                    });

                    button2 = new JButton("Cancel");            //adding our cancel button which has same action listened, which closes the game on click
                    button2.setFont(new Font("Tahoma", Font.BOLD, 12));
                    button2.setMaximumSize(new Dimension(200,30));
                    button2.addActionListener(this);

                    panel.add(label);
                    panel.add(Box.createRigidArea(new Dimension(0, 13))); //gap between button and input field
                    panel.add(textfield);
                    panel.add(Box.createRigidArea(new Dimension(0, 13)));   //gap between text field and buttons
                    button.setAlignmentX(Component.CENTER_ALIGNMENT);
                    button2.setAlignmentX(Component.CENTER_ALIGNMENT);
                    panel.add(button);
                    panel.add(Box.createRigidArea(new Dimension(0, 13)));   //gap between ok button and cancel button
                    panel.add(button2);

                    /**
                    * Adding all out panels, to frame and making the frame visible for user interaction with it
                    */
                    frame.add(messagePanel, BorderLayout.NORTH);
                    frame.add(gallowsPanel, BorderLayout.WEST);
                    frame.add(panel, BorderLayout.SOUTH);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setTitle("Hangman Menu");
                    frame.setLocation(100, 100);
                    frame.pack();
                    frame.setSize(FRAME_WIDTH+100, FRAME_HEIGHT+400);
                    frame.setVisible(true);
                    loopTracker = 1;

                

            }else if(e.getSource() == button1){     //specific action listener condition when button1 is pressed
                System.exit(0);
            }else if(e.getSource() == button2){     //specific action listener condition when button2 is pressed
                System.exit(0);
            }
            
        }
    
}