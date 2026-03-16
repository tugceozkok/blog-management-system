package com.blog_yonetim_sistemi.backend.mapper;

import com.blog_yonetim_sistemi.backend.dto.response.PostResponse;
import com.blog_yonetim_sistemi.backend.entity.Post;
import com.blog_yonetim_sistemi.backend.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(source = "author.username", target = "authorUsername")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(target = "tags", expression = "java(mapTags(post.getTags()))")
    @Mapping(target = "likeCount", expression = "java(post.getLikedByUsers() != null ? post.getLikedByUsers().size() : 0)")
    @Mapping(target = "commentCount", expression = "java(post.getComments() != null ? post.getComments().size() : 0)")
    PostResponse toResponse(Post post);

    List<PostResponse> toResponseList(List<Post> posts);

    // Entity'de tags alanını Set yaptığımız için parametreyi Set<Tag> olarak değiştirdik
    default List<String> mapTags(Set<Tag> tags) {
        if (tags == null) {
            return List.of();
        }
        return tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
    }
}