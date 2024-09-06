package principal.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Aula5_Exercicio2 extends JPanel{
	
	public Aula5_Exercicio2(int telaLargura, int telaAltura) {
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
            	
            } 
        });
		add(buttonEncerrarServidor);
	}
	
}