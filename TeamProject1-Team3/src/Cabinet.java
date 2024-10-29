
public class Cabinet {
	private Customer customer;
	private String item;
	
	public Cabinet(Customer customer, String item) {
		super();
		this.customer = customer;
		this.item = item;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}	
}
