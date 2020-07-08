package vista;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import modelo.ChatGrupo;
import modelo.ChatIndividual;
import modelo.Usuario;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;

import javax.swing.JList;
import javax.swing.border.TitledBorder;

import controlador.AppChat;

@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class VentanaEditorGrupo extends JFrame implements ActionListener {

	private Usuario user;
	private ChatGrupo group;
	private ChatIndividual toAdd;
	private ChatIndividual toRmv;
	private PanelListaChats listaChats;
	
	private JPanel contentPane;
	private JList list_contacts;
	private JList list_added;
	private JTextField txtNombreGrupo;
	private JButton btnAdd;
	private JButton btnRmv;
	private JButton btnOk;
	private JButton btnCancel;
	
	/**
	 * @wbp.parser.constructor
	 */
	public VentanaEditorGrupo(Usuario user, ChatGrupo group, PanelListaChats listaChats) {
		this.user = user;
		this.group = group;
		this.listaChats = listaChats;
		setSize(450, 300);
		setMinimumSize(new Dimension(Graphics.MIN_WIDTH, Graphics.MIN_HEIGHT));
		setLocationRelativeTo(null);
		setTitle("Editor de grupos");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Graphics.BACKGROUND);
		setContentPane(contentPane);
		initialize();
		setVisible(true);
	}
	
	public VentanaEditorGrupo(Usuario user, PanelListaChats listaChats) {
		this(user, null, listaChats);
	}
	
	private void initialize() {
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{100, 100, 100, 0};
		gbl_contentPane.rowHeights = new int[]{10, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		txtNombreGrupo = new JTextField();
		txtNombreGrupo.setColumns(20);
		if (group != null)
			txtNombreGrupo.setText(group.getName());
		else
			txtNombreGrupo.setText("Nombre grupo");
		GridBagConstraints gbc_txtNombreGrupo = new GridBagConstraints();
		gbc_txtNombreGrupo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombreGrupo.insets = new Insets(0, 0, 5, 5);
		gbc_txtNombreGrupo.gridx = 1;
		gbc_txtNombreGrupo.gridy = 0;
		contentPane.add(txtNombreGrupo, gbc_txtNombreGrupo);
		
		configurarBotones();
		configurarListas();
	}
	
	private void configurarBotones() {
/* Parte funcional */
		btnAdd = Graphics.makeButton("->");
		btnRmv = Graphics.makeButton("<-");
		btnOk = Graphics.makeButton("Aceptar");
		getRootPane().setDefaultButton(btnOk);
		btnCancel = Graphics.makeButton("Cancelar");
		
		btnAdd.addActionListener(this);
		btnRmv.addActionListener(this);
		btnOk.addActionListener(this);
		btnCancel.addActionListener(this);
/* Parte visual */
		JPanel panel_addrmv = new JPanel();
		panel_addrmv.setBackground(Graphics.BACKGROUND);
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
		panel_okback.setBackground(Graphics.BACKGROUND);
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
/* Parte funcional */
		ListCellRenderer listRenderer = createListRenderer();
		
		list_contacts = new JList(new DefaultListModel());
		list_contacts.setBackground(Graphics.WHITE);
		loadContacts(user.getPrivateChats(), list_contacts.getModel());
		list_contacts.setCellRenderer(listRenderer);
		list_contacts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				list_added.clearSelection();
				toRmv = null;
				int index = list_contacts.locationToIndex(mouseEvent.getPoint());
				if (index >= 0) {
					toAdd = (ChatIndividual) list_contacts.getModel().getElementAt(index);
					if (mouseEvent.getClickCount() == 2)
						addContact();
				}
			}
		});
		
		list_added = new JList(new DefaultListModel());
		list_added.setBackground(Graphics.WHITE);
		if (group != null) loadContacts(group.getMembers(), list_added.getModel());
		list_added.setCellRenderer(listRenderer);
		list_added.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				list_contacts.clearSelection();
				toAdd = null;
				int index = list_added.locationToIndex(mouseEvent.getPoint());
				if (index >= 0) {
					toRmv = (ChatIndividual) list_added.getModel().getElementAt(index);
					if (mouseEvent.getClickCount() == 2)
						removeContact();
				}
			}
		});
/* Parte visual */
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBackground(Graphics.BACKGROUND);
		scrollPane1.setViewportBorder(new TitledBorder(
				new LineBorder(Graphics.DARK),
				"Contactos",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null, null));
		GridBagConstraints gbc_scrollPane1 = new GridBagConstraints();
		gbc_scrollPane1.gridheight = 2;
		gbc_scrollPane1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane1.gridx = 0;
		gbc_scrollPane1.gridy = 0;
		contentPane.add(scrollPane1, gbc_scrollPane1);
		scrollPane1.setViewportView(list_contacts);
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBackground(Graphics.BACKGROUND);
		scrollPane2.setViewportBorder(new TitledBorder(
				new LineBorder(Graphics.DARK),
				"Contactos añadidos",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null, null));
		GridBagConstraints gbc_scrollPane2 = new GridBagConstraints();
		gbc_scrollPane2.gridheight = 2;
		gbc_scrollPane2.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane2.gridx = 2;
		gbc_scrollPane2.gridy = 0;
		contentPane.add(scrollPane2, gbc_scrollPane2);
		scrollPane2.setViewportView(list_added);
	}
	
	private void loadContacts(Collection<ChatIndividual> contacts, ListModel model) {
		for (ChatIndividual c : contacts)
			((DefaultListModel) model).addElement(c);
	}
	
	private static ListCellRenderer<? super ChatIndividual> createListRenderer() {
		return new DefaultListCellRenderer() {
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
					if (isSelected) {
						label.setBackground(Graphics.MAIN);
						label.setBorder(new LineBorder(Graphics.DARK));
					}
					else
						label.setBackground(Graphics.WHITE);
				}
				return c;
			}
		};
	}
	
	private void addContact() {
		DefaultListModel added = (DefaultListModel) list_added.getModel();
		if (toAdd != null && !added.contains(toAdd)) {
			added.addElement(toAdd);
			toAdd = null;
			list_contacts.clearSelection();
		}
	}
	
	private void removeContact() {
		DefaultListModel added = (DefaultListModel) list_added.getModel();
		if (toRmv != null && added.contains(toRmv)) {
			added.removeElement(toRmv);
			toRmv = null;
		}
	}
	
	private void crearGrupo() {
		DefaultListModel added = (DefaultListModel) list_added.getModel();
		LinkedList<ChatIndividual> contacts = new LinkedList();
		for (int i=0; i<added.getSize(); i++)
			contacts.add((ChatIndividual) added.getElementAt(i));
		
		AppChat.getInstance().createGroup(txtNombreGrupo.getText(), contacts);
		dispose();
	}
	
	private void editarGrupo() {
		DefaultListModel added = (DefaultListModel) list_added.getModel();
		LinkedList<ChatIndividual> contacts = new LinkedList();
		for (int i=0; i<added.getSize(); i++)
			contacts.add((ChatIndividual) added.getElementAt(i));
		
		AppChat.getInstance().editGroup(group, txtNombreGrupo.getText(), contacts);
		dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAdd) {
			addContact();
			return;
		}
		if (e.getSource() == btnRmv) {
			removeContact();
			return;
		}
		if (e.getSource() == btnOk) {
			if (group == null) crearGrupo();
			else editarGrupo();
			listaChats.reloadList();
			return;
		}
		if (e.getSource() == btnCancel) {
			dispose();
			return;
		}
	}

}
