package com.linkedin.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class Engine {

  public static void main(String[] args) throws Exception {
    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/jiusun/IdeaProjects/Test/src/com/linkedin/test/config")));
    String line = null;
    while((line = br.readLine()) != null) {
      Class clazz = Class.forName(line);
      Job job = (Job) clazz.newInstance();
      job.apply();
    }
  }
}
