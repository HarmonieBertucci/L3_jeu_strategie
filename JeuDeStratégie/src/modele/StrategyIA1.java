package modele;
import java.util.*;

/**
 * Représente un personnage virtuel qui joue tout seul aléatoirement
 */
public class StrategyIA1 extends Personnage implements StrategyIA {
	private Random rand;
	private PlateauJeu plateau;

	/**
	 * on lui donne le plateau et un nom
	 * @param plateau le plateau de jeu
	 * @param nom son nom (dans le style IA.PLAYER.1 pour le joueur IA numero 1)
	 */
	public StrategyIA1(PlateauJeu plateau,String nom) {
		super(nom);
		this.rand = new Random();
		this.plateau = plateau;
	}


	/**
	 * permet de choisir une action aléatoire a effectuer
	 */
	public void action() {
		int numeroCase = super.casePlateau.getNumero();
		String action = "";

		switch(this.rand.nextInt(6)) {
		case 0:
			action="deplacement";
			numeroCase= choixCase(numeroCase);
			break;
		case 1:
			action="shoot";
			numeroCase= choixCase(numeroCase);
			break;
		case 2:
			action="bouclier";
			break;
		case 3 :
			action="mine";
			numeroCase= choixCase(numeroCase);
			break;

		case 4:
			action="bombe";
			numeroCase= choixCase(numeroCase);
			break;
		case 5:
			action="";
			break;
		}
		this.plateau.deroulementTour(this,action,numeroCase);

	}

	/**
	 * permet de choisir une case associée a l'action
	 * @param caseDepart la case où il est
	 * @return la case associée a l'action
	 */
	public int choixCase(int caseDepart){
		int numeroCase=caseDepart;
		int taillePlateau = this.plateau.getTaillePlateau();


			if(caseDepart == 0){
				switch(this.rand.nextInt(2)) {
					case 0:
						numeroCase=caseDepart+PlateauJeu.Direction.SUD.getValeur();
						break;
					case 1 :
						numeroCase=caseDepart+PlateauJeu.Direction.EST.getValeur();
						break;
				}
			}
			else if(caseDepart ==taillePlateau-1){
				switch(this.rand.nextInt(2)) {
					case 0:
						numeroCase=caseDepart+PlateauJeu.Direction.SUD.getValeur();
						break;
					case 1 :
						numeroCase=caseDepart+PlateauJeu.Direction.OUEST.getValeur();
						break;
				}
			}
			else if(caseDepart ==taillePlateau*(taillePlateau-1)-1){
				switch(this.rand.nextInt(2)) {
					case 0:
						numeroCase=caseDepart+PlateauJeu.Direction.NORD.getValeur();
						break;
					case 1 :
						numeroCase=caseDepart+PlateauJeu.Direction.EST.getValeur();
						break;
				}
			}
			else if(caseDepart == taillePlateau*taillePlateau -1){
				switch(this.rand.nextInt(2)) {
					case 0:
						numeroCase=caseDepart+PlateauJeu.Direction.NORD.getValeur();
						break;
						case 1 :
						numeroCase=caseDepart+PlateauJeu.Direction.OUEST.getValeur();
						break;
				}
			}

		else if(caseDepart/taillePlateau==0){
			switch(this.rand.nextInt(3)) {
				case 0:
					numeroCase=caseDepart+PlateauJeu.Direction.EST.getValeur();
					break;
				case 1 :
					numeroCase=caseDepart+PlateauJeu.Direction.OUEST.getValeur();
					break;
				case 2:
					numeroCase=caseDepart+PlateauJeu.Direction.SUD.getValeur();
			}
		}
		else if(caseDepart%taillePlateau==0){
			switch(this.rand.nextInt(3)) {
				case 0:
					numeroCase=caseDepart+PlateauJeu.Direction.EST.getValeur();
					break;
				case 1 :
					numeroCase=caseDepart+PlateauJeu.Direction.NORD.getValeur();
					break;
				case 2:
					numeroCase=caseDepart+PlateauJeu.Direction.SUD.getValeur();
			}
		}
		else if(caseDepart/taillePlateau==taillePlateau-1){
			switch(this.rand.nextInt(3)) {
				case 0:
					numeroCase=caseDepart+PlateauJeu.Direction.EST.getValeur();
					break;
				case 1 :
					numeroCase=caseDepart+PlateauJeu.Direction.NORD.getValeur();
					break;
				case 2:
					numeroCase=caseDepart+PlateauJeu.Direction.OUEST.getValeur();
			}
		}
		else if(caseDepart%taillePlateau==taillePlateau-1){
			switch(this.rand.nextInt(3)) {
				case 0:
					numeroCase=caseDepart+PlateauJeu.Direction.OUEST.getValeur();
					break;
				case 1 :
					numeroCase=caseDepart+PlateauJeu.Direction.NORD.getValeur();
					break;
				case 2:
					numeroCase=caseDepart+PlateauJeu.Direction.OUEST.getValeur();
			}
		}
		else {
			numeroCase= choixCaseCroix(numeroCase);
		}
		return numeroCase;
	}

	/**
	 * permet de choisir une case autours de lui
	 * @param caseDepart case où il est
	 * @return une case autours de lui au hazard
	 */
	public int choixCaseCroix(int caseDepart) {
		int numeroCase = caseDepart;
		switch(this.rand.nextInt(4)) {
		case 0:
			numeroCase=caseDepart+PlateauJeu.Direction.NORD.getValeur();
			break;
		case 1 :
			numeroCase=caseDepart+PlateauJeu.Direction.EST.getValeur();
			break;
		case 2 :
			numeroCase=caseDepart+PlateauJeu.Direction.SUD.getValeur();
			break;
		case 3 :
			numeroCase=caseDepart+PlateauJeu.Direction.OUEST.getValeur();
			break;
		}
		return numeroCase;
	}


}
