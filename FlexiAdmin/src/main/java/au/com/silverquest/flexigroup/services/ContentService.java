package au.com.silverquest.flexigroup.services;

import au.com.silverquest.flexigroup.model.entity.Content;
import au.com.silverquest.flexigroup.model.entity.ContentType;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Calvin
 * Date: 21/02/13
 * Time: 9:20 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ContentService {
    public void save(Content content);
    public void delete(Content content);
    public List<Content> listContent();
    public List<Content> listContentByType(Integer type);

    public ContentType getContentType(Integer id);
    public void saveContentType(ContentType type);
    public void deleteContentType(ContentType type);
    public List<ContentType> listContentType();

}
