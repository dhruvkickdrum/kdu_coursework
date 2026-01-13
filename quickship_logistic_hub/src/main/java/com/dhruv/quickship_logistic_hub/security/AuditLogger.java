package com.dhruv.quickship_logistic_hub.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class AuditLogger {
    private  static final Logger log = LoggerFactory.getLogger(AuditLogger.class);

    public void loginSuccess(String userName) {
        log.info("SECURITY: Login success for user={}", userName);
    }

    public void forbidden(String userName, String path) {
        log.warn("SECURITY: Forbidden access attempt user={} path ={}", userName, path);
    }
}