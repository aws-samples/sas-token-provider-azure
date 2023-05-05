package org.apache.hadoop.fs.azurebfs.sas;

import java.io.IOException;
import java.util.Objects;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.azurebfs.extensions.SASTokenProvider;
import org.apache.hadoop.security.AccessControlException;

class FixedSASTokenProvider implements SASTokenProvider {
    private Configuration configuration;

    @Override
    public String getSASToken(String accountName,
                              String fileSystem,
                              String path,
                              String operation)
        throws IOException, AccessControlException {
        Objects.requireNonNull(accountName, "accountName must not be null");

        String fixedTokenConfig = new StringBuilder("fs.azure.sas.fixed.token.").append(accountName)
                .append(".dfs.core.windows.net").toString();
        String fixedToken = configuration.get(fixedTokenConfig);
        if (fixedToken == null) {
            throw new AccessControlException("SAS token isn't defined.");
        }
        return fixedToken;
    }

    @Override
    public void initialize(Configuration configuration, String accountName)
        throws IOException {
        Objects.requireNonNull(configuration, "configuration must not be null");
        this.configuration = configuration;
    }
}