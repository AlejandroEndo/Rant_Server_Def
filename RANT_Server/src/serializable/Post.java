package serializable;

import java.io.Serializable;

public class Post implements Serializable {

	/**
	 * Clase serializable para envio de posts
	 */
	private static final long serialVersionUID = 1L;

	public String name;
	public String fecha;
	public String contenidoPost;
	public byte[] image;
	public String nameImage;
	public String urlImage;

	/**
	 * Constructor complejo de post
	 * 
	 * @param name
	 * @param fecha
	 * @param contenidoPost
	 * @param image
	 * @param nameImage
	 */
	public Post(String name, String fecha, String contenidoPost, byte[] image, String nameImage) {
		this.name = name;
		this.fecha = fecha;
		this.contenidoPost = contenidoPost;
		this.image = image;
		this.nameImage = nameImage;
	}

	/**
	 * Constructor simplificado de post
	 * 
	 * @param name
	 * @param fecha
	 * @param contenidoPost
	 * @param urlImage
	 */
	public Post(String name, String fecha, String contenidoPost, String urlImage) {
		this.name = name;
		this.fecha = fecha;
		this.contenidoPost = contenidoPost;
		this.urlImage = urlImage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getContenidoPost() {
		return contenidoPost;
	}

	public void setContenidoPost(String contenidoPost) {
		this.contenidoPost = contenidoPost;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getNameImage() {
		return nameImage;
	}

	public void setNameImage(String nameImage) {
		this.nameImage = nameImage;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

}
