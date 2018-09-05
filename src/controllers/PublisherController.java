package controllers;

import db.Database;
import entities.Publisher;

import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@SessionScoped
public class PublisherController implements Serializable, Converter {
    private List<SelectItem> selectItems = new ArrayList<>();
    private Map<Long, Publisher> publisherMap;

    public PublisherController() {
        publisherMap = new HashMap<>();
        Database.getInstance().getAllPublishers().forEach(publisher -> {
            publisherMap.put(publisher.getId(), publisher);
            selectItems.add(new SelectItem(publisher, publisher.getName()));
        });
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return publisherMap.get(Long.valueOf(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return ((Publisher) o).getId().toString();
    }
}
