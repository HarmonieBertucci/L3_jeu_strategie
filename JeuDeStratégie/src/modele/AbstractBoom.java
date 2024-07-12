package modele;

/**
 * permet de représenter les objets explosifs (mine et bombe)
 * et de définir les méthodes qui seront les mêmes pour ces objets
 * étend la classe AbstractObjet
 *
 */
public class AbstractBoom extends AbstractObjet {
	/**
	 * le nombre de points d'energie qu'une explosion enlève aux personnages concernés
	 */
	protected int degat;

	/**
	 * Le  joueur ayant posé l'objet explosif
	 */
	protected Personnage joueur;

	/**
	 * constructeur a qui on donne le type de l'objet (qui va le donner a son tour a AbstractObjet) ainsi que les dégats de l'explosion et le joueur l'ayant posé
	 * @param type type de l'objet ("Mine" ou "Bombe")
	 * @param degat le nombre de points d'energie qu'une explosion enlève aux personnages concernés
	 * @param joueur le joueur ayant posé l'objet explosif
	 */
	public AbstractBoom(String type, int degat, Personnage joueur) {
		super(type);
		this.degat=degat;
		this.joueur=joueur;
	}

	/**
	 * permet de savoir si j2 a posé l'objet
	 * @param j2 le joueur que l'on suspecte d'être le joueur ayant posé l'objet explosif
	 * @return si j2 est le joueur ayant posé l'objet explosif
	 */
	public boolean estMonJoueur(Personnage j2){
		return j2.equals(this.joueur);
	}

	/**
	 * permet de récupérer le joueur qui a posé l'objet
	 * @return le joueur ayant posé l'objet explosif
	 */
	public Personnage getJoueur(){
		return this.joueur;
	}
	
	/**
	 * permet d'effectuer l'action de l'objet sur le joueur -- l'action d'un objet explosif est de retirer de l'energie (degat) au joueur donné (sur la même case ou aux alentours de l'objet)
	 * @param joueur le joueur qui subit l'action de l'objet
	 */
	@Override
	public void action(Personnage joueur) {
		joueur.retraitEnergie(this.degat);
	}

}
