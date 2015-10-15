/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainscreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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
 * @author Tom Grootjans
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
    String statusNuchter = "U bent compleet nuchter";
    String statusWelRijden = "U Heeft wel wat alcohol in uw lichaam maar u mag WEL rijden";
    String staturNietRijden = "Er bevind zich te veel alcohol in uw lichaam u mag NIET rijden!";
    String huidigeStatus = statusNuchter;
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
    private JLabel lblStatus;
    private JPanel container;
    private JPanel containerTimer;
    

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
        
        container = new JPanel();
        containerTimer = new JPanel();
        
        plusBtn = new JButton("+1 Biertje");
        lblHour = new JLabel(Integer.toString(hours));
        lblHourTxt = new JLabel("UUR  ");
        lblMin = new JLabel(Integer.toString(minutes));
        lblMinTxt = new JLabel("MINUTEN  ");
        lblSec = new JLabel(Integer.toString(seconds));
        lblSecTxt = new JLabel("SECONDEN");
        lblStatus = new JLabel(huidigeStatus);

        
        // tekst opmaak
                //lblHourTxt.setBorder(BorderFactory.createLineBorder(Color.black));

        lblHour.setFont(new Font("", Font.BOLD, 52));
        lblMin.setFont(new Font("", Font.BOLD, 52));
        lblSec.setFont(new Font("", Font.BOLD, 52));

        
        // center layout opmaak
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        containerTimer.setLayout(new BoxLayout(containerTimer, BoxLayout.LINE_AXIS));
        containerTimer.setAlignmentX(Component.CENTER_ALIGNMENT);
        plusBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
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
        
        //plus btn
        container.add(Box.createRigidArea(new Dimension(0, 40)));
        container.add(plusBtn);
        
        // timer labels
        container.add(Box.createRigidArea(new Dimension(0, 200)));
        containerTimer.add(lblHour);
        containerTimer.add(lblHourTxt);
        containerTimer.add(lblMin);
        containerTimer.add(lblMinTxt);
        containerTimer.add(lblSec);
        containerTimer.add(lblSecTxt);
        container.add(containerTimer);
        
        //status
        //container.add(Box.createRigidArea(new Dimension(0, 20)));
        container.add(lblStatus);
        
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
            huidigeStatus = statusNuchter;
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
            huidigeStatus = statusWelRijden;
        }
        lblStatus.setText(huidigeStatus);
        
    }
}
