package com.liumapp.qtools.property.core;

import com.google.common.base.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * file MapConfigValue.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * A {@link ConfigValue} which holds a map of values.
 */
class MapConfigValue extends ConfigValue {
    volatile ConcurrentMap<Object, SimpleConfigurationNode> values;

    public MapConfigValue(SimpleConfigurationNode holder) {
        super(holder);
        values = newMap();
    }

    @Override
    ValueType getType() {
        return ValueType.MAP;
    }

    private ConcurrentMap<Object, SimpleConfigurationNode> newMap() {
        return holder.getOptions().getMapFactory().create();
    }

    @Nullable
    @Override
    public Object getValue() {
        Map<Object, Object> value = new LinkedHashMap<>();
        for (Map.Entry<Object, ? extends SimpleConfigurationNode> ent : values.entrySet()) {
            value.put(ent.getKey(), ent.getValue().getValue()); // unwrap key from the backing node
        }
        return value;
    }

    @Override
    public void setValue(@Nullable Object value) {
        if (value instanceof Map) {
            final ConcurrentMap<Object, SimpleConfigurationNode> newValue = newMap();
            for (Map.Entry<?, ?> ent : ((Map<?, ?>) value).entrySet()) {
                if (ent.getValue() == null) {
                    continue;
                }
                SimpleConfigurationNode child = holder.createNode(ent.getKey());
                newValue.put(ent.getKey(), child);
                child.attached = true;
                child.setValue(ent.getValue());
            }
            synchronized (this) {
                ConcurrentMap<Object, SimpleConfigurationNode> oldMap = this.values;
                this.values = newValue;
                detachChildren(oldMap);
            }
        } else {
            throw new IllegalArgumentException("Map configuration values can only be set to values of type Map");
        }
    }

    @Nullable
    @Override
    SimpleConfigurationNode putChild(@NonNull Object key, @Nullable SimpleConfigurationNode value) {
        if (value == null) {
            return values.remove(key);
        } else {
            return values.put(key, value);
        }
    }

    @Nullable
    @Override
    SimpleConfigurationNode putChildIfAbsent(@NonNull Object key, @Nullable SimpleConfigurationNode value) {
        if (value == null) {
            return values.remove(key);
        } else {
            return values.putIfAbsent(key, value);
        }
    }

    @Nullable
    @Override
    public SimpleConfigurationNode getChild(@Nullable Object key) {
        return values.get(key);
    }

    @NonNull
    @Override
    public Iterable<SimpleConfigurationNode> iterateChildren() {
        return values.values();
    }

    @NonNull
    @Override
    MapConfigValue copy(@NonNull SimpleConfigurationNode holder) {
        MapConfigValue copy = new MapConfigValue(holder);
        for (Map.Entry<Object, ? extends SimpleConfigurationNode> ent : this.values.entrySet()) {
            copy.values.put(ent.getKey(), ent.getValue().copy(holder)); // recursively copy
        }
        return copy;
    }

    private static void detachChildren(Map<Object, SimpleConfigurationNode> map) {
        for (SimpleConfigurationNode value : map.values()) {
            value.attached = false;
            value.clear();
        }
    }

    @Override
    public void clear() {
        synchronized (this) {
            ConcurrentMap<Object, SimpleConfigurationNode> oldMap = this.values;
            this.values = newMap();
            detachChildren(oldMap);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MapConfigValue that = (MapConfigValue) o;
        return Objects.equal(values, that.values);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

    @Override
    public String toString() {
        return "MapConfigValue{values=" + this.values + '}';
    }
}
