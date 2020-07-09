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

	private static final AppChat appChat = AppChat.getInstance();
	
	public static final Color COLOR_MSG_SENT = Graphics.MAIN;
	public static final Color COLOR_MSG_RCVD = Graphics.SECONDARY;
	
	private Chat actualChat;
	private String myUsername;
	
	public PanelChat() {
		this.myUsername = appChat.getUsuarioActual().getUsername();
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	}
	
	// Para que no se ejecute el botón de búsqueda de mensaje si no se ha elegido un chat.
	public boolean hasChat() {
		return actualChat != null;
	}
	
	public Chat getActualChat() {
		return actualChat;
	}
	
	public Chat removeActualChat() {
		Chat old = actualChat;
		actualChat = null;
		removeAll();
		revalidate();
		return old;
	}
	
	public void loadChat(Chat chat) {
		this.actualChat = chat;
		removeAll();
		for (Mensaje m : chat.getMessages()) {
			String text = m.getBody();
			String sender = m.getSenderName();
			
			Color col;
			int inout;
			if (m.isSender(myUsername)) {
				col = COLOR_MSG_SENT;
				inout = BubbleText.SENT;
			} else {
				col = COLOR_MSG_RCVD;
				inout = BubbleText.RECEIVED;
			}
			
			if (m.getBodyType() == Mensaje.TEXT_BODY)
				add(new BubbleText(this, text, col, sender, inout));
			else
				add(new BubbleText(this, Integer.parseInt(text), col, sender, inout, 12));
		}
		revalidate();
	}

	public void sendText(String text) {
		if (hasChat()) {
			add(new BubbleText(this, text, COLOR_MSG_SENT, myUsername, BubbleText.SENT));
			appChat.sendMessage(actualChat, text);
		}
	}
	
	public void sendEmoji(int emoji) {
		if (hasChat()) {
			add(new BubbleText(this, emoji, COLOR_MSG_SENT, myUsername, BubbleText.SENT, 12));
			appChat.sendMessage(actualChat, emoji);
		}
	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return null;
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
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		return 16;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		return 16;
	}

}
