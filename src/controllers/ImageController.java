package controllers;

import beans.Paginator;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

@Named
@SessionScoped
public class ImageController implements Serializable {
    private final int MAX_UPLOAD_SIZE = 204800;
    private byte[] uploadedImg;

    @Inject
    private BookController bookController;
    @Inject
    private PdfController pdfController;
    public StreamedContent getDefaultImg() {
        return getStreamedContent(bookController.getSelectedBook().getImage());
    }

    public StreamedContent getUploadedImg() {
        return getStreamedContent(uploadedImg);
    }

    private StreamedContent getStreamedContent(byte[] image) {
        if (image != null) {
            try (InputStream inputStream = new ByteArrayInputStream(image)) {
                return new DefaultStreamedContent(inputStream, "image/png");
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return null;
    }

    public void handleFileUpload(FileUploadEvent event) {
        uploadedImg = event.getFile().getContents();
    }

    public int getMAX_UPLOAD_SIZE() {
        return MAX_UPLOAD_SIZE;
    }

    public void clearImg() {
        uploadedImg = null;
        if (bookController.isAddMode())
            pdfController.clear();
    }

    public void updateImg() {
        if (uploadedImg != null) {
            bookController.getSelectedBook().setImage(uploadedImg);
        }
        if (bookController.isAddMode()) {
            pdfController.updateContent();
        }
        clearImg();
    }

}
