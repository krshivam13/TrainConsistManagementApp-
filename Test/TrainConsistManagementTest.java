import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;

public class TrainConsistManagementTest {

    static class Bogie {
        String type;
        int capacity;

        Bogie(String type, int capacity) {
            this.type = type;
            this.capacity = capacity;
        }
    }

    private List<Bogie> getBogies(int size) {
        List<Bogie> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(new Bogie("Type" + i, i % 100));
        }
        return list;
    }

    @Test
    void testLoopFilteringLogic() {
        List<Bogie> bogies = getBogies(100);

        List<Bogie> result = new ArrayList<>();
        for (Bogie b : bogies) {
            if (b.capacity > 60) {
                result.add(b);
            }
        }

        assertTrue(result.stream().allMatch(b -> b.capacity > 60));
    }

    @Test
    void testStreamFilteringLogic() {
        List<Bogie> bogies = getBogies(100);

        List<Bogie> result = bogies.stream()
                .filter(b -> b.capacity > 60)
                .collect(Collectors.toList());

        assertTrue(result.stream().allMatch(b -> b.capacity > 60));
    }

    @Test
    void testLoopAndStreamResultsMatch() {
        List<Bogie> bogies = getBogies(100);

        List<Bogie> loopResult = new ArrayList<>();
        for (Bogie b : bogies) {
            if (b.capacity > 60) {
                loopResult.add(b);
            }
        }

        List<Bogie> streamResult = bogies.stream()
                .filter(b -> b.capacity > 60)
                .collect(Collectors.toList());

        assertEquals(loopResult.size(), streamResult.size());
    }

    @Test
    void testExecutionTimeMeasurement() {
        List<Bogie> bogies = getBogies(1000);

        long start = System.nanoTime();
        bogies.stream().filter(b -> b.capacity > 60).toList();
        long end = System.nanoTime();

        assertTrue((end - start) > 0);
    }

    @Test
    void testLargeDatasetProcessing() {
        List<Bogie> bogies = getBogies(100000);

        List<Bogie> result = bogies.stream()
                .filter(b -> b.capacity > 60)
                .collect(Collectors.toList());

        assertNotNull(result);
    }
}