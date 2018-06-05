package aka.suggester;

import aka.alias.Alias;
import aka.alias.AliasService;
import aka.history.BashHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Component
public class AliasSuggester {

    private final AliasService aliasService;
    private final BashHistoryRepository bashHistoryRepository;

    private static final int MIN_OCCURRENCES_TO_ALIAS = 3;
    private static final int MIN_LENGTH_TO_ALIAS = 5;

    @Autowired
    public AliasSuggester(AliasService aliasService, BashHistoryRepository bashHistoryRepository) {
        this.aliasService = aliasService;
        this.bashHistoryRepository = bashHistoryRepository;
    }

    public Set<AliasSuggestion> suggestAliases() throws IOException {
        Set<String> aliasValues = getAllAliasesValues();
        Map<String, Long> historyWithCount = getHistoryWithCount();

        Predicate<String> commandIsLongEnough = cmd -> cmd.length() >= MIN_LENGTH_TO_ALIAS;
        Predicate<String> commandIsUsedFrequently = cmd -> historyWithCount.get(cmd) >= MIN_OCCURRENCES_TO_ALIAS;
        Predicate<String> commandIsNotAliased = cmd -> !aliasValues.contains(cmd);

        return historyWithCount.keySet().stream()
                .distinct()
                .filter(commandIsLongEnough)
                .filter(commandIsUsedFrequently)
                .filter(commandIsNotAliased)
                .map(cmd -> new AliasSuggestion(cmd, historyWithCount.get(cmd)))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private Set<String> getAllAliasesValues() throws IOException {
        return aliasService.getAllAliases().stream()
                .map(Alias::getValue)
                .collect(toSet());
    }

    private Map<String, Long> getHistoryWithCount() throws IOException {
        return bashHistoryRepository.getBashHistory().stream()
                .map(String::trim)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public void implementSuggestion(AliasSuggestion suggestion) throws IOException {
        aliasService.addAlias(suggestion.getAlias());
    }

}
