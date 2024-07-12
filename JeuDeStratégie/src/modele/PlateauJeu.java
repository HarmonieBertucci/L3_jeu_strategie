package modele;

import observer.AbstractModeleEcoutable;
import observer.EcouteurModele;

import java.util.*;

/**
 * permet de représenter le plateau
 */
public class PlateauJeu extends AbstractModeleEcoutable implements EcouteurModele {
    protected int taillePlateau;

    /**
     * on définit le dégat d'un tir a 15 points d'energie
     */
    public static final int DEGAT_TIR = 15;

    /**
     * on définit les directions possibles
     */
    public enum Direction {
        NORD(-10), SUD(10), EST(1), OUEST(-1);
        private int valeur;

        private Direction(int valeur) {
            this.valeur = valeur;
        }

        public int getValeur() {
            return this.valeur;
        }

        public void setValeur(int taillePlateau) {
            this.valeur = taillePlateau;
        }
    }

    /**
     * la liste des cases du plateau
     */
    protected List<CasePlateau> plateauCase;

    /**
     * la liste des joueurs sur le plateau
     */
    protected List<Personnage> listeJoueurs;

    /**
     * le numero du joueur courrant (dans la liste des joueurs)
     */
    protected int numeroJoueurCourant;

    /**
     * le nombre de joueurs
     */
    protected int nombreDeJoueurs;

    /**
     * la liste des cases où se trouvent des bombes
     */
    private List<CasePlateau> listeBombes;

    /**
     * le message a afficher après chaque action des joueurs
     */
    protected String message="";

    /**
     * constructeur permettant de construire le plateau de jeu
     * @param joueurs la liste des noms des joueurs pour les créer
     * @param nombreDeJoueurs le nombre de joueurs voulant jouer (entre 2 et 4)
     * @param taillePlateau la taille voulue pour le plateau
     */
    public PlateauJeu(List<String> joueurs, int nombreDeJoueurs, int taillePlateau) {
        this.plateauCase = new ArrayList<>();
        this.listeJoueurs = new ArrayList<>();

        //pour les IA
        int nbIA=0;

        //création des joueurs en prenant en compte le nom renseigné
        for (String nom : joueurs) {
            if (nom.contains("[<IA.PLAYER>]")) { //si le nom contient [<IA.PLAYER>] alors l'utilisateur veut que ce personnage soit une ia
                nbIA++;
                this.listeJoueurs.add(new StrategyIA1(this,"IA.PLAYER."+nbIA)); //on définit son nom comme IA.PLAYER.nombre_d'ia
            } else {//sinon on crée un personnage normal
                this.listeJoueurs.add(new Personnage(nom));


            }
        }

        this.taillePlateau = taillePlateau;
        Direction.NORD.setValeur(-1 * taillePlateau); //on définit que les directions NORD et SUD dépendent de la taille du plateau
        Direction.SUD.setValeur(taillePlateau);

        creerPlateau(); //on crée le plateau
        this.numeroJoueurCourant = 0;
        this.nombreDeJoueurs = nombreDeJoueurs;

        new NouveauPlateau(this,this.listeJoueurs); //on appelle NouveauPlateau pour qu'il nous remplisse le plateau avec les personnages, les murs et les pastilles d'energies

        listeBombes = new LinkedList<>();

        for(Personnage joueur :this.listeJoueurs){ //le plateau écoute les personnages pour prévenir ses écouteurs quand les personnages changent (quand ils le previennent pas fireChangement())
            joueur.ajoutEcouteur(this);
        }
    }


    public List<Personnage> getListeJoueurs() {
        return this.listeJoueurs;
    }

    public int getTaillePlateau() {
        return this.taillePlateau;
    }

    public int getNombreDeJoueurs() {
        return this.nombreDeJoueurs;
    }

    public Personnage getPersonnage(int i) {
        return this.listeJoueurs.get(i);
    }

    public String getMessage(){
        return this.message;
    }

    public List<CasePlateau> getPlateauCase() {
        return this.plateauCase;
    }


    /**
     * permet de créer les cases du plateau et de les ajouter a la liste des cases
     */
    public void creerPlateau() {
        for (int i = 0; i < taillePlateau; i++) {
            for (int j = 0; j < taillePlateau; j++) {
                this.plateauCase.add(new CasePlateau(j + i * taillePlateau));
            }
        }


    }


    /**
     * permet de récupérer la case ayant pour numéro numero (les cases sont numérotées de 0 a taillePlateau-1)
     *
     * @param numero le numéro de la case voulue
     * @return la case voulue
     */
    public CasePlateau getCase(int numero) {
        return this.plateauCase.get(numero);
    }

    /**
     * nous permet de gérer le déroulement d'un tour
     * @param joueur le joueur voulant jouer
     * @param action l'action qu'il veut faire (deplacement, shoot, bombe, mine, bouclier, Nothing)
     * @param numeroCase la case où il a cliqué après avoir cliqué sur le bouton (si c'est necessaire)
     */
    public void deroulementTour(Personnage joueur, String action, int numeroCase) {
        boolean reussis = false; //permet de savoir si l'action s'est passé correctement
        if(this.listeJoueurs.size()>1) {
        if (this.listeJoueurs.indexOf(joueur) == this.numeroJoueurCourant) { //on verifie que joueur est bien le joueur qui doit jouer
            joueur.finBouclier(); //on lui enlève son bouclier si il en avait un
            switch (action) {
                case "deplacement": //si il veut se déplacer
                    if (!deplacement(numeroCase)) { //et que la case voulue n'est pas autorisée par la methode deplacement
                        this.message = "Deplacement impossible."; //on met a jour le message
                        fireChangement();
                    }
                    else{ //sinon si la case est autorisée alors l'appel a deplacement a changé la position du personnage
                        reussis=true; //et on met reussi a true
                        this.message = "Déplacement correctement effectué.";
                        fireChangement();
                    }
                    break;
                case "shoot": //si il veut tirer
                    if (!tir(numeroCase)) { //si le tir n'est pas autorisé (parce qu'il n'a plus de munitions par exemple)
                        this.message = "tir impossible"; //on met a jour le message
                        fireChangement();
                    }
                    else{//sinon si le tir est autorisée alors l'appel a tir a effectué l'action
                        reussis=true;
                        this.message = "Tir correctement effectué."; //on met a jour le message
                        fireChangement();
                    }
                    break;
                case "bombe": //si il veut poser une bombe
                    if (!depotBombe(numeroCase)) { //et si le dépot de la bombe sur la case voulue n'est pas autorisé (si il y a un mur sur la case voulue par exemple)
                        this.message = "Vous n'avez plus de bombe ou vous essayez de la poser a un endroit interdit";
                        fireChangement();
                    }
                    else{ //sinon depotBombe a fait l'action
                        reussis=true;
                        this.message = "Objet correctement posée."; //on met a jour le message sans préciser que c'est une bombe
                        fireChangement();
                    }
                    break;
                case "mine": //si il veut poser une mine
                    if (!depotMine(numeroCase)) { //et si le dépot de la mine sur la case voulue n'est pas autorisé (si il y a un mur sur la case voulue par exemple)
                        this.message = "Vous n'avez plus de mine ou vous essayez de la poser a un endroit interdit";
                        fireChangement();
                    }
                    else{
                        reussis=true;
                        this.message = "Objet correctement posée.";//on met a jour le message sans préciser que c'est une mine
                        fireChangement();
                    }
                    break;
                case "bouclier": //si il veut utiliser un bouclier
                    reussis = bouclier();
                    this.message = "Action bien prise en compte.";
                    fireChangement();
                    break;
                case "Nothing": //si il veut rester sur place
                    reussis = true;
                    this.message = "Fin du tour."; //on le préviens que c'est bien la fin du tour
                    fireChangement();
                    break;
                case "next": //si c'est l'ia qui fait une action
                    ((StrategyIA1) joueur).action();
                    reussis = true;
                    this.message = "L'ia va jouer.";
                    fireChangement();
            }
            if (reussis) {
                ChangeTour();
            } else {
                this.message += "\nAction non autorisé, recommence.";
                fireChangement();
            }

        } else { //si un joueur essaye de jouer alors que ce n'est pas son tour
            this.message = "C'est a "+ listeJoueurs.get(this.numeroJoueurCourant).getName()+" de jouer !";
            fireChangement();
        }
        }
        else {
        	this.message = "La partie est terminé";
            fireChangement();
        
        }
    }


    /**
     * permet a un joueur de tirer
     */
    public boolean tir(int numeroCase) {
        Direction direction = numeroToDirection(numeroCase);
        boolean pasFin = true;

        if (direction != null ) {

            int increment = direction.getValeur();
            
            int numeroCaseJoueur = this.listeJoueurs.get(this.numeroJoueurCourant).getCase().getNumero();
            int numeroCaseActuelle = numeroCaseJoueur - increment;
            while (numeroCaseActuelle >= 0 && numeroCaseActuelle < taillePlateau * taillePlateau && (numeroCaseActuelle%taillePlateau==numeroCaseJoueur%taillePlateau || numeroCaseActuelle/taillePlateau==numeroCaseJoueur/taillePlateau)   && pasFin) {
                if (this.getCase(numeroCaseActuelle).getObjet() != null && this.listeJoueurs.get(this.numeroJoueurCourant).gachette()) {
                    switch (this.getCase(numeroCaseActuelle).getObjet().getType()) {
                        case "mur":
                            pasFin = false;
                            break;
                        case "joueur":
                        
                            Personnage tmp = ((PersonnageToObjet) this.getCase(numeroCaseActuelle).getObjet()).getPersonnage();
                            if (!tmp.estProtege()) {
                            	
                                tmp.retraitEnergie(DEGAT_TIR);
                            }
                            pasFin = false;
                            break;
                    }


                }
                numeroCaseActuelle -= increment;
            }
        }
        return !pasFin;
    }

    /**
     * permet a un joueur d'utiliser un bouclier
     */
    public boolean bouclier() {
        Personnage joueur = this.listeJoueurs.get(this.numeroJoueurCourant);
        joueur.setBouclier();
        joueur.retraitEnergie(5);
        return true;

    }

    /**
     * permet au joueur de déposer une mine sur la case ayant pour numéro numeroCase si c'est autorisé
     * @param numeroCase le numero de la case voulue
     */
    public boolean depotMine(int numeroCase) {
        Personnage joueur = this.listeJoueurs.get(this.numeroJoueurCourant);
        if (verifCaseAdjacent(numeroCase, joueur.getCase().getNumero()) && getCase(numeroCase).getObjet() == null && joueur.poseMine()) {
            this.getCase(numeroCase).setObjet(new Mine(joueur));
            joueur.retraitEnergie(4);
            return true;
        } else {
            this.message = "Problème de dépos de mine.";
            fireChangement();
            return false;
        }
    }

    /**
     * permet au joueur de déposer une bombe sur la case ayant pour numéro numeroCase si c'est autorisé
     * @param numeroCase le numero de la case voulue
     */
    public boolean depotBombe(int numeroCase) {
        Personnage joueur = this.listeJoueurs.get(this.numeroJoueurCourant);
        if (verifCaseAdjacent(numeroCase, joueur.getCase().getNumero()) && getCase(numeroCase).getObjet() == null && joueur.poseBombe()) {
            this.getCase(numeroCase).setObjet(new Bombe(joueur));
            this.listeBombes.add(this.getCase(numeroCase));
            joueur.retraitEnergie(4);
            return true;
        } else {
            this.message = "Problème de dépos de bombe.";
            fireChangement();
            return false;
        }
    }

    /**
     * permet au joueur de déplacer le personnage courrant sur la case numeroCaseDestination si c'est autorisé
     * @param numeroCaseDestination
     * @return
     */
    public boolean deplacement(int numeroCaseDestination) {// direction possible : haut bas droite gauche
        Personnage joueur = this.listeJoueurs.get(this.numeroJoueurCourant);
        int numeroCaseActuelle = joueur.getCase().getNumero();
        CasePlateau caseDestination = this.getCase(numeroCaseDestination);
        if (verifCaseAdjacent(numeroCaseDestination, numeroCaseActuelle)) { //verification que la case est valide pour le deplacement (si elle est bien adjacente au personnage)
            Objets objetCase = caseDestination.getObjet();
            if (objetCase != null) {
                if (objetCase.getType() != "mur" && objetCase.getType() != "joueur") {
                    objetCase.action(joueur);
                    caseDestination.setObjet(new PersonnageToObjet(joueur));
                    joueur.setCase(caseDestination);
                    getCase(numeroCaseActuelle).resetObjet();
                    joueur.retraitEnergie(2);
                    return true;
                } else {
                    return false;
                }
            } else {
                PersonnageToObjet p = new PersonnageToObjet(joueur);
                caseDestination.setObjet(p);
                getCase(numeroCaseActuelle).resetObjet();
                joueur.setCase(caseDestination);
                joueur.retraitEnergie(2);
                return true;

            }
        }

        return false;
    }

    public Direction numeroToDirection(int numeroCase) { //permet de transformer le numéro de la case voulue en direction selon sa localisation en fonction de la case du joueur
        Personnage joueurCourant = this.listeJoueurs.get(this.numeroJoueurCourant);
        int numeroCaseActuelle = joueurCourant.getCase().getNumero();
        if (numeroCase == numeroCaseActuelle) {
            return null;
        }
        if (numeroCase % this.taillePlateau == numeroCaseActuelle % this.taillePlateau) {
            if (numeroCaseActuelle / this.taillePlateau - numeroCase / this.taillePlateau > 0) {
                return Direction.SUD;
            } else {
                return Direction.NORD;
            }
        } else if (numeroCase / this.taillePlateau == numeroCaseActuelle / this.taillePlateau) {
            if (numeroCaseActuelle % this.taillePlateau - numeroCase % this.taillePlateau > 0) {
                return Direction.EST;
            } else {
                return Direction.OUEST;
            }
        } else {
            return null;
        }
    }

    /**
     * permet de savoir si la case ayant pour numero numeroCaseDestination est a coté de la case ayant pour numéro numeroCaseActuelle
     * @param numeroCaseDestination
     * @param numeroCaseActuelle
     * @return si elles sont adjacentes
     */
    public boolean verifCaseAdjacent(int numeroCaseDestination, int numeroCaseActuelle) {
        return (Math.abs((numeroCaseActuelle / this.taillePlateau - numeroCaseDestination / this.taillePlateau)) == 1 && numeroCaseActuelle % this.taillePlateau == numeroCaseDestination % this.taillePlateau) ^ ((Math.abs(numeroCaseActuelle % this.taillePlateau - numeroCaseDestination % this.taillePlateau)) == 1 && numeroCaseActuelle / this.taillePlateau == numeroCaseDestination / this.taillePlateau);
    } // ^ = XOR

    /**
     * permet de savoir si les deux cases sont adjacentes (conprend aussi les diagonales)
     * @param numeroCaseDestination
     * @param numeroCaseActuelle
     * @return
     */
    public boolean verifCaseAdjacentEtendu(int numeroCaseDestination, int numeroCaseActuelle) {
        return verifCaseAdjacent(numeroCaseDestination, numeroCaseActuelle) || (Math.abs((numeroCaseActuelle / this.taillePlateau - numeroCaseDestination / this.taillePlateau)) == 1 && (Math.abs(numeroCaseActuelle % this.taillePlateau - numeroCaseDestination % this.taillePlateau)) == 1);
    }

    /**
     * permet de terminer un tour et de passer au joueur suivant
     */
    public void ChangeTour() {
        
        List<Object> aRetirer = new ArrayList<>();
        for (Personnage joueur : this.listeJoueurs) {
            if (joueur.estMort()) {
                joueur.getCase().resetObjet();
                aRetirer.add(joueur);

                this.message = joueur.getName() + " est mort";
                
            }
        }
        this.listeJoueurs.removeAll(aRetirer);
        this.nombreDeJoueurs -= aRetirer.size();
        aRetirer = new ArrayList<>();
        if(this.nombreDeJoueurs ==1) {
        	this.message +="\n"+ this.listeJoueurs.get(0).getName() + " a gagné";
        	fireChangement();
        }
        else {
        	this.numeroJoueurCourant += 1;
	        if (this.numeroJoueurCourant == this.nombreDeJoueurs) {
	            this.numeroJoueurCourant = 0;
	            for (CasePlateau case1 : this.listeBombes) {
	                if (case1.getObjet() != null) {
	                    if (case1.getObjet().getType() != "Bombe") {
	                        aRetirer.add(case1);
	                    }
	                }
	            }
	        }
	        this.listeBombes.removeAll(aRetirer);
	
	        for (CasePlateau case1 : this.listeBombes) {
	            if (case1.getObjet() != null && case1.getObjet().getType() == "Bombe" && ((Bombe) case1.getObjet()).decompte()) {
	                for (Personnage joueur : this.listeJoueurs) {
	                    if (verifCaseAdjacentEtendu(joueur.getCase().getNumero(), case1.getNumero())) {
	                        case1.getObjet().action(joueur);
	                    }
	                }
	                case1.resetObjet();
	            }
	        }
	
	        if (this.listeJoueurs.get(this.numeroJoueurCourant).getName().contains("IA.PLAYER.")) {
	            ((StrategyIA) this.listeJoueurs.get(this.numeroJoueurCourant)).action();
	            fireChangement();
	        }
	
	        this.message+="\nC'est a "+listeJoueurs.get(this.numeroJoueurCourant).getName()+" de jouer.";
	        fireChangement();
	    }
    }

    @Override
    public void modeleMisAJour(Object source) {
        fireChangement();//quand ce qu'il écoute a changé il prévient ses écouteurs qu'il a changé
    }
}
