package bag.alias;

import java.io.IOException;
import java.util.Set;

public interface AliasRepository {
    Set<Alias> getAliases() throws IOException;
}
