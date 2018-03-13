package com.mauersu.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

public class RValue {
	
	private Object value;
	
	private Double score;
	
	private Object member;
	
	private Object field;
	
	public RValue(Object value) {
		this.value = value;
	}
	public RValue(Double score, Object value) {
		this.score = score;
		this.value = value;
	}
	public RValue(Object field, Object value) {
		this.field = field;
		this.value = value;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public Object getMember() {
		return member;
	}
	public void setMember(Object member) {
		this.member = member;
	}
	public Object getField() {
		return field;
	}
	public void setField(Object field) {
		this.field = field;
	}
	public static Object creatListValue(List<Object> values) {
		List<RValue> rValues = new ArrayList<RValue>();
		for(Object v: values) {
			rValues.add(new RValue(v));
		}
		return rValues;
	}
	public static Object creatSetValue(Set<Object> values) {
		Set<RValue> rValues = new HashSet<RValue>();
		for(Object v:values) {
			rValues.add(new RValue(v));
		}
		return rValues;
	}
	public static Object creatZSetValue(Set<TypedTuple<Object>> values) {
		List<RValue> rValues = new ArrayList<RValue>();
		for(TypedTuple<Object> v:values) {
			rValues.add(new RValue(v.getScore(), v.getValue()));
		}
		return rValues;
	}
	public static Object creatHashValue(Map<Object, Object> values) {
		List<RValue> rValues = new ArrayList<RValue>();
		for(Object f:values.keySet()) {
			rValues.add(new RValue(f, values.get(f)));
		}
		return rValues;
	}
}