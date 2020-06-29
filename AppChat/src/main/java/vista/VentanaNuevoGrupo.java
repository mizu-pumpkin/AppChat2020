package vista;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import modelo.ChatIndividual;
import modelo.Usuario;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionListener;

import controlador.AppChat;

@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class VentanaNuevoGrupo extends JFrame implements ActionListener {

	private Usuario user;
	private ChatIndividual selectedContact;
	
	private JPanel contentPane;
	private DefaultListModel model_contacts;
	private DefaultListModel model_added;
	private JTextField txtNombreGrupo;
	private JButton btnAdd;
	private JButton btnRmv;
	private JButton btnOk;
	private JButton btnCancel;
	
	public VentanaNuevoGrupo(Usuario user) {
		this.user = user;
		setBounds(100, 100, 450, 300);
		setTitle("Nuevo grupo");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		//panelList = new PanelListaContactos(user.getPrivateChats());
		initialize();
		configurarListas();
		setVisible(true);
	}
	
	private void initialize() {
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{100, 100, 100, 0};
		gbl_contentPane.rowHeights = new int[]{10, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
/* Nombre grupo */
		txtNombreGrupo = new JTextField();
		txtNombreGrupo.setText("Nombre grupo");
		txtNombreGrupo.setColumns(20);
		GridBagConstraints gbc_txtNombreGrupo = new GridBagConstraints();
		gbc_txtNombreGrupo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombreGrupo.insets = new Insets(0, 0, 5, 5);
		gbc_txtNombreGrupo.gridx = 1;
		gbc_txtNombreGrupo.gridy = 0;
		contentPane.add(txtNombreGrupo, gbc_txtNombreGrupo);
/* Botones */
		btnAdd = new JButton("->");
		btnRmv = new JButton("<-");
		btnOk = new JButton("Aceptar");
		btnCancel = new JButton("Cancelar");
		
		btnAdd.addActionListener(this);
		btnRmv.addActionListener(this);
		btnOk.addActionListener(this);
		btnCancel.addActionListener(this);
		
		JPanel panel_addrmv = new JPanel();
		GridBagConstraints gbc_addrmv = new GridBagConstraints();
		gbc_addrmv.insets = new Insets(0, 0, 5, 5);
		gbc_addrmv.fill = GridBagConstraints.BOTH;
		gbc_addrmv.gridx = 1;
		gbc_addrmv.gridy = 1;
		contentPane.add(panel_addrmv, gbc_addrmv);
		
		GridBagLayout gbl_addrmv = new GridBagLayout();
		gbl_addrmv.columnWidths = new int[]{0, 0, 0, 0};
		gbl_addrmv.rowHeights = new int[]{0, 23, 23, 0, 0};
		gbl_addrmv.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_addrmv.rowWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_addrmv.setLayout(gbl_addrmv);
		
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.gridx = 1;
		gbc_btnAdd.gridy = 1;
		panel_addrmv.add(btnAdd, gbc_btnAdd);
		
		GridBagConstraints gbc_btnRmv = new GridBagConstraints();
		gbc_btnRmv.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRmv.insets = new Insets(0, 0, 5, 5);
		gbc_btnRmv.gridx = 1;
		gbc_btnRmv.gridy = 2;
		panel_addrmv.add(btnRmv, gbc_btnRmv);
		
		JPanel panel_okback = new JPanel();
		GridBagConstraints gbc_okback = new GridBagConstraints();
		gbc_okback.gridwidth = 3;
		gbc_okback.fill = GridBagConstraints.BOTH;
		gbc_okback.gridx = 0;
		gbc_okback.gridy = 2;
		contentPane.add(panel_okback, gbc_okback);
		panel_okback.add(btnOk);
		panel_okback.add(btnCancel);
	}

	private void configurarListas() {
/* Contactos */
		JScrollPane scrollPane_contacts = new JScrollPane();
		scrollPane_contacts.setViewportBorder(new TitledBorder(null, "Contactos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane_contacts, gbc_scrollPane);
		
		model_contacts = new DefaultListModel();
		for (ChatIndividual c : user.getPrivateChats()) model_contacts.addElement(c);
		scrollPane_contacts.setViewportView(initializeList(model_contacts));
/* Contactos añadidos */
		JScrollPane scrollPane_added = new JScrollPane();
		scrollPane_added.setViewportBorder(new TitledBorder(null, "Contactos a\u00F1adidos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridheight = 2;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 2;
		gbc_scrollPane_1.gridy = 0;
		contentPane.add(scrollPane_added, gbc_scrollPane_1);
		
		model_added = new DefaultListModel();
		scrollPane_added.setViewportView(initializeList(model_added));
	}
	
	private JList initializeList(DefaultListModel model) {
		JList list = new JList(model);
		list.setCellRenderer(createListRenderer());
		list.addListSelectionListener(createListSelectionListener(list));
		return list;
	}
	
	private ListSelectionListener createListSelectionListener(JList<ChatIndividual> chats) {
		return e -> {
			if (e.getValueIsAdjusting()) {
				selectedContact = chats.getSelectedValue();
			}
		};
	}
	
	private static ListCellRenderer<? super ChatIndividual> createListRenderer() {
		return new DefaultListCellRenderer() {
			private Color background = new Color(0, 100, 255, 15);
			private Color defaultBackground = (Color) UIManager.get("List.background");
			
			@Override
			public Component getListCellRendererComponent
			(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
			{
				Component c =
					super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (c instanceof JLabel)
				{
					JLabel label = (JLabel) c;
					ChatIndividual contact = (ChatIndividual) value;
					label.setText(contact.getName());
					if (!isSelected)
						label.setBackground(index % 2 == 0 ? background : defaultBackground);
				}
				return c;
			}
		};
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAdd) {//FIXME
			model_added.addElement(selectedContact);
			return;
		}
		if (e.getSource() == btnRmv) {//FIXME
			model_added.removeElement(selectedContact);
			return;
		}
		if (e.getSource() == btnCancel) {
			dispose();
			return;
		}
		if (e.getSource() == btnOk) {//FIXME
			LinkedList<ChatIndividual> contacts = new LinkedList();
			for (int i=0; i<model_added.getSize(); i++)
				contacts.add((ChatIndividual) model_added.getElementAt(i));
			
			AppChat.getInstance().createGroup(txtNombreGrupo.getText(), contacts);
			JOptionPane.showMessageDialog(
				this,
				"Grupo "+txtNombreGrupo.getText()+" creado correctamente.",
				"Group creation success",
				JOptionPane.INFORMATION_MESSAGE
			);
			dispose();
			return;
		}
	}

}
