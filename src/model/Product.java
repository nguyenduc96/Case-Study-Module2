package model;

public class Product extends Common {
    private String company;
    private int amount;
    private long price;
    private String type;

    public Product() {
    }

    public Product(String id, String name, String company, int amount, long price) {
        super(id, name);
        this.company = company;
        this.amount = amount;
        this.price = price;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        if (type == null) {
            return "THÔNG TIN SẢN PHẨM" + "\n- Mã sản phẩm : " + super.getId() + "\n- Tên sản phẩm : " + super.getName() +
                    "\n- Hãng sản xuất : " + company + "\nSố lượng : " + amount + "\n- Giá sản phẩm : " + price + " VNĐ";
        }
        return "THÔNG TIN SẢN PHẨM" + "\n- Mã sản phẩm : " + super.getId() + "\n- Tên sản phẩm : " + super.getName() +
                "\n- Hãng sản xuất : " + company + "\n- Số lượng : " + amount + "\n- Giá sản phẩm : " + price + " VNĐ" +
                "\n- Loại sản phẩm : " + type;
    }
}
