package com.mathiasgrimm.app.controller;

import com.mathiasgrimm.lib.AppConfig;
import com.mathiasgrimm.lib.container.di.Inject;
import com.mathiasgrimm.lib.logger.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexController {

    private Logger logger;

    @Inject
    public IndexController(AppConfig appConfig, Logger logger) {
        this.logger = logger;
    }

    public void index(
            HttpServletRequest request,
            HttpServletResponse response,
            Integer age,
            Double weight

    ) throws Exception {
         this.logger.debug("opa!");
         this.logger.info("opa!");
         this.logger.notice("opa!");
         this.logger.warning("opa!");
         this.logger.error("opa!");
         this.logger.critical("opa!");
         this.logger.alert("opa!");
         this.logger.emerg("opa!");
         this.logger.debug("cool", this.getClass().getCanonicalName());

        JSONObject data = new JSONObject();
        data.put("weight", weight);
        data.put("age", age);

        response.getWriter().write(data.toString());
    }
}
