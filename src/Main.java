import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

/*import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;*/

 
public class Main {
 
	/*static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) throws IOException {*/
		
		/*
		  * IMREAD_UNCHANGED = -1: No conversion is performed. For example, if it is saved as a 16-bit image, it will still be read as 16-bit.
		  * IMREAD_GRAYSCALE = 0: Convert to grayscale image, for example, save as a 16-bit image, read out as 8-bit, type is CV_8UC1.
		  * IMREAD_COLOR = 1: Convert to three-channel image.
		  * IMREAD_ANYDEPTH = 2: If the image depth is 16 bits, it will be read as 16 bits, 32 bits will be read as 32 bits, and the rest will be converted to 8 bits.
		  * IMREAD_ANYCOLOR = 4: The image is read in any possible color format
		  * IMREAD_LOAD_GDAL = 8: Use GDAL driver to read files, GDAL (Geospatial Data Abstraction
		  * Library) is an open source raster spatial data conversion library under the X/MIT license agreement. It uses abstract data models to express the various file formats supported.
		  * It also has a series of command-line tools for data conversion and processing.
		 */
		
		/*File dir = new File("topImg");
    	FileWriter output = new FileWriter("similMatrixCorrel.csv");
    	File[] directoryListing = dir.listFiles();
    	Mat src1, src2;
		MatOfFloat mfloat =new MatOfFloat(0, 256);
		MatOfInt mint = new MatOfInt(255);
		Mat hist1 = new Mat(), hist2 = new Mat();
		List<Mat> matList1 = new ArrayList<Mat>(), matList2 = new ArrayList<Mat>();
		double res;
		
    	output.append("Images;");
    	for (File f : directoryListing)
    		output.append(f.getName() + ";");
    	output.append('\n');
    	for (File f1 : directoryListing) {
    		output.append(f1.getName() + ";");
    		src1 = Imgcodecs.imread(f1.getPath(), -1);
    		matList1.add(src1);
    		Imgproc.calcHist(matList1, new MatOfInt(0), new Mat(), hist1, mint, mfloat);
    		for (File f2 : directoryListing) {
    			if (f1.equals(f2))
    		        output.append("1;");
    			else {
    				src2 = Imgcodecs.imread(f2.getPath(), -1);
        			matList2.add(src2);
        			Imgproc.calcHist(matList2, new MatOfInt(0), new Mat(), hist2, mint, mfloat);
        			Core.normalize(hist1, hist1, 1, hist1.rows(), Core.NORM_MINMAX, -1, new Mat()); 
        			Core.normalize(hist2, hist2, 1, hist2.rows(), Core.NORM_MINMAX, -1, new Mat()); 
        			res = Imgproc.compareHist(hist1, hist2, Imgproc.HISTCMP_BHATTACHARYYA);
    		        output.append(res + ";");
    		        matList2.clear();
    			}
	        }
        	output.append('\n');
	        matList1.clear();
	    }
    	output.flush();
    	output.close();*/
    	/*
		  * HISTCMP_CORREL = 0, correlation comparison
		  * HISTCMP_CHISQR = 1, chi-square comparison
		  * HISTCMP_INTERSECT = 2, crisscross
		  * HISTCMP_BHATTACHARYYA = 3, Babbitt distance
		 * HISTCMP_HELLINGER = HISTCMP_BHATTACHARYYA,
		 * HISTCMP_CHISQR_ALT = 4,
		 * HISTCMP_KL_DIV = 5;
		 */	
		
		
		
			
	//}
    
	public static void main(String[] args) throws IOException {
		boolean correcto = true;
    	double array[][] = new double[200][200];
    	double p;
    	
    	File dir = new File("topImg");
    	FileWriter output = new FileWriter("similMatrixDifference.csv");
    	File[] directoryListing = dir.listFiles();
    	BufferedImage img1, img2;
    	output.append("Images;");
    	
    	for (File f : directoryListing)
    		output.append(f.getName() + ";");
    	output.append('\n');
    	
    	int i = 0;
    	for (File f1 : directoryListing) {
    		output.append(f1.getName() + ";");
	    	img1 = ImageIO.read(f1);
	    	int j = 0;
    		for (File f2 : directoryListing) {
    			if (f1.equals(f2)) {
    		        output.append("0;");
    		        array[i][j] = 0;
    			}
    			else {
    				img2 = ImageIO.read(f2);
    		        p = getDifferencePercent(img1, img2);
    		        output.append(p + ";");
    		        array[i][j] = p;
    			}
    			j++;
	        }
        	output.append('\n');
    		i++;
	    }
    	output.flush();
    	output.close();
    	
    	for (int x = 0; x < 200; x++) {
    		for (int y = x; y < 200; y++) {
    			correcto &= array[x][y] == array[y][x];
    		}
    	}
    	System.out.println(correcto);
    }
 
    private static double getDifferencePercent(BufferedImage img1, BufferedImage img2) {
        int width = img1.getWidth();
        int height = img1.getHeight();
        int width2 = img2.getWidth();
        int height2 = img2.getHeight();
        if (width != width2 || height != height2) {
            throw new IllegalArgumentException(String.format("Images must have the same dimensions: (%d,%d) vs. (%d,%d)", width, height, width2, height2));
        }
 
        long diff = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                diff += pixelDiff(img1.getRGB(x, y), img2.getRGB(x, y));
            }
        }
        long maxDiff = 3L * 255 * width * height;
 
        return 100.0 * diff / maxDiff;
    }
 
    private static int pixelDiff(int rgb1, int rgb2) {
        int r1 = (rgb1 >> 16) & 0xff;
        int g1 = (rgb1 >>  8) & 0xff;
        int b1 =  rgb1        & 0xff;
        int r2 = (rgb2 >> 16) & 0xff;
        int g2 = (rgb2 >>  8) & 0xff;
        int b2 =  rgb2        & 0xff;
        return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
    }
}
