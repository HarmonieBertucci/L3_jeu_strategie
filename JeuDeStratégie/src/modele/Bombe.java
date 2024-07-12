package modele;

/**
 * permet de représenter les bombes présentes sur le plateau
 * étend de AbstractBoom puisqu'une bombe est un objet explosif
 *
 */
public class Bombe extends AbstractBoom {
	/**
	 * on définit le délais d'explosion a 5 tours
	 */
	private int delai = 5;

	/**
	 * on définit les dégats a 10 points d'energie
	 */
	private final static int DEGAT = 10;

	/**
	 * constructeur donnant le type 'Bombe' a AbstractBoom ainsi que les dégats et le joueur ayant posé la bombe
	 * @param joueur joueur ayant posé la bombe
	 */
	public Bombe(Personnage joueur) {
		super("Bombe",DEGAT,joueur);
	}

	/**
	 * permet de diminuer le délai d'explosion de la bombe a la fin de chaque tour
	 * @return si le délai est plus petit ou égal a 0 =: si true la bombe doit exploser
	 */
	public boolean decompte() {
		this.delai--;
		return delai<=0 ;
	}

}
