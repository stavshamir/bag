package alias;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class AliasServiceImpl implements AliasService {

    private final AliasUserRepository aliasUserRepository;
    private final AliasSystemRepository aliasSystemRepository;

    public AliasServiceImpl(AliasUserRepository aliasUserRepository, AliasSystemRepository aliasSystemRepository) {
        this.aliasUserRepository = aliasUserRepository;
        this.aliasSystemRepository = aliasSystemRepository;
    }

    @Override
    public Set<Alias> getAllAliases() throws IOException {
        return aliasSystemRepository.getAliases();
    }

    @Override
    public void addAlias(Alias alias) {

    }

}


