/**
 * This class provides the interface functions to be implemented by the Server.
 * These functions relate to operations of a Calculator, i.e. add, minus, multiply, divide.
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {
	float add(float num1, float num2) throws RemoteException;
	float minus(float num1, float num2) throws RemoteException;
	float multiply(float num1, float num2) throws RemoteException;
	float divide(float num1, float num2) throws RemoteException;
}
