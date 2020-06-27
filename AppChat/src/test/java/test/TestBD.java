package test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import controlador.AppChat;
import modelo.Chat;
import modelo.Mensaje;
import modelo.Usuario;
import persistencia.AdaptadorChatTDS;
import persistencia.AdaptadorMensajeTDS;
import persistencia.AdaptadorUsuarioTDS;

public class TestBD {
	
	private static AdaptadorUsuarioTDS aU = AdaptadorUsuarioTDS.getInstance();
	private static AdaptadorMensajeTDS aM = AdaptadorMensajeTDS.getInstance();
	private static AdaptadorChatTDS aC = AdaptadorChatTDS.getInstance();
	private static Usuario t1;
	private static Usuario t2;
	private static Usuario t3;
	private static Usuario u1;
	private static Usuario u2;
	
	public static void createTesters() throws ParseException {
		t1 = new Usuario("mizu", "aaa", "Marisol Zucca",
				new SimpleDateFormat("yyyy-MM-dd").parse("1989-08-31"),
				"mizu@um.es", "111", "Ho fame!");
		t2 = new Usuario("rey", "aaa", "Reyes Giraldi",
				new SimpleDateFormat("yyyy-MM-dd").parse("1988-04-23"),
				"rey@um.es", "222", "Stupida sexy Catra...");
		t3 = new Usuario("edu", "aaa", "Eduardo Martínez",
				new SimpleDateFormat("yyyy-MM-dd").parse("1999-12-26"),
				"edu@um.es", "333", "");
		aU.create(t1);
		aU.create(t2);
		aU.create(t3);
	}
	
	@BeforeClass
	public static void create() throws ParseException {
		createTesters();
		u1 = new Usuario("gabrio", "aaa", "Gabriele Zucca",
						 new SimpleDateFormat("yyyy-MM-dd").parse("2000-10-30"),
						 "gabri@zucca.com", "111111111", "Chopo!");
		u2 = new Usuario("dado", "aaa", "Davide Zucca",
						 new SimpleDateFormat("yyyy-MM-dd").parse("2017-08-09"),
						 "dado@zucca.com", "000000000", "Sono qui!");
		aU.create(u1);
		aU.create(u2);
	}

	@AfterClass
	public static void delete() {
		System.out.println("---------------------------------------");
		for (Usuario u : aU.readAll()) {
			System.out.println(u);
			if (u.getId()!=1 && u.getId()!=14 && u.getId()!=27) 
				aU.delete(u);
		}
		
		//for (Mensaje m : aM.readAll()) System.out.println(m);
		//for (Chat c : aC.readAll()) System.out.println(c);
		System.out.println("---------------------------------------");
		
		for (Usuario u : aU.readAll()) System.out.println(u);
		
		System.out.println("TEST END");
	}
	
	@Test
	public void testRegister() throws ParseException {
		boolean ok = AppChat.getInstance().register(
				"gabrio",
				"aaa",
				"Gaga",
				new SimpleDateFormat("yyyy-MM-dd").parse("2000-10-30"),
				"ga@ga.com",
				"222222222",
				""
				);
		assertTrue("repetido", !ok);
	}

	@Test
	public void testGetUsuario() {
		Usuario u1b = aU.read(u1.getId());
		
		assertEquals("gabrio:", u1, u1b);
	}

	@Test
	public void testUpdateUsuario() {
		u1.setPassword("1234");
		aU.update(u1);
		Usuario u1b = aU.read(u1.getId());
		
		assertEquals("1234", u1b.getPassword());
	}

}
