package com.aek.web.controller.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

import com.mb.common.util.DateUtils;

public class DateEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		if (!StringUtils.hasText(text)) {
			setValue(null);
		} else {
			setValue(DateUtils.string2Date(text, "yyyy-MM-dd HH:mm:ss"));
		}
	}

	@Override
	public String getAsText() {

		return getValue().toString();
	}
}
