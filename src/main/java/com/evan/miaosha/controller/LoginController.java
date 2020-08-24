package com.evan.miaosha.controller;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;
import org.thymeleaf.util.Validate;

import com.evan.miaosha.domain.User;
import com.evan.miaosha.redis.RedisService;
import com.evan.miaosha.redis.UserKey;
import com.evan.miaosha.result.CodeMsg;
import com.evan.miaosha.result.Result;
import com.evan.miaosha.service.MiaoshaUserService;
import com.evan.miaosha.service.UserService;
import com.evan.miaosha.util.ValidatorUtil;
import com.evan.miaosha.vo.LoginVo;

/**
 * @author yangyifan05 <yangyifan05@kuaishou.com>
 * Created on 2020-07-31
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    RedisService redisService;

    @Autowired
    MiaoshaUserService userService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
        log.info(loginVo.toString());
        /*//参数校验，后面转入注解
        String passInput = loginVo.getPassword();
        String mobile = loginVo.getMobile();
        if (StringUtils.isEmpty(passInput)) {
            return Result.error(CodeMsg.PASSWORD_EMPTY);
        }
        if (StringUtils.isEmpty(mobile)) {
            return Result.error(CodeMsg.MOBILE_EMPTY);
        }
        if (!ValidatorUtil.isMobile(mobile)) {
            return Result.error(CodeMsg.MOBILE_ERROR);
        }*/
        //登录
        String token = userService.login(response, loginVo);
        return Result.success(token);
    }
}
