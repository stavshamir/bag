package alias;

import java.io.IOException;
import java.util.Set;

public interface AliasService {
    Set<Alias> getSystemAliases() throws IOException;
}
