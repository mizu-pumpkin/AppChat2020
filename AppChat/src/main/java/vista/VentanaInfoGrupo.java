package vista;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import modelo.ChatGrupo;
import modelo.ChatIndividual;

import javax.swing.ListCellRenderer;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;


@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class VentanaInfoGrupo extends JFrame {
	
	public VentanaInfoGrupo(ChatGrupo group) {
		initialize(group);
		setVisible(true);
	}
	
	private void initialize(ChatGrupo group) {
		setSize(300, 300);
		setMinimumSize(new Dimension(300, 300));
		setLocationRelativeTo(null);
		setTitle(group.getName());
		JPanel contentPane = new JPanel();
		setContentPane(contentPane);

		DefaultListModel model = new DefaultListModel();
		for (ChatIndividual c : group.getMembers())
			model.addElement(c);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JList list = new JList(model);
		list.setBackground(Graphics.WHITE);
		list.setCellRenderer(createListRenderer());
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(list);
		scrollPane.setBackground(Graphics.BACKGROUND);
		scrollPane.setViewportBorder(new TitledBorder(
				new LineBorder(Graphics.DARK),
				"Miembros",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null, null));
		contentPane.add(scrollPane);
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
					label.setText(contact.getUsername()+" ["+contact.getPhone()+"]");
					label.setBackground(Graphics.WHITE);
					label.setBorder(new LineBorder(Graphics.WHITE));
				}
				return c;
			}
		};
	}

}
