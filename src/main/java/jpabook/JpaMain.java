package jpabook;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpabook.domain.Member;
import jpabook.domain.Order;
import jpabook.domain.OrderItem;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin(); // 트랜잭션 시작

        try {

            Order order = new Order();
//            order.addOrderList(new OrderItem());
            em.persist(order);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);

            em.persist(orderItem); // 단방향 연관관계라도 application에서 다 개발 가능함.
        } catch (Exception e) {
            // 트랜잭션 롤백 - 모든 변경사항 취소
            tx.rollback();
        } finally {
            // 5. EntityManager 닫기 - 리소스 해제 (필수!)
            // finally 블록에서 반드시 닫아야 함
            em.close();
        }

        // 6. EntityManagerFactory 닫기 - 애플리케이션 종료 시점에 닫기
        // 내부적으로 커넥션 풀 등의 리소스 해제
        emf.close();
    }
}
