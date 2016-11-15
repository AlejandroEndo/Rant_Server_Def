package ejecutable;

import java.net.InetAddress;
import java.net.UnknownHostException;

import processing.core.PApplet;

public class MainRant extends PApplet {

	/**
	 * Server realizado en compañia de Karlos Vallejo y ejemplos proporcionados en clase.
	 */
	private static final long serialVersionUID = 1L;
	
	private Server server;
	
	private InetAddress ip;

	public static void main(String[] args) {
		PApplet.main("ejecutable.MainRant");
	}
	
	@Override
	public void setup() {
		server = new Server(this);
		server.start();
		
		try {
			ip = InetAddress.getLocalHost();
			System.out.println(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
