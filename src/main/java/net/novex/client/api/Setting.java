package net.novex.client.api;

import java.util.function.Consumer;

public class Setting<T> {
    private final String name;
    private final T defaultValue;
    private T value;
    private Consumer<T> changeListener;

    public Setting(String name, T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public void setValue(T value) {
        this.value = value;
        if (changeListener != null) {
            changeListener.accept(value);
        }
    }

    public Setting<T> onChange(Consumer<T> listener) {
        this.changeListener = listener;
        return this;
    }

    public void reset() {
        setValue(defaultValue);
    }
}
