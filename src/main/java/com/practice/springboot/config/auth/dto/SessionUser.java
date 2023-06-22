    package com.practice.springboot.config.auth.dto;

    import com.practice.springboot.domain.user.User;
    import lombok.Getter;

    import java.io.Serializable;

    @Getter
    public class SessionUser implements Serializable {
        private String name;
        private String email;
        private String picture;
        // 세션에는 인증된 사용자 정보만 필요하다. 따라서 3가지 필드만 선언
        public SessionUser(User user){
            this.name=user.getName();
            this.email=user.getEmail();
            this.picture=user.getPicture();
        }
    }
