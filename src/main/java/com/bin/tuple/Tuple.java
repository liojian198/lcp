package com.bin.tuple;

import com.sun.nio.sctp.IllegalUnbindException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tuple {
    private Map<String, Object> map;
    private String[] keyArr;
    private Pattern pattern;

    public Tuple(String...names) {
        if (names != null) {
            keyArr = names;
            map = new HashMap<>();
        }
    }

    /**
     * 根据给定的key创建元组
     * @param names
     * @return
     */
    public static Tuple def(String...names) {
        return new Tuple(names);
    }

    /**
     * 引用匹配模式
     * @param ptn 匹配模式
     * @return
     */
    public Tuple use(Pattern pattern) {
        this.pattern = pattern;
        return this;
    }

    /**
     * 根据匹配模式进行匹配，保存匹配结果，返回自身
     * @param source 目标字符串
     * @param containFullText 返回原则是否包含匹配全文
     * @return
     */
    public Tuple match(String source, boolean containFullText) {
        if (pattern == null) {
            throw new IllegalUnbindException("Pattern cannot be null!");
        }
        if (map != null) {
            this.map.clear();
        }
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            int groupCount = matcher.groupCount();
            Object[] valArr = null;
            if (containFullText) {
                groupCount ++;
            }
            if (this.keyArr.length != groupCount)
                throw new IllegalArgumentException("Tuple's key count doesn't match value count!");
            valArr = new Object[groupCount];
            for (int i = 0; i < groupCount; ++i) {
                valArr[i] = matcher.group(containFullText ? i : i+1);
            }
            this.val(valArr);
        }
        return this;
    }

    /**
     * 根据匹配模式进行匹配，保存匹配结果，返回自身, 默认不返回匹配全文
     * @param source
     * @return
     */
    public Tuple match(String source) {
        return this.match(source, false); // 默认不返回匹配的全文
    }

    /**
     * 按key的顺序逐一赋值
     * @param values
     * @return
     */
    public Tuple val(Object...values) {
        if (values != null) {
            if (values.length != keyArr.length)
                throw new IllegalArgumentException("Tuple's key count doesn't match value count!");
            for (int i = 0; i < keyArr.length; ++i) {
                map.put(keyArr[i], values[i]);
            }
        }
        return this;
    }

    /**
     * 将目标元组转换为指定key值的新元组，目标元组中的数据将会被清空并置为null;
     * @param tuple 目标元组
     * @return 返回指定了新key的新元组
     */
    public Tuple with(Tuple tuple) {
        if (this.keyArr.length != tuple.keyArr.length)
            throw new IllegalArgumentException("Tuple's key count doesn't match value count!");
        for (int i = 0; i < tuple.keyArr.length; ++i) {
            this.map.put(this.keyArr[i], tuple.map.get(tuple.keyArr[i]));
        }
        tuple.map.clear(); // help gc
        tuple = null; // help gc
        return this;
    }

    /**
     * 获取元组中指定key的value
     * @param name
     * @return
     */
    public Object get(String name) {
        return this.map.get(name);
    }
    /**
     * 根据指定类型从元组中获取key对应的value
     * @param name key
     * @param type 目标值的类型
     * @return
     */
    public <T> T get(String name, Class<T> type) {
        return type.cast(this.map.get(name));
    }

    /**
     * 根据元组的顺序取值
     * @param index 值在元组中定义的下标
     * @return
     */
    public Object get(int index) {
        return this.map.get(this.keyArr[index]);
    }

    /**
     * 根据元组的顺序取值
     * @param index 值在元组中定义的下标
     * @param type 目标值的类型
     * @return
     */
    public <T> T get(int index, Class<T> type) {
        return type.cast(this.map.get(this.keyArr[index]));
    }

}
