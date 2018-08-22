package com.linkedin.test;

import java.util.ArrayList;
import java.util.List;


public class Test {

  public static void main(String[] args) {
    System.out.println(GenericTest.class.equals(GenericTestImpl.class));

    List<Object> list = new ArrayList<>();
    list.add(Test.class);
    System.out.println(list.get(0));
  }
}

interface GenericTest<T, O> {
  O apply(T t);
}


class GenericTestImpl implements GenericTest<Integer, String> {

  @Override
  public String apply(Integer integer) {
    return String.valueOf(integer);
  }
}