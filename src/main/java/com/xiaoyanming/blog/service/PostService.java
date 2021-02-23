package com.xiaoyanming.blog.service;

import com.xiaoyanming.blog.form.PostForm;
import com.xiaoyanming.blog.model.Post;
import com.xiaoyanming.blog.vo.PostVo;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.util.List;

public interface PostService {

    List<PostVo> getPostsPage(int curPage, int pageSize);

    List<Post> findAll();

    String addNewPost(@Valid PostForm postForm);

    String confirmDeletePostById(Long id, Model model);

    String deletePostById(@PathVariable("id") Long id);

    String findPostById(@PathVariable("id") Long id, Model model);

    String validateUserEditPermissions(@Valid Post post, BindingResult bindingResult);

    String showPostById(@PathVariable("id") Long id, Model model);
}
