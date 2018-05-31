package alias;

import java.io.IOException;
import java.util.Set;

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
    public void addAlias(Alias alias) {

    }

}


