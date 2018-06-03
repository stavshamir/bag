package alias;

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
        try (BufferedReader reader = new BufferedReader(new FileReader(userAliasFile))) {
            return reader.lines()
                    .map(Alias::fromString)
                    .collect(toSet());
        }
    }

    @Override
    public void addAlias(Alias alias) throws IOException {
        try (Writer writer = new BufferedWriter(new FileWriter(userAliasFile, true))) {
           writer.append(alias.toString())
                   .append(System.lineSeparator());
        }
    }
}
