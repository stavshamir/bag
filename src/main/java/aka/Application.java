package aka;

import aka.alias.*;
import aka.history.BashHistoryFromFileRepository;
import aka.history.BashHistoryRepository;
import aka.suggester.AliasSuggester;
import aka.suggester.AliasSuggestion;
import org.apache.commons.cli.*;
import java.io.IOException;
import java.util.List;


public class Application{

    public static void main(String[] args) throws IOException {
        CommandLineParser parser = new DefaultParser();

        CommandLine cmd;
        try {
            cmd = parser.parse(CliOptions.getOptions(), args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            printUsage();
            return;
        }

        AliasSuggester aliasSuggester;
        try {
            aliasSuggester = getAliasSuggester("config/application.properties");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        if (cmd.hasOption(CliOptions.PRINT)) {
            printSuggestions(aliasSuggester);
        } else if (cmd.hasOption(CliOptions.CREATE)) {
            createAlias(cmd, aliasSuggester);
        } else if (cmd.hasOption(CliOptions.HELP)) {
            printUsage();
        } else {
            printUsage();
        }

    }

    private static void createAlias(CommandLine command, AliasSuggester aliasSuggester) {
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

        try {
            aliasSuggester.applySuggestion(suggestion);
        } catch (IOException | AliasAlreadyExists e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("New alias was created: " + suggestion.getAlias().toString());
    }

    private static void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("aka", CliOptions.getOptions());
    }

    private static void printSuggestions(AliasSuggester aliasSuggester) throws IOException {
        System.out.printf("%-14s %-90s %s\n", "SUGGESTED", "COMMAND", "TIMES USED");

        List<AliasSuggestion> suggestions = aliasSuggester.suggestAliases();
        for (int i = 0; i < 5; i++) {
            AliasSuggestion s = suggestions.get(i);
            System.out.printf("(%d) %-10s %-90s %s\n", i + 1, s.getAlias().getName(), s.getAlias().getValue(), s.getCount());
        }
    }

    private static AliasSuggester getAliasSuggester(final String propertiesFilePath) throws IOException {
        PropertiesService propertiesService = new PropertiesService(propertiesFilePath);

        AliasUserRepository aliasUserRepository = new AliasUserFileRepository(propertiesService.getAliasUserFilePath());
        AliasSystemRepository aliasSystemRepository = new AliasSystemRepositoryImpl(propertiesService.getAliasScript());
        AliasService aliasService = new AliasServiceImpl(aliasUserRepository, aliasSystemRepository);
        BashHistoryRepository bashHistoryRepository = new BashHistoryFromFileRepository(propertiesService.getBashHistoryFilePath());

        return new AliasSuggester(aliasService, bashHistoryRepository);
    }

}
