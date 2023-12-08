package ejercicio;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

// Se implementa la interfaz Serializable para poder leer y escribir en ficheros binarios
public class Prestamo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	// LocalDate sirve para coger la fecha de AHORA
	private static LocalDate hoy = LocalDate.now();
	// Y esto formatea la fecha al formato DÍA/MES/AÑO
	private static DateTimeFormatter formatoDMA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	// Atributos del préstamo
	private int idPrestamo;
	private int idLibro;
	private String usuario;
	private String fechaPrestamo;
	private String fechaDevolucion;

	// Constructor, que incluye el ID del préstamo, el ID del libro
	// prestado y el usuario que toma el libro prestado
	public Prestamo(int idPrestamo, int idLibro, String usuario) {
		this.idPrestamo = idPrestamo;
		this.idLibro = idLibro;
		this.usuario = usuario;
		// Tomamos como fecha de préstamo el momento de insertar dicho préstamo
		this.fechaPrestamo = hoy.format(formatoDMA);
		// La fecha de devolución está vacía porque aún no se ha devuelto el libro
		this.fechaDevolucion = "";
	}

	// Otro constructor, en éste se puede especificar el día del préstamo
	public Prestamo(int idPrestamo, int idLibro, String usuario, String fechaPrestamo) {
		this.idPrestamo = idPrestamo;
		this.idLibro = idLibro;
		this.usuario = usuario;
		this.fechaPrestamo = fechaPrestamo;
		// La fecha de devolución está vacía porque aún no se ha devuelto el libro
		this.fechaDevolucion = "";
	}

	// Otro constructor más, en éste se puede especificar todo
	public Prestamo(int idPrestamo, int idLibro, String usuario, String fechaPrestamo, String fechaDevolucion) {
		this.idPrestamo = idPrestamo;
		this.idLibro = idLibro;
		this.usuario = usuario;
		this.fechaPrestamo = fechaPrestamo;
		this.fechaDevolucion = fechaDevolucion;
	}

	// Getters
	public int getIdPrestamo() {
		return idPrestamo;
	}

	public int getIdLibro() {
		return idLibro;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getFechaPrestamo() {
		return fechaPrestamo;
	}

	public String getFechaDevolucion() {
		return fechaDevolucion;
	}

	// Setters
	public void setIdLibro(int idLibro) {
		this.idLibro = idLibro;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public void setFechaPrestamo(String fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

	public void setFechaDevolucion(String fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	// Método que devuelve un String que sirve para imprimir los detalles del
	// préstamo
	public String detallesPrestamo() {
		return 	   "--------------------------------------------------------------------\n" + 
				   "Préstamo con ID: " + this.idPrestamo + "\n" +
				   "ID del libro prestado: " + this.idLibro + "\n" +
				   "Usuario que ha tomado el libro: " + this.usuario + "\n" + 
				   "Fecha del préstamo: " + this.fechaPrestamo + "\n" + 
				   "Fecha de devolución: " + this.fechaDevolucion + "\n" + 
				   "--------------------------------------------------------------------\n";
	}
	
	public static final String vacio = "\nNo hay ningún préstamo registrado\n";
	
	// Método para introducir fechas con validación de formato
	private static String introducirFecha(String msg) {
		String fecha;
		boolean fechaValida = false;
		do {
			fecha = ES.leeCadena(msg);
			if(fecha.equalsIgnoreCase("hoy")) {
				return "hoy";
			} else if(fecha.equals("")) {
				return "";
			} else {
				SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
				try {
					Date parseFecha = formatoFecha.parse(fecha);
					fecha = formatoFecha.format(parseFecha);
					fechaValida = true;
				} catch (ParseException pex) {
					ES.msgErrln("Fecha NO válida");
				}
			}
		} while (!fechaValida);
		
		return fecha;
	}
	
	// Método para introducir un préstamo nuevo
	public static void nuevoPrestamo() {
		boolean idRepetido = true; // Comprobamos que el ID no está repetido
		int idNuevo = 0;
		
		while(idRepetido) {
			idNuevo = ES.leeEntero("ID del préstamo: ");
			idRepetido = false;
			ArrayList<String> lineasPrestamo = GestorFicherosTexto.leerFicheroPrestamos();
			if (lineasPrestamo.contains("Préstamo con ID: " + idNuevo)) {
				ES.msgErrln("\nYa existe un préstamo con este ID, introduce otro\n");
				idRepetido = true;
			}
		}
		
		int idLibro = ES.leeEntero("ID del libro prestado: ");
		String usuario = ES.leeCadena("Nombre del usuario que ha tomado prestado el libro: ");
		String fechaPrestamo = introducirFecha("Fecha del préstamo (Formato: DD/MM/AAAA), si es hoy, pon HOY: ");
		if(fechaPrestamo.equalsIgnoreCase("hoy")) {
			// Si el usuario introduce HOY, se llamará al primer constructor, que asigna la fecha de hoy al préstamo
			GestorFicherosTexto.escribirFicheroPrestamosTXT(new Prestamo(idNuevo, idLibro, usuario));
		} else {
			// Si el usuario introduce una fecha en concreto se llamará al segundo constructor
			GestorFicherosTexto.escribirFicheroPrestamosTXT(new Prestamo(idNuevo, idLibro, usuario, fechaPrestamo));
		}
		ES.msgln("\nPréstamo introducido exitosamente\n");
		
	}
	
	// Método para imprimir los préstamos por pantalla
	public static void imprimirPrestamos() {
		// Insertamos las líneas del fichero en el ArrayList llamando a la función
		ArrayList<String> lineasPrestamos = GestorFicherosTexto.leerFicheroPrestamos();
		if(!lineasPrestamos.isEmpty()) {
			ES.msgln("\nPréstamos registrados:\n");
			// Imprimimos todas las líneas del fichero por pantalla
			for (String linea : lineasPrestamos) {
				ES.msgln(linea);
			}
		} else {
			ES.msgErrln(vacio);
		}
	}
	
	// Método para añadir o modificar la fecha de devolución de un préstamo
	public static void modificarFechaDev() {
		ArrayList<String> lineasPrestamos = GestorFicherosTexto.leerFicheroPrestamos();
		if(!lineasPrestamos.isEmpty()) {
			boolean prestamoEncontrado = false;
			
			while (!prestamoEncontrado) {
				
				int idBuscado = ES.leeEntero("Introduce el ID del préstamo:");
				
				if(lineasPrestamos.contains("Préstamo con ID: " + idBuscado)) {
					String fechaDevolucion = introducirFecha("Introduce la fecha de devolución, si ha sido hoy, pon HOY: ");
					
					if(fechaDevolucion.equals("hoy")) {
						// Editamos la línea de la fecha de devolución con la fecha de hoy
						// si el usuario introduce HOY
						lineasPrestamos.set(lineasPrestamos.indexOf("Préstamo con ID: " + idBuscado) + 4, "Fecha de devolución: " + hoy.format(formatoDMA));
					} else {
						// Si introduce otra fecha, se pondrá esa fecha
						lineasPrestamos.set(lineasPrestamos.indexOf("Préstamo con ID: " + idBuscado) + 4, "Fecha de devolución: " + fechaDevolucion);
					}
					GestorFicherosTexto.escribirModificacionesPrestamos(lineasPrestamos);
					prestamoEncontrado = true;
				} else {
					ES.msgErrln("\nNo hay ningún préstamo con ID: " + idBuscado + "\n");
				}
				
			}
		} else {
			ES.msgErrln(vacio);
		}
	}
	
	// Método para modificar préstamos
	public static void modificarPrestamo() {
		ArrayList<String> lineasPrestamos = GestorFicherosTexto.leerFicheroPrestamos();
		
		if(!lineasPrestamos.isEmpty()) {
			boolean prestamoEncontrado = false;
			
			while (!prestamoEncontrado) {
				
				int idBuscado = ES.leeEntero("ID del préstamo a modificar: ");
				
				if(lineasPrestamos.contains("Préstamo con ID: " + idBuscado)) {
					int idLibro = ES.leeEntero("Actualizar ID del libro prestado: ");
					String usuario = ES.leeCadena("Actualizar el usuario que ha tomado el libro: ");
					String fechaPrestamo = introducirFecha("Actualizar fecha del préstamo (Formato: DD/MM/AAAA), pon HOY si ha sido hoy: ");
					String fechaDevolucion = introducirFecha("Actualizar fecha de devolución (Formato: DD/MM/AAAA), pon HOY si ha sido hoy, si no se ha devuelto, déjalo en blanco: ");
					
					int posicion = lineasPrestamos.indexOf("Préstamo con ID: " + idBuscado);
					
					lineasPrestamos.set(posicion-1, "--------------------------------------------------------------------");
					lineasPrestamos.set(posicion+0, "Préstamo con ID: " + idBuscado);
					lineasPrestamos.set(posicion+1, "ID del libro prestado: " + idLibro);
					lineasPrestamos.set(posicion+2, "Usuario que ha tomado el libro: " + usuario);
					
					if(fechaPrestamo.equals("hoy")) {
						lineasPrestamos.set(posicion+3, "Fecha del préstamo: " + hoy.format(formatoDMA));
					} else {
						lineasPrestamos.set(posicion+3, "Fecha del préstamo: " + fechaPrestamo);
					}
					
					if(fechaDevolucion.equals("hoy")) {
						lineasPrestamos.set(posicion+4, "Fecha de devolución: " + hoy.format(formatoDMA));
					} else {
						lineasPrestamos.set(posicion+4, "Fecha de devolución: " + fechaDevolucion);
					}
					
					lineasPrestamos.set(posicion+5, "--------------------------------------------------------------------");
					
					GestorFicherosTexto.escribirModificacionesPrestamos(lineasPrestamos);
					
					ES.msgln("\nPréstamo actualizado\n");
					prestamoEncontrado = true;
				} else {
					ES.msgErrln("\nNo hay ningún préstamo con ID: " + idBuscado + "\n");
				}
				
			}
		} else {
			ES.msgErrln(vacio);
		}
		
	}
	
	// Método para eliminar un préstamo
	public static void eliminarPrestamo() {
	    ArrayList<String> lineasPrestamos = GestorFicherosTexto.leerFicheroPrestamos();

	    if(!lineasPrestamos.isEmpty()) {
		    boolean prestamoEncontrado = false;
		    while (!prestamoEncontrado) {
		        int idBuscado = ES.leeEntero("ID del préstamo a eliminar: ");

		        if(lineasPrestamos.contains("Préstamo con ID: " + idBuscado)) {
		            int posicion = lineasPrestamos.indexOf("Préstamo con ID: " + idBuscado);
	
		            // Primero vaciamos las líneas del fichero
		            for (int i = -1; i < 6; i++) {
		                lineasPrestamos.set(posicion+i, "");
		            }
		            
		            // Luego los borramos, si los borramos sin vaciarlos antes
		            // dará un error
		            for (int i = lineasPrestamos.size() - 1; i >= 0; i--) {
		                if (lineasPrestamos.get(i).isEmpty()) {
		                    lineasPrestamos.remove(i);
		                }
		            }
	
		            GestorFicherosTexto.escribirModificacionesPrestamos(lineasPrestamos);
	
		            ES.msgln("\nPréstamo eliminado\n");
		            prestamoEncontrado = true;
		        } else {
		            ES.msgErrln("\nNo hay ningún préstamo con ID: " + idBuscado + "\n");
		        }
		    }
	    } else {
	    	ES.msgErrln(vacio);
	    }
	}
}
