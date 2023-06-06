package com.practice.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Getter
@NoArgsConstructor// 파라미터가 없는 기본생성자를 만들어줌.
@Entity //테이블과 링크될 클래스임을 나타냄.클래스의 카멜케이스 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭
public class Posts extends BaseTimeEntity{
    @Id // 해당 테이블의 PK필드를 나타냄
    // PK->Primary Key 각 테이블에서 유일한 값.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //PK 생성 규칙
    private Long id;

    @Column(length = 500,nullable = false)
    // 클래스 필드를 칼럼으로 변환, 옵션을 변경하고자 할 때 사용
    private String title;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title,String content,String author){
        this.title=title;
        this.content=content;
        this.author=author;
    }
    public  void update(String title,String content){
        this.title=title;
        this.content=content;
    }
}
