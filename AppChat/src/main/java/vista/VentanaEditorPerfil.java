package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;

import modelo.Usuario;

import java.awt.GridBagLayout;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import controlador.AppChat;
import javax.swing.BoxLayout;

@SuppressWarnings("serial")
public class VentanaEditorPerfil extends JFrame implements ActionListener {

	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	private Usuario user;
	
	private JPanel contentPane;
	private JTextField txtGreeting;
	private JButton btnEdit;
	private JButton btnAvatar;
	
	private VentanaAppChat father;

	public VentanaEditorPerfil(Usuario user, VentanaAppChat father) {
		this.user = user;
		this.father = father;
		initialize();
		setVisible(true);
	}
	
	private void initialize() {
		setTitle("Perfil - "+user.getUsername());
		setSize(Graphics.MIN_WIDTH, Graphics.MIN_HEIGHT);
		setLocationRelativeTo(father);
		setResizable(false);
		contentPane = (JPanel) getContentPane();
		
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[]{0, 10, 0};
		gbl.rowHeights = new int[]{0, 0};
		gbl.columnWeights = new double[]{1.0, 1.0, 1.0};
		gbl.rowWeights = new double[]{0.0, 0.0};
		contentPane.setLayout(gbl);
		
		configurarInfo();
	}

	private void configurarInfo() {
/* Parte funcional */
		txtGreeting = new JTextField(user.getGreeting());
		txtGreeting.setColumns(100);
		
		btnEdit = Graphics.makeIconButton("/edit.png");
		btnEdit.addActionListener(this);
		getRootPane().setDefaultButton(btnEdit);
		
		btnAvatar = Graphics.makeAvatarButton(user.getAvatar());
		btnAvatar.addActionListener(this);
/* Parte gráfica */
		JPanel panel_info = new JPanel();
		GridBagConstraints gbc_panel_info = new GridBagConstraints();
		gbc_panel_info.insets = new Insets(0, 0, 5, 5);
		gbc_panel_info.fill = GridBagConstraints.BOTH;
		gbc_panel_info.gridx = 1;
		gbc_panel_info.gridy = 0;
		getContentPane().add(panel_info, gbc_panel_info);
		
		GridBagLayout gbl_panel_info = new GridBagLayout();
		gbl_panel_info.columnWidths = new int[]{0, 0, 0};
		gbl_panel_info.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_info.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_info.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_info.setLayout(gbl_panel_info);
		
		JLabel label = new JLabel(user.getUsername());
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.fill = GridBagConstraints.HORIZONTAL;
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 1;
		gbc_label.gridy = 0;
		panel_info.add(label, gbc_label);
		
		JLabel label_4 = new JLabel(user.getName());
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_4.insets = new Insets(0, 0, 5, 0);
		gbc_label_4.gridx = 1;
		gbc_label_4.gridy = 1;
		panel_info.add(label_4, gbc_label_4);
		
		JLabel label_3 = new JLabel(dateFormat.format(user.getBirthday()));
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_3.insets = new Insets(0, 0, 5, 0);
		gbc_label_3.gridx = 1;
		gbc_label_3.gridy = 2;
		panel_info.add(label_3, gbc_label_3);
		
		JLabel label_2 = new JLabel(user.getEmail());
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_2.insets = new Insets(0, 0, 5, 0);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 3;
		panel_info.add(label_2, gbc_label_2);
		
		JLabel label_1 = new JLabel(user.getPhone());
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.NORTH;
		gbc_label_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 4;
		panel_info.add(label_1, gbc_label_1);
		
		JPanel panel_avatar = new JPanel();
		GridBagConstraints gbc_panel_avatar = new GridBagConstraints();
		gbc_panel_avatar.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_avatar.gridheight = 5;
		gbc_panel_avatar.insets = new Insets(0, 0, 5, 5);
		gbc_panel_avatar.gridx = 0;
		gbc_panel_avatar.gridy = 0;
		panel_info.add(panel_avatar, gbc_panel_avatar);
		panel_avatar.add(btnAvatar);
		
		JPanel panel_editGreeting = new JPanel();
		GridBagConstraints gbc_panel_editGreeting = new GridBagConstraints();
		gbc_panel_editGreeting.insets = new Insets(0, 0, 0, 5);
		gbc_panel_editGreeting.fill = GridBagConstraints.BOTH;
		gbc_panel_editGreeting.gridx = 1;
		gbc_panel_editGreeting.gridy = 1;
		getContentPane().add(panel_editGreeting, gbc_panel_editGreeting);
		panel_editGreeting.setLayout(new BoxLayout(panel_editGreeting, BoxLayout.X_AXIS));
		
		panel_editGreeting.add(txtGreeting);
		panel_editGreeting.add(btnEdit);
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
				Graphics.reloadAvatarButton(btnAvatar, user.getAvatar());
				father.reloadAvatar();
			}
		}
		if (e.getSource() == btnEdit) {
			AppChat.getInstance().changeGreeting(txtGreeting.getText());
			father.reloadGreeting();
			dispose();
			return;
		}
	}
}
