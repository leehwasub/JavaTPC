package json;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonJavaMain {
	public static void main(String[] args) {
		//Json-Java(org.json)
		JSONArray students = new JSONArray();
		
		JSONObject student = new JSONObject();
		student.put("name", "홍길동");
		student.put("phone", "010-1111-1111");
		student.put("address", "서울");
		students.put(student);
		
		System.out.println(student); // {"address":"서울","phone":"010-1111-1111","name":"홍길동"}
		
		student = new JSONObject();
		student.put("name", "나길동");
		student.put("phone", "010-2222-2222");
		student.put("address", "광주");
		students.put(student);
		
		JSONObject object = new JSONObject();
		
		object.put("students", students);
		
		System.out.println(object.toString(3));
		
		
		//
	}
}
