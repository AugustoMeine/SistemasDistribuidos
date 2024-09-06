package principal.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICalculadora extends Remote {
	public float somar(float a, float b) throws RemoteException;
	public float subtrair(float a, float b) throws RemoteException;
	public float multiplicar(float a, float b) throws RemoteException;
	public float dividir(float a, float b) throws RemoteException;
	public int calcularCaracteresString(String palavra) throws RemoteException;
}
