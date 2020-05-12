package com.didispace.hello;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 引入Spring对JUnit的支持，SpringBoot1.4版本之前用SpringJUnit4ClassRunner.class
@RunWith(SpringRunner.class)
// 指定Spring Boot启动类，SpringBoot1.4版本之前用@SpringApplicationConfiguration(classes = Application.class)
@SpringBootTest
// 表示测试环境使用的ApplicationContext是WebApplicationContext类型
@WebAppConfiguration
class HelloApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * MockMvc实例化有两种形式：
     * - 一种是StandaloneMockMvcBuilder
     * - 另一种是DefaultMockMvcBuilder
     */
    @BeforeEach
    public void setUp() {
        // 实例化方式一
//        mockMvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();

        // 实例化方式二
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 1、mockMvc.perform执行一个请求
     * 2、MockMvcRequestBuilders.get("XXX")构造一个请求
     * 3、ResultActions.param添加请求参数
     * 4、ResultActions.accept(MediaType.APPLICATION_JSON)设置返回类型
     * 5、ResultActions.andExpect添加执行完成后的断言
     * 6、ResultActions.andDo添加结果处理器，表示要对结果做点什么事情
     * 比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息
     * 7、ResultActions.andReturn表示执行完成后返回相应的结果
     */
    @Test
    public void hello() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Hello World")))
                .andDo(print());
    }

}
