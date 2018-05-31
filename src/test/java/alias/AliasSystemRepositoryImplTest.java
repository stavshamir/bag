package alias;

import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static junit.framework.TestCase.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class AliasSystemRepositoryImplTest {

    private AliasSystemRepository aliasSystemRepository = new AliasSystemRepositoryImpl();

    @Test
    public void getAliases() {
        try {
            Set<Alias> aliases = aliasSystemRepository.getAliases();

            assertThat(aliases)
                    .contains(new Alias("ls", "ls --color=auto"));
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }
}