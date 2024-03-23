package NP.NPKolokvium1.lab3NP.zad1;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

interface Item {
    int getPrice();

    String getType();
}

class InvalidExtraTypeException extends Exception {
}

class InvalidPizzaTypeException extends Exception {
}

class ItemOutOfStockException extends Exception {
    ItemOutOfStockException(Item item) {
        super(String.format("%s out of stock!", item));
    }
}

class EmptyOrder extends Exception {
}

class OrderLockedException extends Exception {
}

class ExtraItem implements Item {
    private final String type;

    public ExtraItem(String type) throws InvalidExtraTypeException {
        if (!Objects.equals(type, "Coke") && !Objects.equals(type, "Ketchup")) {
            throw new InvalidExtraTypeException();
        }
        this.type = type;
    }

    @Override
    public int getPrice() {
        int price = 0;
        if (type.equals("Coke")) {
            price += 5;
        }
        if (type.equals("Ketchup")) {
            price += 3;
        }
        return price;
    }

    @Override
    public String getType() {
        return type;
    }

}

class PizzaItem implements Item {
    private final String type;

    public PizzaItem(String type) throws InvalidPizzaTypeException {
        if (!Objects.equals(type, "Standard") && !Objects.equals(type, "Pepperoni") && !Objects.equals(type, "Vegetarian")) {
            throw new InvalidPizzaTypeException();
        }
        this.type = type;
    }

    @Override
    public int getPrice() {
        int price = 0;
        if (type.equals("Standard")) {
            price += 10;
        }
        if (type.equals("Pepperoni")) {
            price += 12;
        }
        if (type.equals("Vegetarian")) {
            price += 8;
        }
        return price;
    }

    @Override
    public String getType() {
        return type;
    }

}

class Order {
    private Item[] items;
    private int[] quantity;
    private boolean isLocked;

    Order() {
        this.items = new Item[0];
        this.quantity = new int[0];
        this.isLocked = false;
    }

    public void addItem(Item item, int count) throws ItemOutOfStockException, OrderLockedException {
        if (isLocked) throw new OrderLockedException();
        if (count > 10) throw new ItemOutOfStockException(item);
        for (int i = 0; i < items.length; i++) {
            if (Objects.equals(item.getType(), items[i].getType())) {
                quantity[i] = count;
                return;
            }
        }
        items = Arrays.copyOf(items, items.length + 1);
        quantity = Arrays.copyOf(quantity, quantity.length + 1);
        items[items.length - 1] = item;
        quantity[quantity.length - 1] = count;
    }

    int getPrice() {
        int price = 0;
        for (int i = 0; i < items.length; i++) {
            price += items[i].getPrice() * quantity[i];
        }
        return price;
    }

    void displayOrder() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < items.length; i++) {
            str.append(String.format("%3d.%-15sx%2d%5d$\n", i + 1, items[i].getType(), quantity[i], items[i].getPrice() * quantity[i]));
        }
        str.append(String.format("%-22s%5d$", "Total:", getPrice()));
        System.out.println(str);
    }

    void removeItem(int idx) throws OrderLockedException {
        if (idx > items.length || idx < 0) {
            throw new ArrayIndexOutOfBoundsException(idx);
        }
        if (isLocked) {
            throw new OrderLockedException();
        }
        Item[] newItems = new Item[items.length];
        int j = 0;
        for (int i = 0; i < items.length; i++) {
            if (idx != i) {
                newItems[j++] = items[i];
            }
        }
        items = Arrays.copyOf(newItems, j);
    }

    void lock() throws EmptyOrder {
        if (items.length <= 0) {
            throw new EmptyOrder();
        } else {
            isLocked = true;
        }
    }
}

public class PizzaOrderTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Item
            try {
                String type = jin.next();
                String name = jin.next();
                Item item = null;
                if (type.equals("Pizza")) item = new PizzaItem(name);
                else item = new ExtraItem(name);
                System.out.println(item.getPrice());
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
        if (k == 1) { // test simple order
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 2) { // test order with removing
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (jin.hasNextInt()) {
                try {
                    int idx = jin.nextInt();
                    order.removeItem(idx);
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 3) { //test locking & exceptions
            Order order = new Order();
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new ExtraItem("Coke"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.removeItem(0);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }

}