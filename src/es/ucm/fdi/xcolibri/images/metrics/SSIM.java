package es.ucm.fdi.xcolibri.images.metrics;

import java.io.File;

import es.ucm.fdi.xcolibri.images.metrics.jssim.SsimCalculator;

public class SSIM implements ImageSimilarityMetric {

	@Override
	public double computeSimilarity(File file1, File file2) throws Exception {
		SsimCalculator calc = new SsimCalculator(file1);
		double res = calc.compareTo(file2);
		return Math.round(res * 100000.0) / 100000.0;

	}
	
	public static void main(String[] args) {
		ImageSimilarityMetric sim = new SSIM();
		File file1 = new File("testImg/file1.jpg");
		File file2 = new File("testImg/file2.jpg");
		File file3 = new File("testImg/file3.jpg");
		File file4 = new File("testImg/file4.jpg");
		File file5 = new File("testImg/file5.jpg");
		File file6 = new File("testImg/file6.jpg");

		try {
			System.out.println(sim.computeSimilarity(file5, file6));
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
		}

	}


}
