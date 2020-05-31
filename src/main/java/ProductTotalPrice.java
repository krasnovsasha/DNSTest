import java.util.HashMap;
import java.util.Map;

public class ProductTotalPrice {
	private static Map<String,Double> totalPrices= new HashMap<>();;
	private static Map<String,Integer> totalCounts= new HashMap<>();

	public static void addCountAndPrice(String name,Double price){
		totalCounts.put(name,totalCounts.getOrDefault(name,0) + 1);
		totalPrices.put(name,totalPrices.getOrDefault(name,0.0) + price);
	}

	public static Map<String, Double> getTotalPrices() {
		return totalPrices;
	}

	public static Map<String, Integer> getTotalCounts() {
		return totalCounts;
	}
}
