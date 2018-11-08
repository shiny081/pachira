package cn.pachira.pachiracontent.dao;

import cn.pachira.pachiracontent.entity.Phone;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 从数据库中获取归属地信息
 *
 * @author Han Qizhe
 * @version 2018-03-31 17:33
 **/

@Mapper
public interface PhoneDAO {

    /**
     * 查询一个手机号的归属地
     *
     * @param phone 单个手机号
     * @return 单个手机号的归属地信息
     * @throws Exception 查询错误
     */
    Phone getMobileLocalInfo(@Param("phone") String phone) throws Exception;

    /**
     * 查询一组手机号的归属地
     *
     * @param phones 一组手机号
     * @return 手机号对应的归属地结果
     * @throws Exception 查询错误
     */
    List<Phone> getMultipleMobileLocalInfo(@Param("phones") List<String> phones)
            throws Exception;
}
