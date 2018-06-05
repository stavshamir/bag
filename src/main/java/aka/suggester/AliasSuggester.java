package aka.suggester;

import aka.alias.Alias;
import aka.alias.AliasService;
import aka.history.BashHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    public List<AliasSuggestion> suggestAliases() throws IOException {
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
                .sorted((c1, c2) -> (int)(historyWithCount.get(c2) - historyWithCount.get(c1)))
                .map(cmd -> new AliasSuggestion(cmd, historyWithCount.get(cmd)))
                .collect(Collectors.toList());
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

    public void applySuggestion(AliasSuggestion suggestion) throws IOException {
        aliasService.addAlias(suggestion.getAlias());
    }

}
