package aka;

import aka.alias.Alias;
import aka.suggester.AliasSuggester;
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
        aliasSuggester.suggestAliases().stream()
                .map(Alias::toString)
                .forEach(System.out::println);
    }
}
