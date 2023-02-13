package shop.mtcoding.blog1.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blog1.dto.board.BoardReq.BoardUpdateRespDto;
import shop.mtcoding.blog1.dto.board.BoardResp;
import shop.mtcoding.blog1.model.User;

@Transactional // 메서드 실행 직후에 롤백!
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class BoardControllerTest {

    @Autowired
    private MockMvc mvc;

    private MockHttpSession mockSession;

    @Autowired
    private ObjectMapper om;

    // @BeforeAll
    // public static void 테이블차리기() {
    // // 테이블 차리기
    // }

    // @AfterAll
    // public void teardown() {
    // // 데이터 지우고, 다시 인서트
    // }

    @BeforeEach // Test 메서드 실행 직전 마다에 호출됨.
    public void setUp() {
        // 데이터 인서트
        User user = new User();
        user.setId(1);
        user.setUsername("ssar");
        user.setPassword("1234");
        user.setEmail("ssar@nate.com");
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        mockSession = new MockHttpSession();
        mockSession.setAttribute("principal", user);
    }

    @Test
    public void update_test() throws Exception {
        // given
        int id = 1;
        BoardUpdateRespDto boardUpdateRespDto = new BoardUpdateRespDto();
        boardUpdateRespDto.setTitle("제목1-수정");
        boardUpdateRespDto.setContent("내용1-수정");

        String requestBody = om.writeValueAsString(boardUpdateRespDto);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                put("/board/1/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .session(mockSession));

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.code").value(1));
    }

    @Test
    public void delete_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(delete("/board/" + id).session(mockSession));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(jsonPath("$.code").value(1));
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void detail_test() throws Exception {
        // given
        int id = 1;
        // when
        ResultActions resultActions = mvc.perform(
                get("/board/" + id));
        Map<String, Object> map = resultActions.andReturn().getModelAndView().getModel();
        BoardResp.BoardDetailRespDto dto = (BoardResp.BoardDetailRespDto) map.get("dto");
        String model = om.writeValueAsString(dto);
        System.out.println("테스트 : size : " + model);

        // then
        resultActions.andExpectAll(status().isOk());
        Assertions.assertThat(status().isOk());
        assertThat(dto.getUsername()).isEqualTo("ssar");
        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getTitle()).isEqualTo("첫 번째 글입니다.");

    }

    @Test
    public void main_test() throws Exception {
        // given
        // when
        ResultActions resultActions = mvc.perform(
                get("/"));
        Map<String, Object> map = resultActions.andReturn().getModelAndView().getModel();
        List<BoardResp.BoardMainRespDto> dtos = (List<BoardResp.BoardMainRespDto>) map.get("dtos");
        System.out.println("테스트 : size : " + dtos.size());
        String model = om.writeValueAsString(dtos);
        System.out.println("테스트 : size : " + model);

        // then
        resultActions.andExpectAll(status().isOk());
        Assertions.assertThat(status().isOk());
        assertThat(dtos.size()).isEqualTo(6);
        assertThat(dtos.get(0).getUsername()).isEqualTo("ssar");
        assertThat(dtos.get(0).getTitle()).isEqualTo("첫 번째 글입니다.");

    }

    @Test
    public void save_test() throws Exception {
        // given
        setUp();
        String title = "";
        for (int i = 0; i < 200; i++) {
            title += "가";
        }

        String requestBody = "title=" + title + "&content=내용1";

        // when
        ResultActions resultActions = mvc.perform(
                post("/board")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .session(mockSession));

        // then
        resultActions.andExpect(status().is4xxClientError());
    }
}