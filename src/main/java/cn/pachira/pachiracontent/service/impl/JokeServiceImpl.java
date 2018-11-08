package cn.pachira.pachiracontent.service.impl;


import cn.pachira.pachiracontent.result.Result;
import cn.pachira.pachiracontent.result.specific.joke.JokeData;
import cn.pachira.pachiracontent.service.JokeService;
import cn.pachira.pachiracontent.utils.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 提供本地笑话的服务
 *
 * @author Han Qizhe
 * @version 2018-03-31 17:45
 **/

@Service
public class JokeServiceImpl implements JokeService {
    private static final Logger logger = LoggerFactory.getLogger(JokeServiceImpl.class);
    /**
     * 储存笑话资源的Map
     */
    private static Map<String, ArrayList<String>> jokeResourceMap = new HashMap<>();

    /**
     * 初始化方法，从文件读取笑话数据
     */
    public JokeServiceImpl() {
        String adultFilePath = "Jokes/adult.txt";
        String otherFilePath = "Jokes/other.txt";
        ArrayList<String> adultJokeList = readFile(adultFilePath);
        logger.info("读取到" + adultJokeList.size() + " 条成人笑话数据");
        jokeResourceMap.put("adult", adultJokeList);
        ArrayList<String> otherJokeList = readFile(otherFilePath);
        logger.info("读取到" + otherJokeList.size() + " 条非成人笑话数据");
        jokeResourceMap.put("other", otherJokeList);
        adultJokeList.addAll(otherJokeList);
        logger.info("共读取到" + adultJokeList.size() + " 条笑话数据");
        jokeResourceMap.put("all", adultJokeList);
    }

    /**
     * 获取一条笑话
     *
     * @param type 笑话类型
     * @return 笑话内容
     */
    @Override
    public Result getJoke(String type) {
        String jokeString = getAnswer(type);
        JokeData joke = new JokeData();
        joke.setType(type);
        joke.setAnswer(jokeString);
        Result result = new Result();
        if (jokeString != null && !"".equals(jokeString)) {
            result.setCode(ErrorCode.SUCCESS.getErrCode());
            result.setMessage(ErrorCode.SUCCESS.getErrMsg());
            result.setData(joke);
            logger.info("查询笑话成功");
        } else {
            result.setCode(ErrorCode.GET_JOKE_FAIL.getErrCode());
            result.setMessage(ErrorCode.GET_JOKE_FAIL.getErrMsg());
            logger.info("查询笑话失败");
        }
        return result;
    }

    /**
     * 从ClassPath获取笑话列表
     *
     * @param fileName 文件名
     * @return 笑话列表
     */
    private ArrayList<String> readFile(String fileName) {
        ArrayList<String> contentList = new ArrayList<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream != null) {
            try {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream, "UTF-8"));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    line = line.trim();
                    contentList.add(line);
                }
                return contentList;
            } catch (IOException e) {
                logger.error("读取文件失败");
                return contentList;
            }
        } else {
            logger.error("读取文件失败");
            return contentList;
        }
    }

    /**
     * 根据类型获取笑话的方法
     *
     * @param jokeType 笑话类型
     * @return 笑话内容
     */
    private String getAnswer(String jokeType) {
        Random random = new Random();
        int adultSize = jokeResourceMap.get("adult").size();
        int otherSize = jokeResourceMap.get("other").size();
        int allSize = jokeResourceMap.get("all").size();
        String answer;
        switch (jokeType) {
            case "adult":
                answer = jokeResourceMap.get("adult").get(random.nextInt(adultSize));
                break;
            case "other":
                answer = jokeResourceMap.get("other").get(random.nextInt(otherSize));
                break;
            default:
                answer = jokeResourceMap.get("all").get(random.nextInt(allSize));
                break;
        }
        return answer;
    }
}
