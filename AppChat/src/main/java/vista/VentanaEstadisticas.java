package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.SwingWrapper;

import modelo.Usuario;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

@SuppressWarnings("serial")
public class VentanaEstadisticas extends JFrame {
	
	private JPanel contentPane;

	public VentanaEstadisticas(Usuario user) {
		setTitle("Estadísticas de uso");
		setSize(300, 200);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{274, 0};
		gbl_contentPane.rowHeights = new int[]{96, 104, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblSeleccionaLaGrfica = new JLabel("Seleccionar tipo de gráfico:");
		GridBagConstraints gbc_lblSeleccionaLaGrfica = new GridBagConstraints();
		gbc_lblSeleccionaLaGrfica.anchor = GridBagConstraints.SOUTH;
		gbc_lblSeleccionaLaGrfica.insets = new Insets(0, 0, 5, 0);
		gbc_lblSeleccionaLaGrfica.gridx = 0;
		gbc_lblSeleccionaLaGrfica.gridy = 0;
		contentPane.add(lblSeleccionaLaGrfica, gbc_lblSeleccionaLaGrfica);
		
		JPanel panel_btns = new JPanel();
		GridBagConstraints gbc_panel_btns = new GridBagConstraints();
		gbc_panel_btns.anchor = GridBagConstraints.NORTH;
		gbc_panel_btns.gridx = 0;
		gbc_panel_btns.gridy = 1;
		contentPane.add(panel_btns, gbc_panel_btns);
		panel_btns.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnPie = Graphics.makeButton("Tarta");
		panel_btns.add(btnPie);
		btnPie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarTarta(user);
			}
		});
		
		JButton btnBar = Graphics.makeButton("Histograma");
		panel_btns.add(btnBar);
		btnBar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarHistograma(user);
			}
		});
		setVisible(true);
	}
	
	private void mostrarTarta(Usuario user) {
		Thread t = new Thread(new Runnable() {
		    @Override
		    public void run() {
			    new SwingWrapper<PieChart>(new DiagramaTarta(user).getChart())
			    	.displayChart()
			    	.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		    }
		   });
		t.start();
	}

	public void mostrarHistograma(Usuario user) {
		Thread t = new Thread(new Runnable() {
		    @Override
		    public void run() {
			    new SwingWrapper<CategoryChart>(new Histograma(user).getChart())
			    	.displayChart()
			    	.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		    }
		   });
		t.start();
	}

}
