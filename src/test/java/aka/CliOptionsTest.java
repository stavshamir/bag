package aka;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class CliOptionsTest {

    private CommandLineParser parser = new DefaultParser();

    @Test
    public void print() throws ParseException {
        String[] print = { "-p" };
        CommandLine cmd = parser.parse(CliOptions.getOptions(), print);
        assertThat(cmd.hasOption(CliOptions.PRINT))
                .isTrue();
    }

    @Test
    public void apply() {
        Function<String[], CommandLine> parse = args -> {
            try {
                return parser.parse(CliOptions.getOptions(), args);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        };

        assertThat(parse.apply(new String[]{ "-a", "ak"}).getOptionValue(CliOptions.APPLY))
                .isEqualTo("ak");

        assertThat(parse.apply(new String[]{ "--apply", "ak"}).getOptionValue(CliOptions.APPLY))
                .isEqualTo("1");
    }

}