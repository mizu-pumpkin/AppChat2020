package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.Scrollable;

import controlador.AppChat;
import modelo.Chat;
import modelo.Mensaje;
import tds.BubbleText;

@SuppressWarnings("serial")
public class PanelChat extends JPanel implements Scrollable {

	public static final Color COLOR_MSG_RCVD = new Color(255, 255, 255);
	public static final Color COLOR_MSG_SENT = new Color(255, 153, 255);
	
	private Chat actualChat;
	private String myUsername;
	
	public PanelChat(String myUsername) {
		this.myUsername = myUsername;
		setSize(400, 100);
		//setPreferredSize(new Dimension(400, 100));
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	}
	
	public void loadChat(Chat chat) {
		removeAll();
		this.actualChat = chat;
		for (Mensaje m : actualChat.getMessages()) {
			String text = m.getBody();
			String sender = m.getSender().getUsername();
			boolean sent = sender.equals(myUsername);
			Color col = sent ? COLOR_MSG_SENT : COLOR_MSG_RCVD;
			int inout = sent ? BubbleText.SENT : BubbleText.RECEIVED;
			
			if (m.getBodyType() == Mensaje.TEXT_BODY)
				add(new BubbleText(this, text, col, sender, inout));
			else
				add(new BubbleText(this, Integer.parseInt(text), col, sender, inout, 12));
		}
		revalidate();
	}
	
	public void sendText(String username, String text) {
		if (actualChat == null) return;
		add(new BubbleText(this, text, COLOR_MSG_SENT, username, BubbleText.SENT));
		AppChat.getInstance().sendMessage(actualChat, text);
	}
	
	public void sendEmoticon(String username, int emoticon) {
		if (actualChat == null) return;
		add(new BubbleText(this, emoticon, COLOR_MSG_SENT, username, BubbleText.SENT, 12));
		AppChat.getInstance().sendMessage(actualChat, emoticon);
	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return null;
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle arg0, int arg1, int arg2) {
		return 0;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return true;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2) {
		return 0;
	}

}
