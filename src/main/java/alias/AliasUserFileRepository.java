package alias;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class AliasUserFileRepository implements AliasUserRepository {

    private final File userAliasFile;

    public AliasUserFileRepository(String userAliasFilePath) {
        this.userAliasFile = new File(userAliasFilePath);
    }

    @Override
    public Set<Alias> getAliases() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(userAliasFile));

        return reader.lines()
                .map(line -> StringUtils.removeStart(line, "alias "))
                .map(Alias::fromReusableForm)
                .collect(toSet());
    }

    @Override
    public void addAlias(Alias alias) {

    }
}
