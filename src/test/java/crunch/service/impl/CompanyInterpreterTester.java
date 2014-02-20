package crunch.service.impl;

import crunch.model.IgnoredWords;
import crunch.model.Interpretation;
import crunch.module.IgnoredWordsProvider;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompanyInterpreterTester {

    private IgnoredWords ignoredWords=new IgnoredWordsProvider().get();

    @Test
    public void testCapitalized() {
        Element articleBlock=mock(Element.class);
        Elements elements=mock(Elements.class);
        when(articleBlock.select("p.excerpt")).thenReturn(elements);
        when(elements.text()).thenReturn("A big Duck buys little Pig.");

        CompanyInterpreter interpreter=new CompanyInterpreter(ignoredWords);
        Interpretation bestCompany=interpreter.interpretCompany(articleBlock);
        assertEquals("Duck", bestCompany.getName());
    }

    @Test
    public void testCapitalized2() {
        Element articleBlock=mock(Element.class);
        Elements elements=mock(Elements.class);
        when(articleBlock.select("p.excerpt")).thenReturn(elements);
        when(elements.text()).thenReturn("A big Pig buys little Duck.");

        CompanyInterpreter interpreter=new CompanyInterpreter(ignoredWords);
        Interpretation bestCompany=interpreter.interpretCompany(articleBlock);
        assertEquals("Pig", bestCompany.getName());
    }

}
