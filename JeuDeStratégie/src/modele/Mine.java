package modele;
/**
 * permet de représenter les mines présentes sur le plateau
 * étend de AbstractBoom 
 *
 */
public class Mine extends AbstractBoom{
	/**
	 * on définit les dégats de la mine a 20 points d'energie
	 */
	private final static int DEGAT=20;

	/**
	 * constructeur donnant au mines le type 'Mine' a AbstractBoom ainsi que leurs dégats et le joueur
	 * @param joueur joueur qui a posé la mine
	 */
	public Mine(Personnage joueur) {
		super("Mine",DEGAT,joueur);
	}
	
	
	

}
