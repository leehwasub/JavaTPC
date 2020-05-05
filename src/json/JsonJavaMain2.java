package json;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonJavaMain2 {
	public static void main(String[] args) {
		String src = "../info.json";
		// IO->Stream(스트림)
		InputStream is = JsonJavaMain2.class.getResourceAsStream(src);
		if(is == null) {
			throw new NullPointerException("Cannot find resource file");
		}
		JSONTokener tokener = new JSONTokener(is);
		JSONObject object = new JSONObject(tokener);
		JSONArray students = object.getJSONArray("students");
		for(int i = 0; i < students.length(); i++) {
			JSONObject student = (JSONObject)students.get(i);
			System.out.println(student);
		}
	}
}
