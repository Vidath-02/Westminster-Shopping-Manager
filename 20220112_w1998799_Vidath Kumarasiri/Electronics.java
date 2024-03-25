class Electronics extends Product {
    public Electronics(String productId, String productName, String category, double price, int numberOfItems, String info) {
        super(productId, productName, category, price, numberOfItems, info);
    }

    @Override
    public void displayProductInfo() {
        System.out.println("Product ID: " + getProductId());
        System.out.println("Product Name: " + getProductName());
        System.out.println("Category: " + getProductCategory());
        System.out.println("No:of Items: " + getNumberOfItems());
        System.out.println("Price: " + getProductPrice());
        System.out.println("Warranty Period: " + getProductInfo() + " months");
    }
}
