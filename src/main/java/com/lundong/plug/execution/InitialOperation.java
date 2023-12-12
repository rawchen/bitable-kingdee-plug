package com.lundong.plug.execution;

import com.lundong.plug.config.Constants;
import com.lundong.plug.entity.FormTypeDict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Spring Boot启动后自动执行
 *
 * @author RawChen
 * @since 2023-03-07 15:50
 */
@Component
@Order(1)
@Slf4j
public class InitialOperation implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		Constants.formTypeDictList.add(new FormTypeDict().setFormType(1).setFormName("部门单据"));
		Constants.formTypeDictList.add(new FormTypeDict().setFormType(2).setFormName("采购申请单据"));
		Constants.formTypeDictList.add(new FormTypeDict().setFormType(3).setFormName("即时库存"));
		log.info("初始化表单类型列表完成");
	}
}
