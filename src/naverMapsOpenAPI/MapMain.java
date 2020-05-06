package naverMapsOpenAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MapMain {

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
			
			String x = "";
			String y = "";
			String z = "";
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
				x = (String)obj.get("x");
				y = (String)obj.get("y");
				z = (String)obj.get("roadAddress");
			}
			mapService(x, y, z);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static void mapService(String pointX, String pointY, String address) {
		String urlStaticMap = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
		try {
			String pos = URLEncoder.encode(pointX + " " + pointY, "UTF-8");
			urlStaticMap += "center=" + pointX + "," + pointY;
			urlStaticMap += "&level=16&w=700&h=500";
			urlStaticMap += "&markers=type:t|size:mid|pos:"+pos+"|label:"+URLEncoder.encode(address, "UTF-8");
			URL url = new URL(urlStaticMap);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", NaverApi.CLIENT_ID);
			con.setRequestProperty("X-NCP-APIGW-API-KEY", NaverApi.CLIENT_SECRET);
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if(responseCode == 200) {
				InputStream is = con.getInputStream();
				int read = 0;
				byte[] bytes = new byte[1024];
				String tempName = Long.valueOf(new Date().getTime()).toString();
				File f = new File(tempName + ".jpg");
				f.createNewFile();
				OutputStream os = new FileOutputStream(f);
				while((read = is.read(bytes)) != -1) {
					os.write(bytes, 0, read);
				}
				is.close();
				os.close();
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while((inputLine = br.readLine()) != null) {
					response.append(inputLine);
				}
				br.close();
				System.out.println(response);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
