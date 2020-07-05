package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionListener;

import controlador.AppChat;
import modelo.Chat;
import modelo.ChatGrupo;
import modelo.ChatIndividual;
import modelo.Usuario;

@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class PanelListaChats extends JPanel {

	private PanelChat chat;
	private Usuario user;
	private DefaultListModel model;
	
	public PanelListaChats(PanelChat panelChat, Usuario user) {
		this.chat = panelChat;
		this.user = user;
		this.model = new DefaultListModel();
		for (Chat c : user.getChats())
			model.addElement(c);
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

		JList lista = new JList();
		lista.setModel(model);
		lista.setCellRenderer(createListRenderer());
		lista.addListSelectionListener(createListSelectionListener(lista));
		lista.addMouseListener(createMouseAdapter(lista));
		add(lista);
	}
	
	public void reloadList() {
		model.removeAllElements();
		for (Chat c : user.getChats())
			model.addElement(c);
	}

	private static ListCellRenderer<? super Chat> createListRenderer() {
		return new DefaultListCellRenderer() {
			private Color background = Graphics.TRANSPARENT;
			private Color defaultBackground = Graphics.TRANSPARENT;
			
			@Override
			public Component getListCellRendererComponent
			(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
			{
				Component c =
					super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (c instanceof JLabel)
				{
					JLabel label = (JLabel) c;
					Chat contact = (Chat) value;
					label.setIcon(new ImageIcon(/*VistaPrincipal.class.getResource(usuario.getImagen())*/));
					label.setText(contact.getName());
					if (!isSelected)
						label.setBackground(index % 2 == 0 ? background : defaultBackground);
				}
				return c;
			}
		};
	}
	
	private ListSelectionListener createListSelectionListener(JList<Chat> chats) {
		return e -> {
			if (e.getValueIsAdjusting()) {
				chat.loadChat(chats.getSelectedValue());
			}
		};
	}
	
	private MouseListener createMouseAdapter(JList list) {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = list.locationToIndex(e.getPoint());
					if (index >= 0) {
						Object chat = list.getModel().getElementAt(index);
						if (chat instanceof ChatGrupo)
							new VentanaEditorGrupo(user, (ChatGrupo) chat, PanelListaChats.this);
						else {
							ChatIndividual c = (ChatIndividual) chat;
							new VentanaPerfil(c.getUser());
						}
					}
					return;
				}
				if (SwingUtilities.isRightMouseButton(e)) {
					int index = list.locationToIndex(e.getPoint());
					if (index >= 0) {
						Object chat = list.getModel().getElementAt(index);
						DefaultListModel model = ((DefaultListModel) list.getModel());
						model.removeElement(chat);
						AppChat.getInstance().deleteChat((Chat) chat);
					}
				}
			}
		};
	}
}
