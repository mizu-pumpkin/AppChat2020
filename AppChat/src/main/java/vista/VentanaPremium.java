package vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.AppChat;
import modelo.Descuento;
import modelo.DescuentoJoven;
import modelo.DescuentoVerano;
import modelo.Usuario;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class VentanaPremium extends JFrame implements ActionListener {
	
	private static final double PRICE = 5.99; 
	private static final String VERANO = "Verano";
	private static final String JOVEN = "Joven";

	private JPanel contentPane;
	private JButton btnPremium;
	private JButton btnCancelar;
	private VentanaAppChat father;
	private JLabel lblPrice;
	private JLabel lblDiscount;
	private JLabel lblTotal;
	private JComboBox<String> comboBox;
	private double total;

	public VentanaPremium(Usuario user, VentanaAppChat father) {
		this.father = father;
		this.total = PRICE;
		setSize(400, 200);
		setResizable(false);
		setTitle("Convertirse en Premium");
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel_buy = new JPanel();
		contentPane.add(panel_buy, BorderLayout.CENTER);
		panel_buy.setLayout(new BoxLayout(panel_buy, BoxLayout.X_AXIS));
		JPanel panel_izq = new JPanel();
		panel_buy.add(panel_izq);
		
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_izq.setLayout(gbl);
		
		JLabel lblPrecio = new JLabel("Precio estándar:");
		GridBagConstraints gbc_lblPrecio = new GridBagConstraints();
		gbc_lblPrecio.anchor = GridBagConstraints.EAST;
		gbc_lblPrecio.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrecio.gridx = 1;
		gbc_lblPrecio.gridy = 1;
		panel_izq.add(lblPrecio, gbc_lblPrecio);
		
		lblPrice = new JLabel(PRICE+" €/año");
		GridBagConstraints gbc_lblPrice = new GridBagConstraints();
		gbc_lblPrice.anchor = GridBagConstraints.WEST;
		gbc_lblPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrice.gridx = 2;
		gbc_lblPrice.gridy = 1;
		panel_izq.add(lblPrice, gbc_lblPrice);
		
		JLabel lblDescuento = new JLabel("Descuento:");
		GridBagConstraints gbc_lblDescuento = new GridBagConstraints();
		gbc_lblDescuento.anchor = GridBagConstraints.EAST;
		gbc_lblDescuento.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescuento.gridx = 1;
		gbc_lblDescuento.gridy = 2;
		panel_izq.add(lblDescuento, gbc_lblDescuento);
		
		lblDiscount = new JLabel("0 €/año");
		GridBagConstraints gbc_lblSale = new GridBagConstraints();
		gbc_lblSale.anchor = GridBagConstraints.WEST;
		gbc_lblSale.insets = new Insets(0, 0, 5, 5);
		gbc_lblSale.gridx = 2;
		gbc_lblSale.gridy = 2;
		panel_izq.add(lblDiscount, gbc_lblSale);
		
		JLabel lblPrecioFinal = new JLabel("Precio descontado:");
		GridBagConstraints gbc_lblPrecioFinal = new GridBagConstraints();
		gbc_lblPrecioFinal.anchor = GridBagConstraints.EAST;
		gbc_lblPrecioFinal.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrecioFinal.gridx = 1;
		gbc_lblPrecioFinal.gridy = 3;
		panel_izq.add(lblPrecioFinal, gbc_lblPrecioFinal);
		
		lblTotal = new JLabel(total+" €/año");
		GridBagConstraints gbc_lblTotal = new GridBagConstraints();
		gbc_lblTotal.anchor = GridBagConstraints.WEST;
		gbc_lblTotal.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotal.gridx = 2;
		gbc_lblTotal.gridy = 3;
		panel_izq.add(lblTotal, gbc_lblTotal);
		
		JPanel panel_der = new JPanel();
		panel_buy.add(panel_der);
		GridBagLayout gbl_panel_der = new GridBagLayout();
		gbl_panel_der.columnWidths = new int[]{0, 0};
		gbl_panel_der.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_der.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_der.rowWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_der.setLayout(gbl_panel_der);
		
		JLabel lblDescuentosAplicables = new JLabel("Descuentos aplicables");
		GridBagConstraints gbc_lblDescuentosAplicables = new GridBagConstraints();
		gbc_lblDescuentosAplicables.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDescuentosAplicables.insets = new Insets(0, 0, 5, 0);
		gbc_lblDescuentosAplicables.gridx = 0;
		gbc_lblDescuentosAplicables.gridy = 1;
		panel_der.add(lblDescuentosAplicables, gbc_lblDescuentosAplicables);
		
		configurarCombobox(user);
		
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 2;
		panel_der.add(comboBox, gbc_comboBox);
		
		JPanel panel_btns = new JPanel();
		contentPane.add(panel_btns, BorderLayout.SOUTH);
		
		btnPremium = Graphics.makeButton("Premium");
		btnPremium.addActionListener(this);
		panel_btns.add(btnPremium);
		
		btnCancelar = Graphics.makeButton("Cancelar");
		btnCancelar.addActionListener(this);
		getRootPane().setDefaultButton(btnCancelar);
		panel_btns.add(btnCancelar);

		setVisible(true);
	}
	
	private void configurarCombobox(Usuario user) {
		comboBox = new JComboBox<>();
		comboBox.addItem("");
		if (AppChat.getInstance().isSummer())
			comboBox.addItem(VERANO);
		if (AppChat.getInstance().isYoung(user))
			comboBox.addItem(JOVEN);
		comboBox.addActionListener(this);
		comboBox.setToolTipText("Selecciona el descuento a aplicar.");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancelar) {
			dispose();
			return;
		}
		if (e.getSource() == btnPremium) {
			father.changePremium();
			JOptionPane.showMessageDialog(this,
					"Cargados "+total+" € en la cuenta.",
					"Converted to Premium", JOptionPane.INFORMATION_MESSAGE);
			dispose();
			return;
		}
		if (e.getSource() == comboBox) {
			double discount = 0;
			if (comboBox.getSelectedIndex() > 0) {
				String selected = (String) comboBox.getSelectedItem();
				Descuento d = null;
				if (selected.equals(VERANO))
					d = new DescuentoVerano();
				else if (selected.equals(JOVEN))
					d = new DescuentoJoven();
				if (d != null)
					discount = d.calcDescuento(PRICE);
			}
			total = PRICE-discount;
			total = (double) Math.round(total*100)/100;
			
			lblDiscount.setText("-"+discount+" €");
			lblTotal.setText(total+" €/año");
			return;
		}
	}

}
