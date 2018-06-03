package suggester;

import alias.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import history.BashHistoryRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class AliasSuggesterTest {

    @Mock
    private AliasUserRepository aliasUserRepository;

    @Mock
    private AliasSystemRepository aliasSystemRepository;

    @Mock
    private BashHistoryRepository bashHistoryRepository;

    private AliasSuggester aliasSuggester;

    @Before
    public void setUp() {
        AliasService aliasService = new AliasServiceImpl(aliasUserRepository, aliasSystemRepository);
        aliasSuggester = new AliasSuggester(aliasService, bashHistoryRepository);
    }

    @Test
    public void suggestAliases() throws IOException {
        final String defaultAliasName = "foo";

        final String unAliased = "sudo apt-get update";
        final String unAliasedNonFrequent = "sudo apt-get install";
        final String aliasedFromUserFile = "echo test";
        final String aliasedFromSystem = "cat foo.txt";

        List<String> mockBashHistory = ImmutableList.of(
                unAliased, unAliased, unAliased,
                unAliasedNonFrequent,
                aliasedFromUserFile, aliasedFromUserFile, aliasedFromUserFile,
                aliasedFromSystem, aliasedFromSystem, aliasedFromSystem
        );

        Mockito.when(bashHistoryRepository.getBashHistory())
                .thenReturn(mockBashHistory);

        Mockito.when(aliasUserRepository.getAliases())
                .thenReturn(Sets.newHashSet(new Alias(defaultAliasName, aliasedFromUserFile)));

        Mockito.when(aliasSystemRepository.getAliases())
                .thenReturn(Sets.newHashSet(new Alias(defaultAliasName, aliasedFromSystem)));

        assertThat(aliasSuggester.suggestAliases())
                .contains(new Alias(defaultAliasName, unAliased))
                .doesNotContain(new Alias(defaultAliasName, unAliasedNonFrequent))
                .doesNotContain(new Alias(defaultAliasName, aliasedFromUserFile))
                .doesNotContain(new Alias(defaultAliasName, aliasedFromSystem));
    }

}