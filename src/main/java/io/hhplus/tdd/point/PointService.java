package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import java.util.List;
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
    //사용자 포인트 충전
    public void updateUserChargePoint(long userId, long amount) {
        var userPoint = userPointTable.selectById(userId);
        System.out.println("차지 포인트: " + userPoint.point());
    }
    //사용자 포인트 변경 이력
    public void createUserPointHistory(long userId, long amount,TransactionType type) {
        var userPoint = userPointTable.selectById(userId);
        System.out.println("포인트 이력: " + userPoint.point());
    }
    //사용자 포인트 사용
    public void updateUserUsePoint(long userId, long amount) {
        var userPoint = userPointTable.selectById(userId);
        System.out.println("차지 포인트: " + userPoint.point());
    }
    //사용자 포인트 변경 이력 조회
    public List<PointHistory> readUserPointHistory(long userId) {

        return pointHistoryTable.selectAllByUserId(userId);
    }
}