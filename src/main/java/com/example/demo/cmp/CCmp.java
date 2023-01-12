/**
 * <p>Title: liteflow</p>
 * <p>Description: 轻量级的组件式流程框架</p>
 *
 * @author Bryan.Zhang
 * @email weenyc31@163.com
 * @Date 2020/4/1
 */
package com.example.demo.cmp;

import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component("c")
public class CCmp extends NodeComponent {
    @Override
    public void onSuccess() throws Exception {
        System.out.println(this.getDisplayName() + "任务完成");
    }

    @Override
    public void onError() throws Exception {
        System.out.println(this.getDisplayName() + "任务失败");
    }

    @Override
    public boolean isEnd() {
        boolean end = super.isEnd();
        if (end) {
            System.out.println("整个flow都结束了。。。。");
        }
        return end;
    }

    @Override
    public void process() {
        try {
            Thread.sleep(new Random().nextInt(5000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int a = 1 / 0;
        System.out.println("CCmp executed!");
    }

}
