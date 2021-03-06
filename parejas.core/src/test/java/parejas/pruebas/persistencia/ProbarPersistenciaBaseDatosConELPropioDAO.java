package parejas.pruebas.persistencia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import parejas.modelo.Persona;
import parejas.persistencia.PersonaDao;
import parejas.persistencia.PersonaDaoJDBCImpl;

public class ProbarPersistenciaBaseDatosConELPropioDAO {
	//SUT
	private static PersonaDao personaDao;
	//Helper 
	private List<Persona> personas;

	@BeforeClass
	public static void alPrincipio() throws Exception {
		//creamos el pool de conexiones
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:mysql://localhost:3306/test");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		dataSource.setMinIdle(2);
		dataSource.setMaxActive(4);
		dataSource.setValidationQuery("select true");
		//decidimos que implementacion testear
		personaDao=new PersonaDaoJDBCImpl(dataSource);
		
		
	}

	@AfterClass
	public static void alFinal() throws Exception {
	}

	

	@Before
	public void prepara() throws Exception {
		//preparar el entorno
		personas = 
				Arrays.asList(
					new Persona(1L,"pepe",77,1.67f,Persona.HOMBRE),
					new Persona(2L,"luis",62,1.77f,Persona.HOMBRE),
					new Persona(3L,"ana",71,1.77f,Persona.MUJER),
					new Persona(4L,"maria",79,1.57f,Persona.MUJER));
	}

	@After
	public void limpia() throws Exception {
		//List<Persona> listado = personaDao.listar();
		//for (Persona persona : listado) {
			//personaDao.borrar(persona);
		//}
	}

	@Test
	//@Category(Lentos.class)
	//@Ignore
	public void pruebaPersonaDao() {
			
		int ti=personaDao.listar().size();
		for (Persona persona : personas) {
			personaDao.guardar(persona);
		}
		int tf=personaDao.listar().size();
		//Assert.assertEquals("se incrementa en...",ti+personas.size(), ti);
		assertEquals("se incrementa en...",ti+personas.size(), tf);
		
		Persona persona = personaDao.buscar(1L);
		assertNotNull(persona);
		assertEquals("pepe", persona.getNombre());
		
		personaDao.borrar(persona);
		persona = personaDao.buscar(1L);
		assertNull(persona);
		
		
	}

}
