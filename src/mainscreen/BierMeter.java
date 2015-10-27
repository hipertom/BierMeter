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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
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
    // hulpmiddelen aanmaken
    private final Timer timer = new Timer(100, this); // TIMER aanmaken, eerste getal is interval (1000 is 1 sec)
    private final DecimalFormat df = new DecimalFormat("#.###");
    
    // variable aanmaken
    boolean isBeginnerDriver = false;
    int timeToWait = 0;
    int timePerBeer = 100; // tijd wachten in sec per drankje (4500sec is 75 min)
    double promilleInBlood = 0;
    double promilleLimit = 0.50;
    double promilleLimitBeginner = 0.20;
    double promillePerBeer = 0.25;
    double promilleAftrekken = promillePerBeer / timePerBeer;
    int hours = 0;
    int minutes = 0;
    int seconds = 0;
    
    // berichten
    String promilleString; 
    // status waarschuwingen
    String statusNuchter = "U bent compleet nuchter";
    String statusWelRijden = "U Heeft alcohol in uw lichaam en u mag WEL rijden";
    String statusNietRijden = "U MAG NIET RIJDEN!!! Er bevind zich te veel alcohol in uw lichaam!";
    String huidigeStatus = statusNuchter;

    // Aanmaken onderdelen
    // Containers
    private JPanel container;
    private JPanel containerTimer;
    // Objecten in containers (van boven naar beneden)
    private JButton plusBtn;
    private JLabel lblPromille;
    private JLabel lblStatus;
    
    private JLabel lblHour;
    private JLabel lblHourTxt;
    private JLabel lblMin;
    private JLabel lblMinTxt;
    private JLabel lblSec;
    private JLabel lblSecTxt;
    private JLabel lblTxtWachten;
    
    

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
        lblPromille = new JLabel(promilleString);
        lblStatus = new JLabel(huidigeStatus);
        lblHour = new JLabel(Integer.toString(hours));
        lblHourTxt = new JLabel("UUR  ");
        lblMin = new JLabel(Integer.toString(minutes));
        lblMinTxt = new JLabel("MINUTEN  ");
        lblSec = new JLabel(Integer.toString(seconds));
        lblSecTxt = new JLabel("SECONDEN");
        lblTxtWachten = new JLabel("Wachten totdat u compleet nuchter bent");

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
        lblPromille.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTxtWachten.setAlignmentX(Component.CENTER_ALIGNMENT);

        // action van button
        plusBtn.addActionListener((ActionEvent e) -> {
            // Uitvoeren wanneer button press
            promilleInBlood += promillePerBeer;
            timeToWait += timePerBeer;

            if (!timer.isRunning()) {
                timer.start();
            }
        });

    }

    private void render() {
        // inladen en weergeven van blokken in het scherm
        // container.add(Box.createVerticalBox());

        // plus btn
        container.add(Box.createRigidArea(new Dimension(0, 40)));
        container.add(plusBtn);
        // promille plus status
        container.add(Box.createRigidArea(new Dimension(0, 20)));
        container.add(lblPromille);
        container.add(lblStatus);

        // timer labels
        container.add(Box.createRigidArea(new Dimension(0, 150)));
        containerTimer.add(lblHour);
        containerTimer.add(lblHourTxt);
        containerTimer.add(lblMin);
        containerTimer.add(lblMinTxt);
        containerTimer.add(lblSec);
        containerTimer.add(lblSecTxt);
        container.add(containerTimer);
        container.add(lblTxtWachten);
        
        this.add(container);

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
            // reset voor zekerheid
            hours = 0;
            minutes = 0;
            seconds = 0;
            promilleInBlood = 0;
            
        } else {
            //omrekenen van secondes naar uren en minuten
            hours = timeToWait / 3600;
            minutes = (timeToWait % 3600) / 60;
            seconds = timeToWait % 60;

            timeToWait--;
            
            // zorgen dat promille niet minder dan 0 kan worden
            if(promilleInBlood-promilleAftrekken <= 0){
                promilleInBlood = 0;
            } else {
                promilleInBlood -= promilleAftrekken;
            }
            

        }

        if (promilleInBlood == 0) {
            huidigeStatus = statusNuchter;
        } else if (isBeginnerDriver && promilleInBlood < promilleLimitBeginner) {
            huidigeStatus = statusWelRijden;
        } else if (!isBeginnerDriver && promilleInBlood < promilleLimit) {
            huidigeStatus = statusWelRijden;
        } else {
            huidigeStatus = statusNietRijden;
        }

        lblStatus.setText(huidigeStatus);
        promilleString = "U heeft "+df.format(promilleInBlood)+"‰ alcohol in uw bloed";
        lblPromille.setText(promilleString);
        lblHour.setText(Integer.toString(hours));
        lblMin.setText(Integer.toString(minutes));
        lblSec.setText(Integer.toString(seconds));

    }
}
