package modele;

/**
 * Proxy permettant au joueur le possédant de ne voir que ce qu'il a le droit de voir sur le plateau
 */
public class Proxy {
    protected PlateauJeu plateau;

    /**
     * on donne le plateau de jeu au proxy
     * @param plateau le plateau de jeu
     */
    public Proxy(PlateauJeu plateau){
        this.plateau=plateau;
    }

    /**
     * Permet si la case contient un objet que le joueur possédant le proxy ne doit pas voir, d'afficher une case vide (CasePlateau(-1,-1,-1))
     * @param caseDuPlateau case du plateau que l'on regarde et où il faut verifier qu'elle ne contient pas d'objet non visible par le joueur
     * @param joueur joueur ayant le proxy pour vérifier que le joueur ayant posé la mine ou la bombe est lui (ou pas)
     * @return caseDuPlateau si la case ne contient rien que le joueur ne peut pas voir, sinon CasePlateau(-1,-1,-1) (une case vide d'objet)
     */
    public CasePlateau getCase(CasePlateau caseDuPlateau,Personnage joueur){
        if(caseDuPlateau.getObjet()!=null && (caseDuPlateau.getObjet().getType().equals("Bombe")||caseDuPlateau.getObjet().getType().equals("Mine")) ) {
            AbstractBoom boom = (AbstractBoom) caseDuPlateau.getObjet();
            if(!boom.estMonJoueur(joueur)){
                return new CasePlateau(-1);
            }
        }
        return caseDuPlateau;
    }
}
