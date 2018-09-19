import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

    public class BasicShowManager {
        private SessionFactory sessionFactory = null;

        // Creating SessionFactory using 4.2 version of Hibernate
        public void initSessionFactory() {
            if (sessionFactory == null) {
                // loads configuration and mappings
                Configuration configuration = new Configuration().configure();
                // builds a session factory from the service registry
                sessionFactory = configuration.buildSessionFactory();
            }
        }

        public void persistMovie(Movie movie) {
            Transaction tx = null;
            Session session = sessionFactory.getCurrentSession();
            try {
                tx = session.beginTransaction();
                session.save(movie);
                tx.commit();
            } catch (HibernateException ex) {
                if (tx != null) tx.rollback();
                throw ex;
            } finally {
                session.close();
            }
        }

        public static void main(String[] args) {
            BasicShowManager show = new BasicShowManager();
            show.initSessionFactory();

            Show show = new Show();
            show.setId(1);
            show.setDirector("Bay");
            show.setTitle("Transformers");
            show.setSynopsis("Robots");
            System.out.println(show);

            show.persistMovie(show);
        }
    }
