package hibernate;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateExample {

	@SuppressWarnings("deprecation")
	private static SessionFactory factory = new Configuration().configure().buildSessionFactory();

	public static void main(String[] args) throws IOException {
		Session session = factory.openSession();

		try {
			// use SQL query to create table
			SQLQuery query = session.createSQLQuery(readFile("createScript.sql"));
			query.executeUpdate();

			Transaction tx = session.beginTransaction();

			// create and save two new accounts
			Account newAcc1 = new Account();
			newAcc1.setAmount(1000L);
			newAcc1.setIban("LT12 1111");

			// create and save two new accounts
			Account newAcc2 = new Account();
			newAcc2.setAmount(1000L);
			newAcc2.setIban("LT12 1111");

			// create client
			Client client = new Client();
			client.setName("Name");
			client.setSurname("Surname");
			// add mapped accounts
			client.getAccounts().add(newAcc1);
			client.getAccounts().add(newAcc2);
			Serializable clientId = session.save(client);
			client.setName("ChangedName");
			tx.commit();

			outputDatabase();

			tx = session.beginTransaction();
			// managed object
			client = (Client) session.get(Client.class, clientId);
			// evict it it should now be not managed
			session.evict(client);
			client.setName("evictName");
			tx.commit();

			outputDatabase();

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			session.close();
			System.exit(1);
		}
	}

	private static void outputDatabase() {
		Transaction tx = null;
		Session session = factory.openSession();
		try {
			// begin transaction
			tx = session.beginTransaction();

			//
			String[] entities = new String[] { "Account", "Client" };

			// get all using HQL
			for (String entityName : entities) {
				Query query1 = session.createQuery("from " + entityName);
				List<?> results = query1.list();
				for (Object entity : results) {
					System.out.println(entity);
				}
			}

			tx.commit();
		} finally {
			session.close();
		}
	}

	public static String readFile(String path) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, Charset.defaultCharset());
	}
}