package xml;

import processing.core.PApplet;
import processing.data.XML;

public class MensajeXML {

	/**
	 * Clase encargada de control de mensajes
	 */
	private PApplet app;
	private XML usuarios;

	public MensajeXML(PApplet app) {
		this.app = app;

		try {
			usuarios = app.loadXML("data/BD_mensajes.xml");
		} catch (Exception e) {
			usuarios = app.parseXML("<mensajes></mensajes>");
		}
	}

	/**
	 * Agregar mensajes al xml
	 * 
	 * @param usuario
	 *            usuario que postea
	 * @param mensaje
	 *            mensaje enviado
	 */
	public void agregarMensaje(String usuario, String mensaje) {
		XML hijo = app.parseXML("<mensaje usuario=\"" + usuario + "\">" + mensaje + "</mensaje>");
		usuarios.addChild(hijo);
		app.saveXML(usuarios, "data/BD_mensajes.xml");
	}

}
