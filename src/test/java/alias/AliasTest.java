package alias;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AliasTest {

    @Test
    public void testToString() {
        Alias testAlias = new Alias("grep", "grep --color=auto", "grep with colors");

        assertThat(testAlias.toString())
                .isEqualTo("grep='grep --color=auto' # grep with colors");
    }

    @Test
    public void testToString_no_description() {
        Alias testAlias = new Alias("grep", "grep --color=auto", "");

        assertThat(testAlias.toString())
                .isEqualTo("grep='grep --color=auto'");
    }
}