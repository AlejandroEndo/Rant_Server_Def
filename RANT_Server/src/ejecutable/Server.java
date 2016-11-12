package ejecutable;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import serializable.Post;
import xml.ControlCliente;
import xml.UserXML;
import xml.MensajeXML;
import xml.PostXML;

public class Server extends Thread implements Observer {

	/**
	 * Logica del servidor
	 */
	private ServerSocket ss;

	// ArrayList para almacenar registro de todos los clientes.
	private ArrayList<ControlCliente> clientes = new ArrayList<>();

	// Clases contenedoras de xml
	private MensajeXML xmlMensaje;
	private PostXML xmlPost;
	private UserXML xmlUser;

	// ArrayList para posts
	private ArrayList<Post> posteos = new ArrayList<>();

	// Puerto a conectarse
	private final static int PORT = 5000;

	public Server(PApplet app) {
		xmlPost = new PostXML(app);
		xmlMensaje = new MensajeXML(app);
		xmlUser = new UserXML(app);

		posteos = xmlPost.initPost();

		try {
			ss = new ServerSocket(PORT);
			System.out.println("SERVIDOR INICIADO EN <" + ss.toString() + ">" + ss.getInetAddress().getHostAddress());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				// Se asignan los nuevos clientes a un nuevo hilo
				System.out.println("ESPERANDO CLIENE");
				clientes.add(new ControlCliente(ss.accept(), this));
				sleep(100);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Clase para ir creando carpetas por usuario,
	 * @param usuario usuario posteando
	 */
	private void comprobarDirectorio(String usuario) {
		File file = new File("data/xml/" + usuario);
		boolean isDirectoryCreated = file.exists();

		if (!isDirectoryCreated) {
			isDirectoryCreated = file.mkdirs();
			System.out.println("INFORMACION USUARIO <" + usuario + "> NO SE ENCUENTRAN, CREANDO DIRECTORIO...");
		}
		if (isDirectoryCreated) {
			System.out.println("INFORMACION USUARIO <" + usuario + "> ENCONTRADA");
		}
	}

	/**
	 * Metodo update del Observer. 
	 */
	@Override
	public void update(Observable observado, Object mensaje) {
		if (mensaje instanceof String) {
			String notificacion = (String) mensaje;
			if (notificacion.contains("login_req:")) {
				String[] partes = notificacion.split(":");
				int resultadoLogin = xmlUser.validarUsuario(partes[1], partes[2]);
				((ControlCliente) observado).enviarMensaje("login_resp:" + resultadoLogin);
				if (resultadoLogin == 1) {
					comprobarDirectorio(partes[1]);
				}
			}
			if (notificacion.contains("signup_req:")) {
				String[] partes = notificacion.split(":");
				boolean resultadoAgregar = xmlUser.agregarUsuario(partes[1], partes[2]);
				((ControlCliente) observado).enviarMensaje("signup_resp:" + (resultadoAgregar == true ? 1 : 0));
			}
			if (notificacion.contains("post_req")) {

				((ControlCliente) observado).enviarMensaje(posteos);
			}
			if (notificacion.contains("cliente_no_disponible")) {
				clientes.remove(observado);
				System.out.println("SE HA IDO UN CLIENTE, QUEDAN <" + clientes.size() + ">");
			}
			//
			if (notificacion.contains("mensaje_send:")) {
				for (Iterator<ControlCliente> iterator = clientes.iterator(); iterator.hasNext();) {
					ControlCliente controlCliente = iterator.next();
					controlCliente.enviarMensaje(notificacion);
					String[] partes = notificacion.split(":");
					xmlMensaje.agregarMensaje("NA", partes[1]);
				}
			}
		} else if (mensaje instanceof Post) {
			Post posti = (Post) mensaje;
			if (xmlPost.agregarPost(posti.name, posti.contenidoPost, posti.fecha, posti.urlImage)) {
				posteos.add(posti);
			}
		}
	}

}
