package co.cstad.controller;
import co.cstad.util.JTable;
import co.cstad.util.UtilTextTable;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
// view package
import static co.cstad.controller.StockWrite.write;
import static co.cstad.model.StockDelete.delete;
import static co.cstad.model.StockRead.read;
import static co.cstad.model.StockSearch.search;
import static co.cstad.model.StockSetRow.setRow;
import static co.cstad.model.StockUpdate.update;
import static co.cstad.view.StockDisplay.stockDisplay;
import static co.cstad.view.StockFirst.first;
import static co.cstad.view.StockGoto.goTo;
import static co.cstad.view.StockPrevious.previous;
import static co.cstad.view.StockLast.last;
import static co.cstad.view.StockNext.next;
import static co.cstad.view.StockHelp.displayHelp;


public class StockController {
    public static void main(String[] args) {
        int currentPage = 1;
        int rowsPerPage = 2;
        UtilTextTable ascii = new UtilTextTable();
        ascii.display();
        System.out.println("STOCK MANAGEMENT SYSTEM");
        boolean isTrue = true;
        LocalDate localDate =LocalDate.now();
        // Stock of Products
        Product product = new Product(1,"Coca",1.5,1,localDate);
        Product product2 = new Product(2,"Sting",1.5,1,localDate);
        Product product3 = new Product(3,"Anchor",1.5,1,localDate);
        Product product4 = new Product(4 ,"Soda",1.5,1,localDate);
        Product product5 = new Product(5,"Coca1",1.5,1,localDate);
        Product product6 = new Product(6,"Sting1",1.5,1,localDate);
        Product product7 = new Product(7,"Anchor1",1.5,1,localDate);
        Product product8 = new Product(8 ,"Soda1",1.5,1,localDate);
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        productList.add(product5);
        productList.add(product6);
        productList.add(product7);
        productList.add(product8);
        do{
            JTable jtable = new JTable();
            jtable.displayTable();
            // add Option Input
            Scanner scanner = new Scanner(System.in);
            System.out.print("Command ———> ");
            String option = scanner.nextLine();
            switch(option){
                case "*"-> {
                    stockDisplay(productList,currentPage,rowsPerPage);
                }
                case "w","W"->{
                    write(productList);
                }
                case "r","R"->{

                  read(productList);

                }
                case "u","U"->{
                   update(productList);
                }
                case "d","D"->{

                    delete(productList);
                }
                case "f","F"->{
                    currentPage = first(currentPage, rowsPerPage, productList);
                }
                case "p","P"->{
                    currentPage = previous(currentPage,rowsPerPage,productList);
                }
                case "n","N"->{
                    currentPage = next(currentPage,rowsPerPage,productList);
                }
                case "l","L"->{
                    currentPage = last(currentPage,rowsPerPage,productList);
                }
                case "s","S"->{

                   search(productList,currentPage,rowsPerPage);
                }
                case "g","G"->{

                    currentPage = goTo(currentPage,rowsPerPage,productList);

                }
                case "se","Se"->{

                    rowsPerPage = setRow(rowsPerPage,productList);
                }
                case "h","H"->{
                    displayHelp();
                }
                case "e","E"->{

                    System.out.print("You miss to save the record. Do you want to save it? [Y/y] or [N/n]: ");
                    String yesNo = scanner.nextLine();
                    if(yesNo.equals("y")){
                        continue;
                    }
                    isTrue=false;
                    if(yesNo.equals("n")){
                        System.out.println();
                       JTable tableExit = new JTable();
                       tableExit.displayExitTable();
                       System.exit(0);
                    }
                }
                default ->{
                    String [] shortcutOption = option.split("#");
                    String [] values;
                    switch(shortcutOption[0]){
                        case "w","W"->{
                            values=shortcutOption[1].split("-");
                            Product productAdd = new Product(product.getProId(), String.valueOf(values[0]),Double.parseDouble(values[1]),
                                    Integer.parseInt(values[2]),localDate);
                            Product productGetID = productList.get(productList.size()-1);
                            Integer proLastId = productGetID.getProId()+1;
                            Table table = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
                            table.addCell(" ID            : "+proLastId+" ".repeat(10));
                            table.addCell(" Name          : "+productAdd.getProName()+" ".repeat(10));
                            table.addCell(" Unit price    : "+productAdd.getProPrice()+" ".repeat(10));
                            table.addCell(" Qty           : "+productAdd.getProQty()+" ".repeat(10));
                            table.addCell(" Imported Date : "+ LocalDate.now()+" ".repeat(10));
                            System.out.println(table.render());
                            System.out.println("You have written "+proLastId+" : "+productAdd.getProName()+" : "+productAdd.getProPrice()+" : "+productAdd.getProQty()+" : "+localDate.now()+ " into Product Stocks.");
                            productList.add(productAdd);
                            System.out.println();
//
                        }
                        case "r","R"->{
                            boolean isFound=false;
                                for (Product product1 : productList) {
                                    if (product1.getProId().toString().equals(shortcutOption[1])) {
                                        Table table = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
                                        table.addCell(" ID            : "+product1.getProId()+" ".repeat(10));
                                        table.addCell(" Name          : "+product1.getProName()+" ".repeat(10));
                                        table.addCell(" Unit price    : "+product1.getProPrice()+" ".repeat(10));
                                        table.addCell(" Qty           : "+product1.getProQty()+" ".repeat(10));
                                        table.addCell(" Imported Date : "+ LocalDate.now()+" ".repeat(10));
                                        System.out.println(table.render());
                                        isFound = true;
                                        break;
                                    }
                                }
                                if (!isFound) {
                                    System.out.println("Product with ID : "+shortcutOption[0]+" is not found");
                                }
                            }
                            case "d","D"->{
                                boolean isFound=false;
                                for (Product product1 : productList) {
                                    if (product1.getProId().toString().equals(shortcutOption[1])) {
                                        Table table = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
                                        table.addCell(" ID            : "+product1.getProId()+" ".repeat(10));
                                        table.addCell(" Name          : "+product1.getProName()+" ".repeat(10));
                                        table.addCell(" Unit price    : "+product1.getProPrice()+" ".repeat(10));
                                        table.addCell(" Qty           : "+product1.getProQty()+" ".repeat(10));
                                        table.addCell(" Imported Date : "+ LocalDate.now()+" ".repeat(10));
                                        System.out.println(table.render());
                                        isFound = true;
                                        System.out.println("You have deleted product ID: "+product1.getProId()+" successfully");
                                        productList.remove(product1);
                                        break;
                                    }
                                }
                                if (!isFound) {
                                    System.out.println("Product with ID : "+shortcutOption[0]+" is not found");
                                }
                            }

                            }
                        }
                    }

        }while(isTrue);
    }


}
