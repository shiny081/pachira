package cn.pachira.pachiracontent.result.specific.phone;

import java.util.List;

/**
 * 联系人信息（用于多条归属地结果）
 *
 * @author Han Qizhe
 * @version 2018-04-02 10:47
 **/

public class ContactData {
    /**
     * 联系人名
     */
    private String name;
    /**
     * 联系人的电话号码列表
     */
    private List<String> nums;
    /**
     * 查询得到的归属地信息列表
     */
    private List<PhoneData> numberInfo;

    public ContactData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getNums() {
        return nums;
    }

    public void setNums(List<String> nums) {
        this.nums = nums;
    }

    public List<PhoneData> getNumberInfo() {
        return numberInfo;
    }

    public void setNumberInfo(List<PhoneData> numberInfo) {
        this.numberInfo = numberInfo;
    }
}
