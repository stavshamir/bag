package com.stavshamir.bag.history;

import java.io.IOException;
import java.util.List;

public interface BashHistoryRepository {
    List<String> getBashHistory() throws IOException;
}
