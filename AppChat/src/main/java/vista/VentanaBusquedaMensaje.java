package vista;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import controlador.AppChat;
import modelo.Chat;
import modelo.ChatGrupo;
import modelo.Mensaje;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import java.awt.GridBagConstraints;

@SuppressWarnings({ "serial" })
public class VentanaBusquedaMensaje extends JFrame implements ActionListener {

	private final static int MIN_WIDTH = 300;
	private final static int MIN_HEIGHT = 200;
	
	private JPanel contentPane;
	
	private Chat chat;
	private String actualUser;
	
	private JTextField field_user;
	private JTextField field_text;
	private JDateChooser field_date1;
	private JDateChooser field_date2;
	
	private JButton btn_search;

	public VentanaBusquedaMensaje(Chat chat, String actualUser) {
		this.chat = chat;
		this.actualUser = actualUser;
		initialize();
		setVisible(true);
	}
	
	private void initialize() {
		setTitle("Búsqueda mensajes - \""+ chat.getName() +"\"");
		setBounds(100, 100, MIN_WIDTH, MIN_HEIGHT);
		setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		contentPane = (JPanel) getContentPane();
		contentPane.setBackground(Graphics.BACKGROUND);
		
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[]{10, 0, 0, 0, 10, 0};
		gbl.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl);
		
		configurarCampos();
		configurarBotones();
	}
	
	private void configurarCampos() {
		/* Texto */
		GridBagConstraints gbc_lblTexto = new GridBagConstraints();
		gbc_lblTexto.anchor = GridBagConstraints.EAST;
		gbc_lblTexto.insets = new Insets(0, 0, 5, 5);
		gbc_lblTexto.gridx = 1;
		gbc_lblTexto.gridy = 1;
		contentPane.add(new JLabel("Texto:"), gbc_lblTexto);
		
		GridBagConstraints gbc_textField_text = new GridBagConstraints();
		gbc_textField_text.gridwidth = 2;
		gbc_textField_text.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_text.insets = new Insets(0, 0, 5, 5);
		gbc_textField_text.gridx = 2;
		gbc_textField_text.gridy = 1;
		field_text = new JTextField();
		field_text.setColumns(10);
		contentPane.add(field_text, gbc_textField_text);
		
		/* Dates */
		GridBagConstraints gbc_lblDate1 = new GridBagConstraints();
		gbc_lblDate1.anchor = GridBagConstraints.EAST;
		gbc_lblDate1.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate1.gridx = 1;
		gbc_lblDate1.gridy = 2;
		contentPane.add(new JLabel("Fecha inicio:"), gbc_lblDate1);
		
		GridBagConstraints gbc_date1 = new GridBagConstraints();
		gbc_date1.gridwidth = 2;
		gbc_date1.fill = GridBagConstraints.HORIZONTAL;
		gbc_date1.insets = new Insets(0, 0, 5, 5);
		gbc_date1.gridx = 2;
		gbc_date1.gridy = 2;
		field_date1 = new JDateChooser();
		field_date1.setDateFormatString("dd/MM/yyyy");
		contentPane.add(field_date1, gbc_date1);
		
		GridBagConstraints gbc_lblDate2 = new GridBagConstraints();
		gbc_lblDate2.anchor = GridBagConstraints.EAST;
		gbc_lblDate2.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate2.gridx = 1;
		gbc_lblDate2.gridy = 3;
		contentPane.add(new JLabel("Fecha fin:"), gbc_lblDate2);
		
		GridBagConstraints gbc_date2 = new GridBagConstraints();
		gbc_date2.gridwidth = 2;
		gbc_date2.fill = GridBagConstraints.HORIZONTAL;
		gbc_date2.insets = new Insets(0, 0, 5, 5);
		gbc_date2.gridx = 2;
		gbc_date2.gridy = 3;
		field_date2 = new JDateChooser();
		field_date2.setDateFormatString("dd/MM/yyyy");
		contentPane.add(field_date2, gbc_date2);
		
		/* User */
		if (chat instanceof ChatGrupo) {
			GridBagConstraints gbc_lblUser = new GridBagConstraints();
			gbc_lblUser.anchor = GridBagConstraints.EAST;
			gbc_lblUser.insets = new Insets(0, 0, 5, 5);
			gbc_lblUser.gridx = 1;
			gbc_lblUser.gridy = 4;
			contentPane.add(new JLabel("Autor:"), gbc_lblUser);
			
			GridBagConstraints gbc_textField_user = new GridBagConstraints();
			gbc_textField_user.gridwidth = 2;
			gbc_textField_user.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_user.insets = new Insets(0, 0, 5, 5);
			gbc_textField_user.gridx = 2;
			gbc_textField_user.gridy = 4;
			field_user = new JTextField();
			field_user.setColumns(10);
			contentPane.add(field_user, gbc_textField_user);
		}
	}
	
	private void configurarBotones() {
		/* Search  */
		GridBagConstraints gbc_bttn = new GridBagConstraints();
		gbc_bttn.gridwidth = 2;
		gbc_bttn.fill = GridBagConstraints.HORIZONTAL;
		gbc_bttn.insets = new Insets(0, 0, 5, 5);
		gbc_bttn.gridx = 2;
		gbc_bttn.gridy = 5;
		btn_search = Graphics.makeButton("Iniciar búsqueda");
		btn_search.addActionListener(this);
		getRootPane().setDefaultButton(btn_search);
		contentPane.add(btn_search, gbc_bttn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_search) {
			List<Mensaje> resultado = AppChat.getInstance().findMessages(chat, field_text.getText()
					,(field_user != null ? field_user.getText() : "")
					, field_date1.getDate(), field_date2.getDate());
			new VentanaResultadoBusqueda(chat.getName(), actualUser, resultado);
			return;
		}
	}
}
