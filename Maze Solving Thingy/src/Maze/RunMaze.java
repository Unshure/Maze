package Maze;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;


public class RunMaze extends JFrame {

    private JLabel statusbar;

    public RunMaze() {
        
        initUI();
   }
    
   private void initUI() {

        statusbar = new JLabel(" 0");
        add(statusbar, BorderLayout.SOUTH);
        makeMaze makeMaze = new makeMaze(this);
        add(makeMaze);
        makeMaze.start();

        setSize(800, 800);
        setTitle("Maze");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);       
   }

   public JLabel getStatusBar() {
       
       return statusbar;
   }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                
                RunMaze maze = new RunMaze();
                maze.setVisible(true);
            }
        });                
    } 
}