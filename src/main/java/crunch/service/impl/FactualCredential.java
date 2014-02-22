package crunch.service.impl;

public class FactualCredential {
    String key;
    String secret;

    public FactualCredential(String key, String secret) {
        this.key=key;
        this.secret=secret;
    }

    public String getKey() {
        return key;
    }

    public String getSecret() {
        return secret;
    }
}
