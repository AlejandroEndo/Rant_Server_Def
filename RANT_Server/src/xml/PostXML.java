package xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.data.XML;
import serializable.Post;

public class PostXML {

	/**
	 * Control XML para posts
	 */

	private PApplet app;
	private XML posts;

	public PostXML(PApplet app) {
		this.app = app;
		try {
			posts = app.loadXML("data/xml/BD_post.xml");
		} catch (Exception e) {
			posts = app.parseXML("<post></post>");
		}
	}

	/**
	 * Metodo agregar posts al xml
	 * 
	 * @param nombre
	 *            Nombre del usuario
	 * @param descripcion
	 *            Informacion del post
	 * @param fecha
	 *            Fecha de publicacion del post
	 * @param rutaImagen
	 *            url de la imagen
	 * @return true
	 */
	public boolean agregarPost(String nombre, String descripcion, String fecha, String rutaImagen) {
		XML hijo = app.parseXML("<post nombreUsuario=\"" + nombre + " \" descripcion=\"" + descripcion + "\" ruta=\""
				+ rutaImagen + "\" fecha=\"" + fecha + "\"></post>");
		posts.addChild(hijo);
		app.saveXML(posts, "data/xml/BD_post.xml");
		return true;
	}

	/**
	 * Metodo para leer xml y pasar un ArrayList a los post del server
	 * 
	 * @return ArrayList de Posts
	 */
	public ArrayList<Post> initPost() {
		XML[] hijos = posts.getChildren("post");
		ArrayList<Post> posteo = new ArrayList<>();
		for (int i = 0; i < hijos.length; i++) {
			File fil = new File(hijos[i].getString("ruta"));
			byte[] imgSer = null;
			;
			String nombreImagen = fil.getName();
			try {
				byte[] b = new byte[(int) fil.length()];
				FileInputStream fileInputStream = new FileInputStream(fil);
				fileInputStream.read(b);
				fileInputStream.close();
				imgSer = b;
			} catch (IOException e) {
				e.printStackTrace();
			}

			Post posteado = new Post(hijos[i].getString("nombreUsuario"), hijos[i].getString("fecha"),
					hijos[i].getString("descripcion"), imgSer, nombreImagen);
			posteo.add(posteado);

		}
		return posteo;
	}
}
