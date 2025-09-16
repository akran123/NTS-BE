package com.example.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/*
JpaRepository : crud 작업을 처리하는 메소드 내장
타입 : <사용할 엔티지, 기본키값 타입>
 */
public interface UsersRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByName(String name);
    Optional<Users> findByPersonalId(String personalId); // 오류 메세지 던지기 위해 Optional



    @Modifying // 데이터변경 쿼리 의미
    @Query("DELETE FROM Users u WHERE u.name = :name") //db에 적용되는 커스텀 메소드에 사용
    int deleteByName(@Param("name")String name);

    boolean existsByName(String name);
    boolean existsByPersonalId(String personalId);
}
