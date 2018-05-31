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
    public void addAlias() {
        fail();
    }
}