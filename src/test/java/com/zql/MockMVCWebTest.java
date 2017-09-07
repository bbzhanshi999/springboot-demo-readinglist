package com.zql;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 利用mockmvc模拟测试web服务
 * Created by Administrator on 2017/9/6.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReadinglistApplication.class)
@WebAppConfiguration
public class MockMVCWebTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).dispatchOptions(true).build();
    }

    /**
     * 测试读list
     *
     * @throws Exception
     */
    @Test
    public void homePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/readinglist/zql"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("readingList"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("books"))
                .andExpect(MockMvcResultMatchers.model().attribute("books", is(empty())));
    }

    /**
     * 测试添加book
     */
    @Test
    public void addBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/readinglist/zql")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED).param("title", "三国")
                .param("author", "罗贯中")
                .param("isbn", "123")
                .param("description", "some words"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location","/readinglist/zql"));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/readinglist/zql")).andReturn();
        System.out.println(mvcResult.getModelAndView().getModel().get("books"));

    }
}
