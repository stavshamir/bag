package aka.suggester;

import aka.alias.Alias;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import java.util.Map;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class AliasSuggestionTest {

    @Test
    public void aliasNameSuggestion() {
        Map<String, String> commands = ImmutableMap.of(
                "sudo apt-get update", "sau",
                "echo test", "et",
                "mysql -u root -p", "mur",
                "./aka.sh", "ak",
                "./aka.sh .", "ak"
        );

        final Consumer<Alias> assertThatAliasSuggestionIsCorrect = alias -> assertThat(alias.getName())
                .isEqualTo(commands.get(alias.getValue()));

        commands.keySet().stream()
                .map(command -> new AliasSuggestion(command, 1))
                .map(AliasSuggestion::getAlias)
                .forEach(assertThatAliasSuggestionIsCorrect);

    }

    @Test
    public void compareTo() {
        final AliasSuggestion foo = new AliasSuggestion("foo", 2);
        final AliasSuggestion bar = new AliasSuggestion("bar", 0);

        assertThat(foo.compareTo(bar))
                .isLessThan(0);

        assertThat(bar.compareTo(foo))
                .isGreaterThan(0);
    }
}