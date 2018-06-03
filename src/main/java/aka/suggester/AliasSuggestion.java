package aka.suggester;

import aka.alias.Alias;

import java.util.Objects;

public class AliasSuggestion implements Comparable<AliasSuggestion> {

    private final Alias alias;
    private final long occurrences;

    public AliasSuggestion(Alias alias, long occurrences) {
        this.alias = alias;
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
}
