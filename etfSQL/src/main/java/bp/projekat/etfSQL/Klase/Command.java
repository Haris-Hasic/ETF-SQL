package bp.projekat.etfSQL.Klase;

import java.util.Date;

public class Command {
	
	private String user;
	private String izvrsenaKomanda;
	private Date vrijeme;
	
	public Command(String u, String ik, Date v) {
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
