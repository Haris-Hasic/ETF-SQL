package bp.projekat.etfSQL.Klase;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class LoggerTableModel extends AbstractTableModel
{
    private final List<Command> lista;
     
    private final String[] columnNames = new String[] {
            "Time", "User", "ExecutedCommand"
    };
    
    private final Class[] columnClass = new Class[] {
        Date.class, String.class, String.class
    };
 
    public LoggerTableModel(List<Command> l)
    {
        this.lista = l;
    }
     
    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }
 
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return columnClass[columnIndex];
    }
 
    public int getColumnCount()
    {
        return columnNames.length;
    }
 
    public int getRowCount()
    {
        return lista.size();
    }
 
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Command row = lista.get(rowIndex);
        if(0 == columnIndex) {
            return row.getVrijeme();
        }
        else if(1 == columnIndex) {
            return row.getUser();
        }
        else if(2 == columnIndex) {
            return row.getIzvrsenaKomanda();
        }
        return null;
    }
}
