package com.stavshamir.bag;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class PropertiesService {
    private final static String BASH_HISTORY_FILE = "bash-history-file";
    private final static String ALIAS_USER_FILE = "alias-user-file";
    private final static String ALIAS_SCRIPT = "alias-script";

    private final Properties properties = new Properties();

    PropertiesService(String propertiesFilePath) throws IOException {
        properties.load(new FileInputStream(propertiesFilePath));
    }

    String getBashHistoryFilePath() {
        return properties.getProperty(BASH_HISTORY_FILE);
    }

    String getAliasUserFilePath() {
        return properties.getProperty(ALIAS_USER_FILE);
    }

    String getAliasScript() {
        return properties.getProperty(ALIAS_SCRIPT);
    }

}
