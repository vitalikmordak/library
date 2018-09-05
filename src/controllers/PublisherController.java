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
import java.util.*;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class PublisherController implements Serializable, Converter {
    private List<SelectItem> selectItems = new ArrayList<>();
    private Map<Long, Publisher> publisherMap;
    private List<Publisher> publishers;

    public PublisherController() {
        publisherMap = new HashMap<>();
        // sort list
        publishers = Database.getInstance().getAllPublishers().stream().sorted(Comparator.comparing(Publisher::getName)).collect(Collectors.toList());
        this.publishers.forEach(publisher -> {
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

    public List<Publisher> getPublishers() {
        return publishers;
    }
}
