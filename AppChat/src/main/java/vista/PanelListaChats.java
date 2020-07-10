package vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionListener;

import modelo.Chat;
import modelo.ChatGrupo;
import modelo.ChatIndividual;
import modelo.Mensaje;
import modelo.Usuario;

@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class PanelListaChats extends JPanel {
	
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");

	private PanelChat chat;
	private Usuario user;
	private DefaultListModel model;
	
	public PanelListaChats(PanelChat panelChat, Usuario user) {
		this.chat = panelChat;
		this.user = user;
		this.model = new DefaultListModel();
		for (Chat c : user.getChats())
			model.addElement(c);
		
		setLayout(new BorderLayout());

		JList lista = new JList();
		lista.setBackground(Graphics.BACKGROUND);
		lista.setFixedCellHeight(Graphics.SIZE_AVATAR_SMALL);
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

	public void remove(Chat chat) {
		model.removeElement(chat);
	}

	private static ListCellRenderer<? super Chat> createListRenderer() {
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
					Chat chat = (Chat) value;
					
					Mensaje msg = chat.getMostRecentMessage();
					String time = "";
					String preview = "";
					if (msg != null) {
						time = dateFormat.format(msg.getTimestamp());
						if (msg.getBodyType() == Mensaje.TEXT_BODY) {
							preview = msg.getBody();
							if (preview.length() > 20) {
								preview = preview.substring(0,17);
								preview += "...";
							}
						} else preview = "*emoji*";
					}
					if (chat instanceof ChatIndividual)
						label.setIcon(Graphics.makeAvatar(chat.getAvatar(), Graphics.SIZE_AVATAR_SMALL));
					else
						label.setIcon(Graphics.makeGroupAvatar(Graphics.SIZE_AVATAR_SMALL));
					label.setText("<html>"+chat.getName()+"<br/>["+time+"]<br/>"+preview+"</html>");
					if (isSelected) {
						label.setBackground(Graphics.MAIN);
						label.setBorder(new LineBorder(Graphics.DARK));
					} else
						label.setBackground(Graphics.BACKGROUND);
						
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
						if (chat instanceof ChatGrupo) {
							ChatGrupo g = (ChatGrupo) chat;
							if (g.getAdmin().equals(user))
								new VentanaEditorGrupo(user, g, PanelListaChats.this);
							else new VentanaInfoGrupo(g);
						} else {
							ChatIndividual c = (ChatIndividual) chat;
							new VentanaEditorContacto(c, PanelListaChats.this);
						}
					}
					return;
				}
			}
		};
	}
}
