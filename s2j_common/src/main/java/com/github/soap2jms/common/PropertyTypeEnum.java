package com.github.soap2jms.common;

public enum PropertyTypeEnum {
	BYTE(Byte.class), BYTE_ARRAY(byte[].class), CHAR(Character.class), INT(Integer.class), LONG(Long.class), NULL(
			Void.class), OBJECT(Object.class), STRING(String.class);

	public static PropertyTypeEnum fromObject(final Object obj) {
		PropertyTypeEnum result = null;
		if (obj == null) {
			return NULL;
		}
		for (final PropertyTypeEnum value : values()) {
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

	private final Class<?> propType;

	private PropertyTypeEnum(final Class<?> propertyType) {
		this.propType = propertyType;
	}
}
