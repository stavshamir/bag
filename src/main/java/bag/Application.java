package bag;

import bag.alias.*;
import bag.history.BashHistoryFromFileRepository;
import bag.history.BashHistoryRepository;
import bag.suggester.AliasSuggester;
import bag.suggester.AliasSuggestion;
import org.apache.commons.cli.*;
import java.io.IOException;


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
            aliasSuggester = getAliasSuggester(System.getenv("BAG_HOME") + "/config/application.properties");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        if (cmd.hasOption(CliOptions.PRINT)) {
            printSuggestions(aliasSuggester, 5);
        } else if (cmd.hasOption(CliOptions.PRINT_ALL)) {
            printSuggestions(aliasSuggester, 100);
        } else if (cmd.hasOption(CliOptions.CREATE)) {
            createAlias(cmd, aliasSuggester);
        } else if (cmd.hasOption(CliOptions.HELP)) {
            printUsage();
        } else {
            printUsage();
        }

    }

    private static void createAlias(CommandLine command, AliasSuggester aliasSuggester) {
        int index;
        try {
            index = Integer.parseInt(command.getOptionValue(CliOptions.CREATE)) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Error: failed to parse integer value for -c");
            return;
        }

        AliasSuggestion suggestion;
        try {
            suggestion = aliasSuggester.suggestAliases().get(index);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: invalid index - please choose an index within the suggestions bounds");
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
        formatter.printHelp("bag [-c <index> [-a <alternative alias name>]]", CliOptions.getOptions());
        System.out.println("bag version 1.0 by Stav Shamir");
    }

    private static void printSuggestions(AliasSuggester aliasSuggester, int limit) throws IOException {
        System.out.printf("%-14s %-90s %s\n", "SUGGESTED", "COMMAND", "TIMES USED");

        int i = 0;
        for (AliasSuggestion s : aliasSuggester.suggestAliases()) {
            if (limit-- == 0) {
                break;
            }
            System.out.printf("(%d) %-10s %-90s %s\n", ++i, s.getAlias().getName(), s.getAlias().getValue(), s.getCount());
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
