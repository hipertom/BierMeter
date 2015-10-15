/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainscreen;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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
    
    // variable aanmaken
    boolean beginnerDriver = false;
    int timeToWait = 0;
    int timePassed = 0;
    int timePerBeer = 5400; // tijd wachten in sec per drankje (5400sec is 1.5uur)
    int beers = 0;
    double gramsOfAlcPerBeer = 10.00;     // 10 gram alcohol per bier (er gaat 
    double promilleLimit = 0.50;
    double promilleLimitBeginner = 0.20;
    int hours = 0;
    int minutes = 0;
    int seconds = 0;

    // frames, panels etc
    private final Timer timer = new Timer(1000, this); // TIMER aanmaken, eerste getal is interval (1000 is 1 sec)
    private JButton plusBtn;
    private JLabel lblHour;
    private JLabel lblHourTxt;
    private JLabel lblMin;
    private JLabel lblMinTxt;
    private JLabel lblSec;
    private JLabel lblSecTxt;
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
        // variable definieren
        timerContainer = new JPanel();
        container = new JPanel();
        lblHour = new JLabel(Integer.toString(hours));
        lblHourTxt = new JLabel("UUR ");
        lblMin = new JLabel(Integer.toString(minutes));
        lblMinTxt = new JLabel("MINUTEN ");
        lblSec = new JLabel(Integer.toString(seconds));
        lblSecTxt = new JLabel("SECONDEN ");
        plusBtn = new JButton("+1 Biertje");
        
        // tekst opmaak
        lblHour.setFont(new Font("", Font.BOLD, 52));
        lblMin.setFont(new Font("", Font.BOLD, 52));
        lblSec.setFont(new Font("", Font.BOLD, 52));
        lblHour.setPreferredSize(new Dimension(58,40));
        lblMin.setPreferredSize(new Dimension(58,40));
        lblSec.setPreferredSize(new Dimension(58,40));
        
        // center layout opmaak
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        container.setAlignmentX(Component.CENTER_ALIGNMENT);
        plusBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        // action van button
        plusBtn.addActionListener((ActionEvent e) -> {
            // Uitvoeren wanneer button press
            beers++;
            timeToWait += timePerBeer;

            if (!timer.isRunning()) {
                timer.start();
            }
        });
        
        
    }
    
    private void render() {
        // inladen en weergeven van blokken in het scherm
        //container.add(Box.createVerticalBox());
        container.add(Box.createRigidArea(new Dimension(0, 60)));
        container.add(plusBtn);
        container.add(Box.createRigidArea(new Dimension(0, 120)));
        timerContainer.add(lblHour);
        timerContainer.add(lblHourTxt);
        timerContainer.add(lblMin);
        timerContainer.add(lblMinTxt);
        timerContainer.add(lblSec);
        timerContainer.add(lblSecTxt);
        container.add(timerContainer);
        this.add(container, BorderLayout.CENTER);
        
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
            lblHour.setText(Integer.toString(hours));
            lblMin.setText(Integer.toString(minutes));
            lblSec.setText(Integer.toString(seconds));
            timeToWait--;
        }
    }
}
