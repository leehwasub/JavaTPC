package naverMapsOpenAPI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.gson.JsonObject;

public class Main {
	public static void main(String[] args) {
		String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("주소를 입력하세요 : ");
			String address = URLEncoder.encode(br.readLine(), "UTF-8");
			String reqUrl = apiURL + address;
			URL url = new URL(reqUrl);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", NaverApi.CLIENT_ID);
			con.setRequestProperty("X-NCP-APIGW-API-KEY", NaverApi.CLIENT_SECRET);
			int responseCode = con.getResponseCode();
			if(responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String line;
			StringBuffer response = new StringBuffer();
			while((line = br.readLine()) != null) {
				response.append(line);
			}
			br.close();
			JSONTokener tokener = new JSONTokener(response.toString());
			JSONObject object = new JSONObject(tokener);
			//System.out.println(object.toString(3));
			JSONArray arr = object.getJSONArray("addresses");
			for(int i = 0; i < arr.length(); i++) {
				JSONObject obj = (JSONObject)arr.get(i);
				System.out.print("address : " + obj.get("roadAddress") + ", ");
				System.out.print("jibunAddress : " + obj.get("jibunAddress") + ", ");
				System.out.print("경도 : " + obj.get("x") + ", ");
				System.out.println("위도 : " + obj.get("y"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
