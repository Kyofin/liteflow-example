package com.example.demo;

import com.example.demo.context.ServiceContext;
import com.yomahub.liteflow.builder.LiteFlowNodeBuilder;
import com.yomahub.liteflow.builder.el.LiteFlowChainELBuilder;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.FlowBus;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.flow.entity.CmpStep;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Resource
	private FlowExecutor flowExecutor;

	@Test
	public void testFlow() throws Exception {
		//构建组件。注意Id不能重复，会覆盖
		LiteFlowNodeBuilder.createCommonNode().setId("a")
				.setName("初始化Zookeeper")
				.setClazz("com.example.demo.cmp.ACmp")
				.build();


		LiteFlowNodeBuilder.createCommonNode().setId("b")
				.setName("配置Zookeeper")
				.setClazz("com.example.demo.cmp.BCmp")
				.build();

		LiteFlowNodeBuilder.createCommonNode().setId("c")
				.setName("安装Zookeeper")
				.setClazz("com.example.demo.cmp.CCmp")
				.build();

		// 构建chain
		LiteFlowChainELBuilder.createChain().setChainName("初始Zookeeper服务").setChainId("chainID1").setEL(
//				"cmpData = '{\"host\":\"node001\",\"role\":\"ZookeeperServer\"}'; " +
						"THEN(" +
								"a.tag(\"TASK-a1\").data('{\"host\":\"node001\",\"role\":\"ZookeeperServer\"}'), " +
								"a.tag(\"TASK-a2\").data('{\"host\":\"node002\",\"role\":\"ZookeeperServer\"}') ," +
								"a.tag(\"TASK-a3\").data('{\"host\":\"node003\",\"role\":\"ZookeeperServer\"}') ," +
								" b.tag(\"TASK-b1\"), " +
								"WHEN(c.tag(\"TASK-c1\"),c.tag(\"TASK-c2\"),c.tag(\"TASK-c3\"))" +
								")"
		).build();

		ServiceContext ZKserviceContext = new ServiceContext("ZOOKEEPER2");

		LiteflowResponse response = flowExecutor.execute2Resp("chainID1", "workflow-12345",ZKserviceContext);
		System.out.println(response.isSuccess());
		Map<String, CmpStep> executeSteps = response.getExecuteSteps();
		System.out.println("response中提取C组件的异常："+executeSteps.get("c").getException().getMessage());

		// 销毁chain
		FlowBus.removeChain("chainID1");

		LiteflowResponse response2 = flowExecutor.execute2Resp("chainID1", "arg");
		System.out.println(response2.isSuccess());

	}


}
