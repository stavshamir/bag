package aka.alias;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;

public class AliasSystemRepositoryImpl implements AliasSystemRepository {

    private final String aliasScriptPath;

    public AliasSystemRepositoryImpl(String aliasScriptPath) {
        this.aliasScriptPath = aliasScriptPath;
    }

    @Override
    public Set<Alias> getAliases() throws IOException {
        Process process = new ProcessBuilder("bash", "-i", aliasScriptPath).start();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            return reader.lines()
                    .map(Alias::fromString)
                    .collect(Collectors.toSet());
        }
    }

}
