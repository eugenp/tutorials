package jvector;

import static jvector.VectorSearch.persistIndex;

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
import java.util.Objects;

import org.junit.jupiter.api.Test;

import io.github.jbellis.jvector.disk.ReaderSupplier;
import io.github.jbellis.jvector.disk.ReaderSupplierFactory;
import io.github.jbellis.jvector.graph.GraphIndex;
import io.github.jbellis.jvector.graph.GraphSearcher;
import io.github.jbellis.jvector.graph.ListRandomAccessVectorValues;
import io.github.jbellis.jvector.graph.RandomAccessVectorValues;
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

    @Test
    void givenLoadedDataset_whenSearchingForWord_thenPersistIndexInDiskAndFindSimilarResults() throws IOException {
        URL datasetResource = getClass().getClassLoader()
            .getResource("glove.6B.50d.txt");
        Objects.requireNonNull(datasetResource);

        Map<String, VectorFloat<?>> vectors = loadGlove6B50dDataSet(datasetResource.getPath(), 1000);
        VectorFloat<?> queryVector = vectors.get("said");

        Path indexPath = Files.createTempFile("sample", ".inline");

        ArrayList<VectorFloat<?>> vectorsList = new ArrayList<>(vectors.values());

        persistIndex(vectorsList, indexPath);

        try (ReaderSupplier readerSupplier = ReaderSupplierFactory.open(indexPath)) {
            GraphIndex index = OnDiskGraphIndex.load(readerSupplier);

            int originalDimension = vectorsList.get(0)
                .length();

            RandomAccessVectorValues vectorValues = new ListRandomAccessVectorValues(vectorsList, originalDimension);

            SearchResult result = GraphSearcher.search(queryVector, 10, vectorValues, VectorSimilarityFunction.EUCLIDEAN, index, Bits.ALL);
            System.out.println(Arrays.toString(result.getNodes()));
        }
    }

    private static Map<String, VectorFloat<?>> loadGlove6B50dDataSet(String filePath, int limit) throws IOException {
        Map<String, VectorFloat<?>> vectors = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
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
        return vectors;
    }
}