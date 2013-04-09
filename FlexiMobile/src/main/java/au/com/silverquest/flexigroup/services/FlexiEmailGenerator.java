package au.com.silverquest.flexigroup.services;

import au.com.silverquest.flexigroup.model.entity.Quote;

import java.util.Date;

public interface FlexiEmailGenerator {
    String generateEmailBody(Quote quote, Date date, String imagePath) throws Exception;
}
