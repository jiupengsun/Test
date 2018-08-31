package com.linkedin.test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Stream;


public class Test {

  public static void main(String[] args) throws IOException, URISyntaxException {
    URI uri = Thread.currentThread().getContextClassLoader().getResource("model").toURI();
    FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.EMPTY_MAP);
    walk(fileSystem, "model");
  }

  private static void walk(FileSystem fileSystem, String path) throws IOException {

    Path p = fileSystem.getPath(path);
    if (!Files.isDirectory(p)) System.out.println("error!");
    System.out.println(p.getFileName());

    // list files under the folder
    Stream<Path> walk = Files.list(p);
    for (Iterator<Path> it=walk.iterator(); it.hasNext(); ) {
      Path next = it.next();
      if (Files.isDirectory(next)) {
        //
        walk(fileSystem, next.toString());
      } else {
        System.out.println(next.getFileName());
      }
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