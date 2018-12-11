package com.databootcamp.dataprovider.models;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dataprovider")
public class ModelKeyValue {
    @Id
    @Size(min = 1, max = 64, message = "Key shall have a size less than 64 characters and not empty")
    @NotNull(message = "Key cannot be null")
    private String key;

    private String value;

    public ModelKeyValue() {
        super();
    }

    public ModelKeyValue(final String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("ModelKeyValue[key=%s, value='%s']", key, value);
    }
}
