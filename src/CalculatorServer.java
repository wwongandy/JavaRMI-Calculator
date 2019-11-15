/**
 * This class implements the Calculator interface.
 * This is the RMI server that provides the remote Calculator instance to be used by the client.
 */

import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JFrame;

public class CalculatorServer extends UnicastRemoteObject implements Calculator {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalculatorServer server = new CalculatorServer();
					server.frame.setVisible(true);

					// Bind this class' instance to the LocalHost registry, so it can be usable via RMI
					Registry registry = LocateRegistry.createRegistry(1099);
					registry.rebind("CalculatorServer", server);
					System.out.println("CalculatorServer bound in registry");
				} catch (Exception e) {
					System.out.println("Server error: " + e);
					// e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CalculatorServer() throws RemoteException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
