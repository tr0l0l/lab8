package ETU.WebGWT.client;

import java.util.List;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ETU.WebGWT.shared.BusReader;

@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
    List<String> getReaderList();
    List<BusReader> getStudReaderList(String readerFIO) throws IllegalArgumentException;
}