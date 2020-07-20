/**
 *    Copyright 2009-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.test.zy.typeVar;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

public class TypeVariableBean<K extends InputStream & Closeable, V> {
    // K 的上边界是 InputStream
    K key;
    // 没有指定的话 ，V 的 上边界 属于 Object
    V value;
    // 不属于 TypeTypeVariable
    V[] values;
    String str;
    List<K> kList;

    public static void main(String[] args) throws NoSuchFieldException {
        TypeVariableBean bean = new TypeVariableBean<FileInputStream, String>();
        Field fk = TypeVariableBean.class.getDeclaredField("key");
        boolean b = fk.getGenericType() instanceof TypeVariable;
        System.out.println(b);
        TypeVariable keyType = (TypeVariable) fk.getGenericType();
        System.out.println(keyType.getName());
        System.out.println(keyType.getGenericDeclaration());

        Type[] bounds = keyType.getBounds();
        for (Type bound : bounds) {
            System.out.println(bound);
        }
    }

}