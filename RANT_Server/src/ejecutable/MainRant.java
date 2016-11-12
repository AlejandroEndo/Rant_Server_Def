package ejecutable;

import processing.core.PApplet;

public class MainRant extends PApplet {

	/**
	 * Server realizado en compañia de Karlos Vallejo y ejemplos proporcionados en clase.
	 */
	private static final long serialVersionUID = 1L;
	
	private Server server;

	public static void main(String[] args) {
		PApplet.main("ejecutable.MainRant");
	}

	@Override
	public void setup() {
		server = new Server(this);
		server.start();
	}

}
