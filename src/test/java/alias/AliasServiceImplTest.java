package alias;

import com.google.common.collect.ImmutableSet;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


public class AliasServiceImplTest {

    private static AliasService aliasService;
    private final static String userAliasFilePath = "src/test/resources/test_alias_file";

    private static final Set<Alias> originalTestFileAliases = ImmutableSet.of(
            new Alias("test", "echo test"),
            new Alias("double", "echo double quotes")
    );

    @BeforeClass
    public static void setUp() {
        AliasUserFileRepository aliasUserRepository = new AliasUserFileRepository(userAliasFilePath);
        AliasSystemRepositoryImpl aliasSystemRepository = new AliasSystemRepositoryImpl();
        aliasService = new AliasServiceImpl(aliasUserRepository, aliasSystemRepository);
    }

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
    public void getAllAliases() throws IOException {
        Set<Alias> aliases = aliasService.getAllAliases();

        assertThat(aliases)
                .contains(new Alias("ls", "ls --color=auto"))
                .contains(new Alias("test", "echo test"));
    }

    @Test
    public void aliasNameExists() throws IOException {
        Alias fromUserRepository = new Alias("test", "the value is not supposed to matter");
        Alias fromSystemRepository = new Alias("ls", "the value is not supposed to matter");
        Alias notAnAlias = new Alias("not_an_alias", "the value is not supposed to matter");

        assertThat(aliasService.aliasNameExists(fromUserRepository))
                .isTrue();

        assertThat(aliasService.aliasNameExists(fromSystemRepository))
                .isTrue();

        assertThat(aliasService.aliasNameExists(notAnAlias))
                .isFalse();
    }

    @Test
    public void aliasValueExists() throws IOException {
        Alias fromUserRepository = new Alias("foo", "echo test");
        Alias fromSystemRepository = new Alias("foo", "ls --color=auto");
        Alias notAnAlias = new Alias("foo", "the value is not supposed to matter");

        assertThat(aliasService.aliasValueExists(fromUserRepository))
                .isTrue();

        assertThat(aliasService.aliasValueExists(fromSystemRepository))
                .isTrue();

        assertThat(aliasService.aliasValueExists(notAnAlias))
                .isFalse();
    }


    @Test
    public void addAlias_existing_alias() {
        Alias existingInUserRepository = new Alias("test", "the value is not supposed to matter");
        Alias existingInSystemRepository = new Alias("ls", "the value is not supposed to matter");

        assertThatExceptionOfType(AliasAlreadyExists.class)
                .isThrownBy(() -> aliasService.addAlias(existingInUserRepository));

        assertThatExceptionOfType(AliasAlreadyExists.class)
                .isThrownBy(() -> aliasService.addAlias(existingInSystemRepository));
    }

    @Test
    public void addAlias_new_alias() throws IOException {
        Alias newAlias = new Alias("new", "echo test");

        aliasService.addAlias(newAlias);
        try (BufferedReader reader = new BufferedReader(new FileReader(userAliasFilePath))) {
            Set<String> lines = reader
                    .lines()
                    .collect(toSet());

            assertThat(lines)
                    .contains(newAlias.toString());
        }
    }

}