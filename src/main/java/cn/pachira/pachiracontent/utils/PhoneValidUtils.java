package cn.pachira.pachiracontent.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证和转换电话号码格式的工具类
 *
 * @author LH
 * @version 2018-04-02 11:02
 **/

public class PhoneValidUtils {

    /**
     * 手机号验证
     *
     * @param num 电话号码
     * @return 验证通过返回true
     */
    public static boolean isMobile(String num) {
        if (num == null || "".equals(num)) {
            return false;
        }
        //86 135xxxxxxx形式 取135xxxxxxx
        if (num.indexOf(' ') > 0) {
            num = num.substring(num.indexOf(' ') + 1);
        }
        //86-135xxxxxxx形式 取135xxxxxxx
        if (num.indexOf('-') > 0) {
            num = num.substring(num.indexOf('-') + 1);
        }
        if (num.indexOf("(86)") == 0 || num.indexOf("(+86)") == 0) {
            num = num.substring(num.indexOf(')') + 1);
        }
        Pattern p;
        Matcher m;
        boolean b;
        p = Pattern.compile("^((\\+86)|(86))?[1][3456789][0-9]{9}$"); // 验证手机号
        m = p.matcher(num);
        b = m.matches();
        return b;
    }

    /**
     * 电话号码验证
     *
     * @param num 电话号码
     * @return 验证通过返回true
     * @author ：LH
     */
    public static boolean isPhone(String num) {
        if (num == null || "".equals(num) || num.length() < 7) {
            return false;
        }
        if (num.indexOf(' ') > 0) {
            num = num.replace(' ', '-');
        }
        Pattern p1;
        Pattern p2;
        Matcher m;
        boolean b;
        // 验证带区号的010-5108110,0105108110,(010)5108110
        p1 = Pattern.compile("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");
        p2 = Pattern.compile("^[1-9][0-9]{5,8}$");        // 验证没有区号的
        if (num.length() > 9) {
            m = p1.matcher(num);
            b = m.matches();
        } else {
            m = p2.matcher(num);
            b = m.matches();
        }
        return b;
    }

    /**
     * 获取座机区号
     *
     * @param num 座机号码
     * @return 区号
     */
    public static String getPhoneAreaCode(String num) {
        if (num == null || "".equals(num) || num.length() <= 7) {
            return null;
        }
        if (num.indexOf('-') > 0) {
            return num.substring(0, num.indexOf('-'));
        } else if (num.indexOf(')') > 0) {
            return num.substring(1, num.indexOf(')'));
        } else {
            return num.substring(0, num.length() - 7);
        }
    }

    /**
     * 获取手机号前七位
     *
     * @param num 手机号
     * @return 前七位
     */
    public static String getMobileAreaCode(String num) {
        String mobile = "";
        if (num != null && !num.equals("")) {
            if (num.length() >= 11) {
                mobile = num.substring(num.length() - 11, num.length() - 4);
            }

        }
        return mobile;
    }
}

