
import Tools.Game_Panel;
import javax.swing.*;
 public class SnakeGame{
    public static void main(String[] args)
    {
        int boardwidht=600;
        int boardheight=boardwidht;
        
        JFrame frame=new JFrame("Snake Game");
        frame.setVisible(true);
        frame.setSize(boardwidht,boardheight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Game_Panel gmpanel=new Game_Panel(boardwidht,boardheight);
        frame.add(gmpanel);
        frame.pack();
        gmpanel.requestFocus();
    }
 }