package cn.pachira.pachiracontent.result.specific.phone;

/**
 * 手机归属地信息的返回结果
 *
 * @author Han Qizhe
 * @version 2018-04-02 09:40
 **/

public class PhoneData {
    /**
     * 区号
     */
    private String areaCode;
    /**
     * 卡类型
     */
    private String cardType;
    /**
     * 市
     */
    private String city;
    /**
     * 城市编号
     */
    private String cityCode;
    /**
     * 运营商
     */
    private String company;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 邮编
     */
    private String postCode;
    /**
     * 省
     */
    private String province;
    /**
     * 这一条的查询结果（暂均为success）
     */
    private String message = "success";

    public PhoneData() {
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
