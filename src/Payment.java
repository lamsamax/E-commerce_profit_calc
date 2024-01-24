public interface Payment {
    double calculateBankFee(double amount);
}

class WalletPayment implements Payment{
    @Override
    public double calculateBankFee(double amount) {return 0.0;}
}

class BankcardPayment implements Payment{
    @Override
    public double calculateBankFee(double amount) {return (amount*0.05);}
}

class VisaPayment implements Payment{
    @Override
    public double calculateBankFee(double amount) {return (amount*0.02);}
}

class MastercardPayment implements Payment{
    @Override
    public double calculateBankFee(double amount) {return (amount*0.03);}
}

class OtherPayment implements Payment{
    @Override
    public double calculateBankFee(double amount) {return (amount*0.1);}
}

class SpecificPayment{
    private final Payment payment;

    public SpecificPayment(Payment payment) {
        this.payment = payment;
    }

    public double getPaymentFee(double amount) {
        return payment.calculateBankFee(amount);
    }
}