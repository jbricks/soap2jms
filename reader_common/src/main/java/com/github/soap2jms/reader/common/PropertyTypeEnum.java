package com.github.soap2jms.reader.common;

public enum PropertyTypeEnum {
	BYTE(Byte.class), BYTE_ARRAY(byte[].class), CHAR(Character.class), INT(Integer.class), LONG(Long.class), OBJECT(
			Object.class), STRING(String.class);

	private final Class<?> propType;

	private PropertyTypeEnum(final Class<?> propertyType) {
		this.propType = propertyType;
	}

	public static PropertyTypeEnum fromClass(Class<?> clazz) {
		PropertyTypeEnum result = null;
		for (PropertyTypeEnum value : values()) {
			if (value.propType.equals(clazz)) {
				result = value;
				break;
			}
		}
		if (result == null) {
			throw new IllegalArgumentException("Property of type " + clazz + "not supported");
		}
		return result;
	}
}
