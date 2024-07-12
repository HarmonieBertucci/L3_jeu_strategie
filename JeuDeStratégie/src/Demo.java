import controller.Controller;
import vue.VueDebut;
public class Demo {
	
    public static void main(String[] args) {
    	//public static final ImageIcon imgmur = new ImageIcon("img/mur.jpg");
        /**
         * création du controller
         */
        Controller controller = new Controller();

        /**
         * création de la vue du début (avec les parametres) en lui donnant le controller
         */
        new VueDebut(controller);
    }
}
