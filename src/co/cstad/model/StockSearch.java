package co.cstad.model;

import co.cstad.controller.Product;
import co.cstad.view.StockDisplay;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class StockSearch {
    public static void search(List<Product> productList, int currentPage, int rowsPerPage) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Search product by keyword: ");
        String searchKeyword = scanner.nextLine().toLowerCase();

        List<Product> matchingProducts = new ArrayList<>();

        for (Product product : productList) {
            String productName = product.getProName().toLowerCase();

            if (productName.contains(searchKeyword)) {
                matchingProducts.add(product);
            }
        }

        int totalPages = (int) Math.ceil((double) matchingProducts.size() / rowsPerPage);
        if (matchingProducts.isEmpty()) {
            System.out.println("No products found containing the keyword '" + searchKeyword + "'.");
        } else {
            if (currentPage < 1) {
                currentPage = 1;
            } else if (currentPage > totalPages) {
                currentPage = totalPages;
            }

            Table tableDisplay = getTableDisplay(currentPage, rowsPerPage, matchingProducts);

            System.out.println(tableDisplay.render());
            Table pagination = new Table(25,BorderStyle.DESIGN_CURTAIN, ShownBorders.SURROUND);
            pagination.addCell(" Page: \t");
            pagination.addCell(String.valueOf(currentPage));
            pagination.addCell("  of ");
            pagination.addCell(String.valueOf(totalPages));
            pagination.addCell(" ".repeat(30));
            pagination.addCell("Total records : ");
            pagination.addCell(String.valueOf(matchingProducts.size()));
            System.out.println(pagination.render());

        }
    }
    private static Table getTableDisplay(int currentPage, int rowsPerPage, List<Product> matchingProducts) {
        int startIndex = (currentPage - 1) * rowsPerPage;
        int endIndex = Math.min(startIndex + rowsPerPage, matchingProducts.size());

        Table stockTable = new Table(5,BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE,ShownBorders.ALL);
        stockTable.setColumnWidth(0,10,20);
        stockTable.setColumnWidth(1,10,20);
        stockTable.setColumnWidth(2,10,20);
        stockTable.setColumnWidth(3,10,20);
        stockTable.setColumnWidth(4,10,20);
        stockTable.addCell("ID");
        stockTable.addCell("NAME");
        stockTable.addCell("Unit Price");
        stockTable.addCell("Qty");
        stockTable.addCell("Imported Date");
        int counter = 0;
        for (Product product : matchingProducts) {
            if (counter >= startIndex && counter < endIndex) {
                stockTable.addCell(String.valueOf(product.getProId()));
                stockTable.addCell(product.getProName());
                stockTable.addCell(String.valueOf(product.getProPrice()));
                stockTable.addCell(String.valueOf(product.getProQty()));
                stockTable.addCell(String.valueOf(product.getImportedPro()));
            }
            counter++;
        }
        return stockTable;
    }
}
