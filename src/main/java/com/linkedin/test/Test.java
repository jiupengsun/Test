package com.linkedin.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Stream;


public class Test {

  public static void main(String[] args) throws IOException, URISyntaxException {
    URI uri = Thread.currentThread().getContextClassLoader().getResource("model").toURI();
    Path path;
    if (uri.getScheme().equals("jar")) {
      FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.EMPTY_MAP);
      path = fileSystem.getPath("model");
      fileSystem.close();
    } else {
      path = Paths.get(uri);
    }

    //Stream<Path> walk = Files.walk(path, 1);
    Stream<Path> walk = Files.list(path);
    for (Iterator<Path> it = walk.iterator(); it.hasNext(); ) {
      Path p = it.next();
      System.out.println(p);
    }
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