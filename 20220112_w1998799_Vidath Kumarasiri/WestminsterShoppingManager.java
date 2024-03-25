import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class WestminsterShoppingManager implements ShoppingManager {
    private List<Product> productList;
    private List<Product> cart;

    public WestminsterShoppingManager() {
        this.productList = new ArrayList<>();
        this.cart = new ArrayList<>();
    }

    // Getter for product list
    @Override
    public List<Product> getProductList() {
        return productList;
    }

    // Add a new product to the list
    @Override
    public void addProduct(Product product) {
        if (productList.size() < 50) {
            productList.add(product);
        } else {
            System.out.println("Maximum product limit reached.");
        }
    }

    // Delete a product from the list based on product ID
    @Override
    public void deleteProduct(String productId) {
        for (Product product : productList) {
            if (product.getProductId().equals(productId)) {
                productList.remove(product);
                System.out.println("Product with ID " + productId + " deleted.");
                break;
            }
        }
    }

    // Print the product list
    @Override
    public void printProductList() {
        if (productList.isEmpty()) {
            System.out.println("Product list is empty.");
            return;
        }

        System.out.println("Product List:");
        for (Product product : productList) {
            product.displayProductInfo();
            System.out.println("--------------------");
        }
    }

    // Save products to a file
    @Override
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("products.txt"))) {
            for (Product product : productList) {
                saveProductToFile(writer, product);
            }
            System.out.println("Product list saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    // Read products from a file
    @Override
    public void readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                readProductFromFile(reader, line);
            }
            System.out.println("Products read from file successfully.");
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    // Add a product to the cart
    @Override
    public void addToCart(Product product) {
        cart.add(product);
        System.out.println("Product added to cart: " + product.getProductName());
    }

    // Helper method to save a product to a file
    private void saveProductToFile(PrintWriter writer, Product product) {
        if (product instanceof Electronics) {
            Electronics electronics = (Electronics) product;
            writer.println("Type: Electronics");
            writer.println("Product ID: " + electronics.getProductId());
            writer.println("Product Name: " + electronics.getProductName());
            writer.println("No:of Items: " + electronics.getNumberOfItems());
            writer.println("Price: " + electronics.getProductPrice());
            writer.println("Warranty Period: " + electronics.getProductInfo());
            writer.println();
        } else if (product instanceof Clothing) {
            Clothing clothing = (Clothing) product;
            writer.println("Type: Clothing");
            writer.println("Product ID: " + clothing.getProductId());
            writer.println("Product Name: " + clothing.getProductName());
            writer.println("No:of Items: " + clothing.getNumberOfItems());
            writer.println("Price: " + clothing.getProductPrice());
            writer.println("Size: " + clothing.getSize());
            writer.println("Color: " + clothing.getProductInfo());
            writer.println();
        }
    }

    // Helper method to read a product from a file
    private void readProductFromFile(BufferedReader reader, String line) throws IOException {
        if (line.equals("Type: Electronics")) {
            readElectronicsFromFile(reader);
        } else if (line.equals("Type: Clothing")) {
            readClothingFromFile(reader);
        }
        // Read the empty line between product entries
        reader.readLine();
    }

    // Helper method to read Electronics details from a file
    private void readElectronicsFromFile(BufferedReader reader) throws IOException {
        String productId = reader.readLine().substring("Product ID: ".length());
        String productName = reader.readLine().substring("Product Name: ".length());
        int availableItems = Integer.parseInt(reader.readLine().substring("No:of Items: ".length()));
        double price = Double.parseDouble(reader.readLine().substring("Price: ".length()));
        String warrantyPeriod = reader.readLine().substring("Warranty Period: ".length());

        productList.add(new Electronics(productId, productName, "Electronics", price, availableItems, warrantyPeriod));
    }

    // Helper method to read Clothing details from a file
    private void readClothingFromFile(BufferedReader reader) throws IOException {
        String productId = reader.readLine().substring("Product ID: ".length());
        String productName = reader.readLine().substring("Product Name: ".length());
        int availableItems = Integer.parseInt(reader.readLine().substring("No:of Items: ".length()));
        double price = Double.parseDouble(reader.readLine().substring("Price: ".length()));
        String size = reader.readLine().substring("Size: ".length());
        String color = reader.readLine().substring("Color: ".length());

        productList.add(new Clothing(productId, productName, "Clothing", price, availableItems, size, color));
    }
}
