package alias;

import java.util.Objects;

public class Alias {
    private String name;
    private String value;
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alias alias = (Alias) o;
        return Objects.equals(name, alias.name) &&
                Objects.equals(value, alias.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    public Alias(String name, String value, String description) {
        this.name = name;
        this.value = value;
        this.description = description;
    }

    public Alias(String name, String value) {
        this(name, value, null);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name)
                .append("=")
                .append("'")
                .append(value)
                .append("'");

        if (description != null && !description.isEmpty()) {
            builder.append(" # ")
                    .append(description);
        }
        return builder.toString();
    }
}
