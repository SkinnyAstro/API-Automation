package core;

import io.restassured.filter.Filter;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Remembers 4xx/5xx responses per test thread. FailedResponseLogListener prints them only when the
 * test fails, so negative tests that expect an error stay quiet on passing runs.
 */
public final class FailedResponseLog {

    private static final int MAX_BODY_CHARS = 2000;
    private static final ThreadLocal<List<String>> ENTRIES = ThreadLocal.withInitial(ArrayList::new);

    private FailedResponseLog() {
    }

    // Records method, path (no query string: it carries ids), status and body. Headers are never kept
    // because they expose internal hostnames and the repo is public.
    static Filter recordingFilter() {
        return (request, responseSpec, ctx) -> {
            Response response = ctx.next(request, responseSpec);
            if (response.statusCode() >= 400) {
                String uri = request.getURI();
                int query = uri.indexOf('?');
                String path = query >= 0 ? uri.substring(0, query) : uri;
                String body = response.asString();
                if (body.length() > MAX_BODY_CHARS) {
                    body = body.substring(0, MAX_BODY_CHARS) + "...(truncated)";
                }
                ENTRIES.get().add(request.getMethod() + " " + path + " -> " + response.statusCode() + "\n" + body);
            }
            return response;
        };
    }

    static void clear() {
        ENTRIES.get().clear();
    }

    static List<String> drain() {
        List<String> copy = new ArrayList<>(ENTRIES.get());
        ENTRIES.get().clear();
        return copy;
    }
}
