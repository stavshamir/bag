package aka;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesService {
    private final static String BASH_HISTORY_FILE = "bash-history-file";
    private final static String ALIAS_USER_FILE = "alias-user-file";

    private final Properties properties = new Properties();

    public PropertiesService(String propertiesFilePath) throws IOException {
        properties.load(new FileInputStream(propertiesFilePath));
    }

    public String getBashHistoryFilePath() {
        return properties.getProperty(BASH_HISTORY_FILE);
    }

    public String getAliasUserFilePath() {
        return properties.getProperty(ALIAS_USER_FILE);
    }

}
