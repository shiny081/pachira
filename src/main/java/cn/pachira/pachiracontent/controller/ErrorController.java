package cn.pachira.pachiracontent.controller;

import cn.pachira.pachiracontent.result.Result;
import cn.pachira.pachiracontent.utils.ErrorCode;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

/**
 * 返回异常的Controller
 *
 * @author Han Qizhe
 * @version 2018-03-31 18:24
 **/

@ControllerAdvice
@RestController
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {
    /**
     * 最宽泛的异常处理
     *
     * @return 内部错误异常
     */
    @ExceptionHandler(Exception.class)
    @RequestMapping(
            value = "/error",
            produces = "application/json;charset=utf-8"
    )
    public ResponseEntity<Result> baseError() {
        Result result = new Result();
        result.setCode(ErrorCode.INTERN_ERROR.getErrCode());
        result.setMessage(ErrorCode.INTERN_ERROR.getErrMsg());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
    }

    /**
     * 解析参数失败的异常处理
     *
     * @return 缺少参数异常
     */
    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            MissingPathVariableException.class
    })
    @ResponseBody
    public ResponseEntity<Result> missingParamError() {
        Result result = new Result();
        result.setCode(ErrorCode.MISSING_PARAM.getErrCode());
        result.setMessage(ErrorCode.MISSING_PARAM.getErrMsg());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
    }

    /**
     * 参数错误的异常处理
     *
     * @return 参数错误的异常
     */
    @ExceptionHandler({
            TypeMismatchException.class
    })
    public ResponseEntity<Result> invalidParamError() {
        Result result = new Result();
        result.setCode(ErrorCode.INVALID_PARAM.getErrCode());
        result.setMessage(ErrorCode.INVALID_PARAM.getErrMsg());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
    }

    /**
     * 实现异常Controller接口，覆盖Spring默认的异常页面
     *
     * @return 异常的路径
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }
}

