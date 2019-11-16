/**
 * This class implements the Calculator interface.
 * This is the RMI server that provides the remote Calculator instance to be used by the client.
 */

import java.awt.EventQueue;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class CalculatorServer extends UnicastRemoteObject implements Calculator {

	private JFrame frame;
	private JTextArea consoleScreen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalculatorServer server = new CalculatorServer();
					server.frame.setVisible(true);
				} catch (Exception e) {
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
		bindToRegistry();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		consoleScreen = new JTextArea();
		consoleScreen.setEditable(false);
		consoleScreen.setBounds(5, 5, 430, 250);
		frame.getContentPane().add(consoleScreen);
	}
	
	/**
	 * Bind this class' instance to the LocalHost registry, so it can be usable via RMI
	 */
	private void bindToRegistry() {
		try {
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind("CalculatorServer", this);

			System.out.println("CalculatorServer bound in registry");
			consoleScreen.append("CalculatorServer bound in registry\n");
		} catch (Exception e) {
			System.out.println("Server error: " + e);
			consoleScreen.append("Server error: " + e);
			// e.printStackTrace();
		}
	}

	@Override
	public float add(float num1, float num2) throws RemoteException {
		consoleScreen.append(num1 + " + " + num2);
		return num1 + num2;
	}

	@Override
	public float minus(float num1, float num2) throws RemoteException {
		consoleScreen.append(num1 + " - " + num2);
		return num1 - num2;
	}

	@Override
	public float multiply(float num1, float num2) throws RemoteException {
		consoleScreen.append(num1 + " * " + num2);
		return num1 * num2;
	}

	@Override
	public float divide(float num1, float num2) throws RemoteException {
		consoleScreen.append(num1 + " / " + num2);
		return num1 / num2;
	};
}
