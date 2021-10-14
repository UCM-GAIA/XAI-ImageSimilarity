package es.ucm.fdi.xcolibri.images.metrics;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Pixel2PixelSimilarity implements ImageSimilarityMetric {

	@Override
	public double computeSimilarity(File file1, File file2) throws Exception {
		BufferedImage img1 = ImageIO.read(file1);
		BufferedImage img2 = ImageIO.read(file2);
		int width = img1.getWidth();
	    int height = img1.getHeight();
	    int width2 = img2.getWidth();
	    int height2 = img2.getHeight();
	    if (width != width2 || height != height2) {
	        throw new IllegalArgumentException(String.format("Images must have the same dimensions: (%d,%d) vs. (%d,%d)", width, height, width2, height2));
	    }

	    double diff = 0;
	    for (int y = 0; y < height; y++) {
	        for (int x = 0; x < width; x++) {
	            diff += pixelDiff(img1.getRGB(x, y), img2.getRGB(x, y));
	        }
	    }
	    double maxDiff = 3L * 255 * width * height;

	    double res =  1.0 - diff / maxDiff;
	    return Math.round(res * 100000.0) / 100000.0;

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

	public static void main(String[] args) {
		Pixel2PixelSimilarity sim = new Pixel2PixelSimilarity();
		File file1 = new File("testImg/file1.jpg");
		File file2 = new File("testImg/file2.jpg");
		File file3 = new File("testImg/file3.jpg");
		File file4 = new File("testImg/file4.jpg");
		File file5 = new File("testImg/file5.jpg");
		File file6 = new File("testImg/file6.jpg");

		try {
			System.out.println(sim.computeSimilarity(file3, file4));
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
		}

	}

}
