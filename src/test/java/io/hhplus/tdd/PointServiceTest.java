package io.hhplus.tdd;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.PointService;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PointServiceTest {
    @Mock
    private UserPointTable userPointTable;

    @Mock
    private PointHistoryTable pointHistoryTable;

    @InjectMocks
    private PointService pointService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // 정상적으로 이력이 입력되는 경우
    @Test
    void 사용자_포인트_변경_이력_입력_성공() {
        // given
        long userId = 1L;
        long amount = 500L;
        TransactionType type = TransactionType.CHARGE; // 예시로 충전 타입 사용
        UserPoint userPoint = new UserPoint(userId, 1000L, System.currentTimeMillis());
        when(userPointTable.selectById(userId)).thenReturn(userPoint);

        // when
        assertDoesNotThrow(() -> pointService.createUserPointHistory(userId, amount, type));

        // then: pointHistoryTable.insert() 메서드가 호출되었는지 검증
        verify(pointHistoryTable).insert(eq(userId), eq(amount), eq(type), anyLong());
    }


}
