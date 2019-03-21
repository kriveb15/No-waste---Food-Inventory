package beans;

import java.sql.Date;

public class Product {

    private String productName;
    private Date expirationDate;                           
    private String category;
    private int productNr;
    private boolean reminder;

    public Product(String productName, Date expirationDate, String category, int productNr, boolean reminder) {
        this.productName = productName;
        this.expirationDate = expirationDate;
        this.category = category;
        this.productNr = productNr;
        this.reminder = reminder;
    }

    public String getProductName() {
        return productName;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public String getCategory() {
        return category;
    }

    public int getProductNr() {
        return productNr;
    }

    public int getDaysTillExpiration() {
        return 0;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    @Override
    public String toString() {
        if (reminder) {
            return "      R!";
        } else {
            return "";
        }
    }
}
