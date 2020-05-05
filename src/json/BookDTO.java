package json;

public class BookDTO {
	
	private String title;
	private int price;
	private String company;
	private int page;
	
	public BookDTO() {
		
	}

	public BookDTO(String title, int price, String company, int page) {
		this.title = title;
		this.price = price;
		this.company = company;
		this.page = page;
	}

	@Override
	public String toString() {
		return "BookDTO [title=" + title + ", price=" + price + ", company=" + company + ", page=" + page + "]";
	}
	
}
