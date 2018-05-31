package alias;

import java.io.IOException;
import java.util.Set;

public interface AliasService {

    Set<Alias> getAllAliases() throws IOException;

    boolean aliasNameExists(Alias fromUserRepository) throws IOException;

    void addAlias(Alias alias) throws IOException;
}
