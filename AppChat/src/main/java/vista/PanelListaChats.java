package vista;

import java.awt.Color;
import java.awt.Component;
import java.util.Collection;

import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionListener;

import modelo.Chat;

@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class PanelListaChats extends JPanel {

	private PanelChat panelChat;
	private JList lista;
	
	public PanelListaChats(PanelChat panelChat, Collection<Chat> chats) {
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.panelChat = panelChat;
		iniciarLista(chats);
	}
	
	private void iniciarLista(Collection<Chat> chats) {
		this.lista = new JList();
		// clase interna anónima que hereda de AbstractListModel
		lista.setModel(new AbstractListModel() {
			Object[] values = chats.toArray();
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		lista.setCellRenderer(createListRenderer());
		lista.addListSelectionListener(createListSelectionListener(lista));
		//lista.setSelectedIndex(0);
		add(lista);
	}
	
	private static ListCellRenderer<? super Chat> createListRenderer() {
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
				panelChat.loadChat(chats.getSelectedValue());
			}
		};
	}
}
