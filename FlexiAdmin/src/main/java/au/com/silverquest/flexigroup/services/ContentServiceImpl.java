package au.com.silverquest.flexigroup.services;

import au.com.silverquest.flexigroup.model.entity.Content;
import au.com.silverquest.flexigroup.model.entity.ContentType;
import au.com.silverquest.flexigroup.model.repository.ContentRepository;
import au.com.silverquest.flexigroup.model.repository.ContentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Calvin
 * Date: 21/02/13
 * Time: 9:22 AM
 * To change this template use File | Settings | File Templates.
 */
@Service("contentService")
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ContentTypeRepository contentTypeRepository;

   /* @Autowired
    private BenefitRepository benefitRepository;*/

    @Transactional(readOnly = false)
    public void save(Content content) {
        contentRepository.save(content);
    }

    @Transactional(readOnly = false)
    public void delete(Content content) {
        contentRepository.delete(content);
    }

    /*@Transactional(readOnly = false)
    public void saveBenefit(Benefit benefit) {
        benefitRepository.save(benefit);
    }*/

    /*@Transactional(readOnly = true)
    public List<Content> listContent() {
        Iterator<Content> itr = contentRepository.findAll().iterator();

        List<Content> list = new ArrayList<Content>();
        while (itr.hasNext())
            list.add(itr.next());

        return list;
    }*/

    @Transactional(readOnly = true)
    public List<Content> listContent() {
        return contentRepository.getAll();
    }

    public List<Content> listContentByType(Integer type) {
        return contentRepository.getContentsByType(type);
    }

    @Transactional(readOnly = true)
    public List<ContentType> listContentType() {
        return contentTypeRepository.getAll();
    }

    public ContentType getContentType(Integer id) {
        return contentTypeRepository.findOne(id);
    }

    public void saveContentType(ContentType contentType) {
        contentTypeRepository.save(contentType);
    }

    public void deleteContentType(ContentType contentType) {
        contentTypeRepository.delete(contentType);
    }
}
