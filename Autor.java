package ejercicio;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

//Se implementa la interfaz Serializable para poder leer y escribir en ficheros binarios
public class Autor implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// Atributos constantes y estáticos para definir el año de nacimiento MÍNIMO y MÁXIMO
	private static LocalDate anioActual = LocalDate.now(); // Esto sirve para asignar el año actual
	// al valor MÁXIMO del año
	private static final int ANIO_MIN = 180;
	private static final int ANIO_MAX = anioActual.getYear() - 16;

	// Atributos del autor
	private int id;
	private String nombre;
	private String nacionalidad;
	private int anioNacimiento;

	// Constructor, que incluye como parámetros todos los atributos,
	// útil cuando queramos insertar un autor nuevo
	public Autor(int id, String nombre, String nacionalidad, int anioNacimiento) {
		this.id = id;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.anioNacimiento = anioNacimiento;
	}

	// Getters
	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public int getAnioNacimiento() {
		return anioNacimiento;
	}

	// Setters, no hay setter de ID porque no se va a cambiar
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public void setAnioNacimiento(int anioNacimiento) {
		this.anioNacimiento = anioNacimiento;
	}

	// Método que devuelve un String que sirve para imprimir los detalles del autor
	public String detallesAutor() {
		return "--------------------------------------------------------------------\n" + 
			   "Autor con ID: " + this.id + "\n" + 
			   "Nombre: " + this.nombre + "\n" + 
			   "Nacionalidad: " + this.nacionalidad + "\n" + 
			   "Año nacimiento: " + this.anioNacimiento + "\n" + 
			   "--------------------------------------------------------------------\n";
	}
	
	// String constante para indicar que la lista está vacía
	public static final String vacio = "\nNo hay ningún autor en la lista\n";
	
	// Método estático para insertar un nuevo autor en el fichero
	public static void nuevoAutor() {
		
		// Primero leemos el fichero binario de autores para conseguir la lista para poder
		// sobreescribirla con un autor nuevo
		ArrayList<Autor> listaAutores = GestorFicherosBinarios.leerFicheroAutores();
		
		boolean idRepetido = true; // Esto sirve para que no se repitan IDs
		int idNuevo = 0; // Inicializamos este valor para evitar errores
		while (idRepetido) {
			idNuevo = ES.leeEntero("Introduce el ID del nuevo autor: ");
			idRepetido = false; // Cambiamos este booleano a false para evitar un
								// bucle infinito
			for (Autor autor : listaAutores) {
				if (autor.id == idNuevo) {
					ES.msgErrln("\nYa hay un autor con este ID, introduce otro\n");
					idRepetido = true;
					break; // Este break sirve para que no salte al else en los 
					   	   // otros autores del ArrayList
				} else {
					idRepetido = false;
				}
			}
		}

		// Si el ID introducido no se encuentra en la lista, entonces se podrá
		// introducir los detalles del nuevo autor
		if (!idRepetido) {
			String nombreAutor = ES.leeCadena("Introduce su nombre: ");
			String nacionalidadAutor = ES.leeCadena("Su nacionalidad: ");
			int anioNac = ES.leeEntero("Su año de nacimiento (entre " + ANIO_MIN + " y " + ANIO_MAX + "): ", ANIO_MIN, ANIO_MAX);
			
			// Una vez introducido los detalles del nuevo autor, se escribirá
			// el fichero binario desde el EOF
			GestorFicherosBinarios.escribirAutorNuevo(new Autor(idNuevo, nombreAutor, nacionalidadAutor, anioNac));
			ES.msgln("\nAutor introducido exitosamente\n");
		}
	}
	
	// Método estático para imprimir todos los autores
	public static void imprimirAutores() {
		// Obtenemos la lista de autores llamando a la función de lectura
		ArrayList<Autor> listaAutores = GestorFicherosBinarios.leerFicheroAutores();
		// Si el ArrayList NO está vacío se imprime por pantalla todos los autores
		if (!listaAutores.isEmpty()) {
			ES.msgln("\nLista de autores:");
			for (Autor autor : listaAutores) {
				ES.msgln(autor.detallesAutor());
			}
		} else {
			ES.msgErrln(vacio); // Se imprimirá el string de arriba si la lista está vacía
		}
	}
	
	// Método estático para actualizar los datos de un autor
	public static void modificarAutor() {
		// Leemos en el fichero binario los detalles de cada autor
		ArrayList<Autor> listaAutores = GestorFicherosBinarios.leerFicheroAutores();
		
		// Si la lista NO está vacía se prosigue con la modificación de datos
		if(!listaAutores.isEmpty()) {

			boolean autorEncontrado = false; // Esto sirve para saber si el autor ha sido encontrado o no
			while(!autorEncontrado) {
				int idBuscado = ES.leeEntero("Introduce el ID del autor a modificar: ");
				for (Autor autor : listaAutores) {
					if(autor.id == idBuscado) {
						autor.nombre = ES.leeCadena("Actualizar nombre del autor: ");
						autor.nacionalidad = ES.leeCadena("Actualizar la nacionalidad del autor: ");
						autor.anioNacimiento = ES.leeEntero("Año de publicación (entre " + ANIO_MIN + " y " + ANIO_MAX + "): ", ANIO_MIN, ANIO_MAX);
						autorEncontrado = true;
						ES.msgln("\nAutor modificado\n");
						
						// Una vez modificados los datos del autor se procede a escribir
						// en el fichero binario de los autores los datos actualizados
						GestorFicherosBinarios.escribirFicheroAutores(listaAutores);
					}
				}
				if(!autorEncontrado) {
					ES.msgErrln("\nNo hay ningún autor con el ID: " + idBuscado + "\n");
				}
			}
		} else {
			// Si la lista está vacía se mostrará un mensaje de que está vacía y no hará
			// ninguna modificación
			ES.msgErrln(vacio);
		}
	}
	
	// Método estático para eliminar un autor de la lista
	public static void eliminarAutor() {

		// Primero, sacamos los datos de los autores con el lector
		ArrayList<Autor> listaAutores = GestorFicherosBinarios.leerFicheroAutores();

		// Al igual que en la modificación, comprobamos que la lista NO esté vacía
		if (!listaAutores.isEmpty()) {
			boolean libroEncontrado = false; // Esto sirve para saber si el autor ha sido encontrado o no
											 // al igual que en la modificación de datos
			int idBuscado = ES.leeEntero("Introduce el ID del autor a eliminar: ");
			Iterator<Autor> iteradorAutor = listaAutores.iterator(); // Usaremos un iterador para evitar
																	 // un ConcurrentModificationException
			while (iteradorAutor.hasNext()) {
				Autor autor = iteradorAutor.next(); // Movemos al siguiente autor hasta
													// encontrar al que queremos eliminar
				if (autor.id == idBuscado) {
					iteradorAutor.remove(); // Eliminamos el autor especificado
					libroEncontrado = true;
					ES.msgln("\nAutor eliminado\n");

					// Una vez eliminado el autor, escribimos en el fichero binario
					GestorFicherosBinarios.escribirFicheroAutores(listaAutores);
					break;
				}

			}
			
			if (!libroEncontrado) {
				ES.msgErrln("\nNo hay ningún autor con el ID: " + idBuscado + "\n");
			}

		} else {
			// Si la lista está vacía se mostrará un mensaje de que está vacía y no hará
			// ninguna modificación
			ES.msgErrln(vacio);
		}
	}
	
	public static void importarDatosXML() {
		
		// Asignamos al ArrayList lo que devuelve el lector de XML
		ArrayList<Autor> listaAutores = GestorFicherosXML.importarXMLAutores();

		// Si lo que ha devuelto la función de importar XML NO está nulo ni vacío, escribimos el 
		// archivo binario con los datos del ArrayList
		if (listaAutores != null && !listaAutores.isEmpty()) {
			GestorFicherosBinarios.escribirFicheroAutores(listaAutores);
			ES.msgln("\nDatos importados\n");
		} else {
			ES.msgErrln("\nNo hay datos en el XML\n");
		}
	}

	public static void exportarDatosXML() {
		
		// Primero leemos el fichero binario y se lo asignamos a un ArrayList
		ArrayList<Autor> listaAutores = GestorFicherosBinarios.leerFicheroAutores();
		
		// Si el ArrayList NO está vacío, exporta el XML
		if(!listaAutores.isEmpty()) {
			GestorFicherosXML.exportarXMLAutores(listaAutores);
			ES.msgln("\nDatos exportados\n");
		} else {
			ES.msgErrln("\nNo se puede exportar un archivo vacío\n");
		}
	}

}
