package com.example.server.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
        @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.id = :id")
        boolean existsById(@Param("id") Long id);

        @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.id = :id AND u.randomId = :randomId")
        boolean existsByIdAndRandomId(@Param("id") Long id, @Param("randomId") String randomId);

        @Modifying
        @Transactional
        @Query(value = "INSERT INTO user_tb (id, pet_name, pet_weight, random_id, tooth_seq, tooth_date_renew) VALUES (:#{#user.id}, :#{#user.petName}, :#{#user.petWeight}, :#{#user.randomId}, :#{#user.toothSeq}, :#{#user.toothDateRenew})", nativeQuery = true)
        void insertUserInformation(@Param("user") User user);
        
        @Query("SELECT u FROM User u WHERE u.id = :id")
        User getUserInformationById(@Param("id") Long id);
        
        @Modifying
        @Transactional
        @Query(value = "UPDATE user_tb u SET u.pet_name=:petName, u.pet_weight=:petWeight WHERE u.id=:id", nativeQuery = true)
        void setUserPetInformationById(@Param("id") Long id, @Param("petName") String petName, @Param("petWeight") Integer petWeight);

        @Modifying
        @Transactional
        @Query(value = "UPDATE user_tb u SET u.random_id=:randomId, u.random_id=:randomId WHERE u.id = :id", nativeQuery = true)
        void updateRandomIdByUserId(@Param("id") Long id, @Param("randomId") String randomId);

        @Modifying
        @Transactional
        @Query(value = "UPDATE user_tb u SET u.tooth_seq=:toothSeq, u.tooth_date_renew=:toothDateRenew WHERE u.id = :id", nativeQuery = true)
        void updateSeqAndDateByUserId(@Param("id") Long id, @Param("toothSeq") int toothSeq, @Param("toothDateRenew") LocalDate toothDateRenew);
}