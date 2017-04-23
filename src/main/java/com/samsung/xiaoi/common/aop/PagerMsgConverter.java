package com.samsung.xiaoi.common.aop;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

public class PagerMsgConverter extends AbstractHttpMessageConverter<PageList> {

	@Override
	protected boolean supports(Class<?> clazz) {
		return PageList.class.isAssignableFrom(clazz);
	}

	@Override
	protected PageList readInternal(Class<? extends PageList> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void writeInternal(PageList t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		// TODO Auto-generated method stub
		
	}


}
