package alias;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class AliasTest {

    @Test
    public void fromReusableForm() {
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
    public void fromReusableForm_invalid_format() {
        String reusableForm = "grep'grep --color=auto'";

        assertThatExceptionOfType(AliasReusableFormException.class)
                .isThrownBy(() -> Alias.fromReusableForm(reusableForm));
    }

    @Test
    public void testToString() {
        Alias alias = new Alias("grep", "grep --color=auto");

        assertThat(alias.toString())
                .isEqualTo("grep='grep --color=auto'");
    }

}