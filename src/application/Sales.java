package application;

public class Sales {
    private String name;
    private int custId;
    private long contact;
    private int quantity;
    private int price;

    public Sales(String name, int custId, long contact, int quantity, int price) {
        this.name = name;
        this.custId = custId;
        this.contact = contact;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and setters
    // ...

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
