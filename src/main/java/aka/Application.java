package aka;

import aka.alias.Alias;
import aka.suggester.AliasSuggester;
import aka.suggester.AliasSuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Set;


@SpringBootApplication
public class Application implements CommandLineRunner{

    @Autowired
    private AliasSuggester aliasSuggester;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
        Set<AliasSuggestion> suggestions = aliasSuggester.suggestAliases();

        suggestions.stream()
                .map(AliasSuggestion::toString)
                .forEach(System.out::println);
    }
}
