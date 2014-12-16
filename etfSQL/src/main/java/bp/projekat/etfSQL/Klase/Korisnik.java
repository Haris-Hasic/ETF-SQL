package bp.projekat.etfSQL.Klase;

public class Korisnik {
	
	String tag;
	String host;
	String sid;
	String port;
	String username;
	
	public Korisnik(String t, String h, String s, String p, String un) 
	{
		tag = t;
		host = h;
		sid = s;
		port = p;
		username = un;
	}
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
