package alias;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static junit.framework.TestCase.fail;
import static org.assertj.core.api.Assertions.assertThat;


public class AliasServiceImplTest {

    private static AliasService aliasService;

    @BeforeClass
    public static void setUp() {
        AliasUserFileRepository aliasUserRepository = new AliasUserFileRepository("src/test/resources/test_alias_file");
        AliasSystemRepositoryImpl aliasSystemRepository = new AliasSystemRepositoryImpl();
        aliasService = new AliasServiceImpl(aliasUserRepository, aliasSystemRepository);
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
    public void addAlias() {
        fail();
    }
}