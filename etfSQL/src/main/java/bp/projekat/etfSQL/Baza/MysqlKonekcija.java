package bp.projekat.etfSQL.Baza;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class MysqlKonekcija extends Konekcija {

	public MysqlKonekcija(String k, String pass, String h, String p, String dbn) {
		
		super(k, pass);
		
		setDriver("org.gjt.mm.mysql.Driver");
		setUrl("jdbc:mysql://" + h + ":" + p + "/" + dbn);
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
	
	@Override
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
	
	@Override
	public void logiraj(String korisnik, String komanda, Date datum) {
		try {
			
			setIskaz(getKonekcija().createStatement());
			getIskaz().executeUpdate("INSERT INTO LOG(user, izvrsena_komanda, vrijeme) VALUES ('" + korisnik + "', '" + komanda + "', '" + datum.toString() + "')");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
