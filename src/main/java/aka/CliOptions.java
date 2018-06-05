package aka;


import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

class CliOptions {

    static final String PRINT = "p";

    static final String CREATE = "c";
    static final String CREATE_LONG = "create";

    static final String ALTERNATIVE = "a";
    static final String ALTERNATIVE_LONG = "alternative";

    static final String HELP = "h";
    private static final String HELP_LONG = "help";

    private static final Option print = Option.builder(PRINT)
            .required(false)
            .hasArg(false)
            .desc("print a list of suggested aliases")
            .build();

    private static final Option create = Option.builder(CREATE)
            .required(false)
            .hasArg()
            .argName("suggested alias name")
            .longOpt(CREATE_LONG)
            .desc("create an alias with the suggested name")
            .build();

    private static final Option alternative = Option.builder(ALTERNATIVE)
            .required(false)
            .hasArg()
            .argName("alias name")
            .longOpt(ALTERNATIVE_LONG)
            .desc("create an alias with an alternative name")
            .build();

    private static final Option help = Option.builder(HELP)
            .required(false)
            .hasArg(false)
            .longOpt(HELP_LONG)
            .desc("print usage")
            .build();

    static Options getOptions() {
        return new Options()
                .addOption(print)
                .addOption(create)
                .addOption(alternative)
                .addOption(help);
    }

}
