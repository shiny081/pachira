package cn.pachira.pachiracontent.entity;

/**
 * 城市信息的实体类
 *
 * @author LH
 * @version 2018-04-02 11:27
 **/

public class GeneralCity {
    /**
     * 省级
     */
    public static final String LEVEL_PROVINCE = "1";
    /**
     * 市级
     */
    public static final String LEVEL_CITY = "2";
    /**
     * 区/县级
     */
    public static final String LEVEL_AREA = "3";
    /**
     * 城市代码
     */
    private String cityCode;
    /**
     * 城市名称
     */
    private String city;
    /**
     * 上级代码
     */
    private String superiorCode;
    /**
     * 简称
     */
    private String shortName;
    /**
     * 级别
     */
    private String level;
    /**
     * 座机区号
     */
    private String areaCode;
    /**
     * 邮编
     */
    private String postCode;
    /**
     * 拼音
     */
    private String spell;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSuperiorCode() {
        return superiorCode;
    }

    public void setSuperiorCode(String superiorCode) {
        this.superiorCode = superiorCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }
}
