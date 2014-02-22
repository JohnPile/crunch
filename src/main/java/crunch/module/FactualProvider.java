package crunch.module;

import com.factual.driver.Factual;
import com.google.common.io.Closeables;
import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;

import javax.inject.Provider;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class FactualProvider implements Provider<Factual> {

    private static File CREDENTIAL_FILE=null;

    public Factual get() {
        if (CREDENTIAL_FILE==null) {
            synchronized (this) {
                if (CREDENTIAL_FILE==null) {
                    CREDENTIAL_FILE = new File(new File(
                            System.getProperty("user.home"), ".factual"), "factual-auth.yaml");
                }
            }
        }
        return factual();
    }

    private Map<String,String> loadMapFromYaml(File file) {
        InputStream is = null;
        try {
            is = FileUtils.openInputStream(file);
            return (Map<String,String>) (new Yaml()).load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                Closeables.close(is, true);
            } catch (IOException ex) {
                // can't happen
            }
        }
    }

    private Factual factual() {
        if (!CREDENTIAL_FILE.exists()) {
            throw new IllegalStateException("Could not find " + CREDENTIAL_FILE);
        } else {
            Map auth = loadMapFromYaml(CREDENTIAL_FILE);
            return new Factual((String) auth.get("key"), (String) auth.get("secret"));
        }
    }

}
