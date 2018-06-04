package aka.suggester;

import aka.alias.Alias;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AliasSuggestion implements Comparable<AliasSuggestion> {

    private final Alias alias;
    private final long occurrences;

    public AliasSuggestion(String command, long occurrences) {
        this.alias = new Alias(suggestName(command), command);
        this.occurrences = occurrences;
    }

    public Alias getAlias() {
        return alias;
    }

    public long getOccurrences() {
        return occurrences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AliasSuggestion that = (AliasSuggestion) o;
        return Objects.equals(alias, that.alias);
    }

    @Override
    public int hashCode() {

        return Objects.hash(alias);
    }

    @Override
    public String toString() {
        return "'" + alias.getValue() + "' was used " + occurrences + " times: suggesting alias " + alias.getName();
    }

    @Override
    public int compareTo(AliasSuggestion other) {
        return (int) (other.occurrences - this.occurrences);
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
