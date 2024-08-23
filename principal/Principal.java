package principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import principal.frames.ClienteFrame;
import principal.frames.ServidorFrame;

public class Principal{
	
	private JFrame principalFrame;
	private JButton botaoServidorFrame;
	private List<ServidorFrame> listaServidor = new ArrayList<ServidorFrame>();
	
	public Principal() {
		this.principalFrame = new JFrame();
		this.principalFrame.setTitle("Augusto - Socket");
		this.principalFrame.setSize(1080,720);
		this.principalFrame.setLocationRelativeTo(null);
		this.principalFrame.setDefaultCloseOperation(principalFrame.EXIT_ON_CLOSE);		
		this.principalFrame.setUndecorated(true);
		
		KeyboardFocusManager
        .getCurrentKeyboardFocusManager()
        .addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {                
            	System.out.println("<Fechando sistema - " + e + ">");
                if(e.getID() == e.KEY_PRESSED){
                	principalFrame.dispose();
                    return true;
                }
                return false;
            }
        });
		
		JPanel panel = new JPanel();
        panel.setBounds(0,0,1080,720);
        panel.setBackground(Color.BLACK);
        panel.setLayout(null);
        
        this.botaoServidorFrame = new JButton();
        this.botaoServidorFrame.setBounds(864, 600, 216, 120);
        this.botaoServidorFrame.setText("Enviar Mensagem");
        this.botaoServidorFrame.setFont(new Font("Arial",Font.PLAIN,20));
        this.botaoServidorFrame.addActionListener(new ActionListener() {                   
            public void actionPerformed(ActionEvent e) { 
                iniciarServidor();
            } 
        });
        panel.add(this.botaoServidorFrame); 
        
        this.principalFrame.add(panel);
	}
	
	public void iniciar() {
		this.principalFrame.setVisible(true);
	}
	
	public void iniciarServidor() {
		listaServidor.add(new ServidorFrame());
		listaServidor.get(listaServidor.size()-1).iniciar();;
	}
	
	public static void main(String[] args) {
		
		Principal programa = new Principal();
		
		programa.iniciar();
		
	}
	
}
