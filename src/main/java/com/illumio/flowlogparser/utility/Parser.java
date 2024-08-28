package main.java.com.illumio.flowlogparser.utility;

import java.util.List;

/**
 * The interface provides a method to parse data from a file into a list of objects of type T.
 */
public interface Parser {
  /**
   * Parses the data from the specified file into a list of objects of type T.
   *
   * @param filePath the path to the file to be parsed
   * @param clazz the class type of objects to be created
   * @return a list of objects created from the file data
   */
  <T> List<T> parse(String filePath, Class<T> clazz) throws Exception;
}
