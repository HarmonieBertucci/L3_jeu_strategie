package vue;

import modele.*;
import observer.EcouteurModele;
import javax.swing.*;
import java.awt.*;

/**
 * Permet de représenter graphiquement les cases du plateau et les boutons en bas de la vue pour les actions
 */
public class VueCase extends JButton implements EcouteurModele {
    protected Proxy proxy;
    protected Personnage joueur;
    public static int persoCounter = 1;
    private java.util.List<Personnage> listeDesJoueurs;

    /**
     * constructeur pour les boutons d'action a qui on donne :
     * @param nom le nom du bouton
     * @param joueur le joueur passédant la vue où il est
     * @param listeDesJoueurs la liste des joueurs
     */
    public VueCase(String nom, Personnage joueur,java.util.List<Personnage> listeDesJoueurs) {
        super(nom);
        this.joueur = joueur;
        this.listeDesJoueurs=listeDesJoueurs;
    }

    /**
     * constructeur pour les cases du plateau a qui on donne :
     * @param proxy le proxy du joueur pour que la case n'affiche pas les objets que le joueur ne doit pas voir
     * @param joueur le joueur passédant la vue où il est
     * @param listeDesJoueurs la liste des joueurs
     */
    public VueCase(Proxy proxy, Personnage joueur,java.util.List<Personnage> listeDesJoueurs) {
        this.proxy = proxy;
        this.joueur = joueur;
        this.listeDesJoueurs=listeDesJoueurs;
    }

    public Personnage getPersonnage() {
        return this.joueur;
    }

    /**
     * permet de mettre a jour la case quand la case du modèle se modifie
     * @param source la case qui s'est modifiée
     */
    @Override
    public void modeleMisAJour(Object source) {
        CasePlateau caseEcoutee = proxy.getCase((CasePlateau) source, this.joueur); //permet de filtrer le case et de montrer qui ce qu'il a le droit de voir au joueur
        ImageIcon imageIcon = new ImageIcon("src/img/violet.jpg");
        if (caseEcoutee.getObjet() != null) { //si il y a un objet sur la case
            switch (caseEcoutee.getObjet().getType()) { //on regarde son type pour afficher la bonne image
                case "mur":
                    imageIcon = new ImageIcon("src/img/mur.jpg");
                    break;
                case "":
                    break;
                case "joueur":
                    Personnage p =((PersonnageToObjet) caseEcoutee.getObjet()).getPersonnage();
                    if(p.equals(this.listeDesJoueurs.get(0))){
                        imageIcon = new ImageIcon("src/img/orange.jpg");
                    }else if(this.listeDesJoueurs.size()>1 && p.equals(this.listeDesJoueurs.get(1))){
                        imageIcon = new ImageIcon("src/img/vert.jpg");
                    }else if(this.listeDesJoueurs.size()>2 && p.equals(this.listeDesJoueurs.get(2))){
                    	imageIcon = new ImageIcon("src/img/violet.jpg");
                    }else{
                        imageIcon = new ImageIcon("src/img/bleu.jpg");
                    }
                    break;
                case "Bombe":
                    imageIcon = new ImageIcon("src/img/bombetmp.jpeg");
                    break;
                case "Mine":
                    imageIcon = new ImageIcon("src/img/minetmp.png");
                    break;
                case "Pastille":
                    imageIcon = new ImageIcon("src/img/pastille.jpg");
                    break;
            }
            Image image = imageIcon.getImage(); //on récupère l'image
            Image newimg = image.getScaledInstance(52, 52, java.awt.Image.SCALE_SMOOTH);  //on la redimentionne
            imageIcon = new ImageIcon(newimg); // on en refait une ImageIcon
            super.setIcon(imageIcon); //on l'affiche sur la case
        } else {
            super.setIcon(null); //si il n'y a pas d'objet on ne met rien dessus
        }

    }

}
