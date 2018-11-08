package cn.pachira.pachiracontent.controller;

import cn.pachira.pachiracontent.result.Result;
import cn.pachira.pachiracontent.service.JokeService;
import cn.pachira.pachiracontent.utils.ErrorCode;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 获取笑话的Controller
 *
 * @author Han Qizhe
 * @version 2018-03-31 18:14
 **/

@RestController
public class JokeController {
    private static final Logger logger = LoggerFactory.getLogger(JokeController.class);

    private JokeService jokeService;

    public JokeController(@Autowired JokeService jokeService) {
        this.jokeService = jokeService;
    }

    /**
     * 获取笑话的接口
     *
     * @param body 请求信息
     * @return 结果
     */
    @RequestMapping(
            value = "/getJoke",
            method = {RequestMethod.POST},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ResponseBody
    public Result getJoke(@RequestBody String body) {
        Result result;
        JSONObject data = JSON.parseObject(body);
        if (data.containsKey("type")) {
            String type = data.getString("type");
            logger.info("请求获取一条笑话，分类：" + type);
            result = jokeService.getJoke(type);
        } else {
            result = new Result();
            result.setCode(ErrorCode.MISSING_PARAM.getErrCode());
            result.setMessage(ErrorCode.MISSING_PARAM.getErrMsg());
            logger.debug("没有查询参数，查询");
        }
        return result;
    }
}
