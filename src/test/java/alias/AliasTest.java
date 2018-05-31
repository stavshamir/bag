package alias;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class AliasTest {

    @Test
    public void fromReusableForm_no_description() {
        String reusableForm = "grep='grep --color=auto'";

        assertThat(Alias.fromReusableForm(reusableForm))
                .isEqualTo(new Alias("grep", "grep --color=auto"));
    }

    @Test
    public void fromReusableForm_double_quotes() {
        String reusableForm = "grep=\"grep --color=auto\"";

        assertThat(Alias.fromReusableForm(reusableForm))
                .isEqualTo(new Alias("grep", "grep --color=auto"));
    }

    @Test
    public void fromReusableForm_with_description() {
        String reusableFormWithDescription = "grep='grep --color=auto' # grep with colors";
        assertThat(Alias.fromReusableForm(reusableFormWithDescription))
                .isEqualTo(new Alias("grep", "grep --color=auto", "grep with colors"));

        String reusableFormWithDescriptionMultipleWhitespaces = "grep='grep --color=auto'   #  grep with colors";
        assertThat(Alias.fromReusableForm(reusableFormWithDescriptionMultipleWhitespaces))
                .isEqualTo(new Alias("grep", "grep --color=auto", "grep with colors"));
    }

    @Test
    public void fromReusableForm_invalid_format() {
        String reusableForm = "grep'grep --color=auto'";

        assertThatExceptionOfType(AliasReusableFormException.class)
                .isThrownBy(() -> Alias.fromReusableForm(reusableForm));
    }

    @Test
    public void testEquals() {
        Alias aliasWithDescription  = new Alias("grep", "grep --color=auto", "grep with colors");
        Alias aliasEmptyDescription = new Alias("grep", "grep --color=auto", "");
        Alias aliasNullDescription  = new Alias("grep", "grep --color=auto");

        assertThat(aliasWithDescription)
                .isEqualTo(aliasWithDescription);

        assertThat(aliasWithDescription)
                .isEqualTo(aliasEmptyDescription);

        assertThat(aliasWithDescription)
                .isEqualTo(aliasNullDescription);
    }

    @Test
    public void testToString() {
        Alias alias = new Alias("grep", "grep --color=auto", "grep with colors");

        assertThat(alias.toString())
                .isEqualTo("grep='grep --color=auto' # grep with colors");
    }

    @Test
    public void testToString_no_description() {
        Alias aliasEmptyDescription = new Alias("grep", "grep --color=auto", "");
        assertThat(aliasEmptyDescription.toString())
                .isEqualTo("grep='grep --color=auto'");

        Alias aliasNullDescription = new Alias("grep", "grep --color=auto");
        assertThat(aliasNullDescription.toString())
                .isEqualTo("grep='grep --color=auto'");
    }

}