package modele;

import observer.*;

/**
 * permet de représenter les cases du plateau
 * Chaque case est écoutée par une case de la vue
 */
public class CasePlateau extends AbstractModeleEcoutable {
    /**
     * le numéro de la case
     */
    protected int numero;

    /**
     * l'objet présent sur la case
     */
    protected Objets objetContenu;  // mur, bombe, mine , pastille, personnage(ToObjet) ;

    /**
     * @param numero
     */
    public CasePlateau(int numero) {
        super();
        this.numero = numero;
        resetObjet();
    }

    public int getNumero() {
        return this.numero;
    }

    public Objets getObjet() {
        return this.objetContenu;
    }

    /**
     * permet de mettre un objet dans la case et de prévenir la VueCase qui l'écoute qu'elle s'est modifiée
     * @param objet
     */
    public void setObjet(Objets objet) {
        this.objetContenu = objet;
        fireChangement();
    }

    /**
     * permet d'enlever l'objet de la case (si l'objet est une bombe et qu'elle explose par exemple)
     * prévient la VueCase qui l'écoute qu'elle s'est modifiée
     */
    public void resetObjet() {
        this.objetContenu = null;
        fireChangement();
    }
}
