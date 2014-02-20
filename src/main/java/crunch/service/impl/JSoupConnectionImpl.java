package crunch.service.impl;

import crunch.service.JSoupConnection;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class JSoupConnectionImpl implements JSoupConnection {


    @Override
    public Connection newConnection(String site) {
        return Jsoup.connect(site);
    }
}
