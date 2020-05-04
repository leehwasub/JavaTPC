package gson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Main {
	public static void main(String[] args) {
		//Object(BookDTO) -> JSON(String)
		BookDTO bookDTO = new BookDTO("자바", 21000, "에어콘", 670);
		//Gson
		Gson g = new Gson();
		String json = g.toJson(bookDTO);
		System.out.println(json);
		
		BookDTO bookDTO2 = g.fromJson(json, BookDTO.class);
		System.out.println(bookDTO2);
		
		//Object(List<BookDTO>) -> JSON(String) : [{  },{  }.....]
		List<BookDTO> list = new ArrayList<BookDTO>();
		list.add(new BookDTO("자바1", 21000, "에어콘1", 570));
		list.add(new BookDTO("자바2", 31000, "에어콘2", 670));
		list.add(new BookDTO("자바3", 11000, "에어콘3", 370));
		
		String listJson = g.toJson(list);
		System.out.println(listJson);
		
		//Json(String) -> Object(List<BookDTO>)
		List<BookDTO> list2 = g.fromJson(listJson, new TypeToken<List<BookDTO>>(){}.getType());
		for(BookDTO book : list2) {
			System.out.println(book);
		}
	}
}
