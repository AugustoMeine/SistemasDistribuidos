package principal.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Cliente {
	
	ICalculadora calculadora;
	
	public Cliente() {
		try {
			this.calculadora = (ICalculadora) Naming.lookup("rmi://localhost/calculadora");
			float a = 1;
			float b = 2;
			String textoEntrada = "Teste Teste";
			
			
			System.out.println("Numero a: " + a + "   Numero b: " + b);
			System.out.println("Soma: " + this.calculadora.somar(a, b));
			System.out.println("Subtracao: " + this.calculadora.subtrair(a, b));
			System.out.println("Multiplicacao: " + this.calculadora.multiplicar(a, b));
			System.out.println("Divisao: " + this.calculadora.dividir(a, b));
			System.out.println("Texto: " + textoEntrada);
			System.out.println("Quantidade de caracteres: " + this.calculadora.calcularCaracteresString(textoEntrada));
		}catch(RemoteException eR) {
			System.out.println("Erro vinculado a conexão do servidor: " + eR.getMessage());
		}catch(MalformedURLException eMU) {
			System.out.println("Erro na URL:" + eMU.getMessage());
		}catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
