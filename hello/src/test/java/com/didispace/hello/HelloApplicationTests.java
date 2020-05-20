package com.didispace.hello;

import com.didispace.hello.web.HelloController;
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

/**
 * 使用 Spring 的 Test 框架，通过 @RunWith 指定 Runner 运行器，让测试用例运行于 Spring 测试环境。
 */
@RunWith(SpringRunner.class)
/**
 * 通过 @SpringBootTest 指定 SpringBoot 启动类。
 * SpringBoot 1.4 版本之前使用 @SpringApplicationConfiguration。
 */
@SpringBootTest(classes = HelloApplication.class)
/**
 * 通过 @WebAppConfiguration 执行单元测试时启动一个 Web 服务，单元测试结束后关闭 Web 服务。
 *
 * @WebAppConfiguration 表明该类将 Web 应用程序默认根目录加载到 ApplicationContext。
 * 默认根目录为 "src/main/webapp"，若需更改根目录，则修改 @WebAppConfiguration 的 value 值。
 */
@WebAppConfiguration
class HelloApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * MockMvc 实例化有两种形式：
     * - StandaloneMockMvcBuilder
     * - DefaultMockMvcBuilder
     * <p>
     * MockMvcBuilder 用来构造 MockMvc 的构造器。
     * 主要有两个实现：StandaloneMockMvcBuilder 和 DefaultMockMvcBuilder。
     * StandaloneMockMvcBuilder 继承了 DefaultMockMvcBuilder，直接使用静态工厂 MockMvcBuilders 创建。
     * <p>
     * MockMvcBuilders.webAppContextSetup(WebApplicationContext context)：
     * 指定 WebApplicationContext，从上下文获取控制器得到相应的 MockMvc。
     * <p>
     * MockMvcBuilders.standaloneSetup(Object... controllers)：
     * 通过参数指定控制器。
     */
    @BeforeEach
    public void setUp() {
        // 实例化方式一
        mockMvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();

        // 实例化方式二
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 1、mockMvc.perform 执行请求
     * 2、MockMvcRequestBuilders.get("XXX") 构造请求
     * 3、ResultActions.param 添加请求参数
     * 4、ResultActions.accept(MediaType.APPLICATION_JSON) 设置返回的数据类型
     * 5、ResultActions.andExpect 判断接口返回的期望值
     * 6、ResultActions.andDo 添加结果处理器，表示对结果的处理
     * 7、ResultActions.andReturn 表示执行完成后返回相应的结果
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
