package principal.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Aula4_Exercicio1 extends JPanel{
	
	public static Socket cliente;
	public static ServerSocket servidor;
	
	public Aula4_Exercicio1(int telaLargura, int telaAltura) {
		setBounds(0,0,telaLargura,telaAltura);
		setBackground(Color.BLACK);
		setLayout(null);
		
		JLabel labelServidor = new JLabel();
		labelServidor.setText("Servidor");
		labelServidor.setBounds(0,0,telaLargura,telaAltura/3);
		labelServidor.setFont(new Font("Arial",Font.PLAIN,40));
		labelServidor.setForeground(Color.WHITE); 
		add(labelServidor);
		
		JButton buttonIniciarServidor = new JButton();
		buttonIniciarServidor = new JButton();
		buttonIniciarServidor.setBounds(0, (telaAltura/3)*1, telaLargura,telaAltura/3);
		buttonIniciarServidor.setText("Iniciar Servidor");
		buttonIniciarServidor.setFont(new Font("Arial",Font.PLAIN,20));
		buttonIniciarServidor.addActionListener(new ActionListener() {                   
            public void actionPerformed(ActionEvent e) { 
            	try {
            		Aula4_Exercicio1.servidor = new ServerSocket(1235);
            		System.out.println("Servidor iniciado: " + Aula4_Exercicio1.servidor.getLocalPort());
            		
                    Thread threadLeitora = new Thread(){                    	
                        @Override
                        public void run(){
                            try{
                            	Socket clienteConectado = Aula4_Exercicio1.servidor.accept(); //bloqueante
                                System.out.println("Servidor: cliente conectado (" + clienteConectado.getInetAddress().getHostAddress() + ":" + clienteConectado.getPort()+")");
                                DataInputStream dataInputStreamServidor = new DataInputStream(clienteConectado.getInputStream());
                                while(true){                                    
                                    
                                    String mensagemCliente = dataInputStreamServidor.readUTF();//aguarda uma mensagem -> bloqueante
                                    System.out.println("Servidor: Mensagem recebida do cliente: " + mensagemCliente);
                                    
                                    String dados[] = mensagemCliente.split(";");                                        
                                    float resultado = 0;
                                    
                                    if(dados[0].equalsIgnoreCase("sacar")) {
                                    	 
                                    }
                                    
                                    if(dados[0].equalsIgnoreCase("depositar")) {
                                    	
                                    }
                                    
                                    if(dados[0].equalsIgnoreCase("consultarSaldo")) {
                                    	  
                                    }     
                                    
                                    //envio                                        
                                    DataOutputStream dataOutputStreamServidor = new DataOutputStream(clienteConectado.getOutputStream());
                                    dataOutputStreamServidor.writeUTF("Saldo atual:");//envia uma mensagem ao cliente
                                }                                                                                  
                            }catch(IOException eIO){
                            	System.out.println("Servidor: Erro ao receber/enviar mensagem: " + eIO.getMessage());
                            }catch(OutOfMemoryError eOOM) {
                            	System.out.println("Erro de saída na memória: " + eOOM.getMessage());
                            }
                        }
                    };       
                        
                    threadLeitora.start();

                } catch (IOException ex) {
                    System.out.println("Servidor: Porta TCP já está sendo utilizada");
                }
            } 
        });
		add(buttonIniciarServidor);
		
		JButton buttonEncerrarServidor = new JButton();
		buttonEncerrarServidor = new JButton();
		buttonEncerrarServidor.setBounds(0, (telaAltura/3)*2, telaLargura,telaAltura/3);
		buttonEncerrarServidor.setText("Encerrar Servidor");
		buttonEncerrarServidor.setFont(new Font("Arial",Font.PLAIN,20));
		buttonEncerrarServidor.addActionListener(new ActionListener() {                   
            public void actionPerformed(ActionEvent e) { 
            	try {            		
            		Aula4_Exercicio1.servidor.close();        
                } catch (IOException ex) {
                    System.out.println("Servidor: Falha ao encerrar o servidor");
                }
            } 
        });
		add(buttonEncerrarServidor);
	}
	
}