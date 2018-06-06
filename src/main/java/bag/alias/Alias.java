package bag.alias;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class Alias {
    private String name;
    private String value;

    public Alias(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static Alias fromString(String reusableForm) {
        if (!StringUtils.startsWith(reusableForm, "alias ")) {
            throw new AliasReusableFormException(reusableForm + " is not a valid alias reusable form - must start with the \"alias \"");
        }

        String[] split = reusableForm.split("=['\"]");
        if (split.length != 2) {
            throw new AliasReusableFormException(reusableForm + " is not a valid alias reusable form");
        }

        String name = StringUtils.removeStart(split[0], "alias ");
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
        return "alias " + name + "=" + "'" + value + "'";
    }

}
