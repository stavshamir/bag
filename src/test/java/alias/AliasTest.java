package alias;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class AliasTest {

    @Test
    public void fromString() {
        String alias = "alias grep='grep --color=auto'";

        assertThat(Alias.fromString(alias))
                .isEqualTo(new Alias("grep", "grep --color=auto"));
    }

    @Test
    public void fromString_double_quotes() {
        String alias = "alias grep=\"grep --color=auto\"";

        assertThat(Alias.fromString(alias))
                .isEqualTo(new Alias("grep", "grep --color=auto"));
    }

    @Test
    public void fromString_invalid_format() {
        String invalidAlias_missing_assign = "alias grep'grep --color=auto'";
        assertThatExceptionOfType(AliasReusableFormException.class)
                .isThrownBy(() -> Alias.fromString(invalidAlias_missing_assign));

        String invalidAlias_missing_alias_prefix = "grep='grep --color=auto'";
        assertThatExceptionOfType(AliasReusableFormException.class)
                .isThrownBy(() -> Alias.fromString(invalidAlias_missing_alias_prefix));
    }

    @Test
    public void testToString() {
        Alias alias = new Alias("grep", "grep --color=auto");

        assertThat(alias.toString())
                .isEqualTo("alias grep='grep --color=auto'");
    }

}