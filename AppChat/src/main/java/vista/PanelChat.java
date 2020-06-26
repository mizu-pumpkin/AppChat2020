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
	
	private String myUsername;
	private Chat actualChat;
	
	public PanelChat(String myUsername) {
		this.myUsername = myUsername;
		setSize(400, 100);
		//setPreferredSize(new Dimension(400, 100));
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	}
	
	public void loadChat(Chat chat) {
		actualChat = chat;
		String interlocutor = actualChat.getOwner().getUsername();
		for (Mensaje m : actualChat.getMessages()) {
			String text = m.getBody();
			String sender = m.getSender().getUsername();
			boolean rcvd = sender.equals(interlocutor);
			Color col = rcvd ? COLOR_MSG_RCVD : COLOR_MSG_SENT;
			int inout = rcvd ? BubbleText.RECEIVED : BubbleText.SENT;
			
			if (m.getBodyType() == Mensaje.TEXT_BODY)
				add(new BubbleText(this, text, col, sender, inout));
			else
				add(new BubbleText(this, Integer.parseInt(text), col, sender, inout, 12));
		}
		revalidate();
	}
	
	public void sendText(String text) {
		if (actualChat == null) return;
		add(new BubbleText(this, text, COLOR_MSG_SENT, myUsername, BubbleText.SENT));
		AppChat.getInstance().sendMessage(actualChat, text);
	}
	
	public void sendEmoticon(int emoticon) {
		add(new BubbleText(this, emoticon, COLOR_MSG_SENT, myUsername, BubbleText.SENT, 12));
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
