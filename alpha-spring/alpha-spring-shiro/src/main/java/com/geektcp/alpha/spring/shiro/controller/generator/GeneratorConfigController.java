package com.geektcp.alpha.spring.shiro.controller.generator;

import com.geektcp.alpha.spring.shiro.annotation.ControllerEndpoint;
import com.geektcp.alpha.spring.shiro.common.controller.BaseController;
import com.geektcp.alpha.spring.shiro.common.entity.FebsResponse;
import com.geektcp.alpha.spring.shiro.exception.FebsException;
import com.geektcp.alpha.spring.shiro.entity.generator.GeneratorConfig;
import com.geektcp.alpha.spring.shiro.service.IGeneratorConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author MrBird
 */
@Slf4j
@RestController
@RequestMapping("generatorConfig")
public class GeneratorConfigController extends BaseController {

    @Autowired
    private IGeneratorConfigService generatorConfigService;

    @GetMapping
    @RequiresPermissions("generator:configure:view")
    public FebsResponse getGeneratorConfig() {
        return new FebsResponse().success().data(generatorConfigService.findGeneratorConfig());
    }

    @PostMapping("update")
    @RequiresPermissions("generator:configure:update")
    @ControllerEndpoint(operation = "修改GeneratorConfig", exceptionMessage = "修改GeneratorConfig失败")
    public FebsResponse updateGeneratorConfig(@Valid GeneratorConfig generatorConfig) {
        if (StringUtils.isBlank(generatorConfig.getId()))
            throw new FebsException("配置id不能为空");
        this.generatorConfigService.updateGeneratorConfig(generatorConfig);
        return new FebsResponse().success();
    }
}
