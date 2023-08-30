package com.logicea.card.domain;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author infilitry
 *
 */
public enum Status {
	
	TODO("TODO"),
	PROGRESS("TODO"),
	DONE("TODO");
	
	
	private String name;

    private static final Map<String,Status> ENUM_MAP;

    Status (String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    // Build an immutable map of String name to enum pairs.
    // Any Map impl can be used.

    static {
        Map<String,Status> map = new ConcurrentHashMap<String, Status>();
        for (Status instance : Status.values()) {
            map.put(instance.getName().toLowerCase(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static Status get (String name) {
        return ENUM_MAP.get(name);
    }

}
