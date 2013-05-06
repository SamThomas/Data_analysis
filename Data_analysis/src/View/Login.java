package View;

import Model.BoucleDeJeu;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

//Green Planet
import greenplanetclient.Client;
import greenplanetclient.ClientException;
import greenplanetclient.ClientInterface;
import greenplanetclient.OfflineClient;

//AWT libraries
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;


//SWING libraries
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Samuel
 */
public class Login extends JFrame implements ActionListener {

    // ---------- Attributes ---------- //
    private JFrame window;
    private JLabel msg = null;
    private JButton play_online;
    private JButton play_offline;
    private JButton rules;
    private JButton quit;
    private JPanel buttons_board;
    private JPanel names_board;
    private JTextField name;
    private JLabel image;
    private String nomJoueur;
    private JTextField id_game;
    private int id;
    private JLabel intro;
    private JLabel pseudo;
    private JLabel id_in_game;
    private JLabel guys;
    private JLabel mode;
    private JRadioButton radio1;
    private JRadioButton radio2;
    private JRadioButton radio3;
    ButtonGroup radio_group = new ButtonGroup();
    private int choix;

    public int getChoix() {
        return choix;
    }

    // ---------- Constructor ---------- //
    public Login() {

        //Declartion & Initialisation
        int width, height;
        width = 1200;
        height = 800;
        window = new JFrame();
        buttons_board = new JPanel();
        names_board = new JPanel();
        name = new JTextField("");
        id_game = new JTextField("");
        intro = new JLabel("Green Planet Project");
        pseudo = new JLabel("Pseudo                                  ");
        id_in_game = new JLabel("Id                                          ");
        mode = new JLabel("Mode IA");
        guys = new JLabel("Adrien KERROUX , Samuel THOMAS , Derrick HO , Adrien CLAVEL ");
        image = new JLabel(new ImageIcon("green_planet_big retouche.jpg"));
        choix=1;
        //Buttons initialisation

        play_online = new JButton("Play Online");
        play_online.addActionListener(this);
        play_online.setPreferredSize(new Dimension(100, 25));
        play_offline = new JButton("Play Offline");
        play_offline.addActionListener(this);
        rules = new JButton("Règles du jeu");
        rules.addActionListener(this);
        quit = new JButton("Quit");
        quit.addActionListener(this);
        radio1 = new JRadioButton("Mode normal",true);
        radio1.addActionListener(this);
        radio2 = new JRadioButton("Mode Ecologique");
        radio2.addActionListener(this);
        radio3 = new JRadioButton("Mode Pollueur");
        radio3.addActionListener(this);

        //Window initialisation and display
        String title = "Green Planet";
        window.setSize(1200, 600);
        window.setTitle(title);
        window.setVisible(false);
        
        //radio button
        radio_group.add(radio1);
        radio_group.add(radio2);
        radio_group.add(radio3);
        // on ajoute les boutons à un panel
        buttons_board.setPreferredSize(new Dimension(160, 180));
        Font font = new Font("Courier", Font.CENTER_BASELINE, 14);
        intro.setPreferredSize(new Dimension(150, 75));
        buttons_board.add(intro);
        pseudo.setPreferredSize(new Dimension(150, 25));
        buttons_board.add(pseudo);
        name.setPreferredSize(new Dimension(150, 25));
        buttons_board.add(name);
        id_in_game.setPreferredSize(new Dimension(150, 25));
        buttons_board.add(id_in_game);
        id_game.setPreferredSize(new Dimension(150, 25));
        buttons_board.add(id_game);
        mode.setPreferredSize(new Dimension(150, 25));
        buttons_board.add(mode);
        
        radio1.setPreferredSize(new Dimension(150,25));
        buttons_board.add(radio1);
        radio2.setPreferredSize(new Dimension(150,25));
        buttons_board.add(radio2);
        radio3.setPreferredSize(new Dimension(150,25));
        buttons_board.add(radio3);
        
        


        play_online.setPreferredSize(new Dimension(150, 25));
        buttons_board.add(play_online);
        play_offline.setPreferredSize(new Dimension(150, 25));
        buttons_board.add(play_offline);
        rules.setPreferredSize(new Dimension(150, 25));
        buttons_board.add(rules);
        quit.setPreferredSize(new Dimension(150, 25));
        buttons_board.add(quit);
        //2eme panel
        names_board.setPreferredSize(new Dimension(1200, 25));
        names_board.add(guys);


    }

    //-----------Instantiate methods -----------------------//
    /**
     * Display the Log in window
     */
    public void displayWindow() {
        window.getContentPane().add(names_board, BorderLayout.SOUTH);
        window.getContentPane().add(image, BorderLayout.CENTER);
        window.getContentPane().add(buttons_board, BorderLayout.WEST);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    /**
     * Action performed method
     *
     * @param ev
     */
    @Override
    public void actionPerformed(ActionEvent ev) {


        if (ev.getSource() == play_online) {
            try {
                nomJoueur = name.getText();
                try {
                    id = Integer.parseInt(id_game.getText());
                } catch (NumberFormatException e) {
                }
                window.dispose();
                ClientInterface client = new Client(id, nomJoueur); // online client
                BoucleDeJeu obj = new BoucleDeJeu();
                obj.launch(client, nomJoueur);

            } catch (IOException | JAXBException | ClientException | InterruptedException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (ev.getSource() == play_offline) {
            try {
                nomJoueur = name.getText();
                window.dispose();
                ClientInterface client = new OfflineClient(nomJoueur, 10, true); // offline client
                BoucleDeJeu obj = new BoucleDeJeu();
                obj.launch(client, nomJoueur);


            } catch (IOException | JAXBException | ClientException | InterruptedException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);



            }
        }
        if(ev.getSource() == radio1){
            choix=1;
            System.out.println("radio1");
        }
        if(ev.getSource() == radio2){
            choix=2;
            System.out.println("radio2");
        }
        if(ev.getSource() == radio3){
            choix=3;
            System.out.println("radio3");
        }
        if (ev.getSource() == rules) {
            System.out.println("OK pour les règles du jeu");
            Regles fen = new Regles();
            fen.setVisible(true);

        }
        if (ev.getSource() == quit) {

            System.exit(0);
        }

    }
}
