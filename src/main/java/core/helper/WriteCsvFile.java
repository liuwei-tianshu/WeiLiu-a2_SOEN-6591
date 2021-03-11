package core.helper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;


public class WriteCsvFile {

	private static final String NEW_LINE_SEPARATOR = "\n";

	public static void writeCsv(String[][] result, String fileName) {

		FileWriter fileWriter = null;

		CSVPrinter csvFilePrinter = null;

		// create csv format

		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

		try {

			// initialize filewriter

			fileWriter = new FileWriter(fileName + ".csv");

			// initialize CSVPrinter

			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

			// create a csv header

			// csvFilePrinter.printRecord(FILE_HEADER);

			for (int q = 0; q < result.length; q++) {
				List<String> record = new ArrayList<String>();
				for (int p = 0; p < result.length; p++) {
					record.add(result[q][p]);

				}
				csvFilePrinter.printRecord(record);
			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {

				fileWriter.flush();

				fileWriter.close();

				csvFilePrinter.close();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

	}

	public static void writeCsv(List<List<String>> result, String fileName) {

		FileWriter fileWriter = null;

		CSVPrinter csvFilePrinter = null;

		// create csv format

		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

		try {

			// initialize filewriter

			fileWriter = new FileWriter(fileName + ".csv");

			// initialize CSVPrinter

			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

			// create a csv header

			// csvFilePrinter.printRecord(FILE_HEADER);

			for (Object obj : result) {
				csvFilePrinter.printRecord(obj.toString().replace("[", "").replaceAll("]", ""));
			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {

				fileWriter.flush();

				fileWriter.close();

				csvFilePrinter.close();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

	}

}
