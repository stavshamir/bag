package aka.alias;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Repository
public class AliasUserFileRepository implements AliasUserRepository {

    private final File userAliasFile;

    public AliasUserFileRepository(@Value("${alias-user-file}") String userAliasFilePath) {
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
