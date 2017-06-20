/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mshroff
 */
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class DownloadFile {

  public static void main(String[] args) throws IOException {
		 
		 String fileName = "book.txt"; //The file will be saved to your current folder
		 URL link = new URL("http://www.textfiles.com/stories/lmtchgrl.txt"); //The file that you want to download
		
     //Download the file
		 InputStream in = new BufferedInputStream(link.openStream());
		 ByteArrayOutputStream out = new ByteArrayOutputStream();
		 byte[] buf = new byte[1024];
		 int n = 0;
		 while (-1!=(n=in.read(buf)))
		 {
		    out.write(buf, 0, n);
		 }
		 out.close();
		 in.close();
		 byte[] response = out.toByteArray();
 
		 FileOutputStream fos = new FileOutputStream(fileName);
		 fos.write(response);
		 fos.close();
     //End download code
		 
		 System.out.println("File has been downloaded");

	}

}
 
