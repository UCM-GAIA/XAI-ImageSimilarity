package es.ucm.fdi.xcolibri.images.metrics;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ColorHistogramSimilarity implements ImageSimilarityMetric {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	@Override
	public double computeSimilarity(File file1, File file2) throws Exception {
		List<Mat> matList1 = new ArrayList<Mat>(), matList2 = new ArrayList<Mat>();
		Mat hist1 = new Mat(), hist2 = new Mat();
		MatOfFloat mfloat =new MatOfFloat(0, 256);
		MatOfInt mint = new MatOfInt(255);
		
		Mat src1 = Imgcodecs.imread(file1.getPath(), -1);
		matList1.add(src1);
		Imgproc.calcHist(matList1, new MatOfInt(0), new Mat(), hist1, mint, mfloat);
		
		Mat src2 = Imgcodecs.imread(file2.getPath(), -1);
    	matList2.add(src2);
    	Imgproc.calcHist(matList2, new MatOfInt(0), new Mat(), hist2, mint, mfloat);
    			
    	Core.normalize(hist1, hist1, 1, hist1.rows(), Core.NORM_MINMAX, -1, new Mat()); 
    	Core.normalize(hist2, hist2, 1, hist2.rows(), Core.NORM_MINMAX, -1, new Mat()); 
    	double res = Imgproc.compareHist(hist1, hist2, Imgproc.HISTCMP_BHATTACHARYYA);
    	
		return Math.round((1-res) * 100000.0) / 100000.0;
	}

	
	public static void main(String[] args) {
		//System.out.println(System.getProperty("java.library.path"));
			
		//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		
		ColorHistogramSimilarity sim = new ColorHistogramSimilarity();
		File file1 = new File("testImg/file1.jpg");
		File file2 = new File("testImg/file2.jpg");
		File file3 = new File("testImg/file3.jpg");
		File file4 = new File("testImg/file4.jpg");
		File file5 = new File("testImg/file5.jpg");
		File file6 = new File("testImg/file6.jpg");

		try {
			System.out.println(sim.computeSimilarity(file1, file2));
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
		
		

	}

}
