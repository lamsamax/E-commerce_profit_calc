import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrdersReport extends Thread{
    private final String reportName;
    public OrdersReport(String reportName) {this.reportName = reportName;}
    public static double calculateRevenue(ArrayList<Order> orders){
        return orders.size() * 40.0;
    }
    public static double calculateProfit(ArrayList<Order> orders){
        double profit = 0.0;
        for (Order order: orders) {
            profit+=calculateOrderProfit(order);
        }
        return profit;
    }
    public static Map<String, Double> calculateProfitPerShirtSize(ArrayList<Order> orders) {
        Map<String, Double> profitPerSize = new HashMap<>();

        for (Order order : orders) {
            String shirtSize = order.getShirtSize();
            double orderProfit = calculateOrderProfit(order);

            profitPerSize.put(shirtSize, profitPerSize.getOrDefault(shirtSize, 0.0) + orderProfit);
        }
        return profitPerSize;
    }

    private static double calculateOrderProfit(Order order) {
        double supplyCost = 14.0;
        if (order.hasDesign()) supplyCost += 2;
        if (order.hasHoodie()) supplyCost += 3;

        Payment paymentType = getPaymentType(order.getPayment());
        SpecificPayment payment = new SpecificPayment(paymentType);
        double bankFees = payment.getPaymentFee(40.0);

        return 40.0 - (supplyCost + bankFees);
    }

    private static Payment getPaymentType(String payment) {
        Payment paymentType = switch (payment) {
            case "wallet" -> new WalletPayment();
            case "bankcard" -> new BankcardPayment();
            case "visa" -> new VisaPayment();
            case "mastercard" -> new MastercardPayment();
            case null, default -> new OtherPayment();
        };
        return paymentType;
    }

    @Override
    public void run(){
        switch (reportName) {
            case "RevenueReport.txt" -> createRevenueReport(reportName, Math.round(calculateRevenue(Order.customerOrders)));
            case "ProfitReport.txt" -> createProfitReport(reportName, Math.round(calculateProfit(Order.customerOrders)));
            case "ProfitPerShirtSizeReport.txt" ->
                    createProfitPerShirtSizeReport(reportName, calculateProfitPerShirtSize(Order.customerOrders));
            case null, default -> System.out.println("Invalid report name: " + reportName);
        }
    }

    public static void runReports() throws InterruptedException{
        OrdersReport revenueThread = new OrdersReport("RevenueReport.txt");
        OrdersReport profitThread = new OrdersReport("ProfitReport.txt");
        OrdersReport profitPerSizeThread = new OrdersReport("ProfitPerShirtSizeReport.txt");

        revenueThread.start();
        profitThread.start();
        profitPerSizeThread.start();

        revenueThread.join();
        profitThread.join();
        profitPerSizeThread.join();
    }
    private void createProfitPerShirtSizeReport(String name, Map<String, Double> sizeMap) {
        StringBuilder sb = new StringBuilder();
        sb.append("Profit per Shirt Size:").append("\n");
        String[] shirtSizesInOrder = {"XS", "S", "M", "L", "XL", "2XL", "3XL"};
        for (String size: shirtSizesInOrder) {
            sb.append(size).append(": ").append(sizeMap.getOrDefault(size, 0.0)).append("\n");
        }
        createReport(name, sb.toString());
    }

    private void createProfitReport(String name, double profit) {
        createReport(name,"Total profit for orders in eComm Web application: " + profit);
    }

    private void createRevenueReport(String name, double revenue) {
        createReport(name,"Total revenue for orders in eComm Web application: " + revenue);
    }

    private void createReport(String name, String content) {
        try {
            FileWriter fw = new FileWriter(name);
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
