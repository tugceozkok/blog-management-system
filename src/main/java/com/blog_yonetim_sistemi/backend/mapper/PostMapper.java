package com.blog_yonetim_sistemi.backend.mapper;

import com.blog_yonetim_sistemi.backend.dto.response.PostResponse;
import com.blog_yonetim_sistemi.backend.entity.Post;
import com.blog_yonetim_sistemi.backend.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(source = "author.username", target = "authorUsername")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(target = "tags", expression = "java(mapTags(post.getTags()))")
    PostResponse toResponse(Post post);

    List<PostResponse> toResponseList(List<Post> posts);

    default List<String> mapTags(List<Tag> tags) {
        return tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
    }
}