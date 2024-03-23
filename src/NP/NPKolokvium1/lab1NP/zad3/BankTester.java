package NP.NPKolokvium1.lab1NP.zad3;

import java.util.*;
import java.util.stream.Collectors;

//interface ToDouble {
//    static double toDouble(String str) {
//        return Double.parseDouble(str.substring(0, str.length() - 1));
//    }
//}
class Account {
    private final String name;
    private final long id;
    private String balance;

    public Account(String name, String balance) {
        this.name = name;
        long randId = new Random().nextLong();
        this.id = randId;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" + "Balance: " + balance + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && Objects.equals(name, account.name) && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, balance);
    }
}

abstract class Transaction {
    private final long fromId;
    private final long toId;
    private final String description;
    private final String amount;

    public Transaction(long fromId, long toId, String description, String amount) {
        this.fromId = fromId;
        this.toId = toId;
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public long getFromId() {
        return fromId;
    }

    public long getToId() {
        return toId;
    }

    public String getAmount() {
        return amount;
    }

    public abstract double getProvision();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return fromId == that.fromId && toId == that.toId && Objects.equals(description, that.description) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromId, toId, description, amount);
    }
}

class FlatAmountProvisionTransaction extends Transaction //implements ToDouble
{
    private final String flatProvision;

    public FlatAmountProvisionTransaction(long fromId, long toId, String amount, String flatProvision) {
        super(fromId, toId, "FlatAmount", amount);
        this.flatProvision = flatProvision;
    }

    public String getFlatProvision() {
        return flatProvision;
    }

    @Override
    public double getProvision() {
        return Double.parseDouble(flatProvision.replaceAll("[$]", ""));
//        return ToDouble.toDouble(flatProvision);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlatAmountProvisionTransaction that = (FlatAmountProvisionTransaction) o;
        return Objects.equals(flatProvision, that.flatProvision);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flatProvision);
    }
}

class FlatPercentProvisionTransaction extends Transaction //implements ToDouble
{
    private final int centsPerDolar;

    public FlatPercentProvisionTransaction(long fromId, long toId, String amount, int centsPerDolar) {
        super(fromId, toId, "FlatPercent", amount);
        this.centsPerDolar = centsPerDolar;
    }

    public int getCentsPerDolar() {
        return centsPerDolar;
    }

    @Override
    public double getProvision() {
        return (int) Double.parseDouble(getAmount().replaceAll("[$]", "")) * (centsPerDolar / 100.0);
//        return (int)ToDouble.toDouble(getAmount())*(centsPerDolar/100.0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlatPercentProvisionTransaction that = (FlatPercentProvisionTransaction) o;
        return centsPerDolar == that.centsPerDolar;
    }

    @Override
    public int hashCode() {
        return Objects.hash(centsPerDolar);
    }
}

class Bank //implements ToDouble
{
    private final Account[] accounts;
    private final String bankName;
    private double totalTransfers;
    private double totalProvision;

    public Bank(String bankName, Account[] accounts) {
        this.accounts = Arrays.copyOf(accounts, accounts.length);
        this.bankName = bankName;
    }

    public boolean makeTransaction(Transaction t) {
        boolean flagFrom = false;
        boolean flagTo = false;
        long fromId = t.getFromId();
        long toId = t.getToId();
        double fromBalance = 0.0;
        double toBalance = 0.0;
        double ammount = Double.parseDouble(t.getAmount().replaceAll("[$]", ""));
//        double ammount=ToDouble.toDouble(t.getAmount());
        //check if the ids of both accounts are valid
        for (Account account : accounts) {
            if (fromId == account.getId()) {
                fromBalance = Double.parseDouble(account.getBalance().replaceAll("[$]", ""));
//                fromBalance=ToDouble.toDouble(account.getBalance());
                flagFrom = true;
            }
            if (toId == account.getId()) {
                toBalance = Double.parseDouble(account.getBalance().replaceAll("[$]", ""));
//                toBalance=ToDouble.toDouble(account.getBalance());
                flagTo = true;
            }
        }
        if (!(flagTo && flagFrom)) {
            return false;
        }
        if (ammount > fromBalance) {
            return false;
        }
        double provision = t.getProvision();
        for (Account account : accounts) {
            if (fromId == toId && fromId == account.getId() && toId == account.getId()) {
                fromBalance -= provision;
                account.setBalance(String.format("%.2f$", fromBalance));
            } else {
                if (fromId == account.getId()) {
                    fromBalance -= (ammount + provision);
                    account.setBalance(String.format("%.2f$", fromBalance));
                }
                if (toId == account.getId()) {
                    toBalance += ammount;
                    account.setBalance(String.format("%.2f$", toBalance));
                }
            }
        }
        totalProvision += t.getProvision();
        totalTransfers += ammount;
        return true;
    }

    public String totalTransfers() {
        return String.format("%.2f$", totalTransfers);
    }

    public String totalProvision() {
        return String.format("%.2f$", totalProvision);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("Name: ").append(bankName).append("\n").append("\n");
        for (Account account : accounts) {
            out.append(account.toString());
        }
        return out.toString();
    }

    public Account[] getAccounts() {
        return accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Double.compare(totalTransfers, bank.totalTransfers) == 0 && Double.compare(totalProvision, bank.totalProvision) == 0 && Arrays.equals(accounts, bank.accounts) && Objects.equals(bankName, bank.bankName);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(bankName, totalTransfers, totalProvision);
        result = 31 * result + Arrays.hashCode(accounts);
        return result;
    }
}

public class BankTester {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        String test_type = jin.nextLine();
        switch (test_type) {
            case "typical_usage":
                testTypicalUsage(jin);
                break;
            case "equals":
                testEquals();
                break;
        }
        jin.close();
    }

    private static void testEquals() {
        Account a1 = new Account("Andrej", "20.00$");
        Account a2 = new Account("Andrej", "20.00$");
        Account a3 = new Account("Andrej", "30.00$");
        Account a4 = new Account("Gajduk", "20.00$");
        List<Account> all = Arrays.asList(a1, a2, a3, a4);
        if (!(a1.equals(a1) && !a1.equals(a2) && !a2.equals(a1) && !a3.equals(a1) && !a4.equals(a1) && !a1.equals(null))) {
            System.out.println("Your account equals method does not work properly.");
            return;
        }
        Set<Long> ids = all.stream().map(Account::getId).collect(Collectors.toSet());
        if (ids.size() != all.size()) {
            System.out.println("Different accounts have the same IDS. This is not allowed");
            return;
        }
        FlatAmountProvisionTransaction fa1 = new FlatAmountProvisionTransaction(10, 20, "20.00$", "10.00$");
        FlatAmountProvisionTransaction fa2 = new FlatAmountProvisionTransaction(20, 20, "20.00$", "10.00$");
        FlatAmountProvisionTransaction fa3 = new FlatAmountProvisionTransaction(20, 10, "20.00$", "10.00$");
        FlatAmountProvisionTransaction fa4 = new FlatAmountProvisionTransaction(10, 20, "50.00$", "50.00$");
        FlatAmountProvisionTransaction fa5 = new FlatAmountProvisionTransaction(30, 40, "20.00$", "10.00$");
        FlatPercentProvisionTransaction fp1 = new FlatPercentProvisionTransaction(10, 20, "20.00$", 10);
        FlatPercentProvisionTransaction fp2 = new FlatPercentProvisionTransaction(10, 20, "20.00$", 10);
        FlatPercentProvisionTransaction fp3 = new FlatPercentProvisionTransaction(10, 10, "20.00$", 10);
        FlatPercentProvisionTransaction fp4 = new FlatPercentProvisionTransaction(10, 20, "50.00$", 10);
        FlatPercentProvisionTransaction fp5 = new FlatPercentProvisionTransaction(10, 20, "20.00$", 30);
        FlatPercentProvisionTransaction fp6 = new FlatPercentProvisionTransaction(30, 40, "20.00$", 10);
        if (fa1.equals(fa1) && !fa2.equals(null) && fa2.equals(fa1) && fa1.equals(fa2) && fa1.equals(fa3) && !fa1.equals(fa4) && !fa1.equals(fa5) && !fa1.equals(fp1) && fp1.equals(fp1) && !fp2.equals(null) && fp2.equals(fp1) && fp1.equals(fp2) && fp1.equals(fp3) && !fp1.equals(fp4) && !fp1.equals(fp5) && !fp1.equals(fp6)) {
            System.out.println("Your transactions equals methods do not work properly.");
            return;
        }
        Account[] accounts = new Account[]{a1, a2, a3, a4};
        Account[] accounts1 = new Account[]{a2, a1, a3, a4};
        Account[] accounts2 = new Account[]{a1, a2, a3};
        Account[] accounts3 = new Account[]{a1, a2, a3, a4};

        Bank b1 = new Bank("Test", accounts);
        Bank b2 = new Bank("Test", accounts1);
        Bank b3 = new Bank("Test", accounts2);
        Bank b4 = new Bank("Sample", accounts);
        Bank b5 = new Bank("Test", accounts3);

        if (!(b1.equals(b1) && !b1.equals(null) && !b1.equals(b2) && !b2.equals(b1) && !b1.equals(b3) && !b3.equals(b1) && !b1.equals(b4) && b1.equals(b5))) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        //accounts[2] = a1;
        if (!b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        long from_id = a2.getId();
        long to_id = a3.getId();
        Transaction t = new FlatAmountProvisionTransaction(from_id, to_id, "3.00$", "3.00$");
        b1.makeTransaction(t);
        if (b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        b5.makeTransaction(t);
        if (!b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        System.out.println("All your equals methods work properly.");
    }

    private static void testTypicalUsage(Scanner jin) {
        String bank_name = jin.nextLine();
        int num_accounts = jin.nextInt();
        jin.nextLine();
        Account[] accounts = new Account[num_accounts];
        for (int i = 0; i < num_accounts; ++i)
            accounts[i] = new Account(jin.nextLine(), jin.nextLine());
        Bank bank = new Bank(bank_name, accounts);
        while (true) {
            String line = jin.nextLine();
            switch (line) {
                case "stop":
                    return;
                case "transaction":
                    String descrption = jin.nextLine();
                    String amount = jin.nextLine();
                    String parameter = jin.nextLine();
                    int from_idx = jin.nextInt();
                    int to_idx = jin.nextInt();
                    jin.nextLine();
                    Transaction t = getTransaction(descrption, from_idx, to_idx, amount, parameter, bank);
                    System.out.println("Transaction amount: " + t.getAmount());
                    System.out.println("Transaction description: " + t.getDescription());
                    System.out.println("Transaction successful? " + bank.makeTransaction(t));
                    break;
                case "print":
                    System.out.println(bank);
                    System.out.println("Total provisions: " + bank.totalProvision());
                    System.out.println("Total transfers: " + bank.totalTransfers());
                    System.out.println();
                    break;
            }
        }
    }

    private static Transaction getTransaction(String description, int from_idx, int to_idx, String amount, String o, Bank bank) {
        switch (description) {
            case "FlatAmount":
                return new FlatAmountProvisionTransaction(bank.getAccounts()[from_idx].getId(), bank.getAccounts()[to_idx].getId(), amount, o);
            case "FlatPercent":
                return new FlatPercentProvisionTransaction(bank.getAccounts()[from_idx].getId(), bank.getAccounts()[to_idx].getId(), amount, Integer.parseInt(o));
        }
        return null;
    }


}