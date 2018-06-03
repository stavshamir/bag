package alias;

import com.google.common.collect.ImmutableSet;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.*;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

public class AliasUserFileRepositoryTest {

    private static String userAliasFilePath = "src/test/resources/test_alias_file";
    private AliasUserRepository aliasUserRepository = new AliasUserFileRepository(userAliasFilePath);

    private static final Set<Alias> originalTestFileAliases = ImmutableSet.of(
            new Alias("test", "echo test"),
            new Alias("double", "echo double quotes")
    );

    @AfterClass
    public static void tearDown() throws IOException {
        try (Writer writer = new BufferedWriter(new FileWriter(userAliasFilePath))) {
            for (Alias a : originalTestFileAliases) {
                writer.append(a.toString())
                        .append(System.lineSeparator());
            }
        }
    }

    @Test
    public void getAliases() throws IOException {
        Set<Alias> aliases = aliasUserRepository.getAliases();

        originalTestFileAliases.forEach(assertThat(aliases)::contains);
    }

    @Test
    public void addAlias() throws IOException {
        Alias newAlias = new Alias("new", "echo test");
        aliasUserRepository.addAlias(newAlias);

        try (BufferedReader reader = new BufferedReader(new FileReader(userAliasFilePath))) {
            Set<String> lines = reader
                    .lines()
                    .collect(toSet());

            assertThat(lines)
                    .contains(newAlias.toString());
        }
    }
}