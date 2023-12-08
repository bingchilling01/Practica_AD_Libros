package ejercicio;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public final class GestorFicherosXML extends GestorFicheros {

	// Atributos y métodos para los XML de los LIBROS
	private static final String xmlLibro = rutaXML + "libros.xml";
	private static final String etiquetasLibro[] = { "libro", "id", "titulo", "autor", "anio_publicacion", "genero" };

	// Método para importar los LIBROS desde su fichero XML
	public static ArrayList<Libro> importarXMLLibros() {
		
		// Primero, comprobamos que los directorios existen para evitar errores
		comprobarDirectorios();
		
		ArrayList<Libro> listaLibros = new ArrayList<>(); // Declararemos un ArrayList
		File ficheroXMLLibros = new File(xmlLibro);
		
		// Si el fichero existe y NO está vacío, procederemos a la importación
		// de los datos en un ArrayList de libros
		if (ficheroXMLLibros.exists() && ficheroXMLLibros.length() > 54) { 
			try {

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document xmlLibros = builder.parse(ficheroXMLLibros);

				NodeList libros = xmlLibros.getElementsByTagName(etiquetasLibro[0]);

				for (int i = 0; i < libros.getLength(); i++) {
					Node nodoLibro = libros.item(i);
					if (nodoLibro.getNodeType() == Node.ELEMENT_NODE) {
						Element libro = (Element) nodoLibro;
						int idLibro = Integer.parseInt(libro.getAttribute(etiquetasLibro[1]));
						String tituloLibro = libro.getElementsByTagName(etiquetasLibro[2]).item(0).getTextContent();
						String autorLibro = libro.getElementsByTagName(etiquetasLibro[3]).item(0).getTextContent();
						int anioPub = Integer.parseInt(libro.getElementsByTagName(etiquetasLibro[4]).item(0).getTextContent());
						String generoLibro = libro.getElementsByTagName(etiquetasLibro[5]).item(0).getTextContent();
						listaLibros.add(new Libro(idLibro, tituloLibro, autorLibro, anioPub, generoLibro));
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			// Una vez acabado el proceso devolveremos el ArrayList
			return listaLibros;

		} else {
			// Si el archivo no existe devolveremos un valor NULO
			return null;
		}

	}

	// Método para exportar el fichero XML de LIBROS
	public static void exportarXMLLibros(ArrayList<Libro> listaLibros) {
		
		comprobarDirectorios(); // Comprobamos que los directorios existen

		try {

			DocumentBuilderFactory factoryLibros = DocumentBuilderFactory.newInstance();
			DocumentBuilder constructorXMLLibros = factoryLibros.newDocumentBuilder();
			Document xmlLibros = constructorXMLLibros.newDocument();
			Element libros = (Element) xmlLibros.createElement("libros");			

			for (int i = 0; i < listaLibros.size(); i++) {
				Element nuevoLibro = (Element) xmlLibros.createElement(etiquetasLibro[0]);
				nuevoLibro.setAttribute(etiquetasLibro[1], String.valueOf(listaLibros.get(i).getId()));
				
				
				Element tituloLibro = (Element) xmlLibros.createElement(etiquetasLibro[2]);
				tituloLibro.appendChild(xmlLibros.createTextNode(listaLibros.get(i).getTitulo()));
				nuevoLibro.appendChild(tituloLibro);

				Element autorLibro = (Element) xmlLibros.createElement(etiquetasLibro[3]);
				autorLibro.appendChild(xmlLibros.createTextNode(listaLibros.get(i).getAutor()));
				nuevoLibro.appendChild(autorLibro);

				Element anioPub_Libro = (Element) xmlLibros.createElement(etiquetasLibro[4]);
				anioPub_Libro
						.appendChild(xmlLibros.createTextNode(String.valueOf(listaLibros.get(i).getAnioPublicacion())));
				nuevoLibro.appendChild(anioPub_Libro);

				Element generoLibro = (Element) xmlLibros.createElement(etiquetasLibro[5]);
				generoLibro.appendChild(xmlLibros.createTextNode(listaLibros.get(i).getGenero()));
				nuevoLibro.appendChild(generoLibro);
				
				libros.appendChild(nuevoLibro);
			}
			
			xmlLibros.appendChild(libros);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(xmlLibros);
			StreamResult result = new StreamResult(new File(xmlLibro));
			transformer.transform(source, result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Atributos y métodos para los XML de los AUTORES
	private static final String xmlAutor = rutaXML + "autores.xml";
	private static final String etiquetasAutor[] = { "autor", "id", "nombre", "nacionalidad", "anio_nacimiento" };

	// Método para importar los AUTORES desde su fichero XML
	public static ArrayList<Autor> importarXMLAutores() {
		
		comprobarDirectorios();
		ArrayList<Autor> listaAutores = new ArrayList<>();
		File ficheroXMLAutores = new File(xmlAutor);
		
		// Si el fichero existe y NO está vacío, procederemos a la importación
		// de los datos añadiéndolos en un ArrayList de autores 
		if (ficheroXMLAutores.exists() && ficheroXMLAutores.length() > 54) {

			try {

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document xmlAutores = builder.parse(ficheroXMLAutores);

				NodeList autores = xmlAutores.getElementsByTagName(etiquetasAutor[0]);

				for (int i = 0; i < autores.getLength(); i++) {
					Node nodoAutor = autores.item(i);
					if (nodoAutor.getNodeType() == Node.ELEMENT_NODE) {
						Element autor = (Element) nodoAutor;
						int idAutor = Integer.parseInt(autor.getAttribute(etiquetasAutor[1]));
						String nombreAutor = autor.getElementsByTagName(etiquetasAutor[2]).item(0).getTextContent();
						String nacionalidadAutor = autor.getElementsByTagName(etiquetasAutor[3]).item(0)
								.getTextContent();
						int anioNacimento = Integer
								.parseInt(autor.getElementsByTagName(etiquetasAutor[4]).item(0).getTextContent());
						listaAutores.add(new Autor(idAutor, nombreAutor, nacionalidadAutor, anioNacimento));
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			return listaAutores;
		} else {

			return null;
		}

	}

	// Método para exportar el fichero XML de AUTORES
	public static void exportarXMLAutores(ArrayList<Autor> listaAutores) {
		comprobarDirectorios();
		
		try {

			DocumentBuilderFactory factoryAutores = DocumentBuilderFactory.newInstance();
			DocumentBuilder constructorXMLAutores = factoryAutores.newDocumentBuilder();
			Document xmlAutores = constructorXMLAutores.newDocument();
			
			Element autores = (Element) xmlAutores.createElement("autores");

			for (int i = 0; i < listaAutores.size(); i++) {
				Element nuevoAutor = (Element) xmlAutores.createElement(etiquetasAutor[0]);
				nuevoAutor.setAttribute(etiquetasAutor[1], String.valueOf(listaAutores.get(i).getId()));
				
				
				Element nombreAutor = (Element) xmlAutores.createElement(etiquetasAutor[2]);
				nombreAutor.appendChild(xmlAutores.createTextNode(listaAutores.get(i).getNombre()));
				nuevoAutor.appendChild(nombreAutor);

				Element nacionalidadAutor = (Element) xmlAutores.createElement(etiquetasAutor[3]);
				nacionalidadAutor.appendChild(xmlAutores.createTextNode(listaAutores.get(i).getNacionalidad()));
				nuevoAutor.appendChild(nacionalidadAutor);

				Element anioNacimientoAutor = (Element) xmlAutores.createElement(etiquetasAutor[4]);
				anioNacimientoAutor.appendChild(
						xmlAutores.createTextNode(String.valueOf(listaAutores.get(i).getAnioNacimiento())));
				nuevoAutor.appendChild(anioNacimientoAutor);
				
				autores.appendChild(nuevoAutor);

			}
			
			xmlAutores.appendChild(autores);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(xmlAutores);
			StreamResult result = new StreamResult(new File(xmlAutor));
			transformer.transform(source, result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}