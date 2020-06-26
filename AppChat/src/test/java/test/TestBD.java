package test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import controlador.AppChat;
import modelo.Usuario;
import persistencia.AdaptadorUsuarioTDS;

public class TestBD {
	
	private static AdaptadorUsuarioTDS aU = AdaptadorUsuarioTDS.getInstance();
	private static Usuario u1;
	private static Usuario u2;
	
	public static void createTesters() throws ParseException {
		aU.create(new Usuario("mizu", "aaa", "Marisol Zucca",
			 new SimpleDateFormat("yyyy-MM-dd").parse("1989-08-31"),
			 "mizu@um.es", "684253969", "Ho fame!"));

		aU.create(new Usuario("rey", "aaa", "Reyes Giraldi",
			 new SimpleDateFormat("yyyy-MM-dd").parse("1988-04-23"),
			 "rey@um.es", "684208637", "Stupida sexy Catra..."));
	}
	
	@BeforeClass
	public static void create() throws ParseException {
		createTesters();
		u1 = new Usuario("gabrio", "aaa", "Gabriele Zucca",
						 new SimpleDateFormat("yyyy-MM-dd").parse("2000-10-30"),
						 "gabri@zucca.com", "111111111", "Chopo!");
		aU.create(u1);
		u2 = new Usuario("dado", "aaa", "Davide Zucca",
						 new SimpleDateFormat("yyyy-MM-dd").parse("2017-08-09"),
						 "dado@zucca.com", "000000000", "Sono qui!");
		aU.create(u2);
	}

	@AfterClass
	public static void delete() {
		System.out.println("---------------------------------------");
		for (Usuario u : aU.readAll()) {
			System.out.println(u);
			if (!u.getUsername().equals("mizu") && !u.getUsername().equals("rey")) 
				aU.delete(u);
		}
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
