package crunch.service;

import crunch.model.Interpretation;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public interface CompanyInterpreter {

    public Interpretation interpretCompanyName(Element articleBlock, Document document);

    public Interpretation interpretCompanyWebsite(Element articleBlock, Document document);

}
