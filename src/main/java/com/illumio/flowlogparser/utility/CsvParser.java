package main.java.com.illumio.flowlogparser.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CsvParser implements Parser {
  public <T> List<T> parse(String filePath, Class<T> clazz) throws Exception {
    var resultList = new ArrayList<T>();
    try (var br = new BufferedReader(new FileReader(filePath))) {
      String line;
      var headers = br.readLine().split(",");
      while ((line = br.readLine()) != null) {
        var values = line.split(",");
        var obj = clazz.getDeclaredConstructor().newInstance();
        var validObject = true;

        for (int i = 0; i < values.length; i++) {
          var header = headers[i];
          var value = values[i];
          try {
            Field field = null;
            Class<?> tempClass = clazz;
            while (tempClass != null) {
              try {
                field = tempClass.getDeclaredField(header);
                break;  // If found, break the loop
              } catch (NoSuchFieldException e) { tempClass = tempClass.getSuperclass();}
            }
            if (field == null) throw new NoSuchFieldException("No field found for header: " + header);

            if (field.getType().equals(Integer.class)) {
              field.set(obj, Integer.parseInt(value));
            } else if (field.getType().equals(Long.class)) {
              field.set(obj, Long.parseLong(value));
            } else if (field.getType().equals(Double.class)) {
              field.set(obj, Double.parseDouble(value));
            } else if (field.getType().equals(Boolean.class)) {
              field.set(obj, Boolean.parseBoolean(value));
            } else {
              field.set(obj, value);
            }
          } catch (NumberFormatException e) {
            System.err.println("Skipping invalid value: " + value + " for header: " + header);
            validObject = false;  // Mark object as invalid due to data format issues
            break;
          } catch (NoSuchFieldException e) {
            System.err.println("No field found for header: " + header);
            validObject = false;
            break;
          }
        }
        if (validObject) resultList.add(obj);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("Error Parsing the file", e);
    }
    return resultList;
  }
}
