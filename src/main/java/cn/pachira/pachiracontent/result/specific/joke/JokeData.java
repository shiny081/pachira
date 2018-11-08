package cn.pachira.pachiracontent.result.specific.joke;

/**
 * 笑话的返回结果
 *
 * @author Han Qizhe
 * @version 2018-03-31 17:51
 **/

public class JokeData {
    /**
     * 笑话内容
     */
    private String answer;
    /**
     * 笑话类型
     */
    private String type;

    public JokeData() {
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
