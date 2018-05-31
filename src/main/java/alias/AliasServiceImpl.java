package alias;

import java.io.IOException;
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
        Set<Alias> aliases = aliasSystemRepository.getAliases();
        aliases.addAll(aliasUserRepository.getAliases());
        return aliases;
    }

    @Override
    public boolean aliasNameExists(Alias alias) throws IOException {
        return getAllAliases().stream()
                .map(Alias::getName)
                .collect(toSet())
                .contains(alias.getName());
    }

    @Override
    public void addAlias(Alias alias) {

    }

}


