package bp.projekat.etfSQL.Baza;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.dbcp2.BasicDataSource;


public class Konekcija {

	private static String korisnik;                      // Username korisnika
	private static String sifra;                         // Password korisnika
	private static String driver;                        // Driver za bazu podataka na koju se konektujemo
	private static String url;                           // URL za konekciju

	private int dbConnectionsMinCount = 4;     			 // Minimalan broj konekcija na BP
	private int dbConnectionsMaxCount = 10;  			 // Maksimalan broj konekcija na BP
	private int dbConnectionMaxWait = -1;      			 // Maksimalno vrijeme cekanja za konekciju
	private BasicDataSource dataSource;        	  		 // Osobina data source – potrebna za naprednu konekciju na BP

	private static Connection konekcija = null;          // Osobina konekcija za BP
	private Connection connection;                       // Druga konekcija za BP
	private static Statement iskaz = null;               // Osobina naredba za rad sa BP
	private static ResultSet rezultat = null;            // Osobina skup redova rezultata
	private static DatabaseMetaData metaPodaci = null;   // Osobina meta podataka
	
	public static String getKorisnik() {
		return korisnik;
	}
	static void setKorisnik(String korisnik) {
		Konekcija.korisnik = korisnik;
	}
	public static String getSifra() {
		return sifra;
	}
	public static void setSifra(String sifra) {
		Konekcija.sifra = sifra;
	}
	public static String getDriver() {
		return driver;
	}
	public static void setDriver(String driver) {
		Konekcija.driver = driver;
	}
	public static String getUrl() {
		return url;
	}
	public static void setUrl(String url) {
		Konekcija.url = url;
	}
	public int getDbConnectionsMinCount() {
		return dbConnectionsMinCount;
	}
	public void setDbConnectionsMinCount(int dbConnectionsMinCount) {
		this.dbConnectionsMinCount = dbConnectionsMinCount;
	}
	public int getDbConnectionsMaxCount() {
		return dbConnectionsMaxCount;
	}
	public void setDbConnectionsMaxCount(int dbConnectionsMaxCount) {
		this.dbConnectionsMaxCount = dbConnectionsMaxCount;
	}
	public int getDbConnectionMaxWait() {
		return dbConnectionMaxWait;
	}
	public void setDbConnectionMaxWait(int dbConnectionMaxWait) {
		this.dbConnectionMaxWait = dbConnectionMaxWait;
	}
	public BasicDataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}
	public static Connection getKonekcija() {
		return konekcija;
	}
	public static void setKonekcija(Connection konekcija) {
		Konekcija.konekcija = konekcija;
	}
	public static Statement getIskaz() {
		return iskaz;
	}
	public static void setIskaz(Statement iskaz) {
		Konekcija.iskaz = iskaz;
	}
	public static ResultSet getRezultat() {
		return rezultat;
	}
	public static void setRezultat(ResultSet rezultat) {
		Konekcija.rezultat = rezultat;
	}
	public DatabaseMetaData getMetaPodaci() {
		return metaPodaci;
	}
	public static void setMetaPodaci(DatabaseMetaData metaPodaci) {
		Konekcija.metaPodaci = metaPodaci;
	}
	public void setConnection(Connection connection) {
		this.konekcija = connection;
	}
	
	public Konekcija(String k, String pass) 
	{
		korisnik = k;
		sifra = pass;
	}
	
	public void LoadDriver() throws Exception {
		Class.forName(getDriver());
	}
	
	public void Connect()  throws Exception {
		
		setKonekcija(DriverManager.getConnection(getUrl(), getKorisnik(), getSifra()));
		konekcija.setAutoCommit(false);
	}

	public void Disconnect() throws Exception {
		getKonekcija().close();
	}
	
	public Statement getStatement() throws Exception {
		setIskaz(getKonekcija().createStatement());
		return getIskaz();
	}
	
	public boolean executeStatement(String s) throws Exception {
		
		Statement st = getKonekcija().createStatement();
		return st.execute(s);
	}
	
	public Statement getResultSetStatement() throws Exception {
		
		setIskaz(getKonekcija().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
												ResultSet.CONCUR_UPDATABLE,
												ResultSet.HOLD_CURSORS_OVER_COMMIT));
		return getIskaz();
	}
	
	public ResultSet createResultSet(String sql)  throws Exception {
		Statement naredba = getResultSetStatement();
		ResultSet rs = null;
		rs = naredba.executeQuery(sql);
		return rs;
	}
	
	public synchronized void Open() throws Exception {

		dataSource = new BasicDataSource();                         // Instanciranje klase BasicDataSource
		dataSource.setDriverClassName(driver);                      // Setovanje naziva driver-a
		dataSource.setUrl(url);                                     // Setovanje URL-a
		dataSource.setUsername(getKorisnik());                      // Setovanje naziva korisnika
		dataSource.setPassword(sifra);                              // Setovanje sifre	
			                                                            // Setovanje minimalnog, maksimalnog broja konekcija kao i vremena čekanja za uspostavu konekcije
		dataSource.setMaxIdle(dbConnectionsMinCount);
		//dataSource.setMaxActive(dbConnectionsMaxCount);
		//dataSource.setMaxWait(dbConnectionMaxWait);
	}

	public Connection getDataSourceConnection() throws Exception {
		
		connection = null;
		connection = dataSource.getConnection();
		return connection;
	}

	public void closeDataSourceConnection() throws Exception {
		
		dataSource.close();
	}
	
	public void Commit() throws Exception {
		
		konekcija.commit();
	}
	
	public void Rollback() throws Exception {
		
		konekcija.rollback();
	}
	
	// Virtuelne funkcije
	public void createLogTable() {}
	public void logiraj(String korisnik, String komanda, Date datum) {}
}


