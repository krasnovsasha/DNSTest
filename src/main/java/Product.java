public class Product {
	private String name;
	private double price;
	private boolean warranty;

	public Product(String name) {
		this.name = name;
		this.warranty = false;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setWarranty(boolean warranty) {
		this.warranty = warranty;
	}
}
