package aka;


import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

class CliOptions {

    static final String PRINT = "p";

    static final String APPLY = "a";
    private static final String APPLY_LONG = "apply";

    static final String HELP = "h";
    private static final String HELP_LONG = "help";

    private static final Option print = Option.builder(PRINT)
            .required(false)
            .hasArg(false)
            .desc("print a list of suggested aliases")
            .build();

    private static final Option apply = Option.builder(APPLY)
            .required(false)
            .hasArg()
            .argName("alias name")
            .longOpt(APPLY_LONG)
            .desc("apply the suggested alias")
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
                .addOption(apply)
                .addOption(help);
    }

}
