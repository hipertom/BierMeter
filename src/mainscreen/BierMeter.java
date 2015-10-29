/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainscreen;

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
import javax.swing.JTextField;
import javax.swing.Timer;

public class BierMeter extends JFrame implements ActionListener {
    // hulpmiddelen aanmaken
    private final Timer timer = new Timer(1000, this); // TIMER aanmaken, eerste getal is interval (1000 is 1 sec)
    private final DecimalFormat df = new DecimalFormat("#.###");
    
    // variable aanmaken
    int timeToWait = 0;
    int timePerBeer = 4500; // tijd wachten in sec per drankje (4500sec is 75 min)
    double promilleInBlood = 0;
    double promilleLimit = 0.50;
    double promillePerBeer = 0.25;
    double promilleAftrekken = promillePerBeer / timePerBeer;
    int amountToAdd = 0;
    int beers;
    int hours = 0;
    int minutes = 0;
    int seconds = 0;
    
    // berichten
    String promilleString = "U heeft 0‰ alcohol in uw bloed"; 
    // status waarschuwingen
    String statusNuchter = "U bent compleet nuchter";
    String statusWelRijden = "U mag nog wel rijden.";
    String statusNietRijden = "U MAG NIET RIJDEN!!!";
    String huidigeStatus = statusNuchter;

    // Aanmaken variabelen voor panels, labels, buttons etc
    // Containers
    private JPanel container;
    private JPanel containerTimer;
    
    // Objecten in containers (van boven naar beneden)
    private JTextField  textField;
    private JButton     addButton;
    private JButton     plusOneButton;
    private JLabel      lblPromille;
    private JLabel      lblStatus;
    
    private JLabel lblHour;
    private JLabel lblHourTxt;
    private JLabel lblMin;
    private JLabel lblMinTxt;
    private JLabel lblSec;
    private JLabel lblSecTxt;
    private JLabel lblTxtWachten;
    
    private JLabel totalBeers;
    private JButton reset;
    
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
        
        // panels, buttons, en labels aanmaken met eventuele tekst erin
        textField = new JTextField(5);
        addButton = new JButton("add ");
        plusOneButton = new JButton("+1 Biertje");
        
        lblPromille = new JLabel(promilleString);
        lblStatus = new JLabel(huidigeStatus);
        lblHour = new JLabel(Integer.toString(hours));
        lblHourTxt = new JLabel("UUR  ");
        lblMin = new JLabel(Integer.toString(minutes));
        lblMinTxt = new JLabel("MINUTEN  ");
        lblSec = new JLabel(Integer.toString(seconds));
        lblSecTxt = new JLabel("SECONDEN");
        lblTxtWachten = new JLabel("Wachten totdat u compleet nuchter bent");
        
        totalBeers = new JLabel("U heeft nu in totaal "+beers+" bier gedronken!");
        reset = new JButton("Stop & Reset");

        // tekst opmaak
        lblHour.setFont(new Font("", Font.BOLD, 52));
        lblMin.setFont(new Font("", Font.BOLD, 52));
        lblSec.setFont(new Font("", Font.BOLD, 52));
        lblStatus.setFont(new Font("", Font.BOLD, 16));
        
        // grote van textField aanpassen
        textField.setMaximumSize( textField.getPreferredSize());
        
        // set layout voor (sub)containers
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        containerTimer.setLayout(new BoxLayout(containerTimer, BoxLayout.LINE_AXIS));
        
        // force center alignment
        containerTimer.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        plusOneButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPromille.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTxtWachten.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalBeers.setAlignmentX(Component.CENTER_ALIGNMENT);
        reset.setAlignmentX(Component.CENTER_ALIGNMENT);

        // action van +1 button
        addButton.addActionListener((ActionEvent e) -> {
            // kijken of textfield is ingevuld met nummer
            try {
                amountToAdd =  Integer.parseInt(textField.getText());
            } catch (NumberFormatException error) {
                System.out.println(error);
                amountToAdd = 0;
            }
            addSomeBeer(amountToAdd);
        });
        
        // action van aantal toevoegen 
        plusOneButton.addActionListener((ActionEvent e) -> {
            addSomeBeer(1);
        });
        
        // action voor resetknop
        reset.addActionListener((ActionEvent e) -> {
            timer.stop();
            beers = 0;
            timeToWait = 0;
            hours = 0;
            minutes = 0;
            seconds = 0;
            promilleInBlood = 0;
            huidigeStatus = statusNuchter;
            refreshScreen();
        });
    }

    private void render() {
        // inladen en weergeven van blokken in het scherm
        
        // add field and button
        container.add(Box.createRigidArea(new Dimension(0, 40)));
        container.add(textField);
        container.add(addButton);
        
        // plus btn
        container.add(Box.createRigidArea(new Dimension(0, 10)));
        container.add(plusOneButton);
        
        // promille plus status
        container.add(Box.createRigidArea(new Dimension(0, 20)));
        container.add(lblPromille);
        container.add(lblStatus);

        // timer labels
        container.add(Box.createRigidArea(new Dimension(0, 100)));
        containerTimer.add(lblHour);
        containerTimer.add(lblHourTxt);
        containerTimer.add(lblMin);
        containerTimer.add(lblMinTxt);
        containerTimer.add(lblSec);
        containerTimer.add(lblSecTxt);
        container.add(containerTimer);
        
        // wacht text label
        container.add(lblTxtWachten);
        
        // totalbeers en reset
        container.add(Box.createRigidArea(new Dimension(0, 25)));
        container.add(totalBeers);
        container.add(Box.createRigidArea(new Dimension(0, 125)));
        container.add(reset);
        
        // container tovoegen aan JFrame
        this.add(container);

        this.setVisible(true);
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
        
        // promille tekst weergeven
        if (promilleInBlood == 0) {
            huidigeStatus = statusNuchter;
        }  else if (promilleInBlood < promilleLimit) {
            huidigeStatus = statusWelRijden;
        } else {
            huidigeStatus = statusNietRijden;
        }
        
        refreshScreen();
        
    }
    
    // bierje toevoegen method
    private void addSomeBeer(int aantal) {
            for (int i=0; i<aantal; i++ ){
                promilleInBlood += promillePerBeer;
                timeToWait += timePerBeer;
                beers++;
            }

            if (!timer.isRunning()) {
                timer.start();
            }
    }
    
    private void refreshScreen(){
        //updaten van alle label's
        lblStatus.setText(huidigeStatus);
        promilleString = "U heeft "+df.format(promilleInBlood)+"‰ alcohol in uw bloed";
        lblPromille.setText(promilleString);
        lblHour.setText(Integer.toString(hours));
        lblMin.setText(Integer.toString(minutes));
        lblSec.setText(Integer.toString(seconds));
        totalBeers.setText("U heeft nu in totaal "+beers+" bier gedronken!");
    }
}
