
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        UserGUI shoppingGUI = new UserGUI(manager);
        shoppingGUI.setVisible(false);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();

            int choice = 0;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer choice.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("\n=== Adding a New Product ===");
                    addNewProduct(scanner, manager);
                    break;

                case 2:
                    System.out.println("\n=== Deleting a Product ===");
                    System.out.print("\nEnter product ID to delete: ");
                    String productId = scanner.nextLine();
                    manager.deleteProduct(productId);
                    break;

                case 3:
                    System.out.println("\n=== Product List ===");
                    manager.printProductList();
                    break;

                case 4:
                    System.out.println("\n=== Saving Products to File ===");
                    manager.saveToFile();
                    break;

                case 5:
                    System.out.println("\n=== Reading Products from File ===");
                    manager.readFromFile();
                    break;

                case 6:
                    System.out.println("\nExiting...");
                    scanner.close();
                    System.exit(0);
                    break;

                case 7:
                    System.out.println("\n=== User Interface ===");
                    manager.getProductList();

                    SwingUtilities.invokeLater(() -> {
                        new UserGUI(manager).setVisible(true);
                    });
                    break;

                default:
                    System.out.println("\nInvalid choice. Please enter a valid option.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=====/// Menu /// =====");
        System.out.println("1. Add a new product");
        System.out.println("2. Delete a product");
        System.out.println("3. Print list of products");
        System.out.println("4. Save all products to file");
        System.out.println("5. Read all products from file");
        System.out.println("6. Exit the program");
        System.out.println("7. Open user interface");
        System.out.print("Enter your choice: ");
    }
    private static void addNewProduct(Scanner scanner, WestminsterShoppingManager manager) {
        System.out.print("Enter product ID: ");
        String newProductId = scanner.nextLine();

        System.out.print("Enter product name: ");
        String newProductName = scanner.nextLine();

        double newPrice = 0.0;
        int newNumberOfItems = 0;

        try {
            System.out.print("Enter price: ");
            newPrice = scanner.nextDouble();
            scanner.nextLine();  // Consume the newline character

            System.out.print("Enter number of items: ");
            newNumberOfItems = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for price or number of items. Please enter valid numeric values.");
            scanner.nextLine();  // Consume the invalid input
            return;  // Return without adding the product
        }

        System.out.println("1. Electronics");
        System.out.println("2. Clothing");
        System.out.print("Enter product type: ");
        int productType = 0;

        try {
            productType = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for product type. Please enter a valid numeric value.");
            scanner.nextLine();  // Consume the invalid input
            return;  // Return without adding the product
        }

        if (productType == 1) {
            System.out.print("Enter warranty period (in months): ");
            String newInfo = scanner.nextLine();

            manager.addProduct(new Electronics(newProductId, newProductName, "Electronics", newPrice,
                    newNumberOfItems, newInfo));
        } else if (productType == 2) {
            System.out.print("Enter size: ");
            String size = scanner.nextLine();

            System.out.print("Enter color: ");
            String newInfo = scanner.nextLine();

            manager.addProduct(new Clothing(newProductId, newProductName, "Clothing", newPrice,
                    newNumberOfItems, size, newInfo));
        } else {
            System.out.println("Invalid product type.");
        }
    }

}

