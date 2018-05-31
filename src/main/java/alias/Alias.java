package alias;

import java.util.Objects;

public class Alias {
    private String name;
    private String value;

    public Alias(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public static Alias fromReusableForm(String reusableForm) {
        String[] split = reusableForm.split("=['\"]");
        if (split.length != 2) {
            throw new AliasReusableFormException(reusableForm + " is not a valid alias reusable form");
        }

        String name = split[0];
        String value = split[1].substring(0, split[1].length() - 1);

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
        return name + "=" + "'" + value + "'";
    }

}
