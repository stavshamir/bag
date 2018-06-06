package com.stavshamir.bag;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.junit.Test;

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
    public void create_suggested_name() throws ParseException {
        assertThat(parse(new String[]{ "-c", "ak"}).getOptionValue(CliOptions.CREATE))
                .isEqualTo("ak");

        assertThat(parse(new String[]{ "--create", "ak"}).getOptionValue(CliOptions.CREATE))
                .isEqualTo("ak");
    }

    @Test
    public void create_other_name() throws ParseException {
        final String[] createArgs = {"-c", "bar", "-a", "foo"};
        final CommandLine commandLine = parse(createArgs);

        assertThat(commandLine.getOptionValue(CliOptions.ALTERNATIVE_LONG))
                .isEqualTo("foo");

    }

    private CommandLine parse(String[] args) throws ParseException {
        return parser.parse(CliOptions.getOptions(), args);
    }

}