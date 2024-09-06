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

public class Aula3_Exercicio3 extends JPanel{
	
	public static Socket cliente;
	public static ServerSocket servidor;
	public static float saldo = 0;
	
	public Aula3_Exercicio3(int telaLargura, int telaAltura) {
		setBounds(0,0,telaLargura,telaAltura);
		setBackground(Color.BLACK);
		setLayout(null);
		
		JLabel labelServidor = new JLabel();
		labelServidor.setText("Servidor");
		labelServidor.setBounds(0,0,telaLargura/2,telaAltura/4);
		labelServidor.setFont(new Font("Arial",Font.PLAIN,40));
		labelServidor.setForeground(Color.WHITE); 
		add(labelServidor);
		
		JButton buttonIniciarServidor = new JButton();
		buttonIniciarServidor = new JButton();
		buttonIniciarServidor.setBounds(0, telaAltura/4, telaLargura/2,telaAltura/4);
		buttonIniciarServidor.setText("Iniciar Servidor");
		buttonIniciarServidor.setFont(new Font("Arial",Font.PLAIN,20));
		buttonIniciarServidor.addActionListener(new ActionListener() {                   
            public void actionPerformed(ActionEvent e) { 
            	try {
            		Aula3_Exercicio3.servidor = new ServerSocket(1235);
            		System.out.println("Servidor iniciado: " + Aula3_Exercicio3.servidor.getLocalPort());
            		
                    Thread threadLeitora = new Thread(){                    	
                        @Override
                        public void run(){
                            try{
                            	Socket clienteConectado = Aula3_Exercicio3.servidor.accept(); //bloqueante
                                System.out.println("Servidor: cliente conectado (" + clienteConectado.getInetAddress().getHostAddress() + ":" + clienteConectado.getPort()+")");
                                DataInputStream dataInputStreamServidor = new DataInputStream(clienteConectado.getInputStream());
                                while(true){                                    
                                    
                                    String mensagemCliente = dataInputStreamServidor.readUTF();//aguarda uma mensagem -> bloqueante
                                    System.out.println("Servidor: Mensagem recebida do cliente: " + mensagemCliente);
                                    
                                    String dados[] = mensagemCliente.split(";");                                        
                                    float resultado = 0;
                                    
                                    if(dados[0].equalsIgnoreCase("sacar")) {
                                    	Aula3_Exercicio3.saldo = Aula3_Exercicio3.saldo - Float.parseFloat(dados[1]); 
                                    }
                                    
                                    if(dados[0].equalsIgnoreCase("depositar")) {
                                    	Aula3_Exercicio3.saldo = Aula3_Exercicio3.saldo + Float.parseFloat(dados[1]); 
                                    }
                                    
                                    if(dados[0].equalsIgnoreCase("consultarSaldo")) {
                                    	//envio                                        
                                        DataOutputStream dataOutputStreamServidor = new DataOutputStream(clienteConectado.getOutputStream());
                                        dataOutputStreamServidor.writeUTF("Saldo atual:"+Aula3_Exercicio3.saldo);//envia uma mensagem ao cliente  
                                    }                                                                                                          
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
		
		JLabel labelCliente = new JLabel();
		labelCliente.setText("Servidor");
		labelCliente.setBounds((telaLargura/4)*2,0,telaLargura/4,telaAltura/8);
		labelCliente.setFont(new Font("Arial",Font.PLAIN,40));
		labelCliente.setForeground(Color.WHITE); 
		add(labelCliente);
		
		JButton buttonIniciarCliente = new JButton();
		buttonIniciarCliente = new JButton();
		buttonIniciarCliente.setBounds((telaLargura/4)*3, 0, telaLargura/4,telaAltura/8);
		buttonIniciarCliente.setText("Iniciar Cliente");
		buttonIniciarCliente.setFont(new Font("Arial",Font.PLAIN,20));
		buttonIniciarCliente.addActionListener(new ActionListener() {                   
            public void actionPerformed(ActionEvent e) { 
                try {
                    
                	Aula3_Exercicio3.cliente = new Socket("localhost", 1235); 
                    System.out.println("Cliente: Servidor localizado e conectado");
                    DataInputStream dataInputStreamCliente = new DataInputStream(Aula3_Exercicio3.cliente.getInputStream());
                    Thread threadLeitora = new Thread(){
                        @Override
                        public void run(){
                            try{
                                while(true){                                                                                                
                                    String resposta = dataInputStreamCliente.readUTF();                                    
                                    System.out.println("Cliente:Mensagem recebida do Servidor: "+resposta);                                                                                    
                                }                                                        
                            }catch(IOException eIO){
                            	System.out.println("Cliente:Erro ao receber/enviar mensagem: " + eIO.getMessage());
                            }  
                        }
                    }; 
                                            
                    threadLeitora.start();                    
                } catch (IOException ex) {
                    System.out.println("Cliente:Servidor nao encontrado");
                }
                
            } 
        });
		add(buttonIniciarCliente);
		
		JLabel labelValor = new JLabel();
		labelValor.setText("Valor: R$");
		labelValor.setBounds((telaLargura/4)*2,(telaAltura/8)*1,(telaLargura/8)*2,telaAltura/8);
		labelValor.setFont(new Font("Arial",Font.PLAIN,30));
		labelValor.setForeground(Color.WHITE); 
		add(labelValor);
		
		JTextField textFieldValor = new JTextField();
		textFieldValor.setText("");
		textFieldValor.setBounds((telaLargura/4)*3,(telaAltura/8)*1,(telaLargura/8)*2,telaAltura/8);
		textFieldValor.setFont(new Font("Arial",Font.PLAIN,20));
		add(textFieldValor);
		
		JButton buttonSaque = new JButton();
		buttonSaque = new JButton();
		buttonSaque.setBounds((telaLargura/4)*2, (telaAltura/8)*4, telaLargura/2,telaAltura/8);
		buttonSaque.setText("sacar");
		buttonSaque.setFont(new Font("Arial",Font.PLAIN,20));
		buttonSaque.addActionListener(new ActionListener() {                   
            public void actionPerformed(ActionEvent e) { 
            	try{
            		
                    DataOutputStream dataOutputStreamCliente = new DataOutputStream(Aula3_Exercicio3.cliente.getOutputStream());
                    dataOutputStreamCliente.writeUTF("sacar;"+textFieldValor.getText()); 
                }catch(IOException eIO){
                    System.out.println("Cliente: Erro ao enviar mensagem: " + eIO.getMessage());
                }
            } 
        });
		add(buttonSaque);
		
		JButton buttonDeposito = new JButton();
		buttonDeposito = new JButton();
		buttonDeposito.setBounds((telaLargura/4)*2, (telaAltura/8)*5, telaLargura/2,telaAltura/8);
		buttonDeposito.setText("depositar");
		buttonDeposito.setFont(new Font("Arial",Font.PLAIN,20));
		buttonDeposito.addActionListener(new ActionListener() {                   
            public void actionPerformed(ActionEvent e) { 
            	try{
                    DataOutputStream dos = new DataOutputStream(Aula3_Exercicio3.cliente.getOutputStream());
                    dos.writeUTF("depositar;"+textFieldValor.getText());                    
                }catch(IOException eIO){
                    System.out.println("Cliente: Erro ao enviar mensagem: " + eIO.getMessage());
                }
            } 
        });
		add(buttonDeposito);
		
		JButton buttonConsultarSaldo = new JButton();
		buttonConsultarSaldo = new JButton();
		buttonConsultarSaldo.setBounds((telaLargura/4)*2, (telaAltura/8)*6, telaLargura/2,telaAltura/8);
		buttonConsultarSaldo.setText("Consultar Saldo");
		buttonConsultarSaldo.setFont(new Font("Arial",Font.PLAIN,20));
		buttonConsultarSaldo.addActionListener(new ActionListener() {                   
            public void actionPerformed(ActionEvent e) { 
            	try{
                    DataOutputStream dos = new DataOutputStream(Aula3_Exercicio3.cliente.getOutputStream());
                    dos.writeUTF("consultarSaldo;");                    
                }catch(IOException eIO){
                    System.out.println("Cliente: Erro ao enviar mensagem: " + eIO.getMessage());
                }
            } 
        });
		add(buttonConsultarSaldo);	
		
	}	
	
}