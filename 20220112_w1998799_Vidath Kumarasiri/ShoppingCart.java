import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> productList;

    public ShoppingCart() {
        this.productList = new ArrayList<>();
    }

    public void addProductToCart(Product product) {
        productList.add(product);
    }

    public void removeProductFromCart(Product product) {
        productList.remove(product);
    }

    public double calculateTotalCost() {
        double totalCost = 0.0;
        for (Product product : productList) {
            totalCost += product.getProductPrice();
        }
        return totalCost;
    }
}
