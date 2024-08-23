package principal.frames;
import java.awt.Color;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServidorFrame extends JFrame{

    public static ServerSocket servidor;
    public static Socket cliente;
    public static JTextArea areaExibicaoTexto;
    public static JTextField areaDigitacaoTexto;
    public static JButton botaoEnviar;    

    public ServidorFrame(){
        this.setTitle("Servidor");
        this.setSize(1080, 720);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setUndecorated(true);

        KeyboardFocusManager
        .getCurrentKeyboardFocusManager()
        .addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                System.out.println(e);
                if(e.getID() == e.KEY_RELEASED 
                        && e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    dispose();
                    return true;
                }
                return false;
            }
        });

        JPanel panel = new JPanel();
        panel.setBounds(0,0,1080,720);
        panel.setBackground(Color.BLACK);
        panel.setLayout(null);

        ServidorFrame.areaExibicaoTexto = new JTextArea();
        ServidorFrame.areaExibicaoTexto.setBounds(0,0,1080,600);        
        ServidorFrame.areaExibicaoTexto.setFont(new Font("Arial",Font.PLAIN,20));
        ServidorFrame.areaExibicaoTexto.setForeground(Color.WHITE);
        ServidorFrame.areaExibicaoTexto.setBackground(Color.DARK_GRAY);
        panel.add(ServidorFrame.areaExibicaoTexto);

        ServidorFrame.areaDigitacaoTexto = new JTextField();
        ServidorFrame.areaDigitacaoTexto.setBounds(0,600,864,120);
        ServidorFrame.areaDigitacaoTexto.setFont(new Font("Arial",Font.PLAIN,30));
        panel.add(ServidorFrame.areaDigitacaoTexto);

        ServidorFrame.botaoEnviar = new JButton();
        ServidorFrame.botaoEnviar.setBounds(864, 600, 216, 120);
        ServidorFrame.botaoEnviar.setText("Enviar Mensagem");
        ServidorFrame.botaoEnviar.setFont(new Font("Arial",Font.PLAIN,20));
        panel.add(ServidorFrame.botaoEnviar);        

        this.add(panel);        
    }

    public void iniciar(){
    	ServidorFrame sf = new ServidorFrame();
    			
        this.setVisible(true);
    	
    	try {
        	ServidorFrame.servidor = new ServerSocket(1235);
            ServidorFrame.areaExibicaoTexto.setText(ServidorFrame.areaExibicaoTexto.getText() + "\n" +
            "Servidor iniciado: " + ServidorFrame.servidor.getLocalPort());
            while (true) {
            	ServidorFrame.cliente = ServidorFrame.servidor.accept(); //bloqueante

                //System.out.println("Recebi uma conexao de um cliente: " + cliente.getInetAddress().getHostAddress() + ":" + cliente.getPort());
                Thread threadLeitora = new Thread(){
                    @Override
                    public void run(){
                        try{
                            while(true){
                                DataInputStream dis = new DataInputStream(ServidorFrame.cliente.getInputStream());
                                String mensagemCliente = dis.readUTF();//aguarda uma mensagem -> bloqueante
                                ServidorFrame.areaExibicaoTexto.setText(ServidorFrame.areaExibicaoTexto.getText() + "\n" +
                                                                        "Mensagem recebida do cliente: " + mensagemCliente);                                                                                                
                            }                                                                                  
                        }catch(IOException eIO){
                            ServidorFrame.areaExibicaoTexto.setText(ServidorFrame.areaExibicaoTexto.getText() + "\n" +
                                                                    "Erro ao receber/enviar mensagem: " + eIO.getMessage());
                        }                              
                    }
                };       
                
                Thread threadDigitadora = new Thread(){
                    @Override
                    public void run(){
                        Scanner teclado = new Scanner(System.in);
                        try{
                            while(true){                               
                                String resposta = teclado.nextLine();
                                DataOutputStream dos = new DataOutputStream(ServidorFrame.cliente.getOutputStream());
                                dos.writeUTF(resposta);//envia uma mensagem ao cliente                                                                                        
                            }                                                        
                        }catch(IOException eIO){
                            ServidorFrame.areaExibicaoTexto.setText(ServidorFrame.areaExibicaoTexto.getText() + "\n" +
                            "Erro ao receber/enviar mensagem: " + eIO.getMessage());
                        }  
                    }
                };                 

                threadLeitora.start();
                threadDigitadora.start();

                while(threadLeitora.isAlive() || threadDigitadora.isAlive()){}
            }

        } catch (IOException ex) {
            System.out.println("Porta TCP já está sendo utilizada");
        }
    }

}