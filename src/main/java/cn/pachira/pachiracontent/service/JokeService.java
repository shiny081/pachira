package cn.pachira.pachiracontent.service;

import cn.pachira.pachiracontent.result.Result;

/**
 * 提供本地笑话的服务
 *
 * @author Han Qizhe
 * @version 2018-03-31 17:46
 **/

public interface JokeService {
    /**
     * 获取对应的笑话
     *
     * @param type 笑话类型
     * @return 笑话内容
     */
    Result getJoke(String type);
}
