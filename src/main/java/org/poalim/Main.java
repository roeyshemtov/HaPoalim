package org.poalim;

import io.vertx.core.Vertx;
import org.poalim.html.HtmlRoute;

public class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new HtmlRoute());
    }
}
