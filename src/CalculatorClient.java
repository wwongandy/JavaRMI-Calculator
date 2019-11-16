/**
 * This is the RMI client that connects to the remote Calculator instance in the server.
 */

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class CalculatorClient {

	// Swing GUI management
	private JFrame frame;
	private JTextPane inputOutputScreen;
	private JTextArea consoleScreen;
	
	// Calculator management
	private Calculator calculator;
	private ArrayList<String> calculatorHistory; // Most recently selected numbers and operations: [number, operation, number]
	
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
		
		JButton buttonDivide = new JButton("/");
		buttonDivide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operationPressed("/");
			}
		});
		buttonDivide.setBounds(10, 90, 45, 40);
		frame.getContentPane().add(buttonDivide);
		
		JButton buttonMultiply = new JButton("*");
		buttonMultiply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operationPressed("*");
			}
		});
		buttonMultiply.setBounds(10, 130, 45, 40);
		frame.getContentPane().add(buttonMultiply);
		
		JButton buttonMinus = new JButton("-");
		buttonMinus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operationPressed("-");
			}
		});
		buttonMinus.setBounds(10, 170, 45, 40);
		frame.getContentPane().add(buttonMinus);
		
		JButton buttonAdd = new JButton("+");
		buttonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operationPressed("+");
			}
		});
		buttonAdd.setBounds(10, 210, 45, 40);
		frame.getContentPane().add(buttonAdd);
		
		JButton buttonSeven = new JButton("7");
		buttonSeven.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numberPressed("7");
			}
		});
		buttonSeven.setBounds(55, 90, 45, 40);
		frame.getContentPane().add(buttonSeven);
		
		JButton buttonEight = new JButton("8");
		buttonEight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numberPressed("8");
			}
		});
		buttonEight.setBounds(100, 90, 45, 40);
		frame.getContentPane().add(buttonEight);
		
		JButton buttonNine = new JButton("9");
		buttonNine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numberPressed("9");
			}
		});
		buttonNine.setBounds(145, 90, 45, 40);
		frame.getContentPane().add(buttonNine);
		
		JButton buttonFour = new JButton("4");
		buttonFour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numberPressed("4");
			}
		});
		buttonFour.setBounds(55, 130, 45, 40);
		frame.getContentPane().add(buttonFour);
		
		JButton buttonFive = new JButton("5");
		buttonFive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numberPressed("5");
			}
		});
		buttonFive.setBounds(100, 130, 45, 40);
		frame.getContentPane().add(buttonFive);
		
		JButton buttonSix = new JButton("6");
		buttonSix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numberPressed("6");
			}
		});
		buttonSix.setBounds(145, 130, 45, 40);
		frame.getContentPane().add(buttonSix);
		
		JButton buttonOne = new JButton("1");
		buttonOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numberPressed("1");
			}
		});
		buttonOne.setBounds(55, 170, 45, 40);
		frame.getContentPane().add(buttonOne);
		
		JButton buttonTwo = new JButton("2");
		buttonTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numberPressed("2");
			}
		});
		buttonTwo.setBounds(100, 170, 45, 40);
		frame.getContentPane().add(buttonTwo);
		
		JButton buttonThree = new JButton("3");
		buttonThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numberPressed("3");
			}
		});
		buttonThree.setBounds(145, 170, 45, 40);
		frame.getContentPane().add(buttonThree);
		
		JButton buttonZero = new JButton("0");
		buttonZero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numberPressed("0");
			}
		});
		buttonZero.setBounds(55, 210, 45, 40);
		frame.getContentPane().add(buttonZero);
		
		JButton buttonSubmit = new JButton("Submit");
		buttonSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doCalculation();
			}
		});
		buttonSubmit.setBounds(100, 210, 90, 40);
		frame.getContentPane().add(buttonSubmit);
		
		consoleScreen = new JTextArea();
		consoleScreen.setEditable(false);
		consoleScreen.setBounds(10, 250, 180, 80);
		
		JScrollPane scrollPane = new JScrollPane(consoleScreen);
		scrollPane.setBounds(10, 250, 180, 80);
		frame.getContentPane().add(scrollPane);
	}
	
	/**
	 * Retrieves the calculator handler instance via RMI.
	 */
	private void getCalculatorHandler() {
		try {
			calculator = (Calculator) Naming.lookup("//localhost/CalculatorServer");
			
			System.out.println("Retrieved calculator instance binded in CalculatorServer");
			consoleScreen.append("Retrieved calculator instance binded in CalculatorServer\n");
		} catch (Exception e) {
			System.out.println("Client error: " + e);
			consoleScreen.append("Client error: " + e + "\n");
			// e.printStackTrace();
			
			System.exit(0);
		}
	};
	
	/**
	 * Displays the current calculator history onto the input/output screen and logs any changes to the console.
	 */
	private void updateInputOutputScreens() {
		
		String resultString = "";
		for (String s : calculatorHistory) {
			resultString += s + " ";
		}
		
		inputOutputScreen.setText(resultString);
		consoleScreen.append(resultString + "\n");
	}
	
	/**
	 * Displays the given calculator result onto the input/output screen and console.
	 * 
	 * @param result
	 */
	private void updateInputOutputScreens(String resultString) {
		inputOutputScreen.setText(resultString);
		consoleScreen.append(resultString + "\n");
	}
	
	/**
	 * Performs the calculation process using the RMI instance's calculator.
	 */
	private void doCalculation() {
		
		ArrayList<String> _calculatorHistory = (ArrayList<String>) calculatorHistory.clone();
		
		if (_calculatorHistory.size() == 0) {
			consoleScreen.append("No input given\n");
			return;
		};
		
		try {
			// MDAS (Multiply, divide, addition, subtract)
			String[] operations = {"*", "/", "+", "-"};
			
			for (int i = 0; i < operations.length; i++) {
				String currentOperation = operations[i];
				int nextIndex = _calculatorHistory.indexOf(currentOperation);
				
				while (nextIndex != -1) {
			
					// Get the two numbers for the operation (operation - 1, operation + 1)
					if (nextIndex != _calculatorHistory.size() - 1) {
						float num1 = Float.parseFloat(_calculatorHistory.get(nextIndex - 1));
						float num2 = Float.parseFloat(_calculatorHistory.get(nextIndex + 1));
						float result = 0;
						
						// Perform appropriate calculation
						switch(currentOperation) {
							case "*":
								result = calculator.multiply(num1, num2);
								break;
								
							case "/":
								result = calculator.divide(num1, num2);
								break;
								
							case "+":
								result = calculator.add(num1, num2);
								break;
								
							case "-":
								result = calculator.minus(num1, num2);
								break;
						}
						
						// Replace with resulting value
						_calculatorHistory.remove(nextIndex + 1);
						_calculatorHistory.remove(nextIndex);
						_calculatorHistory.set(nextIndex - 1, Float.toString(result));
					} else {
						_calculatorHistory.remove(nextIndex);
					};
					
					nextIndex = _calculatorHistory.indexOf(currentOperation);
				}
			}
			
			updateInputOutputScreens(_calculatorHistory.get(0));
			clearCalculatorHistory();
		} catch (RemoteException e) {
			System.out.println("Client error: " + e);
			// e.printStackTrace();
		}
	}
	
	/**
	 * Resets the calculator history.
	 */
	private void clearCalculatorHistory() {
		calculatorHistory = new ArrayList<String>();
	}
	
	/**
	 * Triggered when a number button in the calculator is pressed.
	 * Adds it to the calculator history for usage in operation later.
	 * 
	 * @param number
	 */
	private void numberPressed(String number) {
		
		if (calculatorHistory.isEmpty()) {
			calculatorHistory.add(number);
		} else {
			
			int size = calculatorHistory.size();
			int lastIndex = size - 1;
			String recent = calculatorHistory.get(lastIndex);
			
			if (recent.equals("+") || recent.equals("-") || recent.equals("*") || recent.equals("/")) {
				calculatorHistory.add(number);
			} else {
				calculatorHistory.set(lastIndex, recent + "" + number);
			}
		};
		
		updateInputOutputScreens();
	}
	
	/**
	 * Triggered when an operation button in the calculator is pressed.
	 * Adds it to the calculator history for usage in operation later.
	 * 
	 * @param operation
	 */
	private void operationPressed(String operation) {
		if (calculatorHistory.isEmpty()) {
			return;
		} else {
			
			int size = calculatorHistory.size();
			int lastIndex = size - 1;
			String recent = calculatorHistory.get(lastIndex);
			
			if (!(recent.equals("+") || recent.equals("-") || recent.equals("*") || recent.equals("/"))) {
				calculatorHistory.add(operation);
			}
		};
		
		updateInputOutputScreens();
	}
}
