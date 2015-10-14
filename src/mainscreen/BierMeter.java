/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainscreen;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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

    // frames, panels etc
    private final Timer timer = new Timer(1000, this); // TIMER aanmaken, eerste getal is interval (1000 is 1 sec)
    private JButton plusBtn;
    private JLabel lblHour;
    private JLabel lblMin;
    private JLabel lblSec;
    private JPanel container;
    private JPanel timerContainer;
    

    BierMeter() {
        super("Biermeter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(480, 640));
        setSize(new Dimension(480, 640));
        setLocationRelativeTo(null);

        // Zet alle panels erin    
        init();
        render();
      
    }

    private void init() {
        timerContainer = new JPanel();
        container = new JPanel();
        lblHour = new JLabel("0UUR");
        lblMin = new JLabel("0MINUTEN");
        lblSec = new JLabel("0SECONDEN");
        plusBtn = new JButton("+1 Biertje");
        
        plusBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        plusBtn.addActionListener((ActionEvent e) -> {
            // Uitvoeren wanneer button press
            beers++;
            timeToWait += timePerDrink;

            if (!timer.isRunning()) {
                timer.start();
            }
        });
        
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        container.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    private void render() {
        container.add(Box.createVerticalBox());
        
        container.add(Box.createRigidArea(new Dimension(0, 60)));
        container.add(plusBtn);
        container.add(Box.createRigidArea(new Dimension(0, 120)));
        timerContainer.add(lblHour);
        timerContainer.add(lblMin);
        timerContainer.add(lblSec);
        container.add(timerContainer);
        add(container, BorderLayout.CENTER);
        
        pack();
        setVisible(true);
    }

    /*
     * Countdown naar wanneer je weer mag rijden
     * word elke "seconden" uitgevoerd
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (timeToWait == 0) {
            lblHour.setText("U bent compleet nuchter!");
            timer.stop();
        } else {
            //omrekenen van secondes naar uren en minuten
            hours = timeToWait / 3600;
            minutes = (timeToWait % 3600) / 60;
            seconds = timeToWait % 60;
            //print uren min en sec op label
            lblHour.setText(hours + "UUR");
            lblMin.setText(minutes + "MINUTEN ");
            lblSec.setText(seconds + "SECONDEN");
            timeToWait--;
        }
    }
}
