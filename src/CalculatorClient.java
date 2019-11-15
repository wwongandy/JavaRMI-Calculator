/**
 * This is the RMI client that connects to the remote Calculator instance in the server.
 */

import java.awt.EventQueue;
import java.rmi.Naming;

import javax.swing.JFrame;

public class CalculatorClient {

	private JFrame frame;
	private Calculator calculator;
	private String[] calculatorHistory; // Most recently selected numbers and operations

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalculatorClient client = new CalculatorClient();
					client.frame.setVisible(true);
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CalculatorClient() {
		calculatorHistory = new String[3]; // [number, operation, number]
		
		initialize();
		getCalculatorHandler();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Retrieves the calculator handler instance via RMI.
	 */
	private void getCalculatorHandler() {
		try {
			calculator = (Calculator) Naming.lookup("//localhost/CalculatorServer");
			System.out.println("Retrieved calculator instance binded in CalculatorServer");
		} catch (Exception e) {
			System.out.println("Client error: " + e);
			// e.printStackTrace();
		}
	};
	
	/**
	 * Triggered when a number button in the calculator is pressed.
	 * Adds it to the calculator history for usage in operation later.
	 * 
	 * @param number
	 */
	private void numberPressed(String number) {
		switch(calculatorHistory.length) {
			case 3:
				calculatorHistory[2] += number.toString();
				break;
				
			default:
				calculatorHistory[0] += number.toString();
				break;
		}
	}
	
	/**
	 * Triggered when an operation button in the calculator is pressed.
	 * Adds it to the calculator history for usage in operation later.
	 * 
	 * @param operation
	 */
	private void operationPressed(String operation) {
		switch(calculatorHistory.length) {
			case 3:
				break;
		
			case 1:
				if (calculatorHistory[0].isEmpty()) {
					break;
				}
		
			default:
				calculatorHistory[1] = operation;
		}
	}
}
