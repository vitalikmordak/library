package controllers;

import org.primefaces.event.FileUploadEvent;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class PdfController implements Serializable {
    private byte[] content;

    @Inject
    private BookController bookController;

    public void handleFileUpload(FileUploadEvent event) {
        content = event.getFile().getContents();
    }

    void clear() {
        content = null;
    }

    void updateContent() {
        if (content != null) {
            bookController.getSelectedBook().setContent(content);
        }
        clear();
    }
}
