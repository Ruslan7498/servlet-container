package repository;

import model.Post;

import java.io.IOException;
import java.util.*;

// Stub
public class PostRepository {
    private final Map<Long, Post> mapPosts = new HashMap<>();
    private long id = 1;

    public List<Post> all() {
        return new ArrayList<>(mapPosts.values());
    }

    public Post getById(long id) {
        return mapPosts.get(id);
    }

    public synchronized Post save(Post post) throws IOException {
        if (post.getId() == 0) {
            Post newPost = new Post(id, post.getContent());
            mapPosts.put(id, newPost);
            id++;
            return newPost;
        }
        if (mapPosts.containsKey(post.getId())) {
            Post replacePost = new Post(post.getId(), post.getContent());
            mapPosts.put(post.getId(), replacePost);
            return replacePost;
        } else {
            throw new IOException("Error id");
        }
    }

    public synchronized void removeById(long id) {
        mapPosts.remove(id);
    }
}