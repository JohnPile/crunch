package crunch.exec;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import crunch.module.ArticleScanModule;
import crunch.service.PageScanner;
import crunch.service.impl.PageRunner;

public class ArticleScan {

    private PageScanner pageScanner;

    @Inject
    public ArticleScan(PageScanner pageScanner) {
        this.pageScanner = pageScanner;
    }

    /**
     * Scan website
     * @param args { "URL of site to scan" }
     */
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ArticleScanModule());
        ArticleScan articleScan=injector.getInstance(ArticleScan.class);
        PageRunner pr=new PageRunner(articleScan.pageScanner);
        int pages=20;
        if (args.length==1) {
            if (args[0].matches("^[0-9]+$")) {
                pages=Integer.parseInt(args[0]);
            }
        }
        try {
            pr.runPool(pages);
        } catch (InterruptedException ex) {
            // log
        }
    }


}
