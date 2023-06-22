package com.practice.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts,Long> { //JpaRepository<entity 클래스, PK타입>
    @Query("SELECT p FROM Posts p ORDER By p.id DESC")
    List<Posts> findAllDesc();
}
