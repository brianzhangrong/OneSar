/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.ihomefnt.module.common.api;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 */
public class ToStringObject implements Serializable {

  /**
   * 默认的ToStringStyle
   */
  public static final transient ToStringStyle DEFAULT_TO_STRING_STYLE = new DefaultToStringStyle();

  @Override
  public boolean equals(Object that) {
    return EqualsBuilder.reflectionEquals(this, that);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, DEFAULT_TO_STRING_STYLE);
  }

  /**
   * 默认的ToStringStyle
   */
  public static class DefaultToStringStyle extends ToStringStyle {

    private static final long serialVersionUID = 1L;

    public DefaultToStringStyle() {
      setUseShortClassName(true);
      setUseIdentityHashCode(false);
    }

    @Override
    public void append(StringBuffer buffer, String fieldName, Object value, Boolean fullDetail) {
      if (value != null) {
        super.append(buffer, fieldName, value, fullDetail);
      }
    }

    @Override
    public void append(StringBuffer buffer, String fieldName, Object[] array, Boolean fullDetail) {
      if (array != null && array.length > 0) {
        super.append(buffer, fieldName, array, fullDetail);
      }
    }
  }
}