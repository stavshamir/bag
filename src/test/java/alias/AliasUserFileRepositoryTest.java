package alias;

import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AliasUserFileRepositoryTest {

    private AliasUserRepository aliasUserRepository = new AliasUserFileRepository("src/test/resources/test_alias_file");

    @Test
    public void getAliases() throws IOException {
        Set<Alias> aliases = aliasUserRepository.getAliases();

        assertThat(aliases)
                .contains(new Alias("test", "echo test"))
                .contains(new Alias("nodesc", "echo no description"))
                .contains(new Alias("double", "echo double quotes"));
    }

    @Test
    public void addAlias() {
    }
}