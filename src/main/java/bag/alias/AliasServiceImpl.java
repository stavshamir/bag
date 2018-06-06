package bag.alias;

import java.io.IOException;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class AliasServiceImpl implements AliasService {

    private final AliasUserRepository aliasUserRepository;
    private final AliasSystemRepository aliasSystemRepository;

    private Set<Alias> allAliasesCache;

    public AliasServiceImpl(AliasUserRepository aliasUserRepository, AliasSystemRepository aliasSystemRepository) {
        this.aliasUserRepository = aliasUserRepository;
        this.aliasSystemRepository = aliasSystemRepository;
    }

    @Override
    public Set<Alias> getAllAliases() throws IOException {
        if (allAliasesCache != null) {
            return allAliasesCache;
        }

        allAliasesCache = aliasSystemRepository.getAliases();
        allAliasesCache.addAll(aliasUserRepository.getAliases());
        return allAliasesCache;
    }

    @Override
    public boolean aliasNameExists(Alias alias) throws IOException {
        return getAllAliases().stream()
                .map(Alias::getName)
                .collect(toSet())
                .contains(alias.getName());
    }

    @Override
    public void addAlias(Alias alias) throws IOException {
        if (aliasNameExists(alias)) {
            throw new AliasAlreadyExists("alias " + alias.getName() + " already exists");
        }

        aliasUserRepository.addAlias(alias);
    }

}


