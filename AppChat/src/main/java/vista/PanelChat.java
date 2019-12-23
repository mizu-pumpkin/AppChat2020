package vista;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import tds.BubbleText;

@SuppressWarnings("serial")
public class PanelChat extends JPanel {

	public static final Color MSG_RECEIVED_COLOR = new Color(255, 255, 255);
	public static final Color MSG_SENT_COLOR = new Color(255, 153, 255);
	
	/**
	 * Create the panel.
	 */
	public PanelChat() {
		JPanel chat = this;
		
		chat.setSize(400, 100);
		//chat.setPreferredSize(new Dimension(400, 100));
		chat.setLayout(new BoxLayout(chat,BoxLayout.Y_AXIS));

		BubbleText burbuja;
		burbuja=new BubbleText(chat,"A", MSG_RECEIVED_COLOR, "Rey", BubbleText.RECEIVED);
		chat.add(burbuja);
		burbuja=new BubbleText(chat,"B", MSG_SENT_COLOR, "Mizu", BubbleText.SENT);
		chat.add(burbuja);
		burbuja=new BubbleText(chat,"C", MSG_RECEIVED_COLOR, "Rey", BubbleText.RECEIVED);
		chat.add(burbuja);
		burbuja=new BubbleText(chat,"D", MSG_SENT_COLOR, "Mizu", BubbleText.SENT);
		chat.add(burbuja);
		burbuja=new BubbleText(chat,"E", MSG_RECEIVED_COLOR, "Rey", BubbleText.RECEIVED);
		chat.add(burbuja);
		burbuja=new BubbleText(chat,"EH?", MSG_SENT_COLOR, "Mizu", BubbleText.SENT);
		chat.add(burbuja);
		burbuja=new BubbleText(chat,"E!", MSG_RECEIVED_COLOR, "Rey", BubbleText.RECEIVED);
		chat.add(burbuja);
		burbuja=new BubbleText(chat,"Aaah!", MSG_SENT_COLOR, "Mizu", BubbleText.SENT);
		chat.add(burbuja);
		burbuja=new BubbleText(chat,"B", MSG_RECEIVED_COLOR, "Rey", BubbleText.RECEIVED);
		chat.add(burbuja);
		burbuja=new BubbleText(chat,"C", MSG_SENT_COLOR, "Mizu", BubbleText.SENT);
		chat.add(burbuja);
		burbuja=new BubbleText(chat,"D", MSG_RECEIVED_COLOR, "Rey", BubbleText.RECEIVED);
		chat.add(burbuja);
		burbuja=new BubbleText(chat,"E", MSG_SENT_COLOR, "Mizu", BubbleText.SENT);
		chat.add(burbuja);
		burbuja=new BubbleText(chat,"EH??", MSG_RECEIVED_COLOR, "Rey", BubbleText.RECEIVED);
		chat.add(burbuja);
		burbuja=new BubbleText(chat,"E!!", MSG_SENT_COLOR, "Mizu", BubbleText.SENT);
		chat.add(burbuja);
		burbuja=new BubbleText(chat,"Aaah!", MSG_RECEIVED_COLOR, "Rey", BubbleText.RECEIVED);
	}
	
	public void sendText(String text, String username) {
		add(new BubbleText(this, text, Color.GREEN, username, BubbleText.SENT, 12));
		this.revalidate();
	}
	
	public void sendEmoticon(int emoticon, String username) {
		add(new BubbleText(this, emoticon, Color.GREEN, username, BubbleText.SENT, 12));
	}

}
