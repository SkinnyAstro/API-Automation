package POJO;

public class MedicineData {

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isKeepOrg() {
        return isKeepOrg;
    }

    public void setKeepOrg(boolean keepOrg) {
        isKeepOrg = keepOrg;
    }

    public boolean isCxAcceptedSubs() {
        return cxAcceptedSubs;
    }

    public void setCxAcceptedSubs(boolean cxAcceptedSubs) {
        this.cxAcceptedSubs = cxAcceptedSubs;
    }

    public boolean isCxOrgAdded() {
        return cxOrgAdded;
    }

    public void setCxOrgAdded(boolean cxOrgAdded) {
        this.cxOrgAdded = cxOrgAdded;
    }

    private String medicineName;
    private String productCode;
    private int quantity;
    private boolean isKeepOrg;
    private boolean cxAcceptedSubs;
    private boolean cxOrgAdded;

}
