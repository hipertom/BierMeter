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
    
    boolean beginnerDriver = false;
    int timeToWait = 0;
    int timePassed = 0;
    int timePerDrink = 5400; // tijd wachten in sec per drankje (5400sec is 1.5uur)
    int beers = 0;
    int gramsOfAlc = 0;
    int hours;
    int minutes;
    int seconds;
    
    private final Timer timer = new Timer(1000, this); // TIMER aanmaken, eerste getal is interval (1000 is 1 sec)
    private JLabel label;
    
    BierMeter() {
        super("Biermeter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(480, 640));
        setSize(new Dimension(480, 640));
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
            // Uitvoeren wanneer button press
            beers++;
            timeToWait += timePerDrink;
                    
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
    * word elke seconden uitgevoerd
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(timeToWait == 0) {
            label.setText("U bent compleet nuchter!");
            timer.stop();
        } else {
            hours = timeToWait / 3600;
            minutes = (timeToWait % 3600) / 60;
            seconds = timeToWait % 60;
            label.setText(
                   hours+"UUR "+
                   minutes+"MINUTEN "+
                   seconds+ "SECONDEN");
            timeToWait--;
        }
    }
}
