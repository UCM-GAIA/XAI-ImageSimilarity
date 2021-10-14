package es.ucm.fdi.xcolibri.images;

import java.io.File;
import java.io.FileWriter;

import es.ucm.fdi.xcolibri.images.metrics.ImageSimilarityMetric;
import es.ucm.fdi.xcolibri.images.metrics.SSIM;

public class ImageSimilarityMatrix {

	public void generateSimilarityMatrix(File inputFolder, File outputCSV, String CSVseparator, ImageSimilarityMetric metric)
	{
    	
    	try {
			FileWriter output = new FileWriter(outputCSV);
			File[] directoryListing = inputFolder.listFiles();
	    	
			//first row
			output.append("Images;");
	    	for (File f : directoryListing)
	    		output.append(f.getName() + CSVseparator);
	    	output.append(System.lineSeparator());
	 
	    	
	    	for (File f1 : directoryListing) {
	    		output.append(f1.getName() + CSVseparator);
	    		for (File f2 : directoryListing) {
	    				System.out.println(f1.getName() +"<-->" + f2.getName());
	    		        double sim = metric.computeSimilarity(f1, f2);
	    		        output.append(sim + CSVseparator);    
		        }
	        	output.append(System.lineSeparator());
	    	}
			
			output.close();	
    	} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
    	
	}
	
	public static void main(String[] args) {
		ImageSimilarityMatrix ism = new ImageSimilarityMatrix();
		ImageSimilarityMetric metric = new SSIM();
		File inputDir = new File("testImg");
		File outputCSV = new File("ssim.csv");
		String CSVseparator = ";";
		
		ism.generateSimilarityMatrix(inputDir, outputCSV, CSVseparator, metric);
		
	}
}
