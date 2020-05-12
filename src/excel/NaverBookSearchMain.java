package excel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class NaverBookSearchMain {
	
	public static void main(String[] args) {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("책제목 : ");
			String title = bf.readLine();
			System.out.print("책저자 : ");
			String author = bf.readLine();
			System.out.print("출판사 : ");
			String company = bf.readLine();
			
			title = "Java의 정석";
			author = "남궁성";
			company = "도우출판";
			
			ExcelVO vo = new ExcelVO(title, author, company);
			getIsbnImage(vo);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static void getIsbnImage(ExcelVO vo) {
		System.out.println(vo);
		try {
			String openApi = "https://openapi.naver.com/v1/search/book_adv.xml?"
					+ "d_titl=" + URLEncoder.encode(vo.getTitle(), "UTF-8")
					+ "&d_auth=" + URLEncoder.encode(vo.getAuthor(), "UTF-8")
					+ "&d_publ=" + URLEncoder.encode(vo.getCompany(), "UTF-8");
			//System.out.println(openApi);
			URL url = new URL(openApi);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", NaverBookSearchApi.CLIENT_ID);
			con.setRequestProperty("X-Naver-Client-Secret", NaverBookSearchApi.CLIENT_SECRET);
			int responseCode = con.getResponseCode();
			BufferedReader br1 = null;
			if(responseCode == 200) {
				br1 = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			} else {
				br1 = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer response = new StringBuffer();
			while((inputLine = br1.readLine()) != null) {
				response.append(inputLine);
			}
			br1.close();
			//isbn, image
			Document doc = Jsoup.parse(response.toString());
			//System.out.println(doc.toString());
			Element total = doc.selectFirst("total");
			System.out.println(total.text());
			if(!total.text().equals("0")) {
				Element isbn = doc.selectFirst("isbn");
				String isbnStr = isbn.text();
				vo.setIsbn(isbnStr.split(" ")[1]);
				System.out.println("isbn : " + vo.getIsbn());
				
				String imgDoc = doc.toString();
				String imgTag = doc.toString().substring(imgDoc.indexOf("<img>") + 5);
				String imageUrl = imgTag.substring(0, imgTag.indexOf("?"));
				System.out.println(imageUrl);
				String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
				System.out.println(fileName);
				vo.setImgUrl(fileName);
				System.out.println(vo);
			} else {
				System.out.println("검색 데이터가 없습니다.");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	 
}
