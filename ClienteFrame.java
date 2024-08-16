import java.awt.Color;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClienteFrame extends JFrame{

    public static Socket cliente;
    public static JTextArea areaExibicaoTexto;
    public static JTextField areaDigitacaoTexto;
    public static JButton botaoEnviar;   

    public ClienteFrame(){
        this.setTitle("Cliente");
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

        ClienteFrame.areaExibicaoTexto = new JTextArea();
        ClienteFrame.areaExibicaoTexto.setBounds(0,0,1080,600);        
        ClienteFrame.areaExibicaoTexto.setFont(new Font("Arial",Font.PLAIN,20));
        ClienteFrame.areaExibicaoTexto.setForeground(Color.WHITE);
        ClienteFrame.areaExibicaoTexto.setBackground(Color.DARK_GRAY);
        panel.add(ClienteFrame.areaExibicaoTexto);

        ClienteFrame.areaDigitacaoTexto = new JTextField();
        ClienteFrame.areaDigitacaoTexto.setBounds(0,600,864,120);
        ClienteFrame.areaDigitacaoTexto.setFont(new Font("Arial",Font.PLAIN,30));
        panel.add(ClienteFrame.areaDigitacaoTexto);

        ClienteFrame.botaoEnviar = new JButton();
        ClienteFrame.botaoEnviar.setBounds(864, 600, 216, 120);
        ClienteFrame.botaoEnviar.setText("Enviar Mensagem");
        ClienteFrame.botaoEnviar.setFont(new Font("Arial",Font.PLAIN,20));
        ClienteFrame.botaoEnviar.addActionListener(new ActionListener() {                   
            public void actionPerformed(ActionEvent e) { 
                if(Cliente.cliente != null){
                    try{
                        DataOutputStream dos = new DataOutputStream(Cliente.cliente.getOutputStream());
                        dos.writeUTF(ClienteFrame.areaDigitacaoTexto.getText());
                        ClienteFrame.areaDigitacaoTexto.setText("");
                    }catch(IOException eIO){
                        System.out.println("Erro ao enviar mensagem: " + eIO.getMessage());
                    }
                    
                }
            } 
        });
        panel.add(ClienteFrame.botaoEnviar);  

        this.add(panel);        
    }

    public void iniciar(){
        this.setVisible(true);
    }

    public static void main(String[] args){
        ClienteFrame cf = new ClienteFrame();
        cf.iniciar();

        Scanner teclado = new Scanner(System.in);
        try {
            //Cria um socket e tenta se conectar ao servidor (socket->connect)
            Cliente.cliente = new Socket("localhost", 1235); 
            ClienteFrame.areaExibicaoTexto.setText(ClienteFrame.areaExibicaoTexto.getText() + "\n" +
            "Servidor localizado e conectado");
            /*
            Thread threadLeitora = new Thread(){
                @Override
                public void run(){
                    try{
                        while(true){            
                            ClienteFrame.areaExibicaoTexto.setText(ClienteFrame.areaExibicaoTexto.getText() + "\n" +
                            "Digite a sua mensagem para enviar: ");
                            String mensagem = teclado.nextLine();//lê pelo teclado
                            DataOutputStream dos = new DataOutputStream(Cliente.cliente.getOutputStream());
                            dos.writeUTF(mensagem);//envia a mensagem                                                                                                                    
                        }                                                                                  
                    }catch(IOException eIO){
                        ClienteFrame.areaExibicaoTexto.setText(ClienteFrame.areaExibicaoTexto.getText() + "\n" +
                        "Erro ao receber/enviar mensagem: " + eIO.getMessage());
                    }                              
                }
            };
             */
            Thread threadDigitadora = new Thread(){
                @Override
                public void run(){
                    Scanner teclado = new Scanner(System.in);
                    try{
                        while(true){                                                            
                            DataInputStream dis = new DataInputStream(Cliente.cliente.getInputStream());
                            String resposta = dis.readUTF();
                            ClienteFrame.areaExibicaoTexto.setText(ClienteFrame.areaExibicaoTexto.getText() + "\n" +
                            "Mensagem recebida do Servidor: "+resposta);                                                                                    
                        }                                                        
                    }catch(IOException eIO){
                        ClienteFrame.areaExibicaoTexto.setText(ClienteFrame.areaExibicaoTexto.getText() + "\n" +
                        "Erro ao receber/enviar mensagem: " + eIO.getMessage());
                    }  
                }
            }; 
                                    
            threadDigitadora.start();

            while(threadDigitadora.isAlive()){}
            
        } catch (IOException ex) {
            ClienteFrame.areaExibicaoTexto.setText(ClienteFrame.areaExibicaoTexto.getText() + "\n" +
            "Servidor nao encontrado");
        }
    }


}