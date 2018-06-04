package aka.suggester;

import aka.alias.Alias;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;

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
        return Arrays
                .stream(command.split(" "))
                .limit(3)
                .map(s -> StringUtils.removeStart(s, "-"))
                .reduce("", (a, b) -> a + b.substring(0, 1));
    }
}
