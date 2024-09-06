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

public class Aula3_Exercicio2 extends JPanel{
	
	public static Socket cliente;
	public static ServerSocket servidor;
	
	public Aula3_Exercicio2(int telaLargura, int telaAltura) {
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
            		Aula3_Exercicio2.servidor = new ServerSocket(1235);
            		System.out.println("Servidor iniciado: " + Aula3_Exercicio2.servidor.getLocalPort());
            		
                    Thread threadLeitora = new Thread(){                    	
                        @Override
                        public void run(){
                            try{
                            	Socket clienteConectado = Aula3_Exercicio2.servidor.accept(); //bloqueante
                                System.out.println("Servidor: cliente conectado (" + clienteConectado.getInetAddress().getHostAddress() + ":" + clienteConectado.getPort()+")");
                                DataInputStream dataInputStreamServidor = new DataInputStream(clienteConectado.getInputStream());
                                while(true){                                    
                                    
                                    String mensagemCliente = dataInputStreamServidor.readUTF();//aguarda uma mensagem -> bloqueante
                                    System.out.println("Servidor: Mensagem recebida do cliente: " + mensagemCliente);
                                    
                                    String dados[] = mensagemCliente.split(";");                                        
                                    float resultado = 0;
                                    
                                    if(dados[0].equalsIgnoreCase("somar")) {
                                    	resultado = Float.parseFloat(dados[1]) + Float.parseFloat(dados[2]); 
                                    }
                                    
                                    if(dados[0].equalsIgnoreCase("subtrair")) {
                                    	resultado = Float.parseFloat(dados[1]) - Float.parseFloat(dados[2]); 
                                    }
                                    
                                    if(dados[0].equalsIgnoreCase("multiplicar")) {
                                    	resultado = Float.parseFloat(dados[1]) * Float.parseFloat(dados[2]); 
                                    }
                                    
                                    if(dados[0].equalsIgnoreCase("dividir")) {
                                    	resultado = Float.parseFloat(dados[1]) / Float.parseFloat(dados[2]); 
                                    }
                                    
                                    //envio                                        
                                    DataOutputStream dataOutputStreamServidor = new DataOutputStream(clienteConectado.getOutputStream());
                                    dataOutputStreamServidor.writeUTF(""+resultado);//envia uma mensagem ao cliente   
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
                    
                	Aula3_Exercicio2.cliente = new Socket("localhost", 1235); 
                    System.out.println("Cliente: Servidor localizado e conectado");
                    DataInputStream dataInputStreamCliente = new DataInputStream(Aula3_Exercicio2.cliente.getInputStream());
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
		
		JLabel primeiroNumero = new JLabel();
		primeiroNumero.setText("Primeiro Numero: ");
		primeiroNumero.setBounds((telaLargura/4)*2,(telaAltura/8)*1,(telaLargura/8)*2,telaAltura/8);
		primeiroNumero.setFont(new Font("Arial",Font.PLAIN,30));
		primeiroNumero.setForeground(Color.WHITE); 
		add(primeiroNumero);
		
		JTextField textFieldPrimeiroNumero = new JTextField();
		textFieldPrimeiroNumero.setText("");
		textFieldPrimeiroNumero.setBounds((telaLargura/4)*3,(telaAltura/8)*1,(telaLargura/8)*2,telaAltura/8);
		textFieldPrimeiroNumero.setFont(new Font("Arial",Font.PLAIN,20));
		add(textFieldPrimeiroNumero);
		
		JLabel segundoNumero = new JLabel();
		segundoNumero.setText("Segundo Numero: ");
		segundoNumero.setBounds((telaLargura/4)*2,(telaAltura/8)*2,(telaLargura/8)*2,telaAltura/8);
		segundoNumero.setFont(new Font("Arial",Font.PLAIN,30));
		segundoNumero.setForeground(Color.WHITE); 
		add(segundoNumero);
		
		JTextField textFieldSegundoNumero = new JTextField();
		textFieldSegundoNumero.setText("");
		textFieldSegundoNumero.setBounds((telaLargura/4)*3,(telaAltura/8)*2,(telaLargura/8)*2,telaAltura/8);
		textFieldSegundoNumero.setFont(new Font("Arial",Font.PLAIN,20));
		add(textFieldSegundoNumero);
		
		JButton buttonSomar = new JButton();
		buttonSomar = new JButton();
		buttonSomar.setBounds((telaLargura/4)*2, (telaAltura/8)*4, telaLargura/4,telaAltura/8);
		buttonSomar.setText("+");
		buttonSomar.setFont(new Font("Arial",Font.PLAIN,20));
		buttonSomar.addActionListener(new ActionListener() {                   
            public void actionPerformed(ActionEvent e) { 
            	try{
            		
                    DataOutputStream dataOutputStreamCliente = new DataOutputStream(Aula3_Exercicio2.cliente.getOutputStream());
                    dataOutputStreamCliente.writeUTF("somar;"+textFieldPrimeiroNumero.getText()+";"+textFieldSegundoNumero.getText()); 
                }catch(IOException eIO){
                    System.out.println("Cliente: Erro ao enviar mensagem: " + eIO.getMessage());
                }
            } 
        });
		add(buttonSomar);
		
		JButton buttonSubtrair = new JButton();
		buttonSubtrair = new JButton();
		buttonSubtrair.setBounds((telaLargura/4)*3, (telaAltura/8)*4, telaLargura/4,telaAltura/8);
		buttonSubtrair.setText("-");
		buttonSubtrair.setFont(new Font("Arial",Font.PLAIN,20));
		buttonSubtrair.addActionListener(new ActionListener() {                   
            public void actionPerformed(ActionEvent e) { 
            	try{
                    DataOutputStream dos = new DataOutputStream(Aula3_Exercicio2.cliente.getOutputStream());
                    dos.writeUTF("subtrair;"+textFieldPrimeiroNumero.getText()+";"+textFieldSegundoNumero.getText());                    
                }catch(IOException eIO){
                    System.out.println("Cliente: Erro ao enviar mensagem: " + eIO.getMessage());
                }
            } 
        });
		add(buttonSubtrair);
		
		JButton buttonMultiplicar = new JButton();
		buttonMultiplicar = new JButton();
		buttonMultiplicar.setBounds((telaLargura/4)*2, (telaAltura/8)*5, telaLargura/4,telaAltura/8);
		buttonMultiplicar.setText("*");
		buttonMultiplicar.setFont(new Font("Arial",Font.PLAIN,20));
		buttonMultiplicar.addActionListener(new ActionListener() {                   
            public void actionPerformed(ActionEvent e) { 
            	try{
                    DataOutputStream dos = new DataOutputStream(Aula3_Exercicio2.cliente.getOutputStream());
                    dos.writeUTF("multiplicar;"+textFieldPrimeiroNumero.getText()+";"+textFieldSegundoNumero.getText());                    
                }catch(IOException eIO){
                    System.out.println("Cliente: Erro ao enviar mensagem: " + eIO.getMessage());
                }
            } 
        });
		add(buttonMultiplicar);
		
		JButton buttonDividir = new JButton();
		buttonDividir = new JButton();
		buttonDividir.setBounds((telaLargura/4)*3, (telaAltura/8)*5, telaLargura/4,telaAltura/8);
		buttonDividir.setText("/");
		buttonDividir.setFont(new Font("Arial",Font.PLAIN,20));
		buttonDividir.addActionListener(new ActionListener() {                   
            public void actionPerformed(ActionEvent e) { 
            	try{
                    DataOutputStream dos = new DataOutputStream(Aula3_Exercicio2.cliente.getOutputStream());
                    dos.writeUTF("dividir;"+textFieldPrimeiroNumero.getText()+";"+textFieldSegundoNumero.getText());                    
                }catch(IOException eIO){
                    System.out.println("Cliente: Erro ao enviar mensagem: " + eIO.getMessage());
                }
            } 
        });
		add(buttonDividir);
		
	}	
	
}
