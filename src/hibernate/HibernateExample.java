package hibernate;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SuppressWarnings("unused")
public class HibernateExample {

	@SuppressWarnings("deprecation")
	private static SessionFactory factory = new Configuration().configure().buildSessionFactory();

	public static void main(String... args) throws IOException {
		Session session = factory.openSession();
		// tworzymy sesje

		try {
			// use SQL query to create table
			// mamy skrypt ktory tworzy baze danych, uzywamy do tego JDBC
			// hibernate potrafi sam stworzyc schemat bazy danych
//			SQLQuery query = session.createSQLQuery(readFile("createScript.sql"));

//			query.executeUpdate();
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

			// mozna zrobic change name
			client.setName("ChangedName");



			//	Homework
			Account newAcc3 = new Account();
			newAcc3.setAmount(10500L);
			newAcc3.setIban("L512 1111");

			RegularClient rclient = new RegularClient();
			rclient.setName("Roch");
			rclient.setSurname("Kowalski");
			rclient.getAccounts().add(newAcc3);
			rclient.setPet("Dog");
			rclient.setAge(30);

			session.save(rclient);



			tx.commit();

			System.out.println();
			System.out.println("First output");

//			outputDatabase();

			tx = session.beginTransaction();
			client.setName("Name 2");
			tx.commit();


			System.out.println();
			System.out.println("Sec output");

//			outputDatabase();



			tx = session.beginTransaction();
			// managed object
			client = (Client) session.get(Client.class, clientId);
			// evict it it should now be not managed
			session.evict(client);
			client.setName("evictName");
			tx.commit();

//			System.out.println();
//			System.out.println("Third output");
//			outputDatabase();

			//  write example of query with join from client to account, return account list and take two parameters: name and surname.

			String hql = "SELECT accounts " +
						 "FROM hibernate.Client client " +
						 "WHERE client.name = :name " +
						 "AND client.surname = :surname";
			Query query = session.createQuery(hql);
			query.setParameter("name", "Name 2");
			query.setParameter("surname", "Surname");
			List<?> results = query.list();
			results.forEach(System.out::println);

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			session.close();
//			System.exit(1);
		}
	}

	private static void outputDatabase() {
		Transaction tx;
		Session session = factory.openSession();
		try {
			// begin transaction
			tx = session.beginTransaction();

			String[] entities = new String[] { "Account", "Client" };

			// get all using HQL
			for (String entityName : entities) {
				Query query1 = session.createQuery("from " + entityName);
				List<?> results = query1.list();
				System.out.println();
				results.forEach(System.out::println);
				System.out.println();
			}

			tx.commit();
		} finally {
			session.close();
		}
	}

	private static void outputDatabase2() {
		Transaction tx;
		Session session = factory.openSession();
		try {
			// begin transaction
			tx = session.beginTransaction();
			String stringGetAccount = "SELECT accounts FROM Client AS C INNER JOIN Account AS A ON C.name = 'Roch' AND C.surname ='Kowalski'";
			Query query1 = session.createQuery(stringGetAccount);
			List<?> results = query1.list();
			results.forEach(System.out::println);

			tx.commit();
		} finally {
			session.close();
		}
	}

	private static String readFile(String path) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, Charset.defaultCharset());
	}
}
