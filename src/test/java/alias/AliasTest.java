package alias;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AliasTest {

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