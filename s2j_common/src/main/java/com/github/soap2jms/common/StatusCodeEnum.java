package com.github.soap2jms.common;

public enum StatusCodeEnum {
	/**
	 *
	 */
	ERR_GENERIC(false, true, true, 100),
	/**
	 *
	 */
	ERR_INCOMPATIBLE_PROTOCOL(false, true, true, 101),
	/**
	 *
	 */
	ERR_JMS(false, true, true, 102),
	/**
	 *
	 */
	ERR_SERIALIZATION(false, true, true, 103),
	/**
	 *
	 */
	ERR_NETWORK(false, true, false, 104),
	/**
	 *
	 */
	OK(false, false, true, 0),
	/**
	 *
	 */
	WARN_MSG_NOT_FOUND(true, false, true, 10);

	private final boolean error;
	private final int errorCode;
	private final boolean permanent;
	private final boolean warining;

	private StatusCodeEnum(final boolean warining, final boolean error, final boolean permanent, final int errorCode) {
		this.error = error;
		this.warining = warining;
		this.errorCode = errorCode;
		this.permanent = permanent;
	}

	public int getErrorCode() {
		return this.errorCode;
	}

	public boolean isError() {
		return this.error;
	}

	public boolean isPermanent() {
		return this.permanent;
	}

	public boolean isWarining() {
		return this.warining;
	}

}
