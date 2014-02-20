package crunch.service;

import crunch.model.Interpretation;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public interface ArticleInterpreter {

    public Interpretation interpretArticleTitle(Element articleBlock, Document document);

    public Interpretation interpretArticleUrl(Element articleBlock, Document document);
    
}
