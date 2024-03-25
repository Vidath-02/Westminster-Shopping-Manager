import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.Component;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class UserGUI extends JFrame {
    private WestminsterShoppingManager manager;
    private JTable productTable;
    private JComboBox<String> categoryDropdown;
    private JTextArea productDetailsArea;
    private Map<Product, Integer> cartItems;

    public UserGUI(WestminsterShoppingManager manager) {
        this.manager = manager;
        this.manager = manager;
        cartItems = new HashMap<>();

        setTitle("Westminster Shopping Center");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 650);
        setLocationRelativeTo(null);

        Components();
        showProductList();
    }

    private void Components() {
        JPanel panel = new JPanel(null); // Use null layout for absolute positioning
        JLabel categoryLabel = new JLabel("Select Product Category:");
        categoryLabel.setBounds(150, 15, 200, 30);

        categoryDropdown = new JComboBox<>(new String[] { "All", "Electronics", "Clothing" });
        JButton shoppingCartButton = new JButton("Shopping Cart");

        categoryDropdown.setBounds(300, 20, 150, 30);
        shoppingCartButton.setBounds(630, 20, 150, 30);

        panel.add(categoryLabel);
        panel.add(categoryDropdown);
        panel.add(shoppingCartButton);

        productTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBounds(20, 120, 760, 200);

        panel.add(scrollPane);

        // Create the selected product details
        productDetailsArea = new JTextArea();
        JScrollPane detailsScrollPane = new JScrollPane(productDetailsArea);
        detailsScrollPane.setBounds(20, 350, 760, 200);

        panel.add(detailsScrollPane);

        // Create the button for sorting
        JButton sortButton = new JButton("Sort");
        sortButton.setBounds(630, 80, 80, 20);
        panel.add(sortButton);

        setContentPane(panel);

        // Create the button for adding to cart
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setBounds(330, 570, 140, 30);
        panel.add(addToCartButton);

        // Add ActionListener to the categoryDropdown
        categoryDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showProductList();
            }
        });

        // Add MouseListener to the productTable to display details on click
        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rowIndex = productTable.getSelectedRow();
                displayProductDetails(rowIndex);
            }
        });

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart();
            }
        });
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortProductListAlphabetically();
            }
        });
        shoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showShoppingCart();
            }
        });

    }

    private void showProductList() {
        List<Product> productList = manager.getProductList();
        String selectedCategory = categoryDropdown.getSelectedItem().toString();

        List<Product> filteredProducts;
        if (selectedCategory.equals("All")) {
            filteredProducts = productList;
        } else {
            filteredProducts = productList.stream()
                    .filter(product -> product.getProductCategory().equalsIgnoreCase(selectedCategory))
                    .collect(Collectors.toList());
        }

        String[] columnNames = { "Product ID", "Name", "Category", "Price", "No. of Items", "Information" };
        Object[][] data = new Object[50][6]; // Fixed 50 rows

        for (int i = 0; i < 50; i++) {
            if (i < filteredProducts.size()) {
                Product product = filteredProducts.get(i);
                data[i][0] = product.getProductId();
                data[i][1] = product.getProductName();
                data[i][2] = product.getProductCategory();
                data[i][3] = product.getProductPrice();
                data[i][4] = product.getNumberOfItems();
                data[i][5] = product.getProductInfo();
            } else {
                for (int j = 0; j < 6; j++) {
                    data[i][j] = "";
                }
            }
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 4 ? Integer.class : Object.class;
            }
        };

        productTable.setModel(model);

        productTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected,
                        hasFocus, row, column);

                if (column == 4 && value instanceof Integer) {
                    int numberOfItems = (int) value;
                    if (numberOfItems < 3) {
                        cellComponent.setForeground(Color.RED);
                    } else {
                        cellComponent.setForeground(table.getForeground());
                    }
                } else {
                    cellComponent.setForeground(table.getForeground());
                }
                return cellComponent;
            }
        });



        int[] columnWidths = { 100, 150, 100, 80, 120, 200 };
        for (int i = 0; i < columnWidths.length; i++) {
            TableColumn column = productTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }
    }

    private void displayProductDetails(int rowIndex) {
        List<Product> productList = manager.getProductList();
        if (rowIndex >= 0 && rowIndex < productList.size()) {
            Product selectedProduct = productList.get(rowIndex);
            String selectedProductHeading = "Selected Product";

            if (selectedProduct instanceof Electronics) {
                selectedProductHeading += " - Electronics";
                Electronics electronicProduct = (Electronics) selectedProduct;
                productDetailsArea.setText(
                        selectedProductHeading + "\n" +
                                "Product ID: " + electronicProduct.getProductId() + "\n" +
                                "Product Name: " + electronicProduct.getProductName() + "\n" +
                                "Category: " + electronicProduct.getProductCategory() + "\n" +
                                "No of Items: " + electronicProduct.getNumberOfItems() + "\n" +
                                "Price: " + electronicProduct.getProductPrice() + "\n" +
                                "Warranty Period: " + electronicProduct.getProductInfo() + " months");
            } else if (selectedProduct instanceof Clothing) {
                selectedProductHeading += " - Clothing";
                Clothing clothingProduct = (Clothing) selectedProduct;
                productDetailsArea.setText(
                        selectedProductHeading + "\n" +
                                "Product ID: " + clothingProduct.getProductId() + "\n" +
                                "Product Name: " + clothingProduct.getProductName() + "\n" +
                                "Category: " + clothingProduct.getProductCategory() + "\n" +
                                "No of Items: " + clothingProduct.getNumberOfItems() + "\n" +
                                "Price: " + clothingProduct.getProductPrice() + "\n" +
                                "Size: " + clothingProduct.getSize() + "\n" +
                                "Info: " + clothingProduct.getProductInfo());
            } else {
                // Handle other product types here
                productDetailsArea.setText("Unknown product type");
            }
        }
    }

    private void sortProductListAlphabetically() {
        List<Product> productList = manager.getProductList();
        productList.sort(Comparator.comparing(Product::getProductName, String.CASE_INSENSITIVE_ORDER));

        showProductList();
    }

    private void addToCart() {
        List<Product> productList = manager.getProductList();
        int rowIndex = productTable.getSelectedRow();

        if (rowIndex >= 0 && rowIndex < productList.size()) {
            Product selectedProduct = productList.get(rowIndex);
            if (selectedProduct.getNumberOfItems() > 0) {

                selectedProduct.setNumberOfItems(selectedProduct.getNumberOfItems() - 1);
                if (cartItems.containsKey(selectedProduct)) {
                    cartItems.put(selectedProduct, cartItems.get(selectedProduct) + 1);
                } else {
                    cartItems.put(selectedProduct, 1);
                }

                showProductList();

                JOptionPane.showMessageDialog(this, "added to cart!");
            } else {
                JOptionPane.showMessageDialog(this, " out of stock.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product");
        }
    }

    private void showShoppingCart() {
        JFrame cartFrame = new JFrame("Shopping Cart");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        // Create a panel for the table
        JPanel cartPanel = new JPanel();
        cartPanel.setBounds(20, 20, 550, 150);
        String[] cartColumnNames = { "Product ", "Number of Items Added", "Total Price" };
        DefaultTableModel cartTableModel = new DefaultTableModel(cartColumnNames, 0);
        JTable cartTable = new JTable(cartTableModel);

        // Populate cart table with cartItems data
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            if (quantity > 0) {
                double totalPrice = quantity * product.getProductPrice();
                Object[] rowData = { product.getProductName(), quantity, totalPrice };
                cartTableModel.addRow(rowData);
            }
        }

        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        cartScrollPane.setBounds(0, 0, 550, 200);
        cartPanel.add(cartScrollPane);

        // Create a panel for the text information
        JPanel textPanel = new JPanel();
        textPanel.setBounds(20, 240, 550, 100);

        double totalPrice = calculateTotalPrice();
        JLabel totalPriceLabel = new JLabel("Total Price: $" + totalPrice);
        totalPriceLabel.setBounds(10, 10, 300, 20);

        double firstPurchaseDiscount = calculateFirstPurchaseDiscount(totalPrice);
        JLabel firstPurchaseDiscountLabel = new JLabel("First Purchase Discount (10%): $" + firstPurchaseDiscount);
        firstPurchaseDiscountLabel.setBounds(10, 30, 300, 20);

        double threeItemsDiscount = calculateThreeItemsDiscount(totalPrice);
        JLabel threeItemsDiscountLabel = new JLabel(
                "Discount for 3 Items in Same Category (20%): $" + threeItemsDiscount);
        threeItemsDiscountLabel.setBounds(10, 50, 300, 20);

        double finalPrice = totalPrice - firstPurchaseDiscount - threeItemsDiscount;
        JLabel finalPriceLabel = new JLabel("Final Price : $" + finalPrice);
        finalPriceLabel.setBounds(10, 70, 300, 20);

        textPanel.add(totalPriceLabel);
        textPanel.add(firstPurchaseDiscountLabel);
        textPanel.add(threeItemsDiscountLabel);
        textPanel.add(finalPriceLabel);

        // Add the panels to the mainPanel
        mainPanel.add(cartPanel);
        mainPanel.add(textPanel);

        cartFrame.getContentPane().add(mainPanel);
        cartFrame.setSize(600, 400);
        cartFrame.setVisible(true);
    }

    private double calculateFirstPurchaseDiscount(double totalPrice) {

        boolean isFirstPurchase = cartItems.size() > 0;
        return isFirstPurchase ? totalPrice * 0.1 : 0;
    }

    private double calculateThreeItemsDiscount(double totalPrice) {

        Map<String, Integer> categoryCount = new HashMap<>();
        for (Product product : cartItems.keySet()) {
            String category = product.getProductCategory();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + cartItems.get(product));
        }

        for (int count : categoryCount.values()) {
            if (count >= 3) {
                return totalPrice * 0.2;
            }
        }
        return 0;
    }

    private double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            totalPrice += product.getProductPrice() * quantity;
        }
        return totalPrice;
    }

}
