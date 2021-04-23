package com.example.oauth.business.controller;

import com.example.oauth.business.entity.TestEntity;
import com.example.oauth.comm.rest.RestResult;
import com.example.oauth.comm.rest.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/json")
    public RestResult getJson () {
        return ResultUtils.success(TestEntity.getInstance());
    }

}
