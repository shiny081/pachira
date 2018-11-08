package cn.pachira.pachiracontent.service.impl;

import cn.pachira.pachiracontent.dao.GeneralCityDAO;
import cn.pachira.pachiracontent.dao.PhoneDAO;
import cn.pachira.pachiracontent.entity.GeneralCity;
import cn.pachira.pachiracontent.entity.Phone;
import cn.pachira.pachiracontent.result.Result;
import cn.pachira.pachiracontent.result.specific.phone.ContactData;
import cn.pachira.pachiracontent.result.specific.phone.PhoneData;
import cn.pachira.pachiracontent.service.PhoneService;
import cn.pachira.pachiracontent.utils.ErrorCode;
import cn.pachira.pachiracontent.utils.PhoneValidUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询归属地信息的服务
 *
 * @author Han Qizhe
 * @version 2018-04-02 10:01
 **/

@Service
public class PhoneServiceImpl implements PhoneService {
    private static final Logger logger = LoggerFactory.getLogger(PhoneServiceImpl.class);

    private PhoneDAO phoneDAO;
    private GeneralCityDAO generalCityDAO;

    // 将省的城市代码转换为对应省会的城市代码的集合
    private static Map<String, String> proToCapMap = new HashMap<>();

    public PhoneServiceImpl(@Autowired PhoneDAO phoneDAO,
                            @Autowired GeneralCityDAO generalCityDAO) {
        this.phoneDAO = phoneDAO;
        this.generalCityDAO = generalCityDAO;
    }

    /**
     * 查询单个手机号的归属地
     *
     * @param phone 手机号
     * @return 返回结果
     */
    @Override
    public Result querySinglePhone(String phone) {
        Result result = new Result();
        Boolean isMobile = PhoneValidUtils.isMobile(phone);
        Boolean isPhone = PhoneValidUtils.isPhone(phone);
        PhoneData phoneData = null;
        //手机，座机号码验证
        if (isMobile || isPhone) {
            if (isMobile) {
                try {
                    String mobileCode = PhoneValidUtils.getMobileAreaCode(phone);
                    Phone mobile = phoneDAO.getMobileLocalInfo(mobileCode);
                    phoneData = setPhoneData(mobile);
                    phoneData.setPhone(phone);
                    result.setData(phoneData);
                    result.setCode(ErrorCode.SUCCESS.getErrCode());
                    result.setMessage(ErrorCode.SUCCESS.getErrMsg());
                    logger.info("查询手机归属地成功");
                } catch (Exception e) {
                    result.setCode(ErrorCode.QUERY_PHONE_FAIL.getErrCode());
                    result.setMessage(ErrorCode.QUERY_PHONE_FAIL.getErrMsg());
                    logger.error("查询手机归属地异常");
                    e.printStackTrace();
                }
            } else {
                //获取座机号码区号
                String areaCode = PhoneValidUtils.getPhoneAreaCode(phone);
                if (areaCode != null && !areaCode.equals("")) {
                    try {
                        //查询对应区号的城市
                        List<GeneralCity> citys = generalCityDAO.getCitiesByAreaCode(areaCode);
                        if (citys != null && citys.size() == 1) {
                            phoneData = setPhoneData(citys.get(0));
                            phoneData.setPhone(phone);
                        } else if (citys != null && citys.size() > 1) {
                            GeneralCity provinceCity = citys.get(0);
                            //如果有多个城市区号相同，取省会城市
                            for (GeneralCity ci : citys) {
                                String superCode = ci.getSuperiorCode();
                                if (proToCapMap.get(superCode).equals(ci.getCityCode())) {
                                    provinceCity = ci;
                                }
                            }
                            phoneData = setPhoneData(provinceCity);
                            phoneData.setPhone(phone);
                        }
                        result.setData(phoneData);
                        result.setCode(ErrorCode.SUCCESS.getErrCode());
                        result.setMessage(ErrorCode.SUCCESS.getErrMsg());
                        logger.info("查询座机归属地成功");
                    } catch (Exception e) {
                        result.setCode(ErrorCode.QUERY_PHONE_FAIL.getErrCode());
                        result.setMessage(ErrorCode.QUERY_PHONE_FAIL.getErrMsg());
                        logger.error("查询座机归属地异常");
                        e.printStackTrace();
                    }
                }
            }
        } else {
            logger.error("手机号码格式错误");
            result.setCode(ErrorCode.INVALID_PARAM.getErrCode());
            result.setMessage(ErrorCode.INVALID_PARAM.getErrMsg());
        }
        return result;
    }

    /**
     * 查询一组手机号的归属地
     *
     * @param phones 通讯录生成的json字符串
     * @return 返回结果
     */
    @Override
    public Result queryMultiplePhone(String phones) {
        List<ContactData> contactCardInfos = new ArrayList<>();
        if (phones != null) {
            // 获取通讯录所以联系人信息
            JSONArray contractArray = JSONArray.parseArray(phones);
            List<String> mobileValidNums = new ArrayList<>();//存储手机有效号码前七位
            List<String> phoneValidNums = new ArrayList<>();//存储座机有效号码区号
            //遍历通讯录筛选可用号码，区分手机号或座机号
            for (int i = 0; i < contractArray.size(); i++) {
                //通讯录个人信息
                JSONObject personInfo = contractArray.getJSONObject(i);
                String name = personInfo.getString("name");
                List<String> numbers = new ArrayList<>();
                JSONArray personNums = personInfo.getJSONArray("nums");
                for (int j = 0; j < personNums.size(); j++) {
                    numbers.add(personNums.getString(j));
                }
                ContactData contactData = new ContactData();
                contactData.setName(name);
                contactData.setNums(numbers);
                contactCardInfos.add(contactData);
                if (!numbers.isEmpty()) {
                    for (String number : numbers) {
                        //支持+86135xxxxxx,(86)135xxxxxx, +86 135xxxxxx等格式
                        if (PhoneValidUtils.isMobile(number)) {
                            mobileValidNums.add(PhoneValidUtils.getMobileAreaCode(number));
                        } else if (PhoneValidUtils.isPhone(number)) {
                            //支持010-5108110，(010)5108110，0105108110,010 5108110格式
                            phoneValidNums.add(PhoneValidUtils.getPhoneAreaCode(number));
                        }
                    }
                }
            }
            //获取手机归属地集合
            List<Phone> mobiles;
            List<GeneralCity> generalCityList;
            try {
                mobiles = phoneDAO.getMultipleMobileLocalInfo(mobileValidNums);
                //获取座机归属地城市信息集合
                generalCityList = generalCityDAO.getCitiesByAreaCodes(phoneValidNums);
            } catch (Exception e) {
                logger.error("查询归属地失败，原因：" + e.toString());
                Result result = new Result();
                result.setCode(ErrorCode.QUERY_PHONE_FAIL.getErrCode());
                result.setMessage(ErrorCode.QUERY_PHONE_FAIL.getErrMsg());
                return result;
            }
            //过滤相同区号城市信息，保留省会城市
            generalCityList = filterCites(generalCityList);

            Map<String, PhoneData> phoneDataMap = new HashMap<>();
            //设置通讯录手机归宿地信息
            if (mobiles != null && !mobiles.isEmpty()) {
                for (Phone mobile : mobiles) {
                    PhoneData phoneData = setPhoneData(mobile);
                    phoneDataMap.put(mobile.getPhone(), phoneData);
                }
            }
            //设置通讯录座机归属地信息
            if (generalCityList != null && !generalCityList.isEmpty()) {
                for (GeneralCity ci : generalCityList) {
                    PhoneData phoneData = setPhoneData(ci);
                    phoneDataMap.put(ci.getAreaCode(), phoneData);
                }
            }
            //设置通讯录归宿地信息
            for (ContactData contactCardInfo : contactCardInfos) {
                List<String> nums = contactCardInfo.getNums();
                if (nums != null && !nums.isEmpty()) {
                    List<PhoneData> phoneDataList = new ArrayList<>();
                    for (String num : nums) {
                        PhoneData phoneDataM;
                        PhoneData phoneData = new PhoneData();
                        if (PhoneValidUtils.isMobile(num)) {
                            phoneDataM = phoneDataMap.get(PhoneValidUtils.getMobileAreaCode(num));
                            if (phoneDataM != null) {
                                phoneData.setAreaCode(phoneDataM.getAreaCode());
                                phoneData.setCardType(phoneDataM.getCardType());
                                phoneData.setCity(phoneDataM.getCity());
                                phoneData.setCityCode(phoneDataM.getCityCode());
                                phoneData.setCompany(phoneDataM.getCompany());
                                phoneData.setMessage(phoneDataM.getMessage());
                                phoneData.setPhone(num);
                                phoneData.setPostCode(phoneDataM.getPostCode());
                                phoneData.setProvince(phoneDataM.getProvince());
                            }
                        } else if (PhoneValidUtils.isPhone(num)) {
                            phoneDataM = phoneDataMap.get(PhoneValidUtils.getPhoneAreaCode(num));
                            if (phoneDataM != null) {
                                phoneData = new PhoneData();
                                phoneData.setAreaCode(phoneDataM.getAreaCode());
                                phoneData.setCardType(phoneDataM.getCardType());
                                phoneData.setCity(phoneDataM.getCity());
                                phoneData.setCityCode(phoneDataM.getCityCode());
                                phoneData.setCompany(phoneDataM.getCompany());
                                phoneData.setMessage(phoneDataM.getMessage());
                                phoneData.setPhone(num);
                                phoneData.setPostCode(phoneDataM.getPostCode());
                                phoneData.setProvince(phoneDataM.getProvince());
                            }
                        } else {
                            phoneData.setAreaCode("");
                            phoneData.setCardType("");
                            phoneData.setCity("");
                            phoneData.setCityCode("");
                            phoneData.setCompany("");
                            phoneData.setPhone(num);
                            phoneData.setMessage("fail");
                            phoneData.setPostCode("");
                            phoneData.setProvince("");
                        }
                        phoneDataList.add(phoneData);
                    }
                    contactCardInfo.setNumberInfo(phoneDataList);
                }
            }
        }
        Result result = new Result();
        result.setCode(ErrorCode.SUCCESS.getErrCode());
        result.setMessage(ErrorCode.SUCCESS.getErrMsg());
        result.setData(contactCardInfos);
        return result;
    }

    /**
     * 设置手机归属地数据
     *
     * @param mobile 归属地实体
     * @return 归属地信息
     */
    private PhoneData setPhoneData(Phone mobile) {
        PhoneData phoneData = new PhoneData();
        if (mobile != null) {
            phoneData.setAreaCode(mobile.getAreaCode());
            phoneData.setCardType(mobile.getTypes());
            phoneData.setCity(mobile.getCity());
            phoneData.setCityCode(mobile.getCityCode());
            phoneData.setCompany(mobile.getIsp());
            phoneData.setMessage("success");
            phoneData.setPhone(mobile.getPhone());
            phoneData.setPostCode(mobile.getPostCode());
            phoneData.setProvince(mobile.getProvince());
        }
        return phoneData;
    }

    /**
     * 设置固话归属地数据
     * 这里设置了城市信息，号码信息由上级设置
     *
     * @param phone 城市信息
     * @return 设置结果
     */
    private PhoneData setPhoneData(GeneralCity phone) {
        PhoneData phoneData = new PhoneData();
        if (phone != null) {
            try {
                GeneralCity province = generalCityDAO.getCityByCityCode(phone.getSuperiorCode());
                phoneData.setAreaCode(phone.getAreaCode());
                phoneData.setCity(phone.getCity());
                phoneData.setCityCode(phone.getCityCode());
                phoneData.setMessage("success");
                phoneData.setPostCode(phone.getPostCode());
                phoneData.setProvince(province.getCity());
            } catch (Exception e) {
                logger.error("查询" + phone.getCity() + "上级地区异常");
                e.printStackTrace();
            }
        }
        return phoneData;
    }

    /**
     * 过滤相同区号城市信息，保留省会城市
     *
     * @param cities 城市信息
     * @return 省会城市信息
     */
    private List<GeneralCity> filterCites(List<GeneralCity> cities) {
        List<GeneralCity> filterList = new ArrayList<>();
        if (cities == null) {
            return null;
        } else if (cities.size() <= 1) {
            return cities;
        } else {
            logger.debug("过滤相同区号城市保留省会城市，" + "过滤前" + cities.size() + "条");
            Boolean checkFlag;
            for (int i = 0; i < cities.size(); i++) {
                checkFlag = true;
                String areaCode = cities.get(i).getAreaCode();
                String cityCode = cities.get(i).getCityCode();
                String superiorCode = cities.get(i).getSuperiorCode();
                for (GeneralCity city : cities) {
                    String cityAreaCode = city.getAreaCode();
                    //发现区号重复
                    if (areaCode.equals(cityAreaCode)) {
                        //iCityCode不是省会城市
                        if (!proToCapMap.get(superiorCode).equals(cityCode)) {
                            checkFlag = false;
                        }
                    }
                }
                if (checkFlag) {
                    filterList.add(cities.get(i));
                }
            }
        }
        logger.debug("过滤后" + filterList.size() + "条");
        return filterList;
    }

    static {
        proToCapMap.put("110000", "110100");//北京
        proToCapMap.put("120000", "120100");//天津
        proToCapMap.put("130000", "130100");//河北省
        proToCapMap.put("140000", "140100");//山西省
        proToCapMap.put("150000", "150100");//内蒙古自治区
        proToCapMap.put("210000", "210100");//辽宁省
        proToCapMap.put("220000", "220100");//吉林省
        proToCapMap.put("230000", "230100");//黑龙江省
        proToCapMap.put("310000", "310100");//上海
        proToCapMap.put("320000", "320100");//江苏省
        proToCapMap.put("330000", "330100");//浙江省
        proToCapMap.put("340000", "340100");//安徽省
        proToCapMap.put("350000", "350100");//福建省
        proToCapMap.put("360000", "360100");//江西省
        proToCapMap.put("370000", "370100");//山东省
        proToCapMap.put("410000", "410100");//河南省
        proToCapMap.put("420000", "420100");//湖北省
        proToCapMap.put("430000", "430100");//湖南省
        proToCapMap.put("440000", "440100");//广东省
        proToCapMap.put("450000", "450100");//广西壮族自治区
        proToCapMap.put("460000", "460100");//海南省
        proToCapMap.put("500000", "500100");//重庆
        proToCapMap.put("510000", "510100");//四川省
        proToCapMap.put("520000", "520100");//贵州省
        proToCapMap.put("530000", "530100");//云南省
        proToCapMap.put("540000", "540100");//西藏自治区
        proToCapMap.put("610000", "610100");//陕西省
        proToCapMap.put("620000", "620100");//甘肃省
        proToCapMap.put("630000", "630100");//青海省
        proToCapMap.put("640000", "640100");//宁夏回族自治区
        proToCapMap.put("650000", "650100");//新疆维吾尔自治区
        proToCapMap.put("710000", "710100");//台湾
        proToCapMap.put("810000", "810100");//香港特别行政区
        proToCapMap.put("820000", "820100");//澳门特别行政区
    }
}
