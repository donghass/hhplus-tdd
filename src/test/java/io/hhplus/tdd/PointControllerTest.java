package io.hhplus.tdd;

import static org.mockito.Mockito.when;

import io.hhplus.tdd.point.PointController;
import io.hhplus.tdd.point.PointService;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PointController.class)
public class PointControllerTest {

    //실제 서버를 띄우지 않고 가짜 HTTP 요청을 보내고 테스트할 수 있는 용도
    @Autowired
    private MockMvc mockMvc;

    // pointService가 가짜 객체로 대체
    @MockBean
    private PointService pointService;

    @Test
    void 포인트조회() throws Exception {

        // given 객체 생성
        long userId = 1L;
        long point = 1500L;
        UserPoint mockUserPoint = new UserPoint(userId, point, System.currentTimeMillis());

        //when 이 호출되면 thenReturn 반환 지시
        Mockito.when(pointService.readUserPoint(1L))
            .thenReturn(new UserPoint(1L, 1500, System.currentTimeMillis()));

        // when & then 응답 값이 일치하는지 확인
        mockMvc.perform(get("/point/{id}", userId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(userId))
            .andExpect(jsonPath("$.point").value(point));
    }

    @Test
    void 포인트충전() throws Exception {
        // given 유저1 500 추가하여 2000 된다
        long userId = 1L;
        long amount = 50000L;
        long updatedPoint = 50000L;
        UserPoint mockUserPoint = new UserPoint(userId, updatedPoint, System.currentTimeMillis());

        // mocking 서비스 동작 500 포인트 충전, 히스토리 추가, 충전 후 포인트 조회 / doNothing는 void이기 때문에 반환 없이 호출만
        Mockito.doNothing().when(pointService).updateUserChargePoint(userId, amount);
        Mockito.doNothing().when(pointService)
            .createUserPointHistory(userId, amount, TransactionType.CHARGE);
        Mockito.when(pointService.readUserPoint(userId)).thenReturn(mockUserPoint);

        // when & then 유저1이 500포인트 추가 후 2000포인트 반환되면 성공
        mockMvc.perform(patch("/point/{id}/charge", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(amount)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(userId))
            .andExpect(jsonPath("$.point").value(updatedPoint));
    }

    @Test
    void 포인트사용() throws Exception {
        // given 유저1 500 사용하여 1000 된다
        long userId = 1L;
        long amount = 5000L;
        long updatedPoint = 1000L;
        UserPoint mockUserPoint = new UserPoint(userId, updatedPoint, System.currentTimeMillis());

        // mocking 서비스 동작 500 포인트 충전, 히스토리 추가, 충전 후 포인트 조회 / doNothing는 void이기 때문에 반환 없이 호출만
        Mockito.doNothing().when(pointService).updateUserUsePoint(userId, amount);
        Mockito.doNothing().when(pointService)
            .createUserPointHistory(userId, amount, TransactionType.USE);
        Mockito.when(pointService.readUserPoint(userId)).thenReturn(mockUserPoint);

        // when & then 유저1이 500포인트 추가 후 2000포인트 반환되면 성공
        mockMvc.perform(patch("/point/{id}/use", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(amount)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(userId))
            .andExpect(jsonPath("$.point").value(updatedPoint));
    }
}
