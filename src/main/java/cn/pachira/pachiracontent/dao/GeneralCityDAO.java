package cn.pachira.pachiracontent.dao;

import cn.pachira.pachiracontent.entity.GeneralCity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 获取城市信息
 *
 * @author Han Qizhe
 * @version 2018-04-02 11:29
 **/

@Mapper
public interface GeneralCityDAO {
    /**
     * 根据城市代码获取城市代码信息
     *
     * @param cityCode 城市代码
     * @return 城市信息
     * @throws Exception 查询异常
     */
    GeneralCity getCityByCityCode(@Param("cityCode") String cityCode) throws Exception;

    /**
     * 根据座机区号 获取城市代码信息
     *
     * @param areaCode 座机区号
     * @return 城市信息
     * @throws Exception 查询异常
     */
    List<GeneralCity> getCitiesByAreaCode(@Param("areaCode") String areaCode)
            throws Exception;

    /**
     * 根据座机区号集合 获取城市代码信息
     *
     * @param areaCodes 区号集合
     * @return 城市信息
     * @throws Exception 查询异常
     */
    List<GeneralCity> getCitiesByAreaCodes(@Param("areaCodes") List<String> areaCodes)
            throws Exception;
}
