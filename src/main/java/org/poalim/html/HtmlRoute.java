package org.poalim.html;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.netty.handler.codec.http.HttpResponse;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.poalim.model.Tag;

public class HtmlRoute extends AbstractVerticle {
    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route(HttpMethod.POST, "/html").handler(rc -> {
            String html = rc.getBodyAsJson().getString("html");
            Tag tag = new HtmlParser().getHtmlValidTags(html);
            rc.response().headers().set("Content-Type", "application/json; charset=UTF-8");
            try {
                ObjectMapper objectMapper = new ObjectMapper();
//                objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
                rc.response().end(objectMapper.writeValueAsString(tag));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        Vertx vertx = Vertx.vertx();
        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(9900);

    }


}
