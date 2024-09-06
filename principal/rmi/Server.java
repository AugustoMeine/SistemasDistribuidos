package principal.rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

	String HOST_URL = "rmi://localhost/calculadora";
	
	public Server(){
		try {
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			Calculadora calculadora = new Calculadora();
			Naming.bind(HOST_URL, calculadora);
		}catch(RemoteException eR) {
			System.out.println("Erro vinculado a conexão do servidor: " + eR.getMessage());
		}catch(AlreadyBoundException eAB) {
			System.out.println("Erro no bound: " + eAB.getMessage());
		}catch(MalformedURLException eMU) {
			System.out.println("Erro na URL:" + eMU.getMessage());
		}
	}
	
}
