package com.cimctht.thtzxt.common.distributedlock;

import com.cimctht.thtzxt.common.entity.JsonResult;
import com.cimctht.thtzxt.common.exception.UnimaxException;
import com.cimctht.thtzxt.system.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@RestController
public class LockController {

    static final Logger logger = LoggerFactory.getLogger(LockController.class);

    public JsonResult lockExceptionControllerMothed(String message) {
        return new JsonResult(new UnimaxException(message));
    }


}
