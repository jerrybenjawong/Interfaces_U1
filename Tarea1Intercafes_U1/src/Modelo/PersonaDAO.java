package Modelo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//clase que maneja datos de persona
public class PersonaDAO {
	private List<Persona> personas= new ArrayList<>();//lista donde se guardan los contactos 
	private final Path dataFile;//ruta alarchivo CSV
	
	public PersonaDAO() {
		this.dataFile= Paths.get("data","contactos.csv");
		load();
	}
	
	public List<Persona> getAll(){
		return new ArrayList<>(personas);
	}
	//Agrega una nueva persona y guarda los cambios 
	public void add(Persona p) {
		personas.add(p);
		save();
	}
	//modifica una persona en una posicion espefifica 
	public void update(int index,Persona p) {
		if (index >= 0 && index < personas.size()) {
			personas.remove(index);
			save();		
			}
	}
	//elimina una persona por su posicion en la lista 
	public void delete (int index) {
		if (index >=0 && index < personas.size()) {
			personas.remove(index);
			save();
		}
	}
	//filtra los contactos por categoria 
	public List<Persona>filterByCategoria(String categoria){
		if (categoria == null || 
				categoria.isEmpty() ||
				categoria.equals("Todos")) {
			return getAll();
		}
		List<Persona> res=new ArrayList<>();
		for (Persona p : personas) {
			if (categoria.equalsIgnoreCase(p.getCategoria()))res.add(p);
		}
		return res;
	}
	//Exporta los contactos a un archivo csv
	public void exportToFile(File file,List<Persona>lista) {
		try(PrintWriter pw=new PrintWriter(new FileWriter(file))){
			pw.println("nombre,telefono,correo,categoria");
			for (Persona p: lista) {
				pw.println(p.toString());
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	//Cargar los contactos a un archivo csv
	private void load() {
		personas.clear();
		try {
			//si el archivo no existe lo crea junto con la carpeta data 
			if (!Files.exists(dataFile)) {
				Files.createDirectories(dataFile.getParent());
				Files.createFile(dataFile);
				Files.write(dataFile, Arrays.asList("nombre,telefono,correo,categoria"), StandardOpenOption.TRUNCATE_EXISTING);
				return;
			}
			//lee todas las lineas del archivo
			List<String> lines =Files.readAllLines(dataFile);
			boolean first = true;
			for(String line:lines) {
				if (first) {
					first= false;
				continue;
				}
				if (line.trim().isEmpty())
					continue;
				String[] parts= line.split(",",-1);
				String nombre= parts.length>0? parts[0].trim():"";
				String telefono= parts.length>1? parts[1].trim():"";
				String correo= parts.length>2? parts[2].trim():"";
				String categoria= parts.length>3? parts[3].trim():"Otro";
				personas.add(new Persona(nombre,telefono,correo,categoria));
			}	
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	//Guardar los contactos actuales en el csv
	private void save() {
		try {
			List<String>out= new ArrayList<>();
			out.add("nombre,telefono,corre,categoria");
			for (Persona p: personas) {
				out.add(p.toString());
			}
			Files.write(dataFile, out, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

}
