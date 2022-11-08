package mariadbApp;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final SessionFactory sf = buildSessionFromConfig();
	/**
	 * Crear el objeto SessionFactory a partir del fichero xml de configuración
	 * Método creado en modo singleton para la variable privada
	 * sólo se puede recuperar con el método getSessionFactory();
	 * @return objeto creado
	 */
	public static SessionFactory buildSessionFromConfig() {
		  // Crear la configuración a parit del fichero de configuración
		  Configuration config = new Configuration();
		  config.configure();
		  // crear local SessionFactory 
		  SessionFactory sessionFactory = config.buildSessionFactory();
		  //Session session = sessionFactory.getCurrentSession();
		  return sessionFactory;
		}
	/**
	 * Método para recuperar el objeto SessionFactory
	 * @return sf : objeto singleton SessionFactoy
	 */
	public static SessionFactory getSessionFactory() {
		
		return sf;
	}
	public static Session getCurrSession() {
		return sf.getCurrentSession();
	}
}
