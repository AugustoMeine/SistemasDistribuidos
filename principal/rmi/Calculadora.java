package principal.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Calculadora extends UnicastRemoteObject implements ICalculadora{

	protected Calculadora() throws RemoteException {
		super();
	}

	@Override
	public float somar(float a, float b) throws RemoteException {
		return(a + b);
	}

	@Override
	public float subtrair(float a, float b) throws RemoteException {
		return(a - b);
	}

	@Override
	public float multiplicar(float a, float b) throws RemoteException {
		return(a * b);
	}

	@Override
	public float dividir(float a, float b) throws RemoteException {
		return(a / b);
	}

	@Override
	public int calcularCaracteresString(String palavra) throws RemoteException {
		int quantidadeCaracteres = 0;
		
		for(char aux : palavra.toCharArray()) {
			quantidadeCaracteres++;
		}
		
		return(quantidadeCaracteres);
	}
	
	
}
