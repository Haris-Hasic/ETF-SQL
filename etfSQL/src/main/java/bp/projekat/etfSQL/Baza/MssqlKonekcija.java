package bp.projekat.etfSQL.Baza;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.digester3.Digester;

public class MssqlKonekcija extends Konekcija {

	public MssqlKonekcija(String k, String pass, String h, String p, String dbn) {
		
		super(k, pass);
		setDriver("net.sourceforge.jtds.jdbc.Driver");
		setUrl("jdbc:jtds:sqlserver://" + h + ":" + p + "/" + dbn);
	}
	
	public void createDatabase(String naziv) {
		try {
			//if(!konekcija.getSchema().contains("LogTabela"))
				getIskaz().executeUpdate("CREATE TABLE " + naziv);
				getKonekcija().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void dropDatabase() {
		try {
			getIskaz().executeUpdate("DROP DATABASE Test2");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createLogTable() {
		try {
			
			DatabaseMetaData dbm = getKonekcija().getMetaData();
			ResultSet tables = dbm.getTables(null, null, "LOG", null);
			if (tables.next()) {
			  // Table exists
			}
			else {
				setIskaz(getKonekcija().createStatement());
				String logtabela = "CREATE TABLE LOG" +
			                    "(ID INTEGER NOT NULL AUTO_INCREMENT, " +
			                    "USER VARCHAR(255), " + 
			                    "IZVRSENA_KOMANDA VARCHAR(255), " + 
			                    "VRIJEME VARCHAR(255), " + 
			                    "PRIMARY KEY ( ID ))";
				
				getIskaz().executeUpdate(logtabela);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void logiraj(String korisnik, String komanda, Date datum) {
		try {
			
			setIskaz(getKonekcija().createStatement());
			getIskaz().executeUpdate("INSERT INTO LOG(user, izvrsena_komanda, vrijeme) VALUES ('" + korisnik + "', '" + komanda + "', '" + datum.toString() + "')");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
