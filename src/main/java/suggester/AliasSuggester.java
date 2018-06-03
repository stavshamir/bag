package suggester;

import alias.Alias;
import alias.AliasService;
import history.BashHistoryRepository;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class AliasSuggester {

    private final AliasService aliasService;
    private final BashHistoryRepository bashHistoryRepository;

    private static final int MIN_OCCURRENCES_TO_ALIAS = 3;
    private static final int MIN_LENGTH_TO_ALIAS = 5;

    public AliasSuggester(AliasService aliasService, BashHistoryRepository bashHistoryRepository) {
        this.aliasService = aliasService;
        this.bashHistoryRepository = bashHistoryRepository;
    }

    public Set<Alias> suggestAliases() throws IOException {
        Set<String> aliasValues = aliasService.getAllAliases().stream()
                .map(Alias::getValue)
                .collect(toSet());

        Map<String, Long> commandOccurrences = bashHistoryRepository.getBashHistory().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Predicate<String> commandIsLongEnough = cmd -> cmd.length() >= MIN_LENGTH_TO_ALIAS;
        Predicate<String> commandIsUsedFrequently = cmd -> commandOccurrences.get(cmd) >= MIN_OCCURRENCES_TO_ALIAS;
        Predicate<String> commandIsNotAliased = cmd -> !aliasValues.contains(cmd);

        return bashHistoryRepository.getBashHistory().stream()
                .filter(commandIsLongEnough)
                .filter(commandIsUsedFrequently)
                .filter(commandIsNotAliased)
                .map(cmd -> new Alias("foo", cmd))
                .collect(toSet());
    }

}
