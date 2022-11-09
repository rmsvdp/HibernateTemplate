package mariadbApp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
//import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import reverse.*;

public class MainApp {

	private static SessionFactory hbsf;

	public static void main(String[] args) {
	
	Alumnos miAlumno = new Alumnos();	
	Asignaturas  asig = new Asignaturas();
	Notas miNota = new Notas();  // Nota para acceso a datos
	
	System.out.println("--- Inicio de la sesión Hibernate");
	hbsf = HibernateUtil.getSessionFactory();			// 1.- Recuperar el sessionFactory
	
	Session hbse = hbsf.openSession();					// 2.- Crear session para operar
	Transaction hbtr = hbse.beginTransaction(); // 3.- Crear transacción
// -----------------------------------------------------------------------------------
//  INSERCIÓN DE REGISTROS	
// Insertar alumno	------------------------------------------------------------------
		miAlumno.setIdn("40000001");
		miAlumno.setEmail("damFFF@vdp.com");
		miAlumno.setApenom("Blanco Terr, Urbano");
		miAlumno.setProv("MADRID");
		miAlumno.setDirecc("C.-Mayor, 27");
		hbse.save(miAlumno);
	hbtr.commit();
// Insertar nota
	hbtr.begin();
		asig = (Asignaturas) hbse.load(Asignaturas.class,486);
		//System.out.printf("Asignatura codigo : %d | %s\n" ,asig.getCdn(),asig.getNombre());
		// b.- Preparar la nota a insertar	
		miNota.setAsignaturas(asig); // Asigno la asignatura
		miNota.setAlumnos(miAlumno);		 // el alumno
		miNota.setNota(9);		 // La nota	
		miNota.setId(new NotasId(asig.getCdn(),miAlumno.getIdn()));	// La clave de relación
		//System.out.printf("Nota : %s | %s\n" ,n486.getAsignaturas().getNombre(),n486.getAlumnos().getApenom());
		hbse.save(miNota);  // Lo guardo
		System.out.printf("\n\nAlumno insertado: %s",miAlumno.getApenom());
		System.out.printf("\nNota asignada: %s\n",miNota.getNota());
		espera(1);
// -----------------------------------------------------------------------------------
//  BORRADO DE REGISTROS	
	// Borrar registros
	hbtr.commit();
	hbtr.begin();		
		hbse.delete(miNota);
		hbse.delete(miAlumno);
	hbtr.commit();
	System.out.printf("\n\nAlumno eliminado: %s",miAlumno.getApenom());
	System.out.printf("\nNota eliminada: %s\n",miNota.getNota()+"\n");
	espera(1);
	
// A.- Cerrar Sessión
	hbse.close();
// -----------------------------------------------------------------------------------
// CONSULTA DE INFORMACIÓN
// Listar información con métodos get-load
	 //listaNotasAlumno("12500501");
	// espera(1);
	// listaAlumnosAsig(486);
	// espera(1);
// Listar información con HQL
	// ejemploHql();
// Listar información con SQL nativo
	 ejemploSqlNativo();
// Uso del objeto criteria
	 ejemploCriteria();
// -----------------------------------------------------------------------------------
	System.out.println("--- Fin de la sesión Hibernate");	
	}

	public static void listaAlumnosAsig(int asigId) {
		Session hbses = hbsf.openSession();		// Creamos una sesión
		Transaction hbtr = hbses.beginTransaction(); 
		Alumnos miAlumno = new Alumnos();	
		Asignaturas  miAsignatura ;
		Notas miNota;
		
		miAsignatura = (Asignaturas) hbses.get(Asignaturas.class, (int) asigId);
		if (miAsignatura != null) {
			Set<Notas> notas_al = miAsignatura.getNotases();
			Iterator<Notas> it = notas_al.iterator();
			System.out.println("\nNotas de la Asignatura: " + miAsignatura.getNombre());
			System.out.println("---------------------------------------------------------");
			while (it.hasNext()) {
				
				miNota = it.next();
				miAlumno= miNota.getAlumnos();
				System.out.println(padRight(miAlumno.getApenom(),32)+ ": "+ miNota.getNota());
//	ACTUALIZACIÓN DE REGISTROS ------------------------------------------------
//				miNota.setNota(8);
//				hbses.update(miNota);
			}
			System.out.println("---------------------------------------------------------");
			hbtr.commit();
			hbses.close();
			
			
		} else {
			System.out.println("Asignatura no encontrada");
		}
	}
	
	public static void listaNotasAlumno(String alumId) {
		
		Session hbses = hbsf.openSession();		// Creamos una sesión
		
		Alumnos miAlumno = new Alumnos();	
		Asignaturas  as = new Asignaturas();
		Notas miNota;
		miAlumno = (Alumnos) hbses.get(Alumnos.class, (String) alumId); // Recupero la asignatura de acceso a datos
		if (miAlumno != null) {
		Set<Notas> notas_al = miAlumno.getNotases();
		Iterator<Notas> it = notas_al.iterator();
		System.out.println("\nNotas del Alumno: " + miAlumno.getApenom());
		System.out.println("---------------------------------------------------------");
		while (it.hasNext()) {
			
			miNota = it.next();
			as= miNota.getAsignaturas();
			System.out.println(padRight(as.getNombre(),32)+ ": "+ miNota.getNota());
			
		}
		System.out.println("---------------------------------------------------------");
		hbses.close();	
		}
		else {
			System.out.println("Alumno no encontrado");

		}
	}
	
	public static void ejemploHql() {
		Session hbses = hbsf.openSession();		// Creamos una sesión
		String ssql ="FROM Alumnos A WHERE A.idn = '12500501'";
		String ssql2 ="FROM Alumnos A WHERE A.idn = :e_id";
		Alumnos al = new Alumnos();
		Query q1 = hbses.createQuery(ssql);
		Query q2 = hbses.createQuery(ssql2);
		q2.setParameter("e_id", "12500501");
		List rs = q1.list();
		List rs2 = q2.list();
		Iterator<Alumnos> it = rs.iterator();
		Iterator<Alumnos> it2 = rs.iterator();
		System.out.println();
		// Query Fija
		while(it.hasNext()) {
			al = it.next();
			System.out.println("ID    :" + al.getIdn());
			System.out.println("Nombre:" + al.getApenom());
			System.out.println("Email :" + al.getEmail());
		}
		System.out.println();
		// Query Parametrizada
		while(it2.hasNext()) {
			al = it2.next();
			System.out.println("ID    :" + al.getIdn());
			System.out.println("Nombre:" + al.getApenom());
			System.out.println("Email :" + al.getEmail());
		}

		hbses.close();							// Cerramos la sesión
	}

	
	public static void ejemploSqlNativo() {
		Session hbses = hbsf.openSession();		// Creamos una sesión
		
		String ssql = " ";

		// Query de escalares (varios campos de distintas tablas)
		// Listado de alumnos ordenado alfabéticamente
		ssql = "SELECT  distinct NOMBRE as nmod, APENOM"
				+ " FROM alumnos, asignaturas"
				+ " WHERE NOMBRE = 'Acceso a Datos'"
				+ " ORDER BY APENOM";
		NativeQuery nq = hbses. createNativeQuery(ssql);
		nq.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List nq_esc = nq.list();
		
	         for(Object registro : nq_esc) {
	            Map tupla = (Map)registro;
	            System.out.print("Asignatura: " + tupla.get("nmod")); 
	            System.out.println(", Alumno: " + tupla.get("APENOM")); 
	         }
	 	// Query de entidades (varios campos de distintas tablas)
	 	// Listado de alumnos ordenado alfabéticamente		
	 	ssql = "SELECT  * FROM alumnos";
        NativeQuery nq2 = hbses. createNativeQuery(ssql);
        nq2.addEntity(Alumnos.class);
        List nq_ent = nq2.list();
        System.out.println("\n" +padRight("Apellidos",32) + padRight("Dirección",32) + "Email\n"); 
        for (Iterator iterator = nq_ent.iterator(); iterator.hasNext();){
           Alumnos eal = (Alumnos) iterator.next(); 
           System.out.print(padRight(eal.getApenom(),32)); 
           System.out.print(padRight(eal.getDirecc(),32)); 
           System.out.println(padRight(eal.getEmail(),32)); 
        }	
		hbses.close();							// Cerramos la sesión
		
	}

	public static void ejemploCriteria() {
		Session hbses = hbsf.openSession();		// Creamos una sesión
		String ssql;
		
		
		Criteria cr = hbses. createCriteria(Alumnos.class);
		//cr.add(Restrictions.eq("idn", "12500501"));
		cr.add(Restrictions.like("apenom","s%"));
		List results = cr.list();
		
	 	
        System.out.println("\n" +padRight("Apellidos",32) + padRight("Dirección",32) + "Email\n"); 
        for (Iterator iterator = results.iterator(); iterator.hasNext();){
           Alumnos eal = (Alumnos) iterator.next(); 
           System.out.print(padRight(eal.getApenom(),32)); 
           System.out.print(padRight(eal.getDirecc(),32)); 
           System.out.println(padRight(eal.getEmail(),32)); 
        }
		
		hbses.close();							// Cerramos la sesión
		
	}
	
	public static void espera(int t ) {
		try {
			Thread.sleep(t*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String padRight(String s,int tam) {
		int t = tam - s.length();
		if (t> 0) {
		s = s + " ".repeat(t);}
		return s;
	}
}
