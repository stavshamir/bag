package suggester;

import alias.*;
import history.BashHistoryFromFileRepository;
import history.BashHistoryRepository;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AliasSuggesterTest {

//    private static final String bashHistoryFilePath = "src/test/resources/test_bash_history";
    private static final String bashHistoryFilePath = "/home/stav/.bash_history";
    private static final String userAliasFilePath = "src/test/resources/test_alias_file";
    private AliasSuggester aliasSuggester;

    @Before
    public void setUp() {
        AliasService aliasService = new AliasServiceImpl(new AliasUserFileRepository(userAliasFilePath), new AliasSystemRepositoryImpl());
        BashHistoryRepository bashHistoryRepository = new BashHistoryFromFileRepository(bashHistoryFilePath);

        aliasSuggester = new AliasSuggester(aliasService, bashHistoryRepository);
    }

    @Test
    public void suggestAliases() throws IOException {
        Set<Alias> suggestions = aliasSuggester.suggestAliases();

        assertThat(suggestions)
                .contains(new Alias("foo", "sudo apt-get update"))
                .doesNotContain(new Alias("foo", "sudo apt-get install"))
                .doesNotContain(new Alias("foo", "echo test"));
    }
}