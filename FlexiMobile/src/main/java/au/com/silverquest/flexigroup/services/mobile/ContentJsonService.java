package au.com.silverquest.flexigroup.services.mobile;

/**
 * Created with IntelliJ IDEA.
 * User: coreyb
 * Date: 22/02/13
 * Time: 9:18 PM
 * To change this template use File | Settings | File Templates.
 */

import au.com.silverquest.flexigroup.model.entity.Content;
import au.com.silverquest.flexigroup.model.repository.ContentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class ContentJsonService {

    final Logger log = LoggerFactory.getLogger(ContentJsonService.class);

    @Inject
    private ContentRepository contentRepository;

    @RequestMapping(value = "/content", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Content> getContent(@RequestParam(value = "lastmodified", required = false, defaultValue = "0") Long lastmodified) {
        log.info("getContent() service call received: " + lastmodified);
        List<Content> content = contentRepository.findCurrentContent(new Date(),new Date(lastmodified));
        log.debug("Returning results: " + content.size());
        return content;
    }

    @RequestMapping(value = "/content/{uuid}", method = RequestMethod.GET, headers = "Accept=application/json")
    public
    @ResponseBody
    Content getContentByUuid(@PathVariable UUID uuid) {
        log.debug("getContentByUuid() service call received for: " + uuid);
        Content content = contentRepository.findByUuid(uuid);
        log.debug("Returning Content: " + content.getId());
        return content;
    }
}
