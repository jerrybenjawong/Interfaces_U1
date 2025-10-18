package Vista;
import Controlador.LogicaVentana;
import Modelo.PersonaDAO;

public class Main {
	public static void main(String[]args) {
		javax.swing.SwingUtilities.invokeLater(()->{
			PersonaDAO Modelo= new PersonaDAO();
			Ventana Vista= new Ventana();
			new LogicaVentana(Vista,Modelo);
		});
	}
}
