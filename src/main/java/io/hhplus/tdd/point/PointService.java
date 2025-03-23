package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import org.springframework.stereotype.Service;

@Service
public class PointService {
    private static UserPointTable userPointTable = new UserPointTable();
    private static PointHistoryTable pointHistoryTable = new PointHistoryTable();
    //사용자 포인트 조회
    public void readUserPoint(long userId) {
        var userPoint = userPointTable.selectById(userId);
        System.out.println("포인트: " + userPoint.point());
    }


}