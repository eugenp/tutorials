package jvector;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import io.github.jbellis.jvector.graph.GraphIndexBuilder;
import io.github.jbellis.jvector.graph.ListRandomAccessVectorValues;
import io.github.jbellis.jvector.graph.OnHeapGraphIndex;
import io.github.jbellis.jvector.graph.RandomAccessVectorValues;
import io.github.jbellis.jvector.graph.disk.OnDiskGraphIndex;
import io.github.jbellis.jvector.graph.similarity.BuildScoreProvider;
import io.github.jbellis.jvector.vector.VectorSimilarityFunction;
import io.github.jbellis.jvector.vector.types.VectorFloat;

public class VectorSearch {

    public static void persistIndex(List<VectorFloat<?>> baseVectors, Path indexPath) throws IOException {
        int originalDimension = baseVectors.get(0)
            .length();

        RandomAccessVectorValues vectorValues = new ListRandomAccessVectorValues(baseVectors, originalDimension);

        BuildScoreProvider scoreProvider = BuildScoreProvider.randomAccessScoreProvider(vectorValues, VectorSimilarityFunction.EUCLIDEAN);

        try (GraphIndexBuilder builder = new GraphIndexBuilder(scoreProvider, vectorValues.dimension(), 28, 100, 1.2f, 1.2f, true)) {
            OnHeapGraphIndex index = builder.build(vectorValues);

            OnDiskGraphIndex.write(index, vectorValues, indexPath);
        }
    }
}
