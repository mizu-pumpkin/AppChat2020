package test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import modelo.Chat;
import modelo.ChatIndividual;
import modelo.ChatGrupo;
import modelo.Usuario;
import persistencia.AdaptadorChatTDS;
import persistencia.AdaptadorUsuarioTDS;

public class TestMessages {
	
	private static AdaptadorUsuarioTDS aUsuario = AdaptadorUsuarioTDS.getInstance();
	private static AdaptadorChatTDS aChat = AdaptadorChatTDS.getInstance();
	private static Usuario umizu = aUsuario.read(6851);
	private static Usuario u1, u2;
	private static ChatIndividual c1, c2, c3;
	private static ChatGrupo g1;
	
	@BeforeClass
	public static void create() throws ParseException {
		
		// Usuario
		
		u1 = new Usuario("rey", "aaa", "Rey Giraldi",
						 new SimpleDateFormat("yyyy-MM-dd").parse("1988-04-23"),
						 "rey@giraldi.com", "684208637", "");
		aUsuario.create(u1);
		
		u2 = new Usuario("dado", "aaa", "Davide Zucca",
						 new SimpleDateFormat("yyyy-MM-dd").parse("2017-08-09"),
						 "dado@zucca.com", "0", "Sono qui!");
		aUsuario.create(u2);
		
		// ContactoIndividual
		
		c1 = new ChatIndividual("mizu");
		aChat.create(c1);
		u1.addChat(c1);
		u2.addChat(c1);
		
		c2 = new ChatIndividual("rey");
		aChat.create(c2);
		//umizu.addContact(c2);
		u2.addChat(c2);
		
		c3 = new ChatIndividual("dado");
		aChat.create(c3);
		//umizu.addContact(c3);
		u1.addChat(c3);
		
		// Grupo
		/*
		g1 = new Grupo("zucchini", u1);
		g1.addMember(c1);
		g1.addMember(c2);
		g1.addMember(c3);
		aGrupo.create(g1);
		
		u1.addAdminGroup(g1);
		//umizu.addContact(g1);
		u1.addContact(g1);
		u2.addContact(g1);
		
		//aUsuario.update(umizu);
		aUsuario.update(u1);	
		aUsuario.update(u2);*/
	}

	@AfterClass
	public static void delete() {
		System.out.println("---------------------------------------");
		for (Usuario u : aUsuario.readAll()) System.out.println(u);
		for (Chat c : aChat.readAll()) System.out.println(c);
		System.out.println("---------------------------------------");
		
		for (Usuario u: aUsuario.readAll())
			if (!u.getUsername().equals("mizu")) aUsuario.delete(u);
		
		for (Chat c: aChat.readAll()) aChat.delete(c);
		
		System.out.println("---------------------------------------");
		for (Usuario u : aUsuario.readAll()) System.out.println(u);
		for (Chat c : aChat.readAll()) System.out.println(c);
		System.out.println("---------------------------------------");
		System.out.println("TEST END");
	}

	@Test
	public void test() {
	}

}
