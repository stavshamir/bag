package alias;

import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static junit.framework.TestCase.fail;
import static org.assertj.core.api.Assertions.assertThat;


public class AliasServiceImplTest {

    private AliasService aliasService = new AliasServiceImpl();

    @Test
    public void getSystemAliases() {
        try {
            Set<Alias> aliases = aliasService.getSystemAliases();

            assertThat(aliases)
                    .contains(new Alias("ls", "ls --color=auto"));
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

}