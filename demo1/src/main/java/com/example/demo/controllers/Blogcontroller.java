package com.example.demo.controllers;

import com.example.demo.models.Post;
import com.example.demo.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller

public class Blogcontroller {
    @Autowired
    PostRepository postRepository;



    @GetMapping("/blog")
    public String blog(Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("title","Блог сайта");
        model.addAttribute("posts",posts);
        return "blog-main";
    }
    @GetMapping("/blog/add")
    public String blogadd(Model model){
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title,@RequestParam String anons,@RequestParam String full_text){
       Post post = new Post(title,anons,full_text);
       postRepository.save(post);
        return "blog-add";
    }
    @GetMapping("/blog/{id}")
    public String blogDetaelse(@PathVariable(value = "id") long id, Model model){
        if(!postRepository.existsById(id)){
            return "blog-add";
        }
        Optional<Post> post =  postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        return "blog-details";
    }


}
