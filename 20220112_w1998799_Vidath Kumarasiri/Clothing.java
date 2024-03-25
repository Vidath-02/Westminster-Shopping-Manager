class Clothing extends Product {
    private String size;

    public Clothing(String productId, String productName, String category, double price, int numberOfItems, String size, String info) {
        super(productId, productName, category, price, numberOfItems, info);
        this.size = size;
    }
   
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public void displayProductInfo() {
        System.out.println("Product ID: " + getProductId());
        System.out.println("Product Name: " + getProductName());
        System.out.println("Category: " + getProductCategory());
        System.out.println("No:of Items: " + getNumberOfItems());
        System.out.println("Price: " + getProductPrice());
        System.out.println("Size: " + getSize());
        System.out.println("Info: " + getProductInfo());
    }
}
