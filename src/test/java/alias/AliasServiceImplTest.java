package alias;

import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static junit.framework.TestCase.fail;
import static org.assertj.core.api.Assertions.assertThat;


public class AliasServiceImplTest {

    private AliasService aliasService = new AliasServiceImpl(new AliasUserFileRepository(), new AliasSystemRepositoryImpl());

    @Test
    public void getAllAliases() {
        fail();
    }

    @Test
    public void addAlias() {
        fail();
    }
}