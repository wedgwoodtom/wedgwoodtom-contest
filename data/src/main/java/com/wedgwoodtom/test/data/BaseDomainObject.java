package com.wedgwoodtom.test.data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class BaseDomainObject
{
    @Id
    private ObjectId id;

    public ObjectId getId()
    {
        return id;
    }

    public void setId(ObjectId id)
    {
        this.id = id;
    }

}
