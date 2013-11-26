package com.juvenxu.portableconfig;

import com.juvenxu.portableconfig.model.Replace;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author juven
 */
public interface ContentFilter
{
  boolean accept(String contentType);

  void filter(File file, File tmpFile, List<Replace> replaces) throws IOException;
}
