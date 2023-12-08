package ejercicio;

public class Principal {
	
	private static final int opcionSalida = 0;
	
	public static void main(String[] args) {
		boolean salir = false;
		while (!salir) {
			mostrarMenu();
			int opcion = ES.leeEntero("Seleccione una opción: ");
			switch (opcion) {
			case 1:
				// Gestionar libros
				gestionarLibros();
				break;
			case 2:
				// Gestionar autores
				gestionarAutores();
				break;
			case 3:
				// Gestionar préstamos
				gestionarPrestamos();
				break;
			case 4:
				// Exportar/Importar datos con XML
				gestionarFicherosXML();
				break;
			case opcionSalida:
				ES.msgln("Programa finalizado...");
				salir = true;
				break;
			default:
				ES.msgErrln(errorOpcion);
			}
		}
	}
	
	private final static String errorOpcion = "Opción no válida. Por favor, intente de nuevo.";

	// Método para mostrar el menú principal
	private static void mostrarMenu() {
		ES.msgln("Bienvenido al Sistema de Gestión de Biblioteca");
		ES.msgln("1. Gestionar Libros");
		ES.msgln("2. Gestionar Autores");
		ES.msgln("3. Gestionar Préstamos");
		ES.msgln("4. Exportar/Importar Datos (XML)");
		ES.msgln("0. Salir del programa");
	}
	
	// Método para mostrar los submenús, dependiendo el tipo de objeto que se quiera manejar
	private static void mostrarSubMenu(String tipo) {
		ES.msgln("Bienvenido a la Gestión de " + tipo);
		ES.msgln("1. Insertar nuevo " + tipo);
		if(tipo.equalsIgnoreCase("autor")) {
			ES.msgln("2. Listar todos los " + tipo + "es");
		} else {
			ES.msgln("2. Listar todos los " + tipo + "s");
		}
		ES.msgln("3. Modificar un " + tipo);
		ES.msgln("4. Eliminar un " + tipo);
		if(tipo.equals("préstamo")) {
			ES.msgln("5. Añadir/modificar fecha de devolución de un préstamo");
		}
		ES.msgln("0. Volver al menú principal");
	}

	private static void gestionarLibros() {
		boolean salirMenuLibros = false;
		while(!salirMenuLibros) {
			mostrarSubMenu("libro");
			int opcionLibros = ES.leeEntero("Seleccione una opción: ");
			switch (opcionLibros) {
			case 1:
				Libro.nuevoLibro();
				break;
				
			case 2: 
				Libro.imprimirLibros();
				break;
				
			case 3:
				Libro.modificarLibro();
				break;
				
			case 4:
				Libro.eliminarLibro();
				break;
				
			case opcionSalida:
				salirMenuLibros = true;
				break;
			
			default:
				ES.msgErrln(errorOpcion);
			}
		}
		
		
	}

	private static void gestionarAutores() {
		boolean salirMenuAutores = false;
		while(!salirMenuAutores) {
			mostrarSubMenu("autor");
			int opcionAutor = ES.leeEntero("Seleccione una opción: ");
			switch (opcionAutor) {
			case 1:
				Autor.nuevoAutor();
				break;
				
			case 2: 
				Autor.imprimirAutores();
				break;
				
			case 3:
				Autor.modificarAutor();
				break;
				
			case 4:
				Autor.eliminarAutor();
				break;
				
			case opcionSalida:
				salirMenuAutores = true;
				break;
			
			default:
				ES.msgErrln(errorOpcion);
				break;
			}
		}
	}

	private static void gestionarPrestamos() {
		boolean salirMenuPrestamos = false;
		while(!salirMenuPrestamos) {
			mostrarSubMenu("préstamo");
			int opcionPrestamos = ES.leeEntero("Seleccione una opción: ");
			switch (opcionPrestamos) {
			case 1:
				Prestamo.nuevoPrestamo();
				break;
				
			case 2: 
				Prestamo.imprimirPrestamos();
				break;
				
			case 3:
				Prestamo.modificarPrestamo();
				break;
				
			case 4:
				Prestamo.eliminarPrestamo();
				break;
				
			case 5:
				Prestamo.modificarFechaDev();
				break;
				
			case opcionSalida:
				salirMenuPrestamos = true;
				break;
			
			default:
				ES.msgErrln(errorOpcion);
				break;
			}
		}
	}
	

	private static void gestionarFicherosXML() {
		boolean salirMenuXML = false;
		while(!salirMenuXML) {
			ES.msgln("Sobre qué objeto quiere realizar importación o exportación de XML:");
			ES.msgln("1. Libros");
			ES.msgln("2. Autores");
			ES.msgln("0. Volver al menú principal");
			int opcionXML = ES.leeEntero("Seleccione: ");
			switch(opcionXML) {
			case 1:
				xmlLibros();
				break;
				
			case 2:
				xmlAutores();
				break;

			case opcionSalida:
				salirMenuXML = true;
				break;
			default:
				ES.msgErrln(errorOpcion);
				break;
			}
		}
	}
	
	private static void subMenuXML(String tipo) {
		ES.msgln("1. Importar " + tipo);
		ES.msgln("2. Exportar " + tipo);
		ES.msgln("0. Volver al menú anterior");
	}
	
	private static void xmlLibros() {
		boolean salir = false;
		while(!salir) {
			subMenuXML("libros");
			int opcion = ES.leeEntero("Seleccione: ");
			switch (opcion) {
			case 1:
				Libro.importarDatosXML();
				break;
			case 2:
				Libro.exportarDatosXML();
				break;
			case opcionSalida:
				salir = true;
				break;
			default:
				ES.msgErrln(errorOpcion);
			}
		}
	}
	
	public static void xmlAutores() {
		boolean salir = false;
		while(!salir) {
			subMenuXML("autores");
			int opcion = ES.leeEntero("Seleccione: ");
			switch (opcion) {
			case 1:
				Autor.importarDatosXML();
				break;
			case 2:
				Autor.exportarDatosXML();
				break;
			case opcionSalida:
				salir = true;
				break;
			default:
				ES.msgErrln(errorOpcion);
			}
		}
	}
	
}