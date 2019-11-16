/**
 * This is the RMI client that connects to the remote Calculator instance in the server.
 */

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JButton;

public class CalculatorClient {

	// Swing GUI management
	private JFrame frame;
	private JTextPane inputOutputScreen;
	private JTextPane consoleScreen;
	
	// Calculator management
	private Calculator calculator;
	private String[] calculatorHistory; // Most recently selected numbers and operations: [number, operation, number]
	
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
		clearCalculatorHistory();
		initialize();
		getCalculatorHandler();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 210, 370);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		inputOutputScreen = new JTextPane();
		inputOutputScreen.setEditable(false);
		inputOutputScreen.setBounds(10, 10, 180, 80);
		frame.getContentPane().add(inputOutputScreen);
		
		consoleScreen = new JTextPane();
		consoleScreen.setEditable(false);
		consoleScreen.setBounds(10, 250, 180, 80);
		frame.getContentPane().add(consoleScreen);
		
		JButton buttonDivide = new JButton("/");
		buttonDivide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonDivide.setBounds(10, 90, 45, 40);
		frame.getContentPane().add(buttonDivide);
		
		JButton buttonMultiply = new JButton("*");
		buttonMultiply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonMultiply.setBounds(10, 130, 45, 40);
		frame.getContentPane().add(buttonMultiply);
		
		JButton buttonMinus = new JButton("-");
		buttonMinus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonMinus.setBounds(10, 170, 45, 40);
		frame.getContentPane().add(buttonMinus);
		
		JButton buttonAdd = new JButton("+");
		buttonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonAdd.setBounds(10, 210, 45, 40);
		frame.getContentPane().add(buttonAdd);
		
		JButton buttonSeven = new JButton("7");
		buttonSeven.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonSeven.setBounds(55, 90, 45, 40);
		frame.getContentPane().add(buttonSeven);
		
		JButton buttonEight = new JButton("8");
		buttonEight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonEight.setBounds(100, 90, 45, 40);
		frame.getContentPane().add(buttonEight);
		
		JButton buttonNine = new JButton("9");
		buttonNine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonNine.setBounds(145, 90, 45, 40);
		frame.getContentPane().add(buttonNine);
		
		JButton buttonFour = new JButton("4");
		buttonFour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonFour.setBounds(55, 130, 45, 40);
		frame.getContentPane().add(buttonFour);
		
		JButton buttonFive = new JButton("5");
		buttonFive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonFive.setBounds(100, 130, 45, 40);
		frame.getContentPane().add(buttonFive);
		
		JButton buttonSix = new JButton("6");
		buttonSix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonSix.setBounds(145, 130, 45, 40);
		frame.getContentPane().add(buttonSix);
		
		JButton buttonOne = new JButton("1");
		buttonOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonOne.setBounds(55, 170, 45, 40);
		frame.getContentPane().add(buttonOne);
		
		JButton buttonTwo = new JButton("2");
		buttonTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonTwo.setBounds(100, 170, 45, 40);
		frame.getContentPane().add(buttonTwo);
		
		JButton buttonThree = new JButton("3");
		buttonThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonThree.setBounds(145, 170, 45, 40);
		frame.getContentPane().add(buttonThree);
		
		JButton buttonZero = new JButton("0");
		buttonZero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonZero.setBounds(55, 210, 45, 40);
		frame.getContentPane().add(buttonZero);
		
		JButton buttonSubmit = new JButton("Submit");
		buttonSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonSubmit.setBounds(100, 210, 90, 40);
		frame.getContentPane().add(buttonSubmit);
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
			
			System.exit(0);
		}
	};
	
	/**
	 * Performs the calculation process using the RMI instance's calculator.
	 */
	private void doCalculation() {
		if (calculatorHistory.length != 3) {
			return;
		};
		
		float num1 = Float.parseFloat(calculatorHistory[0]);
		float num2 = Float.parseFloat(calculatorHistory[2]);
		
		try {
			float result = 0;
			
			switch (calculatorHistory[1]) {
				case "+":
					result = calculator.add(num1, num2);
					break;
					
				case "-":
					result = calculator.minus(num1, num2);
					break;
					
				case "*":
					result = calculator.multiply(num1, num2);
					break;
					
				case "/":
					result = calculator.divide(num1, num2);
					break;
			}
			
			System.out.println("Result calculated: " + result);
		} catch (RemoteException e) {
			System.out.println("Client error " + e);
			// e.printStackTrace("Client error: " + e);
		}
	}
	
	/**
	 * Resets the calculator history.
	 */
	private void clearCalculatorHistory() { 
		calculatorHistory = new String[3];
	}
	
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
