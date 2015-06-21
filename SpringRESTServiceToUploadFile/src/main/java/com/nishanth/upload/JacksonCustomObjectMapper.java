package com.nishanth.upload;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

public class JacksonCustomObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = -7237650376307753723L;

	public JacksonCustomObjectMapper() {
		setOptions();
	}

	@SuppressWarnings("deprecation")
	private void setOptions() {
		AnnotationIntrospector primary = new JaxbAnnotationIntrospector();
		JacksonAnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
		AnnotationIntrospector pair = AnnotationIntrospector.pair(primary, secondary);
		this.getDeserializationConfig().withAppendedAnnotationIntrospector(pair);
		this.getSerializationConfig().withAppendedAnnotationIntrospector(pair);
		this.configure(SerializationFeature.WRAP_ROOT_VALUE, true).               
		configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);

		this.setSerializationInclusion(Include.NON_NULL);
	}
}