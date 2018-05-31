package alias;

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

    public String getName() {
        return name;
    }

    public static Alias fromReusableForm(String reusableForm) {
        String[] split = reusableForm.split("=['\"]");
        if (split.length != 2) {
            throw new AliasReusableFormException(reusableForm + " is not a valid alias reusable form");
        }

        return new Alias(split[0], getValueFromReusableForm(split[1]), getDescriptionFromReusableForm(split[1]));
    }

    private static String getValueFromReusableForm(String valueAndOptionalDescription) {
        String value = valueAndOptionalDescription
                .split("#")[0]
                .trim();
        return value.substring(0, value.length() - 1);
    }

    private static String getDescriptionFromReusableForm(String valueAndOptionalDescription) {
        String[] arr = valueAndOptionalDescription.split("#");

        if (arr.length == 2) {
            return arr[1].trim();
        }

        return null;
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
