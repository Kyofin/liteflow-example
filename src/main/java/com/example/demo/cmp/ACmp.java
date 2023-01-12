/**
 * <p>Title: liteflow</p>
 * <p>Description: 轻量级的组件式流程框架</p>
 *
 * @author Bryan.Zhang
 * @email weenyc31@163.com
 * @Date 2020/4/1
 */
package com.example.demo.cmp;

import com.example.demo.context.CmpData;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.slot.Slot;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

import static ch.qos.logback.classic.ClassicConstants.FINALIZE_SESSION_MARKER;

@Slf4j
@Component("a")
public class ACmp extends NodeComponent {
    public static final String TASKID_AND_SESSION = "taskid-and-session";

    @Override
    public void onSuccess() throws Exception {
        System.out.println(this.getDisplayName()+"-"+getTag() + "任务完成");
    }

    @Override
    public void onError() throws Exception {
        System.out.println(this.getDisplayName()+"-"+getTag() + "任务失败");
    }

    @Override
    public <T> void beforeProcess(String nodeId, Slot slot) {
        String jobID = this.getRequestData();
        MDC.put(TASKID_AND_SESSION, (jobID +"-"+ this.getNodeId()+"-"+System.nanoTime()));
        super.beforeProcess(nodeId, slot);
    }

    /**
     * success或者error回调后才触发
     */
    @Override
    public <T> void afterProcess(String nodeId, Slot slot) {
        //主动让SiftingAppender结束文件.
        log.info(FINALIZE_SESSION_MARKER, "close sifting Appender of `{}`", MDC.get(TASKID_AND_SESSION));

        //清理MDC.
        MDC.clear();
        super.afterProcess(nodeId, slot);
    }

    @Override
    public void process() {
        CmpData cmpData = getCmpData(CmpData.class);
        log.info("info 日志");
        log.info("tag:" + getTag());
        log.info("当前安装服务的节点是：" + cmpData.getHost());
        try {
            Thread.sleep(new Random().nextInt(2000));
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("ACmp executed!");

    }
}
