package servlet;

import controller.PostController;
import repository.PostRepository;
import service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    private PostController controller;
    private final String GET = "GET";
    private final String POST = "POST";
    private final String DELETE = "DELETE";
    private final String DELIMITER = "/";
    private final String API_POSTS = "/api/posts";
    private final String API_POSTS_D = "/api/posts/\\d+";

    @Override
    public void init() {
        final PostRepository repository = new PostRepository();
        final PostService service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        //resp.getWriter().print("Ok");
        //resp.setStatus(HttpServletResponse.SC_OK);
        // если деплоились в root context, то достаточно этого
        try {
            final String path = req.getRequestURI();
            final String method = req.getMethod();
            // primitive routing
            if (method.equals(GET)) {
                methodGet(resp, path);
                return;
            }
            if (method.equals(POST)) {
                methodPost(req, resp, path);
                return;
            }
            if (method.equals(DELETE)) {
                methodDelete(resp, path);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public void methodGet(HttpServletResponse resp, String path) throws IOException {
        if (path.equals(API_POSTS)) {
            controller.all(resp);
        }
        if (path.matches(API_POSTS_D)) {
            long id = Long.parseLong(path.substring(path.lastIndexOf(DELIMITER) + 1));
            controller.getById(id, resp);
        }
    }

    public void methodPost(HttpServletRequest req, HttpServletResponse resp, String path) throws IOException {
        if (path.equals(API_POSTS)) {
            controller.save(req.getReader(), resp);
        }
    }

    public void methodDelete(HttpServletResponse resp, String path) throws IOException {
        if (path.matches(API_POSTS_D)) {
            final long id = Long.parseLong(path.substring(path.lastIndexOf(DELIMITER) + 1));
            controller.removeById(id, resp);
        }
    }
}