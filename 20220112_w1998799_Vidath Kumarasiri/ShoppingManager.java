import java.util.List;

interface ShoppingManager {
    List<Product> getProductList();
    void addProduct(Product product);
    void deleteProduct(String productId);
    void printProductList();
    void saveToFile();
    void readFromFile();
    void addToCart(Product product);
}