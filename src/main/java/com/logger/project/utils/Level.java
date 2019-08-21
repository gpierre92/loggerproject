package com.logger.project.utils;

import java.io.Serializable;

public final class Level implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final String name;
	private final int value;

    public static final Level MESSAGE = new Level("MESSAGE", 1);
	public static final Level WARNING = new Level("WARNING", 2);
	public static final Level ERROR = new Level("ERROR", 3);

	protected Level(final String name, final int value) {
		this.name = name;
		this.value = value;
	}
	
    public final String toString() {
        return this.name;
    }

    public final int intValue() {
        return this.value;
    }
}