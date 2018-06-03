package history;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class BashHistoryFromFileRepositoryTest {

    private static final String bashHistoryFilePath = "src/test/resources/test_bash_history";
    private final BashHistoryRepository bashHistoryRepository = new BashHistoryFromFileRepository(bashHistoryFilePath);

    @Test
    public void getBashHistory() throws IOException {
        List<String> history = bashHistoryRepository.getBashHistory();
        assertThat(history)
                .contains("sudo apt-get update");
    }
}