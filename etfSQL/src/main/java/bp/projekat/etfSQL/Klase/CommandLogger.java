package bp.projekat.etfSQL.Klase;

import java.util.Date;

// Testni komentar
public class CommandLogger {
	
	private String user;
	private String izvrsenaKomanda;
	private Date vrijeme;
	
	public CommandLogger(String u, String ik, Date v) {
		
		setUser(u);
		setIzvrsenaKomanda(ik);
		setVrijeme(v);
	}
	
	public String getUser() {
		return user;
	}
	void setUser(String user) {
		this.user = user;
	}
	
	
	public String getIzvrsenaKomanda() {
		return izvrsenaKomanda;
	}
	void setIzvrsenaKomanda(String _izvrsenaKomanda) {
		this.izvrsenaKomanda = _izvrsenaKomanda;
	}
	
	public Date getVrijeme() {
		return vrijeme;
	}
	private void setVrijeme(Date vrijeme) {
		this.vrijeme = vrijeme;
	}
	
	

}
