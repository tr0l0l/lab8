package ETU.WebGWT.server;

import ETU.WebGWT.client.GreetingService;
import ETU.WebGWT.shared.BusReader;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {
    
    private Map<String, List<BusReader>> db = null;

    public List<String> getReaderList() {
        if (db == null) initDB();
        String[] tmp = new String[db.keySet().size()];
        db.keySet().toArray(tmp);
        return Arrays.asList(tmp);
    }

    public List<BusReader> getStudReaderList(String readerFIO) {
        if (db == null) initDB();
        
        if (readerFIO.equals("123") || readerFIO.equals("321")) {
            return db.get(readerFIO);
        } else {
            throw new IllegalArgumentException("Нет автобусов " + readerFIO);
        }
    }

    private void initDB() {
        db = new HashMap<String, List<BusReader>>();
        
        List<BusReader> entries1 = new ArrayList<BusReader>();
        entries1.add(new BusReader("123", "Иванов И. И.", true)); 
        db.put("123", entries1);

        List<BusReader> entries2 = new ArrayList<BusReader>();
        entries2.add(new BusReader("321", "Петров П. П.", false));
        db.put("321", entries2);
    }
}