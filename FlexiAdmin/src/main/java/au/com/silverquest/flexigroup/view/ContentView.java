package au.com.silverquest.flexigroup.view;

import au.com.silverquest.flexigroup.model.entity.Content;
import au.com.silverquest.flexigroup.model.entity.ContentType;
import au.com.silverquest.flexigroup.services.ContentService;
import au.com.silverquest.flexigroup.util.Util;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Stephanie
 * Date: 21/02/13
 * Time: 12:22 AM
 * To change this template use File | Settings | File Templates.
 */
@Component("contentManager")
@Scope("view")
public class ContentView implements Serializable {

    private Content content;
    private List<Content> contentList;
    private List<Content> filteredContents;
    private ContentType contentType;
    private List<ContentType> contentTypeList;
    private String description;
    private Date activate;
    private Date expire;
    private String text;
    private InputStream imageName;
    private Integer selectedType;

    private StreamedContent graphicImage;

    public StreamedContent getGraphicImage() {
        getContentList();
        return graphicImage;
    }

    public void setGraphicImage(StreamedContent graphicImage) {
        this.graphicImage = graphicImage;
    }

    @Autowired
    private ContentService contentService;

    @PostConstruct
    public void init() {
        content = new Content();
        //selectedContent = new Content();
        contentList = new ArrayList<Content>();
        contentTypeList = new ArrayList<ContentType>();
        loadContentList();
        loadContentTypeList();
    }

    /*public List<EntityView> getContentList() {
        List<Content> list = contentService.listContent();
        List<EntityView> viewList = new ArrayList<EntityView>();

        EntityView view;

        StreamedContent graphicImage = null;
        for (Content entity : list) {
            graphicImage = new DefaultStreamedContent(new ByteArrayInputStream(Base64.decode(entity.getImagelink())), "image/jpeg");

            view = new EntityView();
            view.setType(entity.getType());
            view.setActivate(entity.getActivate());
            view.setExpire(entity.getExpire());
            view.setContent(entity.getContent());
            view.setGraphicImage(graphicImage);
            viewList.add(view);

        }
        setGraphicImage(graphicImage);
        return viewList;
    }*/

    public void loadContentList() {
        contentList = contentService.listContent();
    }

    public void loadContentTypeList() {
        contentTypeList = contentService.listContentType();
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        try {
            imageName = event.getFile().getInputstream();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void createContent(ActionEvent actionEvent) {
       // FacesMessage msg;
        content = new Content();
        contentType = contentService.getContentType(getSelectedType());
        content.setContentType(contentType);
        content.setDescription(getDescription());
        content.setContent(getText());
        content.setActivate(Util.dateToTimestamp(getActivate()));
        content.setExpire(Util.dateToTimestamp(getExpire()));
        content.setUuid(UUID.randomUUID());

        contentService.save(content);
       // msg = new FacesMessage("Successful", "Record has been successfully saved.");

        description = null;
        selectedType = -1;
        activate = null;
        expire = null;
        text = null;
       // content = new Content();
       // contentType = new ContentType();
        loadContentList();

        //FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void updateContent() {
        contentService.save(content);
    }

    public void deleteContent() {
        contentService.delete(content);
        loadContentList();
    }

    /*public void save() {
        FacesMessage msg;

        try {
            content.setActivate(Util.dateToTimestamp(getActivate()));
            content.setExpire(Util.dateToTimestamp(getExpire()));
            content.setUuid(UUID.randomUUID());
            content.setImagelink(Base64.encodeToString(Util.extractBytes(imageName), false));
            contentService.saveContent(content);
            msg = new FacesMessage("Successful", "Record has been successfully saved.");

        } catch (Exception ex) {
            msg = new FacesMessage("Fail", "System error : " + ex.getMessage());
            ex.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }*/

    public boolean isDirty() {
        return false;
    }

    class EntityView extends Content {
        private StreamedContent graphicImage;

        public StreamedContent getGraphicImage() {
            return graphicImage;
        }

        public void setGraphicImage(StreamedContent graphicImage) {
            this.graphicImage = graphicImage;
        }
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public List<Content> getContentList() {
        return contentList;
    }

    public void setContentList(List<Content> contentList) {
        this.contentList = contentList;
    }

    public List<Content> getFilteredContents() {
        return filteredContents;
    }

    public void setFilteredContents(List<Content> filteredContents) {
        this.filteredContents = filteredContents;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public void setContentTypeList(List<ContentType> contentTypeList) {
        this.contentTypeList = contentTypeList;
    }

    public List<ContentType> getContentTypeList() {
        return contentTypeList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getActivate() {
        return activate;
    }

    public void setActivate(Date activate) {
        this.activate = activate;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public Integer getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(Integer selectedType) {
        this.selectedType = selectedType;
        contentType = contentService.getContentType(selectedType);
        content.setContentType(contentType);
        //contentList = contentService.listContentByType(selectedType);
    }

    public byte[] extractBytesStr(String image) throws IOException {
        // open image
        File imgPath = new File(image);
        BufferedImage bufferedImage = ImageIO.read(imgPath);

        // get DataBufferBytes from Raster
        WritableRaster raster = bufferedImage.getRaster();
        DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

        return (data.getData());
    }
}
