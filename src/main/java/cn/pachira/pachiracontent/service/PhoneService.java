package cn.pachira.pachiracontent.service;

import cn.pachira.pachiracontent.result.Result;

/**
 * 查询归属地信息的服务
 *
 * @author Han Qizhe
 * @version 2018-04-02 10:00
 **/

public interface PhoneService {
    /**
     * 查询单个手机号的归属地
     *
     * @param phone 手机号
     * @return 返回结果
     */
    Result querySinglePhone(String phone);

    /**
     * 查询一组手机号的归属地
     *
     * @param phones 通讯录生成的json字符串
     * @return 返回结果
     */
    Result queryMultiplePhone(String phones);
}
