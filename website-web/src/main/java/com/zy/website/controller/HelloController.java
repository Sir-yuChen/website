package com.zy.website.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController
 * @author zhangyu
 * @since 2022-02-24
 */
@RestController
@RequestMapping("/index")
public class HelloController extends BaseController {

    /**
     * website  测试接口
     * @Author zhangyu
     * @Description
     * @Date 11:12 2022/2/23
     * @Param null
     * @return String
     **/
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String index(){
        return "Hello website";
    }
}
