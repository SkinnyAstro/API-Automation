package Models;

public class Addaddress {

    private int addressId;
    private String addressType;
    private String addressline1;
    private String addressline2;
    private String cityName;
    private String landmark;
    private int pincode;

    public int getAddressid() {
        return addressId;
    }

    public void setAddressid(int addressid) {
        this.addressId = addressid;
    }

    public String getAddresstype() {
        return addressType;
    }

    public void setAddresstype(String addresstype) {
        this.addressType = addresstype;
    }

    public String getAddressline1() {
        return addressline1;
    }

    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    public String getAddressline2() {
        return addressline2;
    }

    public void setAddressline2(String addressline2) {
        this.addressline2 = addressline2;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public boolean isRecalcLocation() {
        return recalcLocation;
    }

    public void setRecalcLocation(boolean recalcLocation) {
        this.recalcLocation = recalcLocation;
    }

    private String stateName;
    private boolean recalcLocation;


}
