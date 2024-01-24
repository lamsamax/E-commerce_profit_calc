import java.util.ArrayList;

public class Order {
    /*full_name,shirt_size,with_design,with_hoodie,payment
    full_name - name of the customer represents customer name
    shirt_size - string value representing size of the shirt
    with_design - boolean representing if customer wants design to be applied on the shirt front
    with_hoodie - boolean representing if customer wants hoodie to be attached to the shirt
    payment - credit card used for processing payment*/
    private final String full_name;
    private final String shirt_size;
    private final Boolean with_design;
    private final Boolean with_hoodie;
    private final String payment;

    public static final ArrayList<Order> customerOrders = new ArrayList<>();

    public Order(String full_name, String shirt_size, Boolean with_design, Boolean with_hoodie, String payment) {
        this.full_name = full_name;
        this.shirt_size = shirt_size;
        this.with_design = with_design;
        this.with_hoodie = with_hoodie;
        this.payment = payment;
    }

    public Boolean hasDesign() {
        return with_design;
    }

    public Boolean hasHoodie() {
        return with_hoodie;
    }

    public String getPayment() {
        return payment;
    }

    public String getShirtSize() {
        return shirt_size;
    }

    @Override
    public String toString() {
        return "Order{" +
                "fullName='" + full_name + '\'' +
                ", shirtSize='" + shirt_size + '\'' +
                ", withDesign=" + with_design +
                ", withHoodie=" + with_hoodie +
                ", payment='" + payment + '\'' +
                '}';
    }
}