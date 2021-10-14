package es.ucm.fdi.xcolibri.images.metrics;

import java.io.File;

public interface ImageSimilarityMetric {
	public double computeSimilarity(File file1, File file2) throws Exception ;
}
