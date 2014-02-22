package crunch.service.impl;

import crunch.model.CompanyArticle;
import crunch.service.PageScanner;

import java.io.PrintStream;
import java.util.Set;
import java.util.concurrent.*;

public class PageRunner {

    private static final Object printLock = new Object();
    private final PageScanner pageScanner;

    public PageRunner(PageScanner pageScanner) {
        this.pageScanner = pageScanner;
    }

    /**
     * Scan a fixed number of pages from the site using a pool of workers
     *
     * @param pages Number of pages to process before quitting
     * @throws InterruptedException
     */
    public void runPool(int pages) throws InterruptedException {
        RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        int corePoolSize = 10;
        int maxPoolSize = 20;
        long keepAlive = 10;
        PageManager pageManager = new PageManager(1, pages);
        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAlive, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(corePoolSize), threadFactory, rejectionHandler);
        // Each thread processes one web page at a time

        for (int i = 0; i < maxPoolSize; i++) {
            executorPool.execute(new WorkerThread(pageScanner, pageManager, System.out));
        }
        Thread.sleep(1000);
        //shut down the pool
        executorPool.shutdown();
    }

    /**
     * Hands a sequential number to the next thread
     */
    private class PageManager {
        private int currentPage;
        private final int lastPage;

        public PageManager(int firstPage, int lastPage) {
            currentPage = firstPage;
            this.lastPage = lastPage;
        }

        synchronized int next() throws InterruptedException {
            if (currentPage >= lastPage) {
                throw new InterruptedException("Done");
            }
            return currentPage++;
        }
    }

    /**
     * Pulls from PageManager (a primitive queue) until the queue is exhausted
     */
    private class WorkerThread implements Runnable {

        private final PageScanner pageScanner;
        private final PageManager pageManager;
        private final PrintStream out;


        public WorkerThread(PageScanner pageScanner, PageManager pageManager, PrintStream out) {
            this.out = out;
            this.pageManager = pageManager;
            this.pageScanner = pageScanner;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    int pageno = pageManager.next();
                    String site = "http://techcrunch.com/page/" + pageno;
                    Set<CompanyArticle> companyArticles = pageScanner.scanArticles(site);

                    synchronized (PageRunner.printLock) {
                        CsvGenerator.toCsv(companyArticles, out);
                    }

                }
            } catch (InterruptedException ex) {
                // done
            }

        }

    }
}
