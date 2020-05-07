package jsoup;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlingPractice2 {

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
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
