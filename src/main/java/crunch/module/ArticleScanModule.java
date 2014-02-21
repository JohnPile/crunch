package crunch.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import crunch.model.IgnoredWords;
import crunch.service.*;
import crunch.service.impl.*;

public class ArticleScanModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PageScanner.class).to(TechCrunchPageScannerImpl.class);
        bind(KnownCompanies.class).to(KnownCompaniesImpl.class);
        bind(JSoupConnection.class).to(JSoupConnectionImpl.class);
        bind(IgnoredWords.class).toProvider(IgnoredWordsProvider.class);
        bind(PageArticleParser.class).annotatedWith(Names.named("TechCrunch")).to(TechCrunchPageArticleParserImpl.class);
        bind(DocInterpreter.class).annotatedWith(Names.named("TechCrunch")).to(TechCrunchDocInterpreterImpl.class);
    }
}
