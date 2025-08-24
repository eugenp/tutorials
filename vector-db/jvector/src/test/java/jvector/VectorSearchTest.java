package jvector;

import static jvector.VectorSearch.persistIndex;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.jbellis.jvector.disk.ReaderSupplier;
import io.github.jbellis.jvector.disk.ReaderSupplierFactory;
import io.github.jbellis.jvector.graph.GraphIndex;
import io.github.jbellis.jvector.graph.GraphSearcher;
import io.github.jbellis.jvector.graph.ListRandomAccessVectorValues;
import io.github.jbellis.jvector.graph.SearchResult;
import io.github.jbellis.jvector.graph.disk.OnDiskGraphIndex;
import io.github.jbellis.jvector.util.Bits;
import io.github.jbellis.jvector.vector.VectorSimilarityFunction;
import io.github.jbellis.jvector.vector.VectorizationProvider;
import io.github.jbellis.jvector.vector.types.VectorFloat;
import io.github.jbellis.jvector.vector.types.VectorTypeSupport;

class VectorSearchTest {

    private static final VectorTypeSupport VECTOR_TYPE_SUPPORT = VectorizationProvider.getInstance()
        .getVectorTypeSupport();
    private static Path indexPath;
    private static Map<String, VectorFloat<?>> datasetVectors;

    @BeforeAll
    static void setup() throws IOException {
        datasetVectors = new VectorSearchTest().loadGlove6B50dDataSet(1000);
        indexPath = Files.createTempFile("sample", ".inline");
        persistIndex(new ArrayList<>(datasetVectors.values()), indexPath);
    }

    @Test
    void givenLoadedDataset_whenPersistingIndex_thenPersistIndexInDisk() throws IOException {
        try (ReaderSupplier readerSupplier = ReaderSupplierFactory.open(indexPath)) {
            GraphIndex index = OnDiskGraphIndex.load(readerSupplier);
            assertInstanceOf(OnDiskGraphIndex.class, index);
        }
    }

    @Test
    void givenLoadedDataset_whenSearchingSimilarVectors_thenReturnValidSearchResult() throws IOException {
        VectorFloat<?> queryVector = datasetVectors.get("said");
        ArrayList<VectorFloat<?>> vectorsList = new ArrayList<>(datasetVectors.values());

        try (ReaderSupplier readerSupplier = ReaderSupplierFactory.open(indexPath)) {
            GraphIndex index = OnDiskGraphIndex.load(readerSupplier);

            SearchResult result = GraphSearcher.search(queryVector, 10,
                new ListRandomAccessVectorValues(vectorsList, vectorsList.get(0).length()),
                VectorSimilarityFunction.EUCLIDEAN, index, Bits.ALL);

            assertNotNull(result.getNodes());
            assertEquals(10, result.getNodes().length);
        }
    }

    private Map<String, VectorFloat<?>> loadGlove6B50dDataSet(int limit) throws IOException {
        URL datasetResource = getClass().getClassLoader()
            .getResource("glove.6B.50d.txt");
        assertNotNull(datasetResource);

        Map<String, VectorFloat<?>> vectors = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(datasetResource.getFile()))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null && count < limit) {
                String[] values = line.split(" ");
                String word = values[0];
                VectorFloat<?> vector = VECTOR_TYPE_SUPPORT.createFloatVector(50);
                for (int i = 0; i < 50; i++) {
                    vector.set(i, Float.parseFloat(values[i + 1]));
                }
                vectors.put(word, vector);
                count++;
            }
        }
        assertEquals(1000, vectors.size());
        return vectors;
    }
}