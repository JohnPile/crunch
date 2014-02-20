package crunch.service;

import org.jsoup.Connection;

public interface JSoupConnection {
    public Connection newConnection(String site);
}
