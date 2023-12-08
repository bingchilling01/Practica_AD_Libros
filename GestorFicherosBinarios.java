package ejercicio;

import java.io.*;
import java.util.ArrayList;

public final class GestorFicherosBinarios extends GestorFicheros {

	// Gestión de ficheros binarios de LIBROS
	// Fichero binario de los libros y su fichero copia de seguridad
	private static File archivoLibros = new File(rutaCarpetaRaiz + "libros.bin");
	private static File archivoRespaldoLibros = new File(rutaRespaldos + "libros_bin.bak");

	// Método para insertar un libro nuevo en su archivo binario
	public static void escribirLibroNuevo(Libro nuevoLibro) {
		try {

			// El true en el constructor de FileOutputStream significa que escribe
			// al final del archivo, sin eliminar su contenido antes
			FileOutputStream streamSalida = new FileOutputStream(archivoLibros, true);

			// Si el archivo está vacío se escribirá el nuevo libro con la cabecera
			if (archivoLibros.length() == 0) {
				ObjectOutputStream escribirLibro = new ObjectOutputStream(streamSalida);
				escribirLibro.writeObject(nuevoLibro);
				escribirLibro.close();
			} else {
				// Guardamos una copia de seguridad antes de insertar el libro nuevo, si
				// el archivo binario NO está vacío
				guardarRespaldoLibros();

				// Si no está vacío se escribirá sin la cabecera para evitar errores de lectura
				EscritorSinCabecera escribirSinCabecera = new EscritorSinCabecera(streamSalida);
				escribirSinCabecera.writeObject(nuevoLibro);
				escribirSinCabecera.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Método que escribe un ArrayList de libros en el archivo binario
	public static void escribirFicheroLibros(ArrayList<Libro> listaLibros) {
		try {
			
			// Comprobamos que los directorios existen para evitar errores
			comprobarDirectorios();
			// Guardamos una copia de seguridad antes de modificar libros
			guardarRespaldoLibros();
			// En esta escritura borraremos el contenido el archivo y la
			// sobreescribiremos con el ArrayList de libros
			ObjectOutputStream escritura = new ObjectOutputStream(new FileOutputStream(archivoLibros, false));
			// Con este bucle escribimos todos los libros que hay en el ArrayList
			// en el archivo binario
			for (Libro libro : listaLibros) {
				escritura.writeObject(libro);
			}
			escritura.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Método para leer todos los libros del archivo binario
	public static ArrayList<Libro> leerFicheroLibros() {
		ArrayList<Libro> listaLibros = new ArrayList<>();
		boolean eof = false;
		try {

			// Comprobamos que los directorios existen para evitar errores
			comprobarDirectorios();

			// Si el archivo binario no existe, lo creo, para evitar un
			// FileNotFoundException
			if (!archivoLibros.exists()) {
				archivoLibros.createNewFile();
			}

			// Declaramos un ObjectInputStream para poder leer el archivo binario
			ObjectInputStream leerLibros = new ObjectInputStream(new FileInputStream(archivoLibros));

			// El ObjectInputStream lee el archivo hasta que llegue al EOF
			while (!eof) {
				Libro libro = (Libro) leerLibros.readObject();
				// Añadimos los libros al ArrayList
				listaLibros.add(libro);
			}
			// Cerramos el ObjectInputStream para evitar fallos
			leerLibros.close();

			// Cuando se llegue al EOF se lanzará una excepción, aquí finalizamos
			// la lectura
		} catch (EOFException eofEx) {
			eof = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return listaLibros;
	}

	private static void guardarRespaldoLibros() {
		ArrayList<Libro> listaLibros = new ArrayList<>();
		// Guardamos los libros en el ArrayList llamando a la función de lectura
		listaLibros = leerFicheroLibros();
		try {

			ObjectOutputStream escribirRespaldoLibros = new ObjectOutputStream(new FileOutputStream(archivoRespaldoLibros));

			// Escribimos todos los libros del ArrayList en el archivo de respaldo
			for (Libro libro : listaLibros) {
				escribirRespaldoLibros.writeObject(libro);
			}
			escribirRespaldoLibros.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	
	// Gestión de ficheros binarios de AUTORES
	// Fichero binario de los libros y su fichero copia de seguridad
	private static File archivoAutores = new File(rutaCarpetaRaiz + "autores.bin");
	private static File archivoRespaldoAutores = new File(rutaRespaldos + "autores_bin.bak");

	// Método para insertar un libro nuevo en su archivo binario
	public static void escribirAutorNuevo(Autor nuevoAutor) {
		try {

			// El true en el constructor de FileOutputStream significa que escribe
			// al final del archivo, sin eliminar su contenido antes
			FileOutputStream streamSalida = new FileOutputStream(archivoAutores, true);

			// Si el archivo está vacío se escribirá el nuevo autor con la cabecera
			if (archivoAutores.length() == 0) {
				ObjectOutputStream escribirAutor = new ObjectOutputStream(streamSalida);
				escribirAutor.writeObject(nuevoAutor);
				escribirAutor.close();
			} else {
				// Guardamos una copia de seguridad antes de insertar el autor nuevo, si
				// el archivo binario NO está vacío
				guardarRespaldoAutores();

				// Si no está vacío se escribirá sin la cabecera para evitar errores de lectura
				EscritorSinCabecera escribirSinCabecera = new EscritorSinCabecera(streamSalida);
				escribirSinCabecera.writeObject(nuevoAutor);
				escribirSinCabecera.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Método que escribe un ArrayList de autores en el archivo binario, se usará
	// este método para cuando el usuario quiera modificar o eliminar autores
	public static void escribirFicheroAutores(ArrayList<Autor> listaAutores) {
		try {
			
			comprobarDirectorios();
			// Guardamos una copia de seguridad antes de modificar autores
			guardarRespaldoAutores();
			// En esta escritura borraremos el contenido el archivo y la
			// sobreescribiremos con el ArrayList de autores
			ObjectOutputStream escritura = new ObjectOutputStream(new FileOutputStream(archivoAutores, false));
			// Con este bucle escribimos todos los autores que hay en el ArrayList
			// en el archivo binario
			for (Autor autor : listaAutores) {
				escritura.writeObject(autor);
			}
			escritura.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Método para leer todos los autores del archivo binario
	public static ArrayList<Autor> leerFicheroAutores() {
		ArrayList<Autor> listaAutores = new ArrayList<>();
		boolean eof = false;
		try {
			
			// Comprobamos que los directorios existen para evitar errores
			comprobarDirectorios();

			// Si el archivo binario no existe, lo creamos, para evitar un
			// FileNotFoundException
			if (!archivoAutores.exists()) {
				archivoAutores.createNewFile();
			}

			// Declaramos un ObjectInputStream para poder leer el archivo binario
			ObjectInputStream leerAutores = new ObjectInputStream(new FileInputStream(archivoAutores));

			// El ObjectInputStream lee el archivo hasta que llegue al EOF
			while (!eof) {
				Autor autor = (Autor) leerAutores.readObject();
				// Añadimos los libros al ArrayList
				listaAutores.add(autor);
			}
			// Cerramos el ObjectInputStream para evitar fallos
			leerAutores.close();

			// Cuando se llegue al EOF se lanzará una excepción, aquí finalizamos
			// la lectura del archivo
		} catch (EOFException eofEx) {
			eof = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return listaAutores;
	}


	private static void guardarRespaldoAutores() {
		// Declaramos un ArrayList de autores
		ArrayList<Autor> listaAutores = new ArrayList<>();
		// Guardamos los autores en el ArrayList con la función de lectura
		listaAutores = leerFicheroAutores();
		try {

			ObjectOutputStream escribirRespaldoAutores = new ObjectOutputStream(new FileOutputStream(archivoRespaldoAutores));

			// Escribimos todos los autores del ArrayList en el archivo de respaldo
			for (Autor autor : listaAutores) {
				escribirRespaldoAutores.writeObject(autor);
			}
			escribirRespaldoAutores.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}