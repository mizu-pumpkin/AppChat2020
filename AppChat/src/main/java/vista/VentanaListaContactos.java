package vista;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import modelo.ChatIndividual;
import modelo.Usuario;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Component;

@SuppressWarnings({"serial"})
public class VentanaListaContactos extends JFrame implements ActionListener{
	
	private final static String userhome = System.getProperty("user.home");
	
	private Usuario user;

	private JPanel contentPane;
	private JButton btnPDF;

	private JTable table;
	
	
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

		table = new JTable(new MyTableModel(user.getPrivateChats()));
		table.setRowHeight(Graphics.SIZE_AVATAR_SMALL);
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane);
		
		configurarBotonPDF();
	}
	
	class MyTableModel extends AbstractTableModel {
		private String[] columnNames = {"Imagen","Nombre","Teléfono","Grupos"};
		private Object[][] data;
		
		public MyTableModel(List<ChatIndividual> contacts) {
			data = new Object[contacts.size()][4];
			int i = 0;
			for (ChatIndividual c : contacts) {
				data[i][0] = Graphics.makeAvatar(c.getAvatar(), Graphics.SIZE_AVATAR_SMALL);
				data[i][1] = c.getName();
				data[i][2] = c.getPhone();
				data[i][3] = "//TODO";//TODO
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
		
		btnPDF.addActionListener(this);
	}
	
    public void generarPDF() throws FileNotFoundException, DocumentException {
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(userhome+"\\Contactos.pdf"));
        doc.open();
        
        PdfPTable pdfTable = new PdfPTable(3);
        pdfTable.addCell(makeTitleCell("Nombre"));
        pdfTable.addCell(makeTitleCell("Teléfono"));
        pdfTable.addCell(makeTitleCell("Grupos"));
        for (int i=0; i<table.getRowCount(); i++) {
        	pdfTable.addCell(new Paragraph((String)table.getValueAt(i, 1)));
        	pdfTable.addCell(new Paragraph((String)table.getValueAt(i, 2)));
        	pdfTable.addCell(new Paragraph((String)table.getValueAt(i, 3)));
        }
        doc.add(pdfTable);
        
        doc.close();
     }
    
    public PdfPCell makeTitleCell(String phrase) {
        PdfPCell cell = new PdfPCell(new Phrase(phrase));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(1);
        return cell;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnPDF) {
			try {
				generarPDF();
				JOptionPane.showMessageDialog(
					this,
					"PDF generado correctamente y guardado en "+userhome+".",
					"PDF generated successfully",
					JOptionPane.INFORMATION_MESSAGE
				);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(
					this,
					"No se ha podido generar el pdf en "+userhome+".",
					"Error saving PDF",
					JOptionPane.ERROR_MESSAGE
				);
			}
			return;
		}
	}

}
