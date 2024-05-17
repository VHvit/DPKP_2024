package com.example.config;

import jakarta.servlet.ServletRequest;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;


public class RequestContextCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        try {
            ServletRequest servletRequest = (ServletRequest) context.getBeanFactory().getBean("request");
            return servletRequest != null;
        } catch (Exception ex) {
            return false;
        }
    }
}