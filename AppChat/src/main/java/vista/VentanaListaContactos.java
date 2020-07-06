package vista;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

import modelo.ChatIndividual;
import modelo.Usuario;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Component;

@SuppressWarnings({"serial"})
public class VentanaListaContactos extends JFrame {
	
	private Usuario user;

	private JPanel contentPane;
	private JButton btnPDF;
	
	
	/*
	Por último, con la opción mostrar contactos mostrará una ventana con una tabla con 3 columnas: nombre de
	contacto, imagen en la cuenta, y teléfono, y nombres de los grupos que comparten. Las filas estarán
	ordenadas por el nombre del contacto. Esta información se puede generar en un fichero pdf si el usuario es
	premium (no es necesario que se dibuje la tabla ni considere la imagen).
	*/
	public VentanaListaContactos(Usuario user) {
		this.user = user;
		initialize();
		setVisible(true);
	}
	
	private void initialize() {
		setTitle("Contactos");
		setBounds(100, 100, 450, 300);
		setMinimumSize(new Dimension(300, 250));
		contentPane = new JPanel();
		contentPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);

		JTable tabla = new JTable(new MyTableModel(user.getPrivateChats()));
		tabla.setRowHeight(Graphics.SIZE_AVATAR_SMALL);
		JScrollPane scrollPane = new JScrollPane(tabla);
		contentPane.add(scrollPane);
		
		configurarBotonPDF();
	}
	
	class MyTableModel extends AbstractTableModel {
		private String[] columnNames = {"Imagen","Nombre de contacto","Teléfono"};
		private Object[][] data;
		
		public MyTableModel(Collection<ChatIndividual> contacts) {
			data = new Object[contacts.size()][3];
			int i = 0;
			for (ChatIndividual c : contacts) {
				data[i][0] = Graphics.makeAvatar(c.getAvatar(), Graphics.SIZE_AVATAR_SMALL);
				data[i][1] = c.getName();
				data[i][2] = c.getPhone();
				i++;
			}
		}
		
		public int getColumnCount() {
			return columnNames.length;
		}
		public int getRowCount() {
			return data.length;
		}
		public String getColumnName(int col) {
			return columnNames[col];
		}
		public Object getValueAt(int row, int col) {
			return data[row][col];
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int column)
        {
            return getValueAt(0, column).getClass();
        }
	}
	
	private void configurarBotonPDF() {
		btnPDF = new JButton("PDF");
		btnPDF.setAlignmentX(Component.RIGHT_ALIGNMENT);
		contentPane.add(btnPDF);
		if (!user.isPremium()) btnPDF.setVisible(false);
		
		btnPDF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO imprimir PDF
			}
		});
	}

}
