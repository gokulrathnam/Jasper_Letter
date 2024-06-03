package gokul;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Sample {
	public static void main(String[] args) throws IOException {
		String dLstLoc = "E:Gokul_Jasper/CBI/WorkingFIle_13012023/New_24062023/LstFile";
		File file = new File(dLstLoc);
		File getfile = File.createTempFile("Letter_Content_", ".lst",file);
		String getfilename = getfile.getName();
		System.out.println(getfilename);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime date = LocalDateTime.now();
		String Date = dtf.format(date);
		System.out.println(Date);
		
		File file1 = new File(dLstLoc+"/"+getfilename);
		if(file1.exists())
			System.out.println(file1);
	}

}
