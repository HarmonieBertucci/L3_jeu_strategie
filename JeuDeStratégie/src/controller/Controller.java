package controller;

import modele.*;
import vue.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * permet la gestion des actions
 */
public class Controller {
    /**
     * représente l'action demandée
     */
    private String action;
    /**
     * le plateau de jeu
     */
    private PlateauJeu plateau;

    /**
     * le joueur essayant de jouer
     */
    private Personnage personnage;


    public Controller() {
    }

    /**
     * permet d'acceder au plateau a l'exterieur de la classe
     *
     * @return le plateau du jeu
     */
    public PlateauJeu getPlateau() {
        return this.plateau;
    }

    /**
     * méthode d'action au clic sur une case du plateau
     *
     * @param click l'action d'evenement
     */
    public void caseButtonAction(ActionEvent click) {
        //System.out.println("Click on case");
        JButton o = (JButton) click.getSource();
        String name = o.getName();                    // nom de la case = numero;
        int numeroCase = Integer.parseInt(name);
        if (this.personnage == ((VueCase) click.getSource()).getPersonnage() || this.personnage == null) {
            this.personnage = ((VueCase) click.getSource()).getPersonnage();
            this.action=(this.action.equals("") ? "deplacement" : this.action);
            this.plateau.deroulementTour(this.personnage, this.action, numeroCase);
        }
        this.personnage = null;
        this.action = "";


    }

    /**
     * méthode d'action au clic sur le bouton shoot pour tirer
     * permet aussi de récupérer le joueur qui a joué (sur quelle vue l'action a été faite) pour savoir si c'est le bon joueur qui a cliqué
     *
     * @param click l'action d'evenement
     */
    public void shootButtonAction(ActionEvent click) {
        this.action = "shoot";
        this.personnage = ((VueCase) click.getSource()).getPersonnage();


    }

    /**
     * méthode d'action au clic sur le bouton plant bombe pour poser une bombe
     * permet aussi de récupérer le joueur qui a joué (sur quelle vue l'action a été faite) pour savoir si c'est le bon joueur qui a cliqué
     *
     * @param click l'action d'evenement
     */
    public void bombButtonAction(ActionEvent click) {
        this.action = "bombe";
        this.personnage = ((VueCase) click.getSource()).getPersonnage();

    }

    /**
     * méthode d'action au clic sur le bouton plant mine pour déposer une mine
     * permet aussi de récupérer le joueur qui a joué (sur quelle vue l'action a été faite) pour savoir si c'est le bon joueur qui a cliqué
     *
     * @param click l'action d'evenement
     */
    public void mineButtonAction(ActionEvent click) {
        this.action = "mine";
        this.personnage = ((VueCase) click.getSource()).getPersonnage();

    }

    /**
     * méthode d'action au clic sur le bouton shield pour se proteger avec un bouclier
     * permet aussi de récupérer le joueur qui a joué (sur quelle vue l'action a été faite) pour savoir si c'est le bon joueur qui a cliqué
     *
     * @param click l'action d'evenement
     */
    public void shieldButtonAction(ActionEvent click) {
        this.plateau.deroulementTour(((VueCase) click.getSource()).getPersonnage(), "bouclier", -1);

    }

    /**
     * méthode d'action au clic sur le bouton do nothing pour ne rien faire et garder son energie
     * permet aussi de récupérer le joueur qui a joué (sur quelle vue l'action a été faite) pour savoir si c'est le bon joueur qui a cliqué
     *
     * @param click l'action d'evenement
     */
    public void nothingButtonAction(ActionEvent click) {
        this.plateau.deroulementTour(((VueCase) click.getSource()).getPersonnage(), "Nothing", 0);
    }

    /**
     * méthode d'action pour les ia
     * @param click l'action d'evenement
     */
    public void nextButtonAction(ActionEvent click){
        this.plateau.deroulementTour(((VueCase) click.getSource()).getPersonnage(), "next", -1);
    }

    /**
     * appelée par la vue de début de partie pour initialiser la partie en créant le plateau et les vues associées aux personnages avec leur proxy
     *
     * @param joueurs la liste des noms rentrés par l'utilisateur
     * @param taillePlateau la taille du plateau voulue par l'utilisateur
     */
    public void debutPartie(List<String> joueurs, int taillePlateau) {
        this.plateau = new PlateauJeu(joueurs, joueurs.size(), taillePlateau);
        this.action = "";

        for (int i = 0; i < this.plateau.getNombreDeJoueurs(); i++) {
            this.plateau.getPersonnage(i).setProxy(new Proxy(this.plateau));
            new VueJeu(this, taillePlateau, this.plateau.getPersonnage(i),this.plateau.getListeJoueurs());
        }

    }


}
