import java.util.HashMap;
import java.util.Map;

public class ProductTotalPrice {
	private static Map<String,Double> totalPrices= new HashMap<>();;
	private static Map<String,Integer> totalCounts= new HashMap<>();
	private static Double totalPriceInCart = 0.0;

	public static Double getTotalPriceInCart() {
		return totalPriceInCart;
	}

	public static void setTotalPriceInCart(Double totalPriceInCart) {
		ProductTotalPrice.totalPriceInCart = totalPriceInCart;
	}

	public static void addCountAndPrice(String name, Double price){
		totalCounts.put(name,totalCounts.getOrDefault(name,0) + 1);
		totalPrices.put(name,totalPrices.getOrDefault(name,0.0) + price);
	}

	public static Map<String, Double> getTotalPrices() {
		return totalPrices;
	}

	public static Map<String, Integer> getTotalCounts() {
		return totalCounts;
	}
	
	public static double getTotalPriceOfAll(){
		double sum = 0.0;
		for (double d:totalPrices.values()) {
			sum += d;
		}
		return sum;
	}
}
