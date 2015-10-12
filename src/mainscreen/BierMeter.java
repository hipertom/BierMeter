/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainscreen;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Tom
 */
public class BierMeter extends JFrame implements ActionListener {
    
    boolean beginner = false;
    int currentWaitTime = 0;
    int beers = 0;
    int waitTime = 60;
    
    private final Timer timer = new Timer(100, this);
    private JLabel label;
    
    BierMeter() {
        super("Biermeter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1280, 960));
        setSize(new Dimension(1280, 960));
        setLocationRelativeTo(null);
        
        // Zet alle panels erin    
        init();
        
        // "Render" het
        pack();
        setVisible(true);
    }
    
    private void init() {
        JPanel container = new JPanel();
        label = new JLabel("Je mag nu nog rijden!");
        JButton startButton = new JButton("+1 Biertje");
        
        startButton.addActionListener((ActionEvent e) -> {
            beers++;
            currentWaitTime = waitTime * beers;
                    
            if(!timer.isRunning()) {
                timer.start();
            }
        });
        
        container.add(startButton);
        container.add(label);
        add(container);
    }
    
    /*
    * Countdown naar wanneer je weer mag rijden
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(currentWaitTime == 0) {
            label.setText("Je mag weer rijden, koekwaus.");
        } else {
            label.setText("Je moet " + String.valueOf(currentWaitTime--) + " seconden wachten.");
        }
    }
}
