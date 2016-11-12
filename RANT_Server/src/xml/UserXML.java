package xml;

import processing.core.PApplet;
import processing.data.XML;

public class UserXML {

	/**
	 * Clase encargada de validar usuario y realizar registro
	 */
	private PApplet app;
	private XML usuarios;

	public UserXML(PApplet app) {
		this.app = app;
		try {
			usuarios = app.loadXML("data/xml/BD_usuarios.xml");
		} catch (Exception e) {
			usuarios = app.parseXML("<usuarios></usuarios>");
		}
	}

	/**
	 * Agregar usuario nuevo
	 * 
	 * @param usuario
	 *            Nombre usuario
	 * @param contrasena
	 *            Contraseña usuario
	 * @return
	 */
	public boolean agregarUsuario(String usuario, String contrasena) {
		boolean existe = false;
		boolean agregado = false;

		XML[] hijos = usuarios.getChildren("usuario");
		for (int i = 0; i < hijos.length; i++) {
			if (hijos[i].getString("usuario").equals(usuario)) {
				existe = true;
			}
		}
		if (!existe) {
			XML hijo = app
					.parseXML("<usuario usuario=\"" + usuario + "\" contrasena=\"" + contrasena + "\"></usuario>");
			usuarios.addChild(hijo);
			app.saveXML(usuarios, "data/xml/BD_usuarios.xml");
			agregado = true;
		}
		return agregado;
	}

	/**
	 * Validar si el usuario ya esta registrado
	 * 
	 * @param usuario
	 *            Nombre usuario
	 * @param contrasena
	 *            Contraseña usuario
	 * @return
	 */
	public int validarUsuario(String usuario, String contrasena) {
		int estadoUsuario = 0;
		XML[] hijos = usuarios.getChildren("usuario");
		for (int i = 0; i < hijos.length; i++) {
			if (hijos[i].getString("usuario").equals(usuario)) {
				if (hijos[i].getString("contasena").equals(contrasena)) {
					estadoUsuario = 1;
				} else {
					estadoUsuario = 2;
				}
			}
		}
		return estadoUsuario;
	}
}
