package ejercicio;

import java.io.File;

public abstract class GestorFicheros {

	// Ruta donde se va a guardar todos los ficheros
	protected static final String rutaCarpetaRaiz = "C:/Users/" + System.getProperty("user.name") + "/Desktop/Práctica1T_AD_Lin_Juanjo/";
	protected static File carpetaRaiz = new File(rutaCarpetaRaiz);
	// Carpeta de las copias de seguridad
	protected static String rutaRespaldos = rutaCarpetaRaiz + "respaldos/";
	protected static File directorioRespaldos = new File(rutaRespaldos);
	
	// Carpeta donde se va a guardar los ficheros XML para la importación/exportación
	protected static final String rutaXML = rutaCarpetaRaiz + "ficheros_xml/";
	protected static File directorioXML = new File(rutaXML);

	// Método para comprobar si los directorios existen, si no, los crea,
	// sirve para evitar Excepciones
	protected static void comprobarDirectorios() {
		if (!carpetaRaiz.exists()) {
			try {
				carpetaRaiz.mkdir();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (!directorioRespaldos.exists()) {
			try {
				directorioRespaldos.mkdir();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (!directorioXML.exists()) {
			try {
				directorioXML.mkdir();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}
}
