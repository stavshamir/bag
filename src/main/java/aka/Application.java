package aka;

import aka.suggester.AliasSuggester;
import aka.suggester.AliasSuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@SpringBootApplication
public class Application implements CommandLineRunner{

    @Autowired
    private AliasSuggester aliasSuggester;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
        String template = "%-90s %-15s %s\n";
        System.out.printf(template, "COMMAND", "TIMES USED", "SUGGESTED ALIAS");
        aliasSuggester.suggestAliases().stream()
                .limit(5)
                .forEach(s -> System.out.printf(template, s.getAlias().getValue(), s.getOccurrences(), s.getAlias().toString()));
    }
}
