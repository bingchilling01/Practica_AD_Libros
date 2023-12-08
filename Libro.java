package ejercicio;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

//Se implementa la interfaz Serializable para poder leer y escribir en ficheros binarios
public class Libro implements Serializable {

	private static final long serialVersionUID = 1L;

	// Atributos constantes y estáticos para definir el año de publicación MÍNIMO y
	// MÁXIMO
	private static LocalDate anioActual = LocalDate.now(); // Esto sirve para asignar el año actual
	// al valor MÁXIMO del año
	private static final int ANIO_MIN = 200;
	private static final int ANIO_MAX = anioActual.getYear();

	// Atributos del libro
	private int id;
	private String titulo;
	private String autor;
	private int anioPublicacion;
	private String genero;

	// Constructor, que incluye como parámetros todos los atributos,
	// útil cuando queramos insertar un libro nuevo
	public Libro(int id, String titulo, String autor, int anioPub, String genero) {
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.anioPublicacion = anioPub;
		this.genero = genero;
	}

	// Getters
	public int getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getAutor() {
		return autor;
	}

	public int getAnioPublicacion() {
		return anioPublicacion;
	}

	public String getGenero() {
		return genero;
	}

	// Setters, no hay setter de ID porque no se va a cambiar
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public void setAnioPublicacion(int anioPublicacion) {
		this.anioPublicacion = anioPublicacion;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	// Método que devuelve un String que sirve para imprimir los detalles del libro
		public String detallesLibro() {
			return "--------------------------------------------------------------------\n" + 
				   "Libro con ID: " + this.id + "\n" +
				   "Título: " + this.titulo + "\n" +
				   "Autor: " + this.autor + "\n" + 
				   "Año de publicación: " + this.anioPublicacion + "\n" + 
				   "Género: " + this.genero + "\n" + 
				   "--------------------------------------------------------------------\n";
		}

	// String constante para indicar que la lista está vacía
	public static final String vacio = "\nNo hay ningún libro en la lista\n";

	// Método estático para insertar un nuevo libro en el fichero
	public static void nuevoLibro() {

		// Primero leemos el fichero binario de libros para conseguir la lista para
		// poder
		// sobreescribirla con un libro nuevo
		ArrayList<Libro> listaLibros = GestorFicherosBinarios.leerFicheroLibros();

		boolean idRepetido = true; // Esto sirve para que no se repitan IDs
		int idNuevo = 0; // Inicializamos este valor para evitar errores
		while (idRepetido) {
			idNuevo = ES.leeEntero("Introduce el ID del libro nuevo: ");
			idRepetido = false; // Cambiamos este booleano a false para evitar un
								// bucle infinito
			for (Libro libro : listaLibros) {
				if (libro.id == idNuevo) {
					ES.msgErrln("\nYa hay un libro con este ID, introduce otro\n");
					idRepetido = true;
					break; // Este break sirve para que no salte al else en los 
						   // otros libros del ArrayList
				} else {
					idRepetido = false;
				}
			}
		}

		// Si el ID introducido no se encuentra en la lista, entonces se podrá
		// introducir los detalles del nuevo libro
		if (!idRepetido) {
			String tituloNuevo = ES.leeCadena("Título del libro nuevo: ");
			String autorLibro = ES.leeCadena("Autor del libro: ");
			int anioPub = ES.leeEntero("Año de publicación (entre " + ANIO_MIN + " y " + ANIO_MAX + "): ", ANIO_MIN,ANIO_MAX);
			String generoLibro = ES.leeCadena("Género del libro: ");

			// Una vez introducido los detalles del nuevo libro, se escribirá
			// el fichero binario desde el EOF
			GestorFicherosBinarios.escribirLibroNuevo(new Libro(idNuevo, tituloNuevo, autorLibro, anioPub, generoLibro));
			ES.msgln("\nLibro introducido exitosamente\n");
		}
	}

	// Método que imprime todos los libros por pantalla
	public static void imprimirLibros() {
		// Obtenemos la lista de libros llamando a la función de lectura
		ArrayList<Libro> listaLibros = GestorFicherosBinarios.leerFicheroLibros();
		// Si el ArrayList NO está vacío se imprime por pantalla todos los libros
		if (!listaLibros.isEmpty()) {
			ES.msgln("\nEstos son los libros que hay en la biblioteca:");
			for (Libro libro : listaLibros) {
				ES.msgln(libro.detallesLibro());
			}
		} else {
			ES.msgErrln(vacio);
		}
	}

	// Método para modificar los datos de un libro
	public static void modificarLibro() {

		// Leemos en el fichero binario los detalles de cada libro
		ArrayList<Libro> listaLibros = GestorFicherosBinarios.leerFicheroLibros();

		// Si la lista NO está vacía se prosigue con la modificación de datos
		if (!listaLibros.isEmpty()) {
			boolean libroEncontrado = false; // Esto sirve para saber si el libro ha sido encontrado o no
			while (!libroEncontrado) {
				int idBuscado = ES.leeEntero("Introduce el ID del libro a modificar: ");
				for (Libro libro : listaLibros) {
					if (libro.id == idBuscado) {
						libro.titulo = ES.leeCadena("Nuevo título del libro: ");
						libro.autor = ES.leeCadena("Nuevo autor del libro: ");
						libro.anioPublicacion = ES.leeEntero(
								"Año de publicación (entre " + ANIO_MIN + " y " + ANIO_MAX + "): ", ANIO_MIN, ANIO_MAX);
						libro.genero = ES.leeCadena("Género del libro: ");
						libroEncontrado = true;
						ES.msgln("\nLibro modificado\n");

						// Una vez modificados los datos del libro se procede a escribir
						// en el fichero binario de los libro los datos actualizados
						GestorFicherosBinarios.escribirFicheroLibros(listaLibros);
					}
				}
				if (!libroEncontrado) {
					ES.msgErrln("\nNo hay ningún libro con el ID: " + idBuscado + "\n");
				}
			}
		} else {
			// Si la lista está vacía se mostrará un mensaje de que está vacía y no hará
			// ninguna modificación
			ES.msgErrln(vacio);
		}

	}

	// Método para eliminar un libro de la lista
	public static void eliminarLibro() {

		// Primero, sacamos los datos de los libros con el lector
		ArrayList<Libro> listaLibros = GestorFicherosBinarios.leerFicheroLibros();

		// Al igual que en la modificación, comprobamos que la lista NO esté vacía
		if (!listaLibros.isEmpty()) {
			boolean libroEncontrado = false; // Esto sirve para saber si el libro ha sido encontrado o no
												// al igual que en la modificación de datos
			int idBuscado = ES.leeEntero("Introduce el ID del libro a eliminar: ");
			Iterator<Libro> iteradorLibro = listaLibros.iterator(); // Usaremos un iterador para evitar
			 														// un ConcurrentModificationException
			while (iteradorLibro.hasNext()) {
				Libro libro = iteradorLibro.next(); // Movemos al siguiente libro hasta
													// encontrar al que queremos eliminar
				if (libro.id == idBuscado) {
					iteradorLibro.remove(); // Eliminamos el libro especificado
					libroEncontrado = true;
					ES.msgln("\nLibro eliminado\n");

					// Una vez eliminado el libro, escribimos en el fichero binario
					GestorFicherosBinarios.escribirFicheroLibros(listaLibros);
					break;
				}

			}
			
			if (!libroEncontrado) {
				ES.msgErrln("\nNo hay ningún libro con el ID: " + idBuscado + "\n");
			}

		} else {
			// Si la lista está vacía se mostrará un mensaje de que está vacía y no hará
			// ninguna modificación
			ES.msgErrln(vacio);
		}
	}

	public static void importarDatosXML() {
		
		// Asignamos al ArrayList lo que devuelve el lector de XML
		ArrayList<Libro> listaLibros = GestorFicherosXML.importarXMLLibros();

		if (listaLibros != null && !listaLibros.isEmpty()) {
			GestorFicherosBinarios.escribirFicheroLibros(listaLibros);
			ES.msgln("\nDatos importados\n");
		} else {
			ES.msgErrln("\nNo hay datos en el XML\n");
		}
	}

	public static void exportarDatosXML() {
		
		// Primero leemos el fichero binario y se lo asignamos a un ArrayList
		ArrayList<Libro> listaLibros = GestorFicherosBinarios.leerFicheroLibros();
		
		if(!listaLibros.isEmpty()) {
			GestorFicherosXML.exportarXMLLibros(listaLibros);
			ES.msgln("\nDatos exportados\n");
		} else {
			ES.msgErrln("\nNo se puede exportar un archivo vacío\n");
		}
	}

}