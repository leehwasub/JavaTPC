package jsoup;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class DownloadBroker implements Runnable {
	
	private String address;
	private String fileName;
	
	public DownloadBroker(String address, String fileName) {
		this.address = address;
		this.fileName = fileName;
	}

	@Override
	public void run() {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			
			URL url = new URL(address);
			InputStream is = url.openStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			int data;
			while((data = bis.read()) != -1) {
				bos.write(data);
			}
			bos.close();
			bis.close();
			System.out.println("download complete...");
			System.out.println(fileName);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
