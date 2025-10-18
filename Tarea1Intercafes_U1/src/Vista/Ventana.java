package Vista;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class Ventana extends JFrame {
	/**
	 * crea la interfaz grafica 
	 */
	private static final long serialVersionUID = 1L;
	//campos de textos para escribir los datos
	public JTextField tfNombre= new JTextField(20);
	public JTextField tfTelefono= new JTextField(20);
	public JTextField tfCorreo= new JTextField(20);
	//comobox para elegir una categria
	public JComboBox<String>cbCategoria= new JComboBox<>(new String[] {
			"Familiar","Amigo","Trabajo","Emergencia","Otro"});
	
	//botones de acciones
	public JButton btnAgregar = new JButton("Agregar");
	public JButton btnModificar = new JButton("Modificar");
	public JButton btnEliminar = new JButton("Eliminar");
	public JButton btnRefrescar = new JButton("Refrescar");
	public JButton btnExportar = new JButton("Exportar CSV");
	
	//comobox para filtrar contactos
	public JComboBox<String> cbFiltro=new JComboBox<>(new String[] {
			"Todos","Familiar","Amigo","Trabajo","Emergencia","Otro"});
	//tabla donde se mostraran los contactos 
	public JTable tabla;
	public DefaultTableModel tableModel;
	public Ventana() {
		super("Gestion de Contactos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900,500);
		setLocationRelativeTo(null);//centra la venta en la pantalla
		//definimos el modelo de la tabla
		tableModel= new DefaultTableModel(new Object[] {
				"Nombre","Telefono","Correo","Categoria"}, 0)
				{
			@Override 
			public boolean isCellEditable(int row, int column) {return false; }//evita eitar directo en la tabla 
			};
			tabla= new JTable(tableModel);
			tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			//Panel del formulario para ingresar datos 
			JPanel form=new JPanel (new GridBagLayout());
			GridBagConstraints c=new GridBagConstraints();
			c.gridx=0; c.gridy=0; form.add(new JLabel("Nombre:"), c);
			c.gridx=1; form.add(tfNombre, c);
			c.gridx=0; c.gridy=1;form.add(new JLabel("Telefono:"), c);
			c.gridx=1; form.add(tfTelefono, c);
			c.gridx=0; c.gridy=2; form.add(new JLabel("Correo:"), c);
			c.gridx=1; form.add(tfCorreo, c);
			c.gridx=0; c.gridy=3; form.add(new JLabel("Categoria:"), c);
			c.gridx=1; form.add(cbCategoria, c);
			
			//panel de botones 
			JPanel buttons = new JPanel();
			buttons.add(btnAgregar);
			buttons.add(btnModificar);
			buttons.add(btnEliminar);
			buttons.add(btnRefrescar);
			buttons.add(btnExportar);
			buttons.add(new JLabel("Filtrar:"));
			buttons.add(cbFiltro);
			//colocamos todo en la ventana 
			getContentPane().setLayout(new BorderLayout());
			getContentPane().add(form, BorderLayout.NORTH);
			getContentPane().add(new JScrollPane(tabla), BorderLayout.CENTER);
			getContentPane().add(buttons, BorderLayout.SOUTH);
			
			setVisible(true);
	}
	
}
