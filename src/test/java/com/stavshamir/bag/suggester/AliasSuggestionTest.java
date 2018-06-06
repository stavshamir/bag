package com.stavshamir.bag.suggester;

import com.stavshamir.bag.alias.Alias;
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
                "./bag.sh", "ba",
                "./bag.sh .", "ba"
        );

        final Consumer<Alias> assertThatAliasSuggestionIsCorrect = alias -> assertThat(alias.getName())
                .isEqualTo(commands.get(alias.getValue()));

        commands.keySet().stream()
                .map(command -> new AliasSuggestion(command, 1))
                .map(AliasSuggestion::getAlias)
                .forEach(assertThatAliasSuggestionIsCorrect);

    }

}