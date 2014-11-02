package bp.projekat.etfSQL.Baza;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.digester3.Digester;


public class Konekcija {

	static String korisnik = "root";                                     // Naziv korisnika za konekciju na BP
	static String sifra = "";                                            // Sifra korisnika za konekciju na BP
	static String driver = "org.gjt.mm.mysql.Driver";                    // Drajver za konekciju na BP
	static String driverOld = "com.mysql.jdbc.Driver";                   // Drajver za konekciju - alternativni
	static String urlTest2 = "jdbc:mysql://localhost:3306/Test2";        // URL za jednu konekciju
	static String urlOld = "jdbc:mysql://localhost:3306/urls1";          // Drugi URL
	static String url = "jdbc:mysql://localhost:3306/jdbcsotacnew";      // Treci URL

	// Podaci potrebni za napredni oblik konekcija na BP
	protected int dbConnectionsMinCount = 4;                             // Minimalan broj konekcija na BP
	protected int dbConnectionsMaxCount = 10;                            // Maksimalan broj konekcija na BP
	protected int dbConnectionMaxWait = -1;                              // Maksimalno vrijeme cekanja za konekciju
	protected BasicDataSource dataSource;                                // Osobina data source – potrebna za naprednu konekciju na BP

	// Osobine potrebne za rad sa bazom podataka
	static Connection konekcija = null;                                  // Osobina konekcija za BP
	private Connection connection;                                       // Druga konekcija za BP
	static Statement iskaz = null;                                       // Osobina naredba za rad sa BP
	static ResultSet rezultat = null;                                    // Osobina skup redova rezultata
	static DatabaseMetaData metaPodaci = null;                           // Osobina meta podataka

	public void loadDriver() {
		try {
			// Driver je staticka varijabla, definisana naprijed koja daje naziv drajvera
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void connect() {
		try {
			konekcija = DriverManager.getConnection(url, korisnik, sifra);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void diskonekt(Connection konekcija) {
		try {
			konekcija.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public synchronized void open() throws Exception {
		// Using commons-dbcp's BasicDataSource
		try {
			dataSource = new BasicDataSource();                         // Instanciranje klase BasicDataSource
			dataSource.setDriverClassName(driver);                      // Setovanje naziva driver-a
			dataSource.setUrl(url);                                     // Setovanje URL-a
			dataSource.setUsername(korisnik);                           // Setovanje naziva korisnika
			dataSource.setPassword(sifra);                              // Setovanje sifre
			
			// Setovanje minimalnog, maksimalnog broja konekcija kao i vremena čekanja za uspostavu konekcije
			dataSource.setMaxIdle(dbConnectionsMinCount);
			//dataSource.setMaxActive(dbConnectionsMaxCount);
			//dataSource.setMaxWait(dbConnectionMaxWait);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		connection = null;
		try {
			connection = dataSource.getConnection();
		}
		catch (SQLException e) {
			System.out.println("Greska - konekcija");
		}
		return connection;
	}

	public void closeConnection() {
		try {
			dataSource.close();
		}
		catch (SQLException e) {
			System.out.println("Greska - konekcija");
		}
	}

	public Statement getStatement() {
		try {
			// za datu konekciju kreira i vraća objekat klase Statement
			iskaz = konekcija.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return iskaz;
	}

	public Statement getStatement(Connection konekcija) {
		try {
			// Za datu konekciju kreira i vraća objekat klase Statement
			iskaz = konekcija.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return iskaz;
	}

	public void createDatabase() {
		try {
			iskaz.executeUpdate("CREATE DATABASE Test2");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void dropDatabase() {
		try {
			iskaz.executeUpdate("DROP DATABASE Test2");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}


