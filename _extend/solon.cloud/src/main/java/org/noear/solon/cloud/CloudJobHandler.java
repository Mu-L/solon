package org.noear.solon.cloud;

/**
 * @author noear
 * @since 1.3
 */
public interface CloudJobHandler {
    boolean handler() throws Throwable;
}
