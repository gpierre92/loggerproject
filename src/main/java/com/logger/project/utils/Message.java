package com.logger.project.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Message {

	private Level level;
	private String message;

	public boolean isValid() {
		return (this.level != null) && (this.message != null) && (!this.message.isEmpty());
	}
}