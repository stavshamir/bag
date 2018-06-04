package aka.suggester;

import aka.alias.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import aka.history.BashHistoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        final String unAliased = "sudo apt-get update";
        final String unAliasedNonFrequent = "sudo apt-get install foo";
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
                .thenReturn(Sets.newHashSet(new Alias("does not matter", aliasedFromUserFile)));

        Mockito.when(aliasSystemRepository.getAliases())
                .thenReturn(Sets.newHashSet(new Alias("does not matter", aliasedFromSystem)));

        final Set<String> suggestionsValues = aliasSuggester.suggestAliases().stream()
                .map(AliasSuggestion::getAlias)
                .map(Alias::getValue)
                .collect(Collectors.toSet());

        assertThat(suggestionsValues)
                .contains(unAliased)
                .doesNotContain(unAliasedNonFrequent)
                .doesNotContain(aliasedFromUserFile)
                .doesNotContain(aliasedFromSystem);
    }

}