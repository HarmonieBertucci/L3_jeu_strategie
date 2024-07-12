package vue;

import controller.Controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Est la vue de paramétrage de la partie où on peut choisir le nombre de personnage et leur nom, ainsi que la taille du plateau
 */
public class VueDebut extends JFrame {
    public static final Color ORANGE = new Color(208,87,32); // #D05720
    private Controller controller;
    private JPanel east_panel;

    private JTextField nom_joueur1_area;
    private String nom_joueur1="orange";

    private JTextField nom_joueur2_area;
    private String nom_joueur2="grenouille";

    private JTextField nom_joueur3_area;
    private String nom_joueur3="violet";

    private JTextField nom_joueur4_area;
    private String nom_joueur4="bleu";

    private JButton twoPlayerButton;
    private JButton threePlayerButton;
    private JButton fourPlayerButton;
    private JButton huitParHuit;
    private JButton dixParDix;
    private JButton douzeParDouze;
    private JButton go;

    private int nombreDeJoueurs =2;
    private int taillePlateau =10;

    /**
     * constructeur où on crée les éléements de la frame et où on les ajoute
     * @param c le controlleur que l'on va appeler après le click sur le bouton de début de partie
     */
    public VueDebut(Controller c){
        this.controller=c;

        setTitle("Jeu"); //paramétrage de la fenêtre de vue
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(900, 650));
        setLayout(new BorderLayout());

        // LES PANELS

        JPanel west_panel = new JPanel(); //Panel de gauche où se situe les boutons de choix du nombre de joueurs et de la taille du plateau
        west_panel.setLayout(new GridLayout(6,1));

            JPanel north_button_panel = new JPanel(); // Panel des boutons haut (choix du nombre de joueurs) du panel de gauche
            JPanel south_button_panel = new JPanel(); // Panel des boutons bas (la taille du plateau) du panel de gauche

        JPanel south_panel = new JPanel(); //Panel bas où se situe le bouton de début de partie
        south_panel.setLayout(new BorderLayout());


        east_panel = new JPanel(); //panel droit où se situe les JTextField pour les noms des joueurs
        east_panel.setLayout(new GridLayout(8,1));

        eastPanel(); //permet d'afficher le panel gauche en fonction du choix du nombre de joueur (initialement 2)


        // LES BOUTONS

        twoPlayerButton = new JButton("2 joueurs"); // bouton pour jouer a 2
        twoPlayerButton.setBackground(Color.BLACK); // comme c'est le choix par défaut on le met sur fond noir
        twoPlayerButton.setForeground(ORANGE); //avec le texte en orange
        twoPlayerButton.addActionListener( //on ajoute un listener sur le bouton
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(getNombreDeJoueurs()!=2){ //si le nombre de joueurs n'était pas déja égal a 2
                            setNombreDeJoueurs(2); //on met le nombre de joueurs a 2
                            recolorButtons(2); //on recolore les boutons grace a recolorButtons en lui disant qui est choisi
                            eastPanel(); //on réaffiche le panel gauche
                        }
                    }
                });

        threePlayerButton = new JButton("3 joueurs"); // bouton pour jouer a 3
        threePlayerButton.setBackground(ORANGE); //comme ce n'est pas le choix par défaut on le met sur fond orange
        threePlayerButton.setForeground(Color.WHITE); //avec le texte en blanc
        threePlayerButton.addActionListener( //idem que précedemment mais pour 3 joueurs
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(getNombreDeJoueurs()!=3){
                            setNombreDeJoueurs(3);
                            recolorButtons(3);
                            eastPanel();
                        }
                    }
                });

        fourPlayerButton = new JButton("4 Joueurs"); //bouton pour jouer a 4
        fourPlayerButton.setBackground(ORANGE);
        fourPlayerButton.setForeground(Color.WHITE);
        fourPlayerButton.addActionListener( //idem que précedemment mais pour 4 joueurs
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(getNombreDeJoueurs()!=4){
                            setNombreDeJoueurs(4);
                            recolorButtons(4);
                            eastPanel();
                        }
                    }
                });

        huitParHuit = new JButton("8x8"); //bouton pour jouer sur un plateau de dimentions 8x8
        huitParHuit.setBackground(ORANGE);
        huitParHuit.setForeground(Color.WHITE);
        huitParHuit.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(getTaillePlateau()!=8){
                            setTaillePlateau(8);
                            recolorButtons(8);
                            eastPanel();
                        }
                    }
                });

        dixParDix = new JButton("10x10"); //bouton pour jouer sur un plateau de dimentions 10x10 (choix par défaut)
        dixParDix.setBackground(Color.BLACK);
        dixParDix.setForeground(ORANGE);
        dixParDix.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(getTaillePlateau()!=10){
                            setTaillePlateau(10);
                            recolorButtons(10);
                            eastPanel();
                        }
                    }
                });

        douzeParDouze = new JButton("12x12"); //bouton pour jouer sur un plateau de dimentions 12x12
        douzeParDouze.setBackground(ORANGE);
        douzeParDouze.setForeground(Color.WHITE);
        douzeParDouze.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(getTaillePlateau()!=12){
                            setTaillePlateau(12);
                            recolorButtons(12);
                            eastPanel();
                        }
                    }
                });

        go = new JButton("Commencer la partie"); //bouton pour commencer la partie avec les paramètres séléctionnés
        go.setBackground(ORANGE);
        go.setForeground(Color.WHITE);
        go.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        java.util.List<String> listeJoueurs = new ArrayList<>(); //D'abord on met les noms des joueurs renseignés dans une liste
                        listeJoueurs.add(getNomJoueur(nom_joueur1_area));
                        listeJoueurs.add(getNomJoueur(nom_joueur2_area));
                        if(getNombreDeJoueurs()>=3){
                            listeJoueurs.add(getNomJoueur(nom_joueur3_area));
                        }
                        if(getNombreDeJoueurs()==4){
                            listeJoueurs.add(getNomJoueur(nom_joueur4_area));
                        }

                        controller.debutPartie(listeJoueurs,getTaillePlateau()); //ensuite on appelle debutPartie avec la liste des noms des joueurs et la taille du plateau
                        dispose(); //enfin on ferme la fenêtre
                    }
                });

        //AJOUT DES BOUTONS AU PANEL DES BOUTONS

        north_button_panel.add(twoPlayerButton);
        north_button_panel.add(threePlayerButton);
        north_button_panel.add(fourPlayerButton);

        south_button_panel.add(huitParHuit);
        south_button_panel.add(dixParDix);
        south_button_panel.add(douzeParDouze);

        south_panel.add(go);

        // COTÉ GAUCHE DE L'ÉCRAN
        JLabel label_nb_joueurs = new JLabel("Nombre de joueurs :");

        west_panel.add(label_nb_joueurs); //on ajoute le label
        west_panel.add(north_button_panel); //puis le panel des boutons pour le nombre de joueurs


        //permet que le joueur soit un joueur aléatoire (IA)
        JLabel message = new JLabel("Si vous voulez qu'un joueur soit l'ordinateur mettez lui comme nom :");
        JTextField field=new JTextField("[<IA.PLAYER>]");
        field.setEditable(false);

        west_panel.add(message);
        west_panel.add(field);


        JLabel label_taille = new JLabel("Taille du plateau :");

        west_panel.add(label_taille);
        west_panel.add(south_button_panel);
        west_panel.setBorder(BorderFactory.createEmptyBorder(10,100,10,0)); //permet que le panel ne soit pas collé aux bords et aux autres panels

        // COTÉ GAUCHE DE L'ÉCRAN
        east_panel.setBorder(BorderFactory.createEmptyBorder(10,50,10,50));

        //ajout des panels a la frame
        add(west_panel, BorderLayout.WEST);
        add(east_panel, BorderLayout.EAST);
        add(south_panel, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    /**
     * Permet de supprimer tout ce qu'il y a dans le panel de noms des joueurs et de le reconstruire (utile pour ajouter des TextField quand on ajoute des joueurs
     * mais surtout pour en supprimer de l'affichage qu'on on enlève des joueurs)
     */
    public void eastPanel(){
        this.east_panel.removeAll();

        JLabel label1 = new JLabel("Nom du joueur 1 :");
        this.east_panel.add(label1);

        nom_joueur1_area=new JTextField(this.nom_joueur1);
        this.east_panel.add(nom_joueur1_area);

        JLabel label2 = new JLabel("Nom du joueur 2 :");
        this.east_panel.add(label2);

        nom_joueur2_area=new JTextField(this.nom_joueur2);
        this.east_panel.add(nom_joueur2_area);

        if(this.nombreDeJoueurs>=3){
            JLabel label3 = new JLabel("Nom du joueur 3 :");
            this.east_panel.add(label3);

            nom_joueur3_area=new JTextField(this.nom_joueur3);
            this.east_panel.add(nom_joueur3_area);
            if(nom_joueur4_area != null) {
                this.east_panel.remove(nom_joueur4_area);
            }
        }
        if(this.nombreDeJoueurs==4){
            JLabel label4 = new JLabel("Nom du joueur 4 :");
            this.east_panel.add(label4);

            nom_joueur4_area=new JTextField(this.nom_joueur4);
            this.east_panel.add(nom_joueur4_area);
        }

        add(east_panel);
        repaint();
        pack();
    }

    public int getNombreDeJoueurs(){
        return this.nombreDeJoueurs;
    }
    public int getTaillePlateau(){
        return this.taillePlateau;
    }
    public void setNombreDeJoueurs(int nb){
        this.nombreDeJoueurs=nb;
    }
    public void setTaillePlateau(int t){
        this.taillePlateau=t;
    }

    public String getNomJoueur(JTextField joueur){
        return joueur.getText();
    }

    /**
     * permet de recolorer les boutons (si actuel est inférieur a 5 ça veut dre qu'on a cliqué sur un bouton de nombre de joueurs sinon on a cliqué sur une dimention de plateau
     * @param actuel permet de savoir ce que l'on doit recolorer
     */
    public void recolorButtons(int actuel){
        if(actuel<5){ //on a cliqué sur un bouton de nombre de joueur

            twoPlayerButton.setBackground(ORANGE); //on remet tous les boutons en fond orange
            twoPlayerButton.setForeground(Color.WHITE); //et en couleur de texte blanc
            threePlayerButton.setBackground(ORANGE);
            threePlayerButton.setForeground(Color.WHITE);
            fourPlayerButton.setBackground(ORANGE);
            fourPlayerButton.setForeground(Color.WHITE);

            //Puis on change la couleur de celui cliqué
            if(actuel==3){
                threePlayerButton.setBackground(Color.BLACK);
                threePlayerButton.setForeground(ORANGE);
            }
            else if(actuel==4){
                fourPlayerButton.setBackground(Color.BLACK);
                fourPlayerButton.setForeground(ORANGE);
            }
            else{
                twoPlayerButton.setBackground(Color.BLACK);
                twoPlayerButton.setForeground(ORANGE);
            }
        }
        else{ //on a cliqué sur un bouton de taile de plateau

            huitParHuit.setBackground(ORANGE);//on remet tous les boutons en orange
            huitParHuit.setForeground(Color.WHITE);
            dixParDix.setBackground(ORANGE);
            dixParDix.setForeground(Color.WHITE);
            douzeParDouze.setBackground(ORANGE);
            douzeParDouze.setForeground(Color.WHITE);

            //on change la couleur de celui cliqué
            if(actuel==8){
                huitParHuit.setBackground(Color.BLACK);
                huitParHuit.setForeground(ORANGE);
            }
            else if(actuel==12){
                douzeParDouze.setBackground(Color.BLACK);
                douzeParDouze.setForeground(ORANGE);
            }
            else{
                dixParDix.setBackground(Color.BLACK);
                dixParDix.setForeground(ORANGE);
            }
        }
    }

}
