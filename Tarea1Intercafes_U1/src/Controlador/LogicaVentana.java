package Controlador;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Modelo.Persona;
import Modelo.PersonaDAO;
import Vista.Ventana;

//conecta la parte visual con la logica 
public class LogicaVentana {
	private Ventana Vista;
	private PersonaDAO Modelo;
	
	public LogicaVentana(Ventana Vista, PersonaDAO Modelo) {
		this.Vista= Vista;
		this.Modelo= Modelo;
		init();//inicializa los eventos 
	}
	private void init() {
		refreshTable();
		Vista.btnAgregar.addActionListener(e->{
			Persona p = new Persona(
					Vista.tfNombre.getText(), 
					Vista.tfTelefono.getText(), 
					Vista.tfCorreo.getText(), 
					(String)Vista.cbCategoria.getSelectedItem());
			Modelo.add(p);
			clearForm();
			refreshTable();
		});
		//Boton de modificar contacto
		Vista.btnModificar.addActionListener(e->{
			int sel= Vista.tabla.getSelectedRow();
			if (sel==-1) {
				showMessage("Seleccion un contacto para modificar");
				return;
			}
			Persona p= new Persona(
					Vista.tfNombre.getText(), 
					Vista.tfTelefono.getText(),
					Vista.tfCorreo.getText(),
					(String)Vista.cbCategoria.getSelectedItem());
			Modelo.update(sel,p);
			clearForm();
			refreshTable();
		});
		
		//Boton eliminar contacto 
		Vista.btnEliminar.addActionListener(e->{
			int sel= Vista.tabla.getSelectedRow();
			if (sel== -1) {
				showMessage("Seleccione un contacto para eliminar");
				return;
				}
			if (confirm("¿Eliminar contacto seleccionado?")) {
				Modelo.delete(sel);
				clearForm();
				refreshTable();
			}
		});
		//Boton refrescar y filtro 
		Vista.btnRefrescar.addActionListener(e->refreshTable());
		Vista.cbFiltro.addActionListener(e->refreshTable());
		//boton para exportar contactos a CSV
		Vista.btnExportar.addActionListener(e-> ExportarCSV());
		//evento al seleccionar una fila en la tabla
		Vista.tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				int sel= Vista.tabla.getSelectedRow();
				if (sel!=-1) {
					Vista.tfNombre.setText((String)
							Vista.tableModel.getValueAt(sel, 0));
					Vista.tfTelefono.setText((String)
							Vista.tableModel.getValueAt(sel, 1));
					Vista.tfCorreo.setText((String)
							Vista.tableModel.getValueAt(sel, 2));
					Vista.cbCategoria.setSelectedItem((String)
					Vista.tableModel.getValueAt(sel, 3));
				}
			}
		});
	}
	private void ExportarCSV() {
		JFileChooser chooser=new JFileChooser();
		chooser.setDialogTitle("Exportar contactos a CSV");
		int result= chooser.showSaveDialog(Vista);
		if (result==JFileChooser.APPROVE_OPTION) {
			File file= chooser.getSelectedFile();
			if (! file.getName(). toLowerCase().endsWith(".csv")) {
				file =new File(file.getAbsolutePath()+ ".csv");
			}
			String filtro= (String)Vista.cbFiltro.getSelectedItem();
			List<Persona>lista=Modelo.filterByCategoria(filtro);
			Modelo.exportToFile(file, lista);
			showMessage("Contactos exportados a: " + file.getAbsolutePath());
		}
	}
	private void refreshTable() {
		String filtro=(String)Vista.cbFiltro.getSelectedItem();
		List<Persona>lista = Modelo.filterByCategoria(filtro);
		Vista.tableModel.setRowCount(0);
		for (Persona p:lista) {
			Vista.tableModel.addRow(new Object[] {
					p.getNombre(),p.getTelefono(),p.getCorreo(),p.getCategoria()});
		}
	}
	//limpiar los campos del formulario 
	private void clearForm() {
		Vista.tfNombre.setText("");
		Vista.tfTelefono.setText("");
		Vista.tfCorreo.setText("");
		Vista.cbCategoria.setSelectedIndex(0);
		Vista.tabla.clearSelection();
	}
	//Muestramos un mensaje simple 
	private void showMessage(String m) {
		JOptionPane.showMessageDialog(Vista, m);
	}
	//Muestra un mensaje de confirmacion 
	private boolean confirm(String m) {
		int r= JOptionPane.showConfirmDialog(Vista,m, "Confirmar" ,JOptionPane.YES_NO_OPTION);
		return r==JOptionPane.YES_OPTION;
	}
}
