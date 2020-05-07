package jsoup;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlingPractice3 {

	public static void main(String[] args) {
		String url = "https://sum.su.or.kr:8888/bible/today/Ajax/Bible/BodyMatter?qt_ty=QT1";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("[입력->년(yyyy)-월(mm)-일(dd)] : ");
			String bible = br.readLine();
			url += "&base_de=" + bible + "&bibleType=1";
			System.out.println("================================");
			Document doc = Jsoup.connect(url).post();
			Element bibleText = doc.selectFirst(".bible_text");
			System.out.println(bibleText.text());
			
			Element bibleInfoBox = doc.selectFirst(".bibleinfo_box");
			System.out.println(bibleInfoBox.text());
			
			Elements liList = doc.select(".body_list > li");
			for(Element li : liList) {
				System.out.println(li.selectFirst(".num").text() + " : " + li.selectFirst(".info").text());
			}
			// downloading resource
			// (mp3)
			
			Element tag = doc.selectFirst("source");
			String dPath = tag.attr("src").trim();
			System.out.println(dPath); // http://meditation.su.or.kr/meditation_mp3/2020/20200507.mp3
			String fileName = dPath.substring(dPath.lastIndexOf("/") + 1);
			
			// (image)
			/*Element tag = doc.selectFirst(".img > img");
			String dPath = "https://sum.su.or.kr:8888" + tag.attr("src").trim();
			System.out.println(dPath);
			String fileName = dPath.substring(dPath.lastIndexOf("/") + 1);*/
			
			Runnable r = new DownloadBroker(dPath, fileName);
			Thread dload = new Thread(r);
			dload.start();
			for(int i = 0; i < 10; i++) {
				try {
					Thread.sleep(1000);
				}catch(Exception e) {
					e.printStackTrace();
				}
				System.out.print(""+(i+1));
			}
			System.out.println();
			System.out.println("============================");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
