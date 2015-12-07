package com.wbl.base;

import com.wbl.utils.web.PageDriver;
import org.apache.log4j.Logger;

/**
 * Created by ${supriya} on 10/31/2015.
 */
public class MobBase {
    public Logger _logger;
    public PageDriver driver;

    public MobBase()
    {
        _logger = Logger.getLogger(BaseTest.class);

    }

}
