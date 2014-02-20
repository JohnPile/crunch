package crunch.module;

import com.google.inject.AbstractModule;
import crunch.model.IgnoredWords;
import crunch.service.JSoupConnection;
import crunch.service.KnownCompanies;
import crunch.service.PageArticleParser;
import crunch.service.PageScanner;
import crunch.service.impl.JSoupConnectionImpl;
import crunch.service.impl.KnownCompaniesImpl;
import crunch.service.impl.PageArticleParserImpl;
import crunch.service.impl.PageScannerImpl;

public class ArticleScanModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PageScanner.class).to(PageScannerImpl.class);
        bind(KnownCompanies.class).to(KnownCompaniesImpl.class);
        bind(JSoupConnection.class).to(JSoupConnectionImpl.class);
        bind(PageArticleParser.class).to(PageArticleParserImpl.class);
        bind(IgnoredWords.class).toProvider(IgnoredWordsProvider.class);
    }
}
