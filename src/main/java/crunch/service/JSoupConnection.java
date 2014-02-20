package crunch.service;

import org.jsoup.Connection;

/**
 * Created by Costco on 2/19/14.
 */
public interface JSoupConnection {
    public Connection newConnection(String site);
}
