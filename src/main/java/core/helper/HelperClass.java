package core.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class HelperClass {

	public static void fileAppendMethod(String fileName, String content) throws IOException {
		File file = new File(fileName);
		if (!file.exists())
			file.createNewFile();
		FileOutputStream out = new FileOutputStream(file, true);

		StringBuffer sb = new StringBuffer();
		sb.append(content);
		sb.append("\r");
		out.write(sb.toString().getBytes("utf-8"));

		out.close();
	}

	
}
