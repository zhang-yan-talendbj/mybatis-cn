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
package org.apache.ibatis.test.zy.genericArray;

import org.apache.ibatis.test.zy.Person;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

public class GenericArrayTypeBean<T> {
	
	public void test(List<String>[] pTypeArray, T[] vTypeArray,
					 List<String> list, String[] strings, Person[] ints) {
	    }

	public static void main(String[] args) {
	testGenericArrayType();
	}

	public static void testGenericArrayType() {
		Method method = GenericArrayTypeBean.class.getDeclaredMethods()[1];
		System.out.println(method);
		// public void test(List<String>[] pTypeArray, T[]
		// vTypeArray,List<String> list, String[] strings, Person[] ints)
		Type[] types = method.getGenericParameterTypes(); // 这是 Method 中的方法
		for (Type type : types) {
			System.out.println(type instanceof GenericArrayType);// 依次输出true，true，false，false，false

			GenericArrayType type1= (GenericArrayType) type;

			System.out.println(type1.getGenericComponentType());
		}
	}
}