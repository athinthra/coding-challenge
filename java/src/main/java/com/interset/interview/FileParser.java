package com.interset.interview;

import java.io.File;
import java.util.List;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import com.interset.interview.StatCalculator;
import com.interset.interview.StatPrinter;

/*
 * Utility to identify the the type of file and parse it accordingly
 * Calculates the required stats for printing
 */
public class FileParser {
	private String filePath;
	private StatPrinter calculatedResult;
	private String fileExt;
	
	/*
	 * Constructor method that finds file extensions
	 * and initializes statprinter object
	 */
	public FileParser(String path)
	{
		this.filePath = path;
		this.fileExt = this.getFileExt(path);
		calculatedResult = new StatPrinter();
	}
	
	/*
	 * Enumerator for the fileype
	 */
	private enum FileType
	{
		JSON,
		CSV, 
		GZ;
		
		public static FileType fromValue(String v) {
			try {
	            return valueOf(v.toUpperCase());
	        } catch (Exception ex) {
	        	return GZ;
	        }
		}
	}
	 /*
	  * getter method for statprinter object
	  */
	
	public StatPrinter getPopulation()
	{
		return this.calculatedResult;
	}
	
	/* Method to extract the file extension
	 * @param file path 
	 * @return file extension
	 */
	private String getFileExt(String path)
	{
		return path.substring(path.lastIndexOf(".") +1 , path.length()).toUpperCase();
	}
	
	/*Method to analyze filepath and its extension. Takes appropriate
	 * steps according to file extension
	 * @param file path
	 */
	
	private boolean isZipFile()
	{
		switch (FileType.fromValue(this.fileExt))
		{
		case JSON:
		case CSV: 
			return false;
		default : return true;
		}
	}
	
	public void fileAnalyzer(String path) throws IOException {
		
		
		if(isZipFile())
		{
			this.filePath = unzipFile(this.filePath);
			this.fileExt = this.getFileExt(this.filePath);
		}

		switch (FileType.valueOf(this.fileExt))
		{
		case JSON: JSONReader(); break;
		case CSV : CSVReader(); break;
		}

	}
	
	/*
	 * Method to read and parse CSV type files
	 */
	public void CSVReader() throws IOException
	{
		CsvMapper csvMapper = new CsvMapper();
		CsvSchema csvSchema = csvMapper.typedSchemaFor(StatCalculator.class).withHeader(); 
		calculatedResult.setRecords( csvMapper.readerFor(StatCalculator.class)
				.with(csvSchema.withColumnSeparator(CsvSchema.DEFAULT_COLUMN_SEPARATOR))
				.readValues(new File(this.filePath)).readAll());    
	}
	
	/*
	 * Method to read and parse JSON type files
	 */
	public void JSONReader() throws IOException
	{		
		ObjectMapper mapper = new ObjectMapper();
		this.calculatedResult.setRecords(mapper.reader()
				.forType(new TypeReference<List<StatCalculator>>() {})
				.readValue(new File(this.filePath)));		
	}
	
	/*
	 * Method to unzip *.gzip type file
	 * @param file path of gzip type file
	 * @return file path of unzipped file
	 */
	private static String unzipFile(String zipFilePath) throws IOException { 
		String file = "";
		file = zipFilePath.substring(0, zipFilePath.lastIndexOf("."));
		FileInputStream ft1 = new FileInputStream(zipFilePath);
		GZIPInputStream ft2 = new GZIPInputStream(ft1);
		FileOutputStream ft3 = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int len;
		while((len = ft2.read(buffer)) != -1){
			ft3.write(buffer, 0, len);
		}
		ft3.close();
		ft2.close();

		return file;
	}
}
