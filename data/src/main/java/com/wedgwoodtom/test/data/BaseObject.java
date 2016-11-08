package com.wedgwoodtom.test.data;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public class BaseObject
{

    @Id
    private ObjectId id;

    // OptimisticLockingFailureException is thrown if the versions do not match on an update (http://docs.spring.io/spring-data/mongodb/docs/current/reference/html/)
    @Version
    private Integer version;

    public ObjectId getId()
    {
        return id;
    }

    public void setId(ObjectId id)
    {
        this.id = id;
    }

    public String idAsString()
    {
        return getId().toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof BaseObject)) return false;

        BaseObject that = (BaseObject) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode()
    {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString()
    {
        StandardToStringStyle style = new StandardToStringStyle();
        style.setFieldSeparator(", ");
        style.setUseClassName(false);
        style.setUseIdentityHashCode(false);

        return new ReflectionToStringBuilder(this, style).toString();
    }
}
