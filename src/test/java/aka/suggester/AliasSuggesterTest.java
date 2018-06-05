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
import java.util.HashSet;
import java.util.List;
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
    public void suggestAliases_contains() throws IOException {
        final String unAliased = "sudo apt-get update";
        List<String> mockBashHistory = ImmutableList.of(unAliased, unAliased, unAliased);

        Mockito.when(bashHistoryRepository.getBashHistory())
                .thenReturn(mockBashHistory);

        Mockito.when(aliasUserRepository.getAliases())
                .thenReturn(new HashSet<>());

        Mockito.when(aliasSystemRepository.getAliases())
                .thenReturn(new HashSet<>());

        assertThat(getAliasSuggestionsValues())
                .contains(unAliased);
    }

    @Test
    public void suggestAliases_does_not_contain() throws IOException {
        final String unAliasedNonFrequent = "sudo apt-get install foo";
        final String unAliasedShort = "foo";
        final String aliasedFromUserFile = "echo test";
        final String aliasedFromSystem = "cat foo.txt";

        List<String> mockBashHistory = ImmutableList.of(
                unAliasedNonFrequent,
                unAliasedShort, unAliasedShort, unAliasedShort,
                aliasedFromUserFile, aliasedFromUserFile, aliasedFromUserFile,
                aliasedFromSystem, aliasedFromSystem, aliasedFromSystem
        );

        Mockito.when(bashHistoryRepository.getBashHistory())
                .thenReturn(mockBashHistory);

        Mockito.when(aliasUserRepository.getAliases())
                .thenReturn(Sets.newHashSet(new Alias("does not matter", aliasedFromUserFile)));

        Mockito.when(aliasSystemRepository.getAliases())
                .thenReturn(Sets.newHashSet(new Alias("does not matter", aliasedFromSystem)));

        assertThat(getAliasSuggestionsValues())
                .doesNotContain(unAliasedNonFrequent)
                .doesNotContain(unAliasedShort)
                .doesNotContain(aliasedFromUserFile)
                .doesNotContain(aliasedFromSystem);
    }

    @Test
    public void suggestAliases_is_sorted_by_count() throws IOException {
        final String first = "first";
        final String second = "second";
        final String third = "third";

        List<String> mockBashHistory = ImmutableList.of(
                third, third, third,
                first, first, first, first, first,
                second, second, second, second
        );

        Mockito.when(bashHistoryRepository.getBashHistory())
                .thenReturn(mockBashHistory);

        Mockito.when(aliasUserRepository.getAliases())
                .thenReturn(new HashSet<>());

        Mockito.when(aliasSystemRepository.getAliases())
                .thenReturn(new HashSet<>());

        final List<String> aliasSuggestionsValues = getAliasSuggestionsValues();

        assertThat(aliasSuggestionsValues)
                .element(0).isEqualTo(first);

        assertThat(aliasSuggestionsValues)
                .element(1).isEqualTo(second);

        assertThat(aliasSuggestionsValues)
                .element(2).isEqualTo(third);
    }

    private List<String> getAliasSuggestionsValues() throws IOException {
        return aliasSuggester.suggestAliases().stream()
                .map(AliasSuggestion::getAlias)
                .map(Alias::getValue)
                .collect(Collectors.toList());
    }

}