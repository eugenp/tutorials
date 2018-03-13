// Copyright (C) 2002 by Jason Hunter <jhunter_AT_acm_DOT_org>.
// All rights reserved.  Use of this class is limited.
// Please see the LICENSE for more information.

package com.oreilly.servlet.multipart;

import java.io.*;

/**
 * An interface to provide a pluggable file renaming policy, particularly
 * useful to handle naming conflicts with an existing file.
 * 
 * @author Jason Hunter
 * @version 1.0, 2002/04/30, initial revision, thanks to Changshin Lee for
 *                           the basic idea
 */
public interface FileRenamePolicy {
  
  /**
   * Returns a File object holding a new name for the specified file.
   *
   * @see FilePart#writeTo(File fileOrDirectory)
   */
  public File rename(File f);

}
