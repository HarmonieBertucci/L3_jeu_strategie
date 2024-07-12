package modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * NouveauPlateau permet de remplir les cases du plateau pour initialiser la partie
 * Nous avons créé deux plateaux différents pour chaque dimention de plateau et un d'eux est choisi aléatoirement
 */
public class NouveauPlateau {
    private List<CasePlateau> plateau;
    private List<Personnage> listeJoueurs;
    private int intRandom;
    private Random rand;
    private int taillePlateau;

    /**
     * constructeur
     * @param plateau le PlateauJeu de la partie (on donne le plateauJeu pour ne pas donner la liste des cases du plateau et sa taille au controller)
     * @param listeJoueurs la liste des joueurs pour pouvoir les placer
     */
    public NouveauPlateau(PlateauJeu plateau, List<Personnage> listeJoueurs){
        this.plateau=plateau.getPlateauCase(); //on met la liste des cases du plateau dans la variable plateau
        this.listeJoueurs=listeJoueurs;
        this.rand = new Random(); //nous permet d'avoir des nombres randoms
        this.intRandom = rand.nextInt(20); //on prend un nombre entre 0 et 19
        this.taillePlateau=plateau.getTaillePlateau();

        switch(this.taillePlateau){ //selon la taille voulue du plateau on va appeler une fonction différente
            case 8 :
                create8x8();
                break;
            case 10 :
                create10x10();
                break;
            case 12 :
                create12x12();
                break;
            default: //si la taille du plateau est ni un 8x8 ni un 10x10 ni un 12x12 on lance une exception (ne nous servira pas mais peut être utile si le code est réutilisé)
                throw new IllegalArgumentException("Taille "+this.taillePlateau+" non valide.");
        }
    }

    /**
     * permet de placer arbitrairement les murs et personnages sur un plateau 8x8 (2 modèles possibles)
     */
    public void create8x8(){
        List<Integer> placementPersonnages=new ArrayList<>(); //on crée une liste de numéro de case où seront les joueurs
        List<Integer> placementMurs=new ArrayList<>();//on crée une liste de numéro de case où seront les murs
        switch(this.intRandom%2){ //selon le résultat du nombre aléatoire tiré on va créer un plateau ou un autre
            case 0:
                //placement des personnages
                placementPersonnages.add(0);
                placementPersonnages.add(54);
                if(this.listeJoueurs.size()>=3){ //si il y a plus de 3 joueurs
                    placementPersonnages.add(57);
                }
                if(this.listeJoueurs.size()==4){ //si il y a 4 joueurs
                    placementPersonnages.add(15);
                }

                //placement des murs
                placementMurs.add(4);
                placementMurs.add(10);
                placementMurs.add(14);
                placementMurs.add(24);
                placementMurs.add(31);
                placementMurs.add(34);
                placementMurs.add(45);
                placementMurs.add(58);

                break;

            case 1:
                placementPersonnages.add(9);
                placementPersonnages.add(63);
                if(this.listeJoueurs.size()>=3){
                    placementPersonnages.add(48);
                }
                if(this.listeJoueurs.size()==4){
                    placementPersonnages.add(5);
                }

                //placement des murs

                placementMurs.add(4);
                placementMurs.add(17);
                placementMurs.add(18);
                placementMurs.add(22);
                placementMurs.add(28);
                placementMurs.add(36);
                placementMurs.add(41);
                placementMurs.add(62);
                break;
        }

        placementObjets(placementPersonnages,placementMurs); //on appelle placementObjets pour éviter la redondance de code entre les 3 create()
    }

    /**
     * permet de placer arbitrairement les murs et personnages sur un plateau 10x10 (2 modèles possibles)
     */
    public void create10x10(){
        List<Integer> placementPersonnages=new ArrayList<>();
        List<Integer> placementMurs=new ArrayList<>();
        switch(this.intRandom%2){
            case 0:
                //placement des personnages
                placementPersonnages.add(0);
                placementPersonnages.add(88);
                if(this.listeJoueurs.size()>=3){
                    placementPersonnages.add(91);
                }
                if(this.listeJoueurs.size()==4){
                    placementPersonnages.add(19);
                }

                //placement des murs
                placementMurs.add(2);
                placementMurs.add(6);
                placementMurs.add(12);
                placementMurs.add(45);
                placementMurs.add(50);
                placementMurs.add(51);
                placementMurs.add(56);
                placementMurs.add(81);
                placementMurs.add(89);
                placementMurs.add(94);
                break;
            case 1:
                placementPersonnages.add(11);
                placementPersonnages.add(99);
                if(this.listeJoueurs.size()>=3){
                    placementPersonnages.add(80);
                }
                if(this.listeJoueurs.size()==4){
                    placementPersonnages.add(8);
                }

                //placement des murs

                placementMurs.add(13);
                placementMurs.add(23);
                placementMurs.add(28);
                placementMurs.add(39);
                placementMurs.add(45);
                placementMurs.add(54);
                placementMurs.add(60);
                placementMurs.add(61);
                placementMurs.add(86);
                placementMurs.add(96);
                break;
        }

        placementObjets(placementPersonnages,placementMurs);
    }

    /**
     * permet de placer arbitrairement les murs et personnages sur un plateau 12x12 (2 modèles possibles)
     */
    public void create12x12(){
        List<Integer> placementPersonnages=new ArrayList<>();
        List<Integer> placementMurs=new ArrayList<>();
        switch(this.intRandom%2){
            case 0:
                //placement des personnages
                placementPersonnages.add(0);
                placementPersonnages.add(130);
                if(this.listeJoueurs.size()>=3){
                    placementPersonnages.add(133);
                }
                if(this.listeJoueurs.size()==4){
                    placementPersonnages.add(23);
                }

                //placement des murs
                placementMurs.add(2);
                placementMurs.add(15);
                placementMurs.add(32);
                placementMurs.add(44);
                placementMurs.add(52);
                placementMurs.add(65);
                placementMurs.add(78);
                placementMurs.add(83);
                placementMurs.add(85);
                placementMurs.add(99);
                placementMurs.add(116);
                placementMurs.add(135);
                break;
            case 1:
                placementPersonnages.add(13);
                placementPersonnages.add(143);
                if(this.listeJoueurs.size()>=3){
                    placementPersonnages.add(120);
                }
                if(this.listeJoueurs.size()==4){
                    placementPersonnages.add(10);
                }

                //placement des murs

                placementMurs.add(7);
                placementMurs.add(14);

                placementMurs.add(34);
                placementMurs.add(48);
                placementMurs.add(55);
                placementMurs.add(66);
                placementMurs.add(75);
                placementMurs.add(95);
                placementMurs.add(112);
                placementMurs.add(117);
                placementMurs.add(124);
                placementMurs.add(140);
                break;
        }
        placementObjets(placementPersonnages,placementMurs);
    }

    /**
     * permet de placer les personnages et les murs aux numeros de cases donnés dans les listes placementPersonnages et placementMurs
     * @param placementPersonnages
     * @param placementMurs
     */
    public void placementObjets(List<Integer> placementPersonnages,List<Integer> placementMurs){
        int cpt=0;
        for(int place : placementPersonnages){ //pour chaque numero de cases dans placementPersonnages on ajoute le personnage correspondant (dans l'ordre)
            this.plateau.get(place).setObjet(new PersonnageToObjet(this.listeJoueurs.get(cpt)));
            this.listeJoueurs.get(cpt).setCase(this.plateau.get(place));
            cpt++;
        }

        for(int place : placementMurs){ //pour chaque numero de cases dans placementMurs on ajoute un mur sur la case correspondante
            this.plateau.get(place).setObjet(new Mur());
        }
        placementAleatoirePastilles(); //on appelle cette méthode pour qu'elle place les pastilles
    }

    /**
     * Nous permet de placer des pastilles d'energie aléatoirement sur le plateau (sur les cases vides)
     * Pourrait être réutilisé si on souhaite ajouter des pastilles au milieu d'une partie (quand il n'y en a plus par exemple)
     * Mais il faudrait prendre en compte le fait que au milieu d'une partie il peut y avoir moins de 5 cases où une pastille est posable
     * (a cause des bombes et mines posées par les joueurs)et donc retoucher le code afin de ne pas avoir une boucle infinie
     */
    public void placementAleatoirePastilles(){
        int randomCase;
        int increment=0;
        while(increment <5){ //on met 5 pastilles
            randomCase=this.rand.nextInt(this.plateau.size());
            if(this.plateau.get(randomCase).getObjet()==null){
                this.plateau.get(randomCase).setObjet(new Pastille());
                increment++;
            }
        }
    }
}
