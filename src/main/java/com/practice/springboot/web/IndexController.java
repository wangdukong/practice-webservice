package com.practice.springboot.web;

import com.practice.springboot.config.auth.dto.SessionUser;
import com.practice.springboot.service.posts.PostsService;
import com.practice.springboot.web.Dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private final HttpSession httpSession;
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts",postsService.findAllDesc());

        SessionUser user= (SessionUser) httpSession.getAttribute("user");
        //CustomOAuth2UserService에서 로그인 성공시 세션에 sessionUser를 저장하도록 구성
        if(user!=null)
        {
            model.addAttribute("memberName",user.getName());
        }
        return "index";
    }
    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto=postsService.findById(id);
        model.addAttribute("post",dto);
        return "posts-update";
    }
}
