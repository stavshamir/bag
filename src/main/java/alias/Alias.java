package alias;

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
