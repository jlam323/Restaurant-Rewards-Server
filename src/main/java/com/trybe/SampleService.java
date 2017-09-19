/**
 *  This sample service is an object with different values that will be put into a JSON.
 */

package com.trybe;

public class SampleService {

    private final long id;
    private final String content;

    public SampleService(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
