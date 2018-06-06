package bag.suggester;

import bag.alias.Alias;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AliasSuggestion {

    private final Alias alias;
    private final long count;

    public AliasSuggestion(String command, long count) {
        this.alias = new Alias(suggestName(command), command);
        this.count = count;
    }

    public void setAliasName(String aliasName) {
        alias.setName(aliasName);
    }

    public Alias getAlias() {
        return alias;
    }

    public long getCount() {
        return count;
    }

    private String suggestName(String command) {
        List<String> words = Arrays
                .stream(command.split(" "))
                .map(String::toLowerCase)
                .map(s -> s.replaceAll("[^a-z]", ""))
                .filter(s -> s.length() > 0)
                .collect(Collectors.toList());

        if (words.size() > 1) {
          return suggestNameMultipleWords(words);
        }

        return suggestNameSingleWord(words.get(0));
    }

    private String suggestNameSingleWord(String command) {
        return command
                .toLowerCase()
                .replaceAll("[^a-z]", "")
                .substring(0, 2);
    }

    private String suggestNameMultipleWords(List<String> words) {
        final int maxNameSize = 3;
        return words.stream()
                .limit(maxNameSize)
                .reduce("", (a, b) -> a + b.substring(0, 1));
    }
}
