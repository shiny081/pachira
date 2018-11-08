package cn.pachira.pachiracontent.controller;

import cn.pachira.pachiracontent.result.Result;
import cn.pachira.pachiracontent.service.PhoneService;
import cn.pachira.pachiracontent.utils.ErrorCode;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 查询归属地的Controller
 *
 * @author Han Qizhe
 * @version 2018-04-02 10:11
 **/

@RestController
public class PhoneController {
    private static final Logger logger = LoggerFactory.getLogger(PhoneController.class);

    private PhoneService phoneService;

    public PhoneController(@Autowired PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    /**
     * 获取归属地的接口
     *
     * @param body 请求信息
     * @return 结果
     */
    @RequestMapping(
            value = "/queryPhone",
            method = {RequestMethod.POST},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ResponseBody
    public Result queryPhone(@RequestBody String body) {
        Result result;
        JSONObject data = JSON.parseObject(body);
        // 优先按组查询
        if (data.containsKey("phones")) {
            String phones = data.getString("phones");
            logger.info("查询一组归属地信息，查询字符串：" + phones);
            result = phoneService.queryMultiplePhone(phones);
        } else if (data.containsKey("phone")) {
            String phone = data.getString("phone");
            logger.info("查询一条归属地信息，号码：" + phone);
            result = phoneService.querySinglePhone(phone);
        } else {
            result = new Result();
            result.setCode(ErrorCode.MISSING_PARAM.getErrCode());
            result.setMessage(ErrorCode.MISSING_PARAM.getErrMsg());
            logger.debug("没有查询参数，查询");
        }
        return result;
    }
}
