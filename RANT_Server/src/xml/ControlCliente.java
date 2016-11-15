package xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Observable;
import java.util.Observer;
import serializable.Post;

public class ControlCliente extends Observable implements Runnable {

	/**
	 * Clase encargada de controlar lo que pasa con cada cliente nuevo
	 */
	private Socket s;
	private Observer jefe;
	private boolean disponible;

	public ControlCliente(Socket s, Observer jefe) {
		this.s = s;
		this.jefe = jefe;
		disponible = true;
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		while (disponible) {
			try {
				recibirMensaje();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Metodo para recibir mensajes
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void recibirMensaje() throws IOException, ClassNotFoundException, SocketException {
		ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
		Object o = ois.readObject();
		if (o instanceof Post) {
			Post p = (Post) o;
			System.out.println(p.getName());
			System.out.println(p.getContenidoPost());
			String ruta = guardarArchivo(p.getNameImage(), p.getImage(), p.getName());
			System.out.println("POST DE <" + p.getName() + ">");
			System.out.println("RUTA <" + ruta + ">");
			Post resumen = new Post(p.getName(), p.getFecha(), p.getContenidoPost(), ruta);
			jefe.update(this, resumen);
		} else if (o instanceof String) {
			String mensaje = (String) o;
			System.out.println("MENSAJE RECIBIDO <" + mensaje + ">");
			jefe.update(this, mensaje);
		}
		//ois.close();
	}

	/**
	 * Metodo para guardar los archivos recividos en el xml
	 * 
	 * @param nombre
	 * @param buf
	 * @param user
	 * @return null
	 * @throws IOException
	 */
	private String guardarArchivo(String user, byte[] buf, String nombre) throws IOException {
		try {
			File archivo = new File("data/xml/" + user + "/" + nombre);
			archivo.createNewFile();
			FileOutputStream salida = new FileOutputStream(archivo);
			salida.write(buf);

			salida.flush();
			salida.close();
			return archivo.getPath();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Metodo para enviar mensajes
	 * 
	 * @param mensaje
	 *            mensaje a enviar
	 */
	public void enviarMensaje(Object mensaje) {
		try {
			ObjectOutputStream ous = new ObjectOutputStream(s.getOutputStream());
			ous.writeObject(mensaje);
			System.out.println("MENSAJE ENVIADO <" + mensaje + ">");
			//ous.flush();
			// ous.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
