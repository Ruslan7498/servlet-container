package service;

import model.Post;
import repository.PostRepository;

import java.io.IOException;
import java.util.List;

public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all();
    }

    public Post getById(long id) {
        return repository.getById(id);
    }

    public Post save(Post post) throws IOException {
        return repository.save(post);
    }

    public void removeById(long id) {
        repository.removeById(id);
    }
}

