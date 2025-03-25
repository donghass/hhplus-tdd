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
    public UserPoint readUserPoint(long id) {
        return userPointTable.selectById(id);
    }
    //사용자 포인트 충전
    public synchronized void updateUserChargePoint(long id, long amount) {
        var userPoint = userPointTable.selectById(id);
        if (userPoint != null) {
            long newPoints = userPoint.point() + amount;  // 기존 포인트에 충전 금액 추가
            if (newPoints > 45000) {
                throw new IllegalArgumentException("충전 금액이 한도를 초과했습니다.");
            }
            userPointTable.insertOrUpdate(id, newPoints);  // 새로운 포인트 값 저장
        } else {
            userPointTable.insertOrUpdate(id, amount);  // 새로운 사용자에 대해 포인트 설정
        }
    }
    //사용자 포인트 변경 이력 입력
    public void createUserPointHistory(long id, long amount,TransactionType type) {
        var userPoint = userPointTable.selectById(id);
        if(userPoint == null){
            throw new IllegalArgumentException("없는 사용자입니다.");
        }
        pointHistoryTable.insert(id,amount,type,System.currentTimeMillis());
    }
    //사용자 포인트 사용
    public synchronized void updateUserUsePoint(long id, long amount) {
        var userPoint = userPointTable.selectById(id);
        if (userPoint != null) {
            long newPoint = userPoint.point() - amount;  // 기존 포인트에 충전 금액 추가
            if (newPoint < 0) {
                throw new IllegalArgumentException("잔액이 부족합니다.");
            }
            userPointTable.insertOrUpdate(id, newPoint);  // 새로운 포인트 값 저장
        } else {
            throw new IllegalArgumentException("없는 사용자입니다.");
        }

    }
    //사용자 포인트 변경 이력 조회
    public List<PointHistory> readUserPointHistory(long id) {
        var userPoint = userPointTable.selectById(id);
        if(userPoint == null){
            throw new IllegalArgumentException("없는 사용자입니다.");
        }
        var userPointHistory = pointHistoryTable.selectAllByUserId(id);
        return userPointHistory;
    }
}