package bp.projekat.etfSQL.Klase;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CommandLogger {
	List<Command> listaKomandi;
	private static Charset UTF8 = Charset.forName("UTF-8");
	
	public CommandLogger() {
		listaKomandi = new ArrayList<Command>();
	}
	
	public void dodajKomandu(Command c) {
		listaKomandi.add(c);
	}
	
	public List<Command> dajListuKomandi() {
		return listaKomandi;
	}
	
	public void spasiUDatoteku(String path) throws IOException {
		Writer writer = new OutputStreamWriter(new FileOutputStream(path), UTF8);
        try {
        	for(Command c : listaKomandi) {
        		String output = String.format(c.getVrijeme() + "," + c.getUser() + "," + c.getIzvrsenaKomanda() + "%s",System.getProperty("line.separator"));
        		writer.write(output);
        	}
        } finally {
            writer.close();
        }
	}
	
    public void ucitajIzDatoteke(String path) throws IOException, ParseException {
    	BufferedReader br = null;
    	try {
	    	br = new BufferedReader(new FileReader(path));
	    	String line;
	    	while ((line = br.readLine()) != null) {
	    		String[] lineVariables = line.split(",");
	    		String datum = lineVariables[0];
	    		Date date = new SimpleDateFormat("E MMM d HH:mm:ss z yyyy").parse(datum);
	    		System.out.println(date); // Sat Jan 02 00:00:00 BOT 2010
	    		Command c = new Command(lineVariables[1], lineVariables[2], date);
	    		dodajKomandu(c);
	    	}
    	}
    	catch (IOException e) {
    		System.out.println(e);
    	}
    	finally {
    		br.close();
    	}
    }
}
