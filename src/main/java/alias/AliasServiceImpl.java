package alias;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class AliasServiceImpl implements AliasService {

    @Override
    public Set<Alias> getSystemAliases() throws IOException {

        Process process = new ProcessBuilder("bash", "-i", "src/main/resources/alias.sh").start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        return reader.lines()
                .map(line -> StringUtils.removeStart(line, "alias "))
                .map(Alias::fromReusableForm)
                .collect(toSet());
    }

}


