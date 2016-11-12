package com.github.soap2jms.reader.common;

public enum PropertyTypeEnum {
	BYTE(Byte.class), BYTE_ARRAY(byte[].class), CHAR(Character.class), 
		INT(Integer.class), LONG(Long.class), OBJECT(
			Object.class), STRING(String.class),NULL(Void.class);

	private final Class<?> propType;

	private PropertyTypeEnum(final Class<?> propertyType) {
		this.propType = propertyType;
	}

	public static PropertyTypeEnum fromObject(Object obj) {
		PropertyTypeEnum result = null;
		if(obj == null){
			return NULL;
		}
		for (PropertyTypeEnum value : values()) {
			if (value.propType.equals(obj.getClass())) {
				result = value;
				break;
			}
		}
		if (result == null) {
			throw new IllegalArgumentException("Property of type " + obj.getClass() + "not supported");
		}
		return result;
	}
}
