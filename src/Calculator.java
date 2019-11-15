/**
 * This class provides the interface functions to be implemented by the Server.
 * These functions relate to operations of a Calculator, i.e. add, minus, multiply, divide.
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {
	int add(int num1, int num2) throws RemoteException;
	int minus(int num1, int num2) throws RemoteException;
	int multiply(int num1, int num2) throws RemoteException;
	int divide(int num1, int num2) throws RemoteException;
}
