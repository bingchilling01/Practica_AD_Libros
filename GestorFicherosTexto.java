package ejercicio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public final class GestorFicherosTexto extends GestorFicheros {

	// Ruta del archivo de texto de préstamos
	static File archivoPrestamosTXT = new File(rutaCarpetaRaiz + "prestamos.txt");
	static File archivoPrestamosAUX = new File(rutaCarpetaRaiz + "prestamos_aux.txt");
	
	// Método para escribir los datos de un préstamo en el fichero en formato TXT
	public static void escribirFicheroPrestamosTXT(Prestamo prestamo) {
		comprobarDirectorios();
				
		try {
			// Indicamos que el append es true cuando llamamos al constructor del FileWriter
			// para indicar que no queremos vaciar el contenido del archivo, que escriba desde el final
			FileWriter escritorArchivoPrestamos = new FileWriter(archivoPrestamosTXT, true);
			
			escritorArchivoPrestamos.write(prestamo.detallesPrestamo());
			
			escritorArchivoPrestamos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// Método que sirve para escribir el ArrayList de Strings en el fichero de texto
	// cuando actualizamos o eliminamos algún préstamo registrado
	public static void escribirModificacionesPrestamos(ArrayList<String> lineasPrestamos) {
		comprobarDirectorios();
		try {
			
			// Aquí ponemos el append a false para vaciar el contenido y escribir el
			// ArrayList entero
			FileWriter escritorArchivoPrestamos = new FileWriter(archivoPrestamosTXT, false);
			
			for (String linea : lineasPrestamos) {
				// Con el \n separamos por líneas cada elemento del ArrayList
				// a la hora de escribirlos en el fichero
				escritorArchivoPrestamos.write(linea + "\n");
			}
			escritorArchivoPrestamos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// Método para leer el fichero y devolver un ArrayList con el contenido del fichero
	public static ArrayList<String> leerFicheroPrestamos() {
		comprobarDirectorios();
		
		// Comprobamos primero si existe el fichero
		if(!archivoPrestamosTXT.exists()) {
			try {
				archivoPrestamosTXT.createNewFile();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		ArrayList<String> lineasPrestamo = new ArrayList<>();
		
		try {
			// Si el fichero tiene contenido, se lee y se añade cada línea
			// al ArrayList de Strings
			if(archivoPrestamosTXT.length() > 0) {
				
				String linea;
				BufferedReader lectorArchivoPrestamos = new BufferedReader(new FileReader(archivoPrestamosTXT));
				
				while ((linea = lectorArchivoPrestamos.readLine()) != null) {
					lineasPrestamo.add(linea);
				}
				
				lectorArchivoPrestamos.close();
		}
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return lineasPrestamo;
	}
	
}
