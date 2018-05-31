package alias;

import org.apache.commons.lang3.StringUtils;
import java.util.Objects;

public class Alias {
    private String name;
    private String value;
    private String description;

    public Alias(String name, String value, String description) {
        this.name = name;
        this.value = value;
        this.description = description;
    }

    public Alias(String name, String value) {
        this(name, value, null);
    }

    public static Alias fromReusableForm(String reusableForm) {
        String[] nameAndValue = reusableForm.split("='");
        if (nameAndValue.length != 2) {
            throw new AliasReusableFormException("Error: " + reusableForm + "is not a valid alias reusable form");
        }

        String name = nameAndValue[0];
        String value = StringUtils.removeEnd(nameAndValue[1], "'");

        return new Alias(name, value);
    }

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
