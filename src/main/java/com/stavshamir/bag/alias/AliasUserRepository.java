package com.stavshamir.bag.alias;

import java.io.IOException;

public interface AliasUserRepository extends AliasRepository{
    void addAlias(Alias alias) throws IOException;
}
