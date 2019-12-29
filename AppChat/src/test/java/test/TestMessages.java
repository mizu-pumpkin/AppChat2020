package test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Usuario;
import persistencia.AdaptadorContactoIndividualTDS;
import persistencia.AdaptadorGrupoTDS;
import persistencia.AdaptadorUsuarioTDS;

public class TestMessages {
	
	private static AdaptadorUsuarioTDS aUsuario = AdaptadorUsuarioTDS.getInstance();
	private static AdaptadorContactoIndividualTDS aContInd = AdaptadorContactoIndividualTDS.getInstance();
	private static AdaptadorGrupoTDS aGrupo = AdaptadorGrupoTDS.getInstance();
	private static Usuario umizu = aUsuario.get(6209);
	private static Usuario u1, u2;
	private static ContactoIndividual c1, c2, c3;
	private static Grupo g1;
	
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
		
		c1 = new ContactoIndividual("mizu", "684253969");
		aContInd.create(c1);
		u1.addContact(c1);
		u2.addContact(c1);
		
		c2 = new ContactoIndividual("rey", "684208637");
		aContInd.create(c2);
		umizu.addContact(c2);
		u2.addContact(c2);
		
		c3 = new ContactoIndividual("dado", "0");
		aContInd.create(c3);
		umizu.addContact(c3);
		u1.addContact(c3);
		
		// Grupo
		
		g1 = new Grupo("zucchini", u1);
		g1.addMember(c1);
		g1.addMember(c2);
		g1.addMember(c3);
		aGrupo.create(g1);
		
		u1.addAdminGroup(g1);
		umizu.addContact(g1);
		u1.addContact(g1);
		u2.addContact(g1);
		
		aUsuario.update(umizu);
		aUsuario.update(u1);	
		aUsuario.update(u2);
	}

	@AfterClass
	public static void delete() {
		System.out.println("---------------------------------------");
		for (Usuario u : aUsuario.getAll()) System.out.println(u);
		for (Contacto c : aContInd.getAll()) System.out.println(c);
		for (Contacto g : aGrupo.getAll()) System.out.println(g);
		System.out.println("---------------------------------------");
		
		for (Usuario u: aUsuario.getAll())
			if (!u.getUsername().equals("mizu")) aUsuario.delete(u);
		
		for (Grupo g: aGrupo.getAll()) aGrupo.delete(g);
		for (ContactoIndividual c: aContInd.getAll()) aContInd.delete(c);
		
		System.out.println("---------------------------------------");
		for (Usuario u : aUsuario.getAll()) System.out.println(u);
		for (Contacto c : aContInd.getAll()) System.out.println(c);
		for (Contacto g : aGrupo.getAll()) System.out.println(g);
		System.out.println("---------------------------------------");
		System.out.println("TEST END");
	}

	@Test
	public void test() {
	}

}
