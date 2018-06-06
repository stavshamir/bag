package bag.history;


import java.io.*;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class BashHistoryFromFileRepository implements BashHistoryRepository {

    private final File bashHistoryFile;

    public BashHistoryFromFileRepository(String bashHistoryFilePath) {
        this.bashHistoryFile = new File(bashHistoryFilePath);
    }

    @Override
    public List<String> getBashHistory() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(bashHistoryFile))) {
            return reader.lines()
                    .collect(toList());
        }
    }
}
