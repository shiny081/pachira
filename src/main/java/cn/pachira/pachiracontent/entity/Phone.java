package cn.pachira.pachiracontent.entity;

/**
 * 手机号码归属地
 *
 * @author Han Qizhe
 * @version 2018-03-31 17:34
 **/

public class Phone {
    /**
     * 手机号前三位
     */
    private String pref;
    /**
     * 手机号前七位
     */
    private String phone;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 运营商简称
     */
    private String isp;
    /**
     * 邮编
     */
    private String postCode;
    /**
     * 城市代码
     */
    private String cityCode;
    /**
     * 地区代码
     */
    private String areaCode;
    /**
     * 运营商类型
     */
    private String types;

    public Phone() {
    }

    public String getPref() {
        return pref;
    }

    public void setPref(String pref) {
        this.pref = pref;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }
}
