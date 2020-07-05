package vista;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import modelo.Usuario;
import tds.BubbleText;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import controlador.AppChat;

import controlador.AppChat;

@SuppressWarnings("serial")
public class VentanaPerfil extends JFrame implements ActionListener {

	private final static int MIN_WIDTH = 300;
	private final static int MIN_HEIGHT = 200;
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	private Usuario user;
	
	private JPanel contentPane;
	
	private JTextField txtGreeting;
	private JButton btnEdit;
	private JButton btnAvatar;
	
	// FIXME: almaceno aquí la ventana que invoca a esta para actualizar correctamente
	// el avatar. Probablemente haya otra solución, no es esto lo más elegante.
	private VentanaAppChat vac;

	/*public static void main(String[] args) {//FIXME: delete later
		new VentanaPerfil(AdaptadorUsuarioTDS.getInstance().read(7284));
	}*/

	public VentanaPerfil(Usuario user) {
		this.user = user;
		initialize();
		setVisible(true);
	}
	
	// A fin de evitar incompatibilidad con el constructor anterior, he creado este método.
	public void setVAC(VentanaAppChat vac) {
		this.vac = vac;
	}
	
	private void initialize() {
		setTitle("Perfil - "+user.getUsername());
		setBounds(100, 100, MIN_WIDTH, MIN_HEIGHT);
		setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		contentPane = (JPanel) getContentPane();
		
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[]{10, 0, 0, 0, 10, 0};
		gbl.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl);
		
		configurarInfoUsuario();
		configurarEditables();
	}
	
	private void configurarInfoUsuario() {
		
		GridBagConstraints gbc_lblNick = new GridBagConstraints();
		gbc_lblNick.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNick.insets = new Insets(0, 0, 5, 5);
		gbc_lblNick.gridx = 2;
		gbc_lblNick.gridy = 1;
		contentPane.add(new JLabel(user.getUsername()), gbc_lblNick);
		
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 2;
		gbc_lblName.gridy = 3;
		contentPane.add(new JLabel(user.getName()), gbc_lblName);
		
		GridBagConstraints gbc_lblBirthday = new GridBagConstraints();
		gbc_lblBirthday.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblBirthday.insets = new Insets(0, 0, 5, 5);
		gbc_lblBirthday.gridx = 2;
		gbc_lblBirthday.gridy = 4;
		contentPane.add(new JLabel(dateFormat.format(user.getBirthday())), gbc_lblBirthday);
		
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 2;
		gbc_lblEmail.gridy = 5;
		contentPane.add(new JLabel(user.getEmail()), gbc_lblEmail);
		
		GridBagConstraints gbc_lblPhone = new GridBagConstraints();
		gbc_lblPhone.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPhone.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhone.gridx = 2;
		gbc_lblPhone.gridy = 6;
		contentPane.add(new JLabel(user.getPhone()), gbc_lblPhone);
	}
	
	private void showAvatar(String nombre) {
		File fichero = new File(nombre);
		BufferedImage myPicture;
		try { 
			myPicture = ImageIO.read(fichero);			
			Image aux=myPicture.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
			btnAvatar.setIcon(new ImageIcon(aux));
			btnAvatar.repaint();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void configurarEditables() {
		btnAvatar = new JButton();
		showAvatar(user.getAvatar());
		btnAvatar.setFocusPainted(false);
		btnAvatar.setMargin(new Insets(0, 0, 0, 0));
		btnAvatar.setContentAreaFilled(false);
		btnAvatar.setBorderPainted(false);
		btnAvatar.setOpaque(false);
		btnAvatar.addActionListener(this);
		GridBagConstraints gbc_lblAvatar = new GridBagConstraints();
		gbc_lblAvatar.gridheight = 2;
		gbc_lblAvatar.insets = new Insets(0, 0, 5, 5);
		gbc_lblAvatar.gridx = 1;
		gbc_lblAvatar.gridy = 1;
		contentPane.add(btnAvatar, gbc_lblAvatar);
		
		GridBagConstraints gbc_greeting = new GridBagConstraints();
		gbc_greeting.fill = GridBagConstraints.HORIZONTAL;
		gbc_greeting.insets = new Insets(0, 0, 5, 5);
		gbc_greeting.gridx = 2;
		gbc_greeting.gridy = 2;
		
		if (user.equals(AppChat.getInstance().getUsuarioActual())) {
			txtGreeting = new JTextField();
			txtGreeting.setText(user.getGreeting());
			txtGreeting.setColumns(100);
			contentPane.add(txtGreeting, gbc_greeting);
			
			btnEdit = new JButton("Edit");
			//btnEdit.addActionListener(this);
			GridBagConstraints gbc_btnEdit = new GridBagConstraints();
			gbc_btnEdit.insets = new Insets(0, 0, 5, 5);
			gbc_btnEdit.gridx = 3;
			gbc_btnEdit.gridy = 2;
			contentPane.add(btnEdit, gbc_btnEdit);
		} else {
			JLabel lblGreeting = new JLabel(user.getGreeting());
			gbc_greeting.gridwidth = 2;
			contentPane.add(lblGreeting, gbc_greeting);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAvatar) {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG, JPG o JPEG", "png", "jpg", "jpeg");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				AppChat.getInstance().changeAvatar(user, chooser.getSelectedFile().getAbsolutePath());
				showAvatar(user.getAvatar());
				vac.showAvatar(user.getAvatar());
			}
		}
	}
}
