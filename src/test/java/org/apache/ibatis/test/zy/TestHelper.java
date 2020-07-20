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
package org.apache.ibatis.test.zy;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestHelper {

	private static class ParameterizedTypeBean {
		// 下面的 field 的 Type 属于 ParameterizedType
		Map<String, Person> map;
		Set<String> set1;
		Class<?> clz;
		Holder<String> holder;
		List<String> list;
		// Map<String,Person> map 这个 ParameterizedType 的 getOwnerType() 为 null，
		// 而 Map.Entry<String, String> entry 的 getOwnerType() 为 Map 所属于的 Type。
		Map.Entry<String, String> entry;
		// 下面的 field 的 Type 不属于ParameterizedType
		String str;
		Integer i;
		Set set;
		List aList;

		static class Holder<V> {

		}
	}

	public static void testParameterizedType() {
		Field f = null;
		try {
			Field[] fields = ParameterizedTypeBean.class.getDeclaredFields();
            // 打印出所有的 Field 的 TYpe 是否属于 ParameterizedType
			for (int i = 0; i < fields.length; i++) {
				f = fields[i];
				boolean b = f.getGenericType() instanceof ParameterizedType;
				String s = b==true ? "is" : "is not";
				System.out.println(f.getName()
						+ " getGenericType() " +s+
						" ParameterizedType ")
				;
			}
			getParameterizedTypeMes("map" );
			getParameterizedTypeMes("entry" );
			

		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		testParameterizedType();
	}

	private static void getParameterizedTypeMes(String fieldName) throws NoSuchFieldException {
		Field f = ParameterizedTypeBean.class.getDeclaredField(fieldName);
		f.setAccessible(true);
		System.out.println(f.getGenericType());
		boolean b=f.getGenericType() instanceof ParameterizedType;
		System.out.println(b);
		if(b){
			ParameterizedType pType = (ParameterizedType) f.getGenericType();
			System.out.println(pType.getRawType());
			for (Type type : pType.getActualTypeArguments()) {
				System.out.println(type);
			}
			System.out.println(pType.getOwnerType()); // null
		}
	}
}