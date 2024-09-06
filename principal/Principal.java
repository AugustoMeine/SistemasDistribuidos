package principal;

import java.awt.CardLayout;
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

import principal.panels.Aula3_Exercicio2;
import principal.panels.Aula3_Exercicio3;

public class Principal{
	
	private JFrame principalFrame;
	private JButton botaoExercicio1;
	private JButton botaoExercicio2;
	private JButton botaoExercicio3;
	private JButton botaoExercicio4;
	JPanel panelGerenciador;
	
	public Principal() {
		
		int telaLargura = 1080;
		int telaAltura = 720;		
		
		int quantidadeDeExercicios = 4;
		
		this.principalFrame = new JFrame();
		this.principalFrame.setTitle("Augusto - Sistemas distribuidos");
		this.principalFrame.setSize(telaLargura,telaAltura);
		this.principalFrame.setLocationRelativeTo(null);
		this.principalFrame.setDefaultCloseOperation(principalFrame.EXIT_ON_CLOSE);		
		this.principalFrame.setUndecorated(true);
		
		KeyboardFocusManager
        .getCurrentKeyboardFocusManager()
        .addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {                            	
                if((e.getID() == e.KEY_PRESSED) && (e.getKeyChar() == KeyEvent.VK_ESCAPE)){
                	System.out.println("<Finalizando sistema>");
                	principalFrame.dispose();
                    return true;
                }
                return false;
            }
        });
		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBounds(0,0,telaLargura,telaAltura);
		panelPrincipal.setBackground(Color.BLACK);
		panelPrincipal.setLayout(null);
        
        this.botaoExercicio1 = new JButton();
        this.botaoExercicio1.setBounds(0, ((telaAltura/quantidadeDeExercicios) * 0), telaLargura, (telaAltura/quantidadeDeExercicios));
        this.botaoExercicio1.setText("Aula 3 - Exercício 2");
        this.botaoExercicio1.setFont(new Font("Arial",Font.PLAIN,20));
        this.botaoExercicio1.addActionListener(new ActionListener() {                   
            public void actionPerformed(ActionEvent e) { 
                iniciarAula3Exercicio2();
            } 
        });
        panelPrincipal.add(this.botaoExercicio1); 
        
        this.botaoExercicio2 = new JButton();
        this.botaoExercicio2.setBounds(0, ((telaAltura/quantidadeDeExercicios) * 1), telaLargura, (telaAltura/quantidadeDeExercicios));
        this.botaoExercicio2.setText("Aula 3 - Exercício 3");
        this.botaoExercicio2.setFont(new Font("Arial",Font.PLAIN,20));
        this.botaoExercicio2.addActionListener(new ActionListener() {                   
            public void actionPerformed(ActionEvent e) { 
            	iniciarAula3Exercicio3();
            } 
        });
        panelPrincipal.add(this.botaoExercicio2); 
        
        this.botaoExercicio3 = new JButton();
        this.botaoExercicio3.setBounds(0, ((telaAltura/quantidadeDeExercicios) * 2), telaLargura, (telaAltura/quantidadeDeExercicios));
        this.botaoExercicio3.setText("Aula 4 - Exercício 1");
        this.botaoExercicio3.setFont(new Font("Arial",Font.PLAIN,20));
        this.botaoExercicio3.addActionListener(new ActionListener() {                   
            public void actionPerformed(ActionEvent e) { 
                //iniciarServidor();
            } 
        });
        panelPrincipal.add(this.botaoExercicio3);
        
        this.botaoExercicio4 = new JButton();
        this.botaoExercicio4.setBounds(0, ((telaAltura/quantidadeDeExercicios) * 3), telaLargura, (telaAltura/quantidadeDeExercicios));
        this.botaoExercicio4.setText("Aula 5 - Exercício 2");
        this.botaoExercicio4.setFont(new Font("Arial",Font.PLAIN,20));
        this.botaoExercicio4.addActionListener(new ActionListener() {                   
            public void actionPerformed(ActionEvent e) { 
                //iniciarServidor();
            } 
        });
        panelPrincipal.add(this.botaoExercicio4);
        
        
        JPanel aula3Exercicio2 = new Aula3_Exercicio2(telaLargura, telaAltura);
        JPanel aula3Exercicio3 = new Aula3_Exercicio3(telaLargura, telaAltura);
        
        this.panelGerenciador = new JPanel(new CardLayout());
        this.panelGerenciador.add("principal",panelPrincipal);
        this.panelGerenciador.add("aula3Exercicio2",aula3Exercicio2);
        this.panelGerenciador.add("aula3Exercicio3",aula3Exercicio3);
        
        this.principalFrame.add(panelGerenciador);
	}
	
	public void iniciar() {
		this.principalFrame.setVisible(true);
	}
	
	public void iniciarAula3Exercicio2() {
		CardLayout cardLayout = (CardLayout)(this.panelGerenciador.getLayout());
	    cardLayout.show(this.panelGerenciador,"aula3Exercicio2");
	}
	
	public void iniciarAula3Exercicio3() {
		CardLayout cardLayout = (CardLayout)(this.panelGerenciador.getLayout());
	    cardLayout.show(this.panelGerenciador,"aula3Exercicio3");
	}
	
	public static void main(String[] args) {
		
		Principal programa = new Principal();
		
		programa.iniciar();
		
	}
	
}
