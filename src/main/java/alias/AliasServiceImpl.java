package alias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
public class AliasServiceImpl implements AliasService {

    private final AliasUserRepository aliasUserRepository;
    private final AliasSystemRepository aliasSystemRepository;

    @Autowired
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
    public boolean aliasValueExists(Alias alias) throws IOException {
        return getAllAliases().stream()
                .map(Alias::getValue)
                .collect(toSet())
                .contains(alias.getValue());
    }

    @Override
    public void addAlias(Alias alias) throws IOException {
        if (aliasNameExists(alias)) {
            throw new AliasAlreadyExists("alias " + alias.getName() + " already exists");
        }

        aliasUserRepository.addAlias(alias);
    }

}


