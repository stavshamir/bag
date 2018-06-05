package aka;

import aka.suggester.AliasSuggester;
import aka.suggester.AliasSuggestion;
import org.apache.commons.cli.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;


@SpringBootApplication
public class Application implements CommandLineRunner{

    @Autowired
    private AliasSuggester aliasSuggester;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = null;
        try {
            cmd = parser.parse(CliOptions.getOptions(), args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            printUsage();
            return;
        }

        if (cmd.hasOption(CliOptions.PRINT)) {
            printSuggestions();
        } else if (cmd.hasOption(CliOptions.CREATE)) {
            createAlias(cmd);
        } else if (cmd.hasOption(CliOptions.HELP)) {
            printUsage();
        } else {
            printUsage();
        }

    }

    private void createAlias(CommandLine command) throws IOException {
        int index = Integer.parseInt(command.getOptionValue(CliOptions.CREATE)) - 1;

        AliasSuggestion suggestion;
        try {
            suggestion = aliasSuggester.suggestAliases().get(index);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (command.hasOption(CliOptions.ALTERNATIVE)) {
            suggestion.setAliasName(command.getOptionValue(CliOptions.ALTERNATIVE));
        }

        aliasSuggester.applySuggestion(suggestion);

        System.out.println("New alias was created: " + suggestion.getAlias().toString());
    }

    private void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("aka", CliOptions.getOptions());
    }

    private void printSuggestions() throws IOException {
        System.out.printf("%-14s %-90s %s\n", "SUGGESTED", "COMMAND", "TIMES USED");

        List<AliasSuggestion> suggestions = aliasSuggester.suggestAliases();
        for (int i = 0; i < 5; i++) {
            AliasSuggestion s = suggestions.get(i);
            System.out.printf("(%d) %-10s %-90s %s\n", i + 1, s.getAlias().getName(), s.getAlias().getValue(), s.getCount());
        }
    }

}
