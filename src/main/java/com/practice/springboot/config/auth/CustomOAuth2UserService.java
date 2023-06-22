package com.practice.springboot.config.auth;

import com.practice.springboot.config.auth.dto.OAuthAttributes;
import com.practice.springboot.config.auth.dto.SessionUser;
import com.practice.springboot.domain.user.User;
import com.practice.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2UserService<OAuth2UserRequest,OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User =delegate.loadUser(userRequest);

        String registrationId=userRequest.getClientRegistration().getRegistrationId();
        //로그인 진행 중인 서비스를 구분하는 코드
        // 지금은 구글만 사용해서 필요없지만 나중에 네이버나 다른 서비스로 로그인할 때 구분하기 위해 사용

        String userNameAttributeName= //로그인 진행시 키가 되는 필드값 PK와 같은 의미. 나중에 네이버 로그인과 구글 로그인 동시 지원할때 사용

                userRequest.getClientRegistration().
                getProviderDetails().
                getUserInfoEndpoint().
                getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId,userNameAttributeName,oAuth2User.getAttributes());
        // OAuth2UserService를 통해 가져온 OAuth2User의 attribute(특성)를 담을 클래스.
        User user= saveOrUpdate(attributes);

        httpSession.setAttribute("user",new SessionUser(user));
        //SessionUser 세션에 사용자 정보를 저장하기 위한 Dto 클래스.
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),attributes.getNameAttributeKey());
    }
        private User saveOrUpdate(OAuthAttributes attributes){
            User user=userRepository.findByEmail(attributes.getEmail())
                    .map(entity->entity.update(attributes.getName(),attributes.getPicture()))
                    .orElse(attributes.toEntity());
            return userRepository.save(user);

        }


}
