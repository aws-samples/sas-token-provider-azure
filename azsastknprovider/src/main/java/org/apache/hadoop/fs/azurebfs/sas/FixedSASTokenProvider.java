package org.apache.hadoop.fs.azurebfs.sas;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.azurebfs.extensions.SASTokenProvider;

class FixedSASTokenProvider implements SASTokenProvider {
    private Configuration configuration;

    public String getSASToken(String accountName, String fileSystem, String path, String operation) {
        String fixedTokenConfig = new StringBuilder("fs.azure.sas.fixed.token.").append(accountName)
                .append(".dfs.core.windows.net").toString();
        String fixedToken = configuration.get(fixedTokenConfig);
        return fixedToken;
    }

    public void initialize(Configuration configuration, String accountName) throws IOException {
        this.configuration = configuration;
    }
}