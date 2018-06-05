package aka;

import aka.suggester.AliasSuggester;
import org.apache.commons.cli.*;
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
    public void run(String... args) throws IOException, ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(CliOptions.getOptions(), args);

        if (cmd.hasOption(CliOptions.PRINT)) {
            printSuggestions();
        } else if (cmd.hasOption(CliOptions.CREATE)) {
            System.out.println(cmd.getOptionValue(CliOptions.CREATE));
            if (cmd.hasOption(CliOptions.ALTERNATIVE)) {
                System.out.println(cmd.getOptionValue(CliOptions.ALTERNATIVE));
            }
        } else if (cmd.hasOption(CliOptions.HELP)) {
            printUsage();
        } else {
            printUsage();
        }


    }

    private void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("aka", CliOptions.getOptions());
    }

    private void printSuggestions() throws IOException {
        String template = "%-10s %-90s %s\n";
        System.out.printf(template, "SUGGESTED", "COMMAND", "TIMES USED");
        aliasSuggester.suggestAliases()
                .forEach(s -> System.out.printf(template, s.getAlias().getName(), s.getAlias().getValue(), s.getOccurrences()));
    }

}
