package com.cmb.bankcheck.service.impl;

import com.cmb.bankcheck.service.DeployService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-26
 * Time:08:59
 */
@Service
public class DeployServiceImpl implements DeployService {
    @Override
    public String deploy() {
        return "this is deploy";
    }

    @Override
    public int upload() {
        return 100;
    }
}
