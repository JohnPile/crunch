package crunch.service;

import crunch.model.Interpretation;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public interface CompanyInterpreter {

    public Interpretation interpretCompanyName(Element articleBlock, Document searchDocument, Document detailDocument);

    public Interpretation interpretCompanyWebsite(Element articleBlock, Document searchDocument, Document detailDocument);

}
