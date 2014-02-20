package crunch.service.impl;

public class WebPageParser implements Runnable {

    private Thread thread;

    public WebPageParser(String name) {
        this.thread=new Thread(this, name);
    }

    @Override
    public void run() {

    }

    public Thread getThread() {
        return thread;
    }
}
