package vue;

import observer.*;
import modele.*;
import controller.*;
import javax.swing.*;

import java.util.*;

import java.awt.*;
import java.io.File;
import java.util.HashMap;

/**
 * permet d'avoir une vue graphique du jeu pour un joueur donné
 */
public class VueJeu extends JFrame implements EcouteurModele {
    public static Color ORANGE = new Color(208, 87, 32); // #D05720
    public static Color VERT = new Color(34, 153, 84); // #D05720
    public static Color PURPLE = new Color(165, 105, 189); // #D057
    public static Color CYAN = new Color(93, 173, 226); // #D057
    public static Color[] COLOR_ARRAY = {ORANGE, VERT, PURPLE, CYAN};
    public static String[] PLAYER_NAME_ARRAY = new String[16];
    private Controller controller;
    private Personnage joueur;
    private PlateauJeu plateauJeu;
    private java.util.Map<Personnage, JProgressBar> vie;
    private java.util.List<Personnage> listeDesJoueurs;
    private JTextArea message;
    private TextField nombreDeBombesDuJoueur;
    private TextField nombreDeMinesDuJoueur;
    private TextField nombreDeMunitionsDuJoueur;

    /**
     * constructeur pour initialiser la vue
     * @param c le controlleur
     * @param taille la taille du plateau
     * @param joueur le joueur a qui appartient la vue
     * @param listeDesJoueurs la liste des joueurs
     */
    public VueJeu(Controller c, int taille, Personnage joueur, java.util.List<Personnage> listeDesJoueurs) {
        this.joueur = joueur;
        this.listeDesJoueurs = listeDesJoueurs;
        this.controller = c;
        this.plateauJeu=c.getPlateau();
        windowCreate(taille);
        this.plateauJeu.ajoutEcouteur(this);
    }

    /**
     * permet de créer les éléments de la vue
     * @param taille la taille du plateau
     */
    public void windowCreate(int taille) {
        setTitle("Vue de " + this.joueur.getName()); // les lignes suivantes sont pour la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 650));
        setLayout(new BorderLayout());

        GridLayout grid = new GridLayout(0, taille);
        JPanel grid_panel = new JPanel(grid); // panneau de la grille de jeu
        VueCase[][] interactive_grid = new VueCase[taille][taille]; // création grille clickable 10x10
        grid.setHgap(-1);
        grid.setVgap(-1);
        JPanel east_panel = new JPanel(); //Panel droit
        east_panel.setLayout(new GridLayout(4,1));
        JPanel south_panel = new JPanel(); //Panel du bas
        south_panel.setLayout(new BorderLayout());
        JPanel inventory_panel = new JPanel(); //Panel de l'inventaire
        JPanel button_panel = new JPanel(); // Panel des bouton
        JPanel life_panel = new JPanel(); // Panel du compteur de vie
        life_panel.setLayout(new GridBagLayout());
        inventory_panel.setLayout(new GridBagLayout());

        this.vie = new HashMap<>();
        GridBagConstraints constraints = new GridBagConstraints();
        int adder = 0;
        JProgressBar progressBar;
        TextField persoName;

        for (int i = 0; i < this.plateauJeu.getNombreDeJoueurs(); i++) {
            progressBar = new JProgressBar(); // création de la progressBar
            progressBar.setBackground(Color.WHITE); // application de son backgound en blanc
            progressBar.setForeground(COLOR_ARRAY[adder]); // application de sa couleur en fct du contenu du tab COLOR
            progressBar.setValue(joueur.getEnergie()); // on modifie la vie de chacun
            progressBar.setStringPainted(true); // application couleur sur le texte

            String nom_du_joueur = this.plateauJeu.getListeJoueurs().get(i).getName();
            PLAYER_NAME_ARRAY[i] = nom_du_joueur; // on remplit un tableau avec le nom des joueurs
            persoName = new TextField(); // création de la zone de texte
            persoName.setEditable(false);
            persoName.setText(PLAYER_NAME_ARRAY[i]); // on remplit la zone de texte du nom du joueur
            constraints.gridy = adder;
            life_panel.add(persoName, constraints);
            life_panel.add(progressBar, constraints);
            adder += 1;

            this.vie.put(this.plateauJeu.getListeJoueurs().get(i), progressBar);

        }
       
        this.nombreDeBombesDuJoueur=getAdder2(inventory_panel, constraints, 0, this.joueur.getInventaire().get("bombe"), new ImageIcon("src/img/bombetmp.jpeg"));
        this.nombreDeMinesDuJoueur=getAdder2(inventory_panel, constraints, 1, this.joueur.getInventaire().get("mine"), new ImageIcon("src/img/minetmp.png"));
        this.nombreDeMunitionsDuJoueur=getAdder2(inventory_panel, constraints, 2, this.joueur.getInventaire().get("munition"), new ImageIcon("src/img/munition.jpeg"));

        if(this.joueur.getName().contains("IA.PLAYER.")){ //si le joueur est une ia on lui met un bouton next
            VueCase nextButton = new VueCase("Next", this.joueur, this.listeDesJoueurs);
            nextButton.setBackground(ORANGE); // on définit les couleurs des buttons
            nextButton.setForeground(Color.WHITE);
            button_panel.add(nextButton);
            nextButton.addActionListener(controller::nextButtonAction);
        }
        else{ //sinon si c'est un joueur normal on lui met tous les boutons d'actions
            VueCase shootButton = new VueCase("Shoot", this.joueur, this.listeDesJoueurs); // création des buttons avec un nom dessus
            VueCase bombButton = new VueCase("Plant bomb", this.joueur, this.listeDesJoueurs);
            VueCase mineButton = new VueCase("Plant mine", this.joueur, this.listeDesJoueurs);
            VueCase nothingButton = new VueCase("Do nothing", this.joueur, this.listeDesJoueurs);
            VueCase shieldButton = new VueCase("Shield", this.joueur, this.listeDesJoueurs);
            shootButton.setBackground(ORANGE); // on définit les couleurs des buttons
            shootButton.setForeground(Color.WHITE);
            bombButton.setBackground(ORANGE);
            bombButton.setForeground(Color.WHITE);
            mineButton.setBackground(ORANGE);
            mineButton.setForeground(Color.WHITE);
            shieldButton.setBackground(ORANGE);
            shieldButton.setForeground(Color.WHITE);
            nothingButton.setBackground(ORANGE);
            nothingButton.setForeground(Color.WHITE);
            button_panel.add(shootButton); // ajout des boutons au panel
            button_panel.add(bombButton);
            button_panel.add(mineButton);
            button_panel.add(shieldButton);
            button_panel.add(nothingButton);
            shootButton.addActionListener(controller::shootButtonAction); // link pour l'éxécution des méthodes du controlleur
            bombButton.addActionListener(controller::bombButtonAction);
            mineButton.addActionListener(controller::mineButtonAction);
            shieldButton.addActionListener(controller::shieldButtonAction);
            nothingButton.addActionListener(controller::nothingButtonAction);
        }


        for (int i = 0; i < interactive_grid.length; i++) {
            for (int j = 0; j < interactive_grid[i].length; j++) {
                VueCase caseButton = new VueCase(joueur.getProxy(), joueur, this.listeDesJoueurs);
                caseButton.setName(String.valueOf(j + i * taille));
                caseButton.addActionListener(controller::caseButtonAction);
                grid_panel.add(caseButton);
                this.plateauJeu.getCase(j + i * taille).ajoutEcouteur(caseButton);

            }
        }

        this.message =new JTextArea("Début de partie");
        this.message.setLineWrap(true);
        this.message.setEditable(false);

        east_panel.add(inventory_panel); // Panneau Inventaire ajouté au panneau
        east_panel.add(life_panel); // idem pour le compteur d'énergie
        east_panel.add(this.message);
        south_panel.add(button_panel, BorderLayout.CENTER); // Panneau des boutons en bas
        add(grid_panel, BorderLayout.CENTER); // Grid au centre
        add(east_panel, BorderLayout.EAST); // Panneau contenant les panneaux Inventaire, Profile et Compteur d'énergie
        add(south_panel, BorderLayout.SOUTH); // Panneau contenant les différents boutons cliquables

        pack();
        setVisible(true);
    }

    /**
     *
     * @param inventory_panel le panel de l'inventaire
     * @param constraints la contrainte de placement
     * @param adder2 numéro du panel
     * @param value niméro de l'objet dans l'inventaire
     * @param imageIcon l'image associée a l'objet
     * @return le text field
     */
    private TextField getAdder2(JPanel inventory_panel, GridBagConstraints constraints, int adder2, int value, ImageIcon imageIcon) {
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(52, 52, Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back
        JLabel lab = new JLabel(imageIcon);
        String valueToString = String.valueOf(value);
        TextField ammount = new TextField(); // création de la zone de texte
        ammount.setEditable(false);
        ammount.setText(valueToString); // on remplit la zone de texte du nom du joueur
        constraints.gridy = adder2;
        inventory_panel.add(ammount, constraints);
        inventory_panel.add(lab, constraints);
        return ammount;
    }

    /**
     * permet de mettre a jours la vue quand le modèle se met a jours
     * @param source l'element du modèle qui s'est mis a jour
     */
    @Override
    public void modeleMisAJour(Object source) {
        JProgressBar progressBarAModifier;
        for(Personnage joueur : this.plateauJeu.getListeJoueurs()){
            progressBarAModifier = this.vie.get(joueur);
            progressBarAModifier.setValue(joueur.getEnergie());
            progressBarAModifier.setStringPainted(true);
            if(joueur.equals(this.joueur)){
                this.nombreDeBombesDuJoueur.setText(String.valueOf((joueur.getInventaire().get("bombe"))));
                this.nombreDeMinesDuJoueur.setText(String.valueOf((joueur.getInventaire().get("mine"))));
                this.nombreDeMunitionsDuJoueur.setText(String.valueOf((joueur.getInventaire().get("munition"))));
                this.message.setText(this.plateauJeu.getMessage());
            }
        }



    }

}
