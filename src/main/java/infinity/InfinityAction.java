package infinity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.exec.Blocking;
import ratpack.exec.Execution;
import ratpack.exec.Promise;
import ratpack.func.Action;

import java.util.UUID;

public class InfinityAction implements Action<Execution> {

    private static final Logger LOG = LoggerFactory.getLogger(InfinityAction.class);

    public InfinityAction() {
    }

    @Override
    public void execute(Execution execution) throws Exception {
        poll()
            .result(r -> {
                Throwable error = r.getThrowable();
                LOG.error("Unexpected error", error);
            });
    }

    private Promise<Void> poll() {
        return getFoo()
            .map(f -> {
                LOG.debug("foo={}", f.name);
                return null;
            })
            .flatMap(v -> this.poll());
    }

    private Promise<Foo> getFoo() {
        return Blocking.get(Foo::new);
    }

    private static class Foo {
        String name = UUID.randomUUID().toString();
    }
}