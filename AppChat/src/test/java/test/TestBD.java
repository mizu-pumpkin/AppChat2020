package test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import modelo.Usuario;
import persistencia.AdaptadorUsuarioTDS;

public class TestBD {
	
	private static AdaptadorUsuarioTDS a = AdaptadorUsuarioTDS.getInstance();
	private static Usuario u1;
	private static Usuario u2;
	private static Usuario u3;
	
	@BeforeClass
	public static void create() throws ParseException {
		u1 = new Usuario("mizu", "aaa", "Marisol Zucca",
						 new SimpleDateFormat("yyyy-MM-dd").parse("1989-08-31"),
						 "mi@zucca.com", "684253969", "Ho fame");
		a.create(u1);
		u2 = new Usuario("rey", "aaa", "Rey Giraldi",
						 new SimpleDateFormat("yyyy-MM-dd").parse("1988-04-23"),
						 "rey@giraldi.com", "684208637", "");
		a.create(u2);
		u3 = new Usuario("dado", "aaa", "Davide Zucca",
						 new SimpleDateFormat("yyyy-MM-dd").parse("2017-08-09"),
						 "dado@zucca.com", "0", "Sono qui!");
		a.create(u3);
	}

	@AfterClass
	public static void delete() {
		System.out.println("---------------------------------------");
		for (Usuario u : a.getAll()) System.out.println(u);
		System.out.println("---------------------------------------");
		
		a.delete(u1);
		a.delete(u2);
		a.delete(u3);
		
		for (Usuario u : a.getAll()) System.out.println(u);
		
		System.out.println("TEST END");
	}

	@Test
	public void testGetUsuario() {
		Usuario u1b = a.get(u1.getId());
		
		assertEquals("mizu:", u1, u1b);
	}

	@Test
	public void testUpdateUsuario() {
		u1.setPassword("password1");
		a.update(u1);
		Usuario u1b = a.get(u1.getId());
		
		assertEquals("password1", u1b.getPassword());
	}

}
