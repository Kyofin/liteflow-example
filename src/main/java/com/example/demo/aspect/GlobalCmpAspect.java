package com.example.demo.aspect;

import com.example.demo.context.ServiceContext;
import com.yomahub.liteflow.aop.ICmpAroundAspect;
import com.yomahub.liteflow.slot.Slot;
import org.springframework.stereotype.Component;

@Component
public class GlobalCmpAspect implements ICmpAroundAspect {
    @Override
    public void beforeProcess(String nodeId, Slot slot) {
        ServiceContext context = slot.getContextBean(ServiceContext.class);
        //before business
        System.out.println(context.getSid()+":"+nodeId+":"+"is Running , write state to DB.");
    }

    @Override
    public void afterProcess(String nodeId, Slot slot) {
        ServiceContext context = slot.getContextBean(ServiceContext.class);
        //after business
        System.out.println(context.getSid()+":"+nodeId+ ":"+"is finish , write state to DB.");

    }
}
