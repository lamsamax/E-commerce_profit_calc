import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File inputFile = new File("customer_orders.csv");
        try {
            Scanner s = new Scanner(inputFile);
            if(s.hasNextLine()){
                s.nextLine();
            }
            while (s.hasNextLine()){
                String currentLine  = s.nextLine();
                String[] lineParts = currentLine.split(",");

                try {
                    String fullName = lineParts[0];
                    String shirtSize = lineParts[1];
                    Boolean withDesign = Boolean.parseBoolean(lineParts[2]);
                    Boolean withHoodie = Boolean.parseBoolean(lineParts[3]);
                    String payment = lineParts[4];

                    Order.customerOrders.add(new Order(fullName,shirtSize, withDesign, withHoodie, payment));
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
            s.close();
            OrdersReport.runReports();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    }