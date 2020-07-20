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
package org.apache.ibatis.test.zy.wildcard;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.List;

public class WildcardTypeBean {
    private List<? extends Number> a;  // a没有下界,
    //	没有指定的话，上边界默认是 Object ,下边界是 	String
    private List<? super String> b;

    private List<String> c;

    private Class<?> aClass;

    public static void main(String[] args) {
        testWildCardType();
    }

    public static void testWildCardType() {
        try {
            Field[] fields = WildcardTypeBean.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                Type type = field.getGenericType();
                String nameString = field.getName();
                PrintUtils.print("下面开始打印" + nameString + "是否具有通配符");
                if (!(type instanceof ParameterizedType)) {
                    PrintUtils.print("---------------------------");
                    continue;
                }
                ParameterizedType parameterizedType = (ParameterizedType) type;
                type = parameterizedType.getActualTypeArguments()[0];
                if (!(type instanceof WildcardType)) {
                    PrintUtils.print("---------------------------");
                    continue;
                }
                WildcardType wildcardType = (WildcardType) type;
                Type[] lowerTypes = wildcardType.getLowerBounds();
                if (lowerTypes != null) {
                    PrintUtils.print("下边界");
                    PrintUtils.printTypeArr(lowerTypes);
                }
                Type[] upTypes = wildcardType.getUpperBounds();
                if (upTypes != null) {
                    PrintUtils.print("上边界");
                    PrintUtils.printTypeArr(upTypes);
                }
                PrintUtils.print("---------------------------");

            }
            Field fieldA = WildcardTypeBean.class.getDeclaredField("a");
            Field fieldB = WildcardTypeBean.class.getDeclaredField("b");
            // 先拿到范型类型
            PrintUtils.print(fieldA.getGenericType() instanceof ParameterizedType);
            PrintUtils.print(fieldB.getGenericType() instanceof ParameterizedType);
            ParameterizedType pTypeA = (ParameterizedType) fieldA.getGenericType();
            ParameterizedType pTypeB = (ParameterizedType) fieldB.getGenericType();
            // 再从范型里拿到通配符类型
            PrintUtils.print(pTypeA.getActualTypeArguments()[0] instanceof WildcardType);
            PrintUtils.print(pTypeB.getActualTypeArguments()[0] instanceof WildcardType);
            WildcardType wTypeA = (WildcardType) pTypeA.getActualTypeArguments()[0];
            WildcardType wTypeB = (WildcardType) pTypeB.getActualTypeArguments()[0];
            // 方法测试
            System.out.println(wTypeA.getUpperBounds()[0]);
            System.out.println(wTypeB.getLowerBounds()[0]);
            // 看看通配符类型到底是什么, 打印结果为: ? extends java.lang.Number
            System.out.println(wTypeA);
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static class PrintUtils {
        public static void print(Object s) {
            System.out.println(s);
        }

        public static void printTypeArr(Type[] upTypes) {
            for (Type upType : upTypes) {
                System.out.println(upType);
            }
        }
    }
}