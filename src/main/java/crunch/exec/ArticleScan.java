package crunch.exec;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import crunch.module.ArticleScanModule;
import crunch.service.PageScanner;
import crunch.service.impl.PageRunner;

import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

public class ArticleScan {

    private final Map<String, PageScanner> pageScanners;

    @Inject
    private ArticleScan(@Named("TechCrunch") PageScanner techCrunchPageScanner) {
        pageScanners = new HashMap<String, PageScanner>();
        pageScanners.put("TechCrunch", techCrunchPageScanner);
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ArticleScanModule());
        ArticleScan articleScan = injector.getInstance(ArticleScan.class);
        // Will implement a selection process when we grow past one site
        PageRunner pr = new PageRunner(articleScan.pageScanners.get("TechCrunch"));
        int pages = 2000;
        if (args.length == 1) {
            if (args[0].matches("^[0-9]+$")) {
                pages = Integer.parseInt(args[0]);
            }
        }
        try {
            pr.runPool(pages);
        } catch (InterruptedException ex) {
            // log
        }
    }


}
