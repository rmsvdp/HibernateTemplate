package mariadbApp;

import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


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
// -----------------------------------------------------------------------------------
//  BORRADO DE REGISTROS	
	// Borrar registros
	hbtr.commit();
	hbtr.begin();		
		hbse.delete(miNota);
		hbse.delete(miAlumno);
	hbtr.commit();
	
	System.out.printf("Alumno insertado: %s",miAlumno.getApenom());
	System.out.printf("Nota asignada: %s\n",miNota.getNota());
	
// A.- Cerrar Sessión
	hbse.close();
// -----------------------------------------------------------------------------------
// CONSULTA DE INFORMACIÓN
// Listar información con métodos get-load
	 listaNotasAlumno("12500501");
	 listaAlumnosAsig(486);
// Listar información con HQL
	 ejemploHql();
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
// ACTUALIZA REGISTROS -------------------------------------
				//miNota.setNota(8);
				//hbses.update(miNota);
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
	
	
}

public static void ejemploSqlNativo() {
	
	
}

public static void ejemploCriteria() {
	
	
}
	public static String padRight(String s,int tam) {
		int t = tam - s.length();
		if (t> 0) {
		s = s + " ".repeat(t);}
		return s;
	}
}
