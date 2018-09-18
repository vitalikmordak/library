package beans;

import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;

import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

// View Admin tools for book
@Named(value = "atView")
public class ATView {
    public void editBook() {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable",false);
        options.put("draggable", false);
        options.put("modal",true);
        PrimeFaces.current().dialog().openDynamic("/pages/editBook.xhtml",options,null);
    }
}
