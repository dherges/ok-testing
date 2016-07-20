/*
 * ok-testing
 * https://github.com/dherges/ok-testing
 *
 * Copyright (c) 2016 David Herges
 * Licensed under the MIT license.
 */
package ext.spark;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import spark.ResponseTransformer;


/**
 * A response transformer for a spark application that serializes a Java type to its JSON representation.
 *
 * @param <T> Type that is transformed to JSON
 */
public class MoshiResponseTransformer<T> implements ResponseTransformer {

    private final JsonAdapter<T> jsonAdapter;
    private MoshiResponseTransformer(JsonAdapter<T>  jsonAdapter) {
        this.jsonAdapter = jsonAdapter;
    }

    @Override
    public String render(Object model) throws Exception {
        // A JsonAdapter does the real Type-to-JSON serialization magic
        return jsonAdapter.toJson((T) model);
    }


    public static ResponseTransformer create(Class<?> convertibleClz) {
        return new MoshiResponseTransformer<>(
            new Moshi.Builder()
                .build()
                .adapter(convertibleClz)
        );
    }
}
