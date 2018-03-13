/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.tomcat.jni;
/* Import needed classes */
import java.nio.ByteBuffer;

/** File
 *
 * @author Mladen Turk
 */
public class File {

    /** Open the file for reading */
    public static final int APR_FOPEN_READ       = 0x00001;
    /** Open the file for writing */
    public static final int APR_FOPEN_WRITE      = 0x00002;
    /** Create the file if not there */
    public static final int APR_FOPEN_CREATE     = 0x00004;
    /** Append to the end of the file */
    public static final int APR_FOPEN_APPEND     = 0x00008;
    /** Open the file and truncate to 0 length */
    public static final int APR_FOPEN_TRUNCATE   = 0x00010;
    /** Open the file in binary mode */
    public static final int APR_FOPEN_BINARY     = 0x00020;
    /** Open should fail if APR_CREATE and file exists. */
    public static final int APR_FOPEN_EXCL       = 0x00040;
    /** Open the file for buffered I/O */
    public static final int APR_FOPEN_BUFFERED   = 0x00080;
    /** Delete the file after close */
    public static final int APR_FOPEN_DELONCLOSE = 0x00100;
    /** Platform dependent tag to open the file for
     * use across multiple threads
     */
    public static final int APR_FOPEN_XTHREAD     = 0x00200;
    /** Platform dependent support for higher level locked read/write
     * access to support writes across process/machines
     */
    public static final int APR_FOPEN_SHARELOCK   = 0x00400;
    /** Do not register a cleanup when the file is opened */
    public static final int APR_FOPEN_NOCLEANUP   = 0x00800;
    /** Advisory flag that this file should support
     * apr_socket_sendfile operation
     */
    public static final int APR_FOPEN_SENDFILE_ENABLED = 0x01000;
    /** Platform dependent flag to enable large file support;
     * <br><b>Warning :</b> The APR_LARGEFILE flag only has effect on some platforms
     * where sizeof(apr_off_t) == 4.  Where implemented, it allows opening
     * and writing to a file which exceeds the size which can be
     * represented by apr_off_t (2 gigabytes).  When a file's size does
     * exceed 2Gb, apr_file_info_get() will fail with an error on the
     * descriptor, likewise apr_stat()/apr_lstat() will fail on the
     * filename.  apr_dir_read() will fail with APR_INCOMPLETE on a
     * directory entry for a large file depending on the particular
     * APR_FINFO_* flags.  Generally, it is not recommended to use this
     * flag.
     */
    public static final int APR_FOPEN_LARGEFILE      = 0x04000;

    /** Set the file position */
    public static final int APR_SET = 0;
    /** Current */
    public static final int APR_CUR = 1;
    /** Go to end of file */
    public static final int APR_END = 2;

    /* flags for apr_file_attrs_set */

    /** File is read-only */
    public static final int APR_FILE_ATTR_READONLY   = 0x01;
    /** File is executable */
    public static final int APR_FILE_ATTR_EXECUTABLE = 0x02;
    /** File is hidden */
    public static final int APR_FILE_ATTR_HIDDEN     = 0x04;


    /* File lock types/flags */

    /** Shared lock. More than one process or thread can hold a shared lock
     * at any given time. Essentially, this is a "read lock", preventing
     * writers from establishing an exclusive lock.
     */
    public static final int APR_FLOCK_SHARED    = 1;

    /** Exclusive lock. Only one process may hold an exclusive lock at any
     * given time. This is analogous to a "write lock".
     */
    public static final int APR_FLOCK_EXCLUSIVE = 2;
    /** mask to extract lock type */
    public static final int APR_FLOCK_TYPEMASK  = 0x000F;
    /** do not block while acquiring the file lock */
    public static final int APR_FLOCK_NONBLOCK  = 0x0010;

    /* apr_filetype_e values for the filetype member of the
     * apr_file_info_t structure
     * <br><b>Warning :</b>: Not all of the filetypes below can be determined.
     * For example, a given platform might not correctly report
     * a socket descriptor as APR_SOCK if that type isn't
     * well-identified on that platform.  In such cases where
     * a filetype exists but cannot be described by the recognized
     * flags below, the filetype will be APR_UNKFILE.  If the
     * filetype member is not determined, the type will be APR_NOFILE.
     */

    /** no file type determined */
    public static final int APR_NOFILE  = 0;
    /** a regular file */
    public static final int APR_REG     = 1;
    /** a directory */
    public static final int APR_DIR     = 2;
    /** a character device */
    public static final int APR_CHR     = 3;
    /** a block device */
    public static final int APR_BLK     = 4;
    /** a FIFO / pipe */
    public static final int APR_PIPE    = 5;
    /** a symbolic link */
    public static final int APR_LNK     = 6;
    /** a [unix domain] socket */
    public static final int APR_SOCK    = 7;
    /** a file of some other unknown type */
    public static final int APR_UNKFILE = 127;


    /*
     * apr_file_permissions File Permissions flags
     */

    public static final int APR_FPROT_USETID     = 0x8000; /** Set user id */
    public static final int APR_FPROT_UREAD      = 0x0400; /** Read by user */
    public static final int APR_FPROT_UWRITE     = 0x0200; /** Write by user */
    public static final int APR_FPROT_UEXECUTE   = 0x0100; /** Execute by user */

    public static final int APR_FPROT_GSETID     = 0x4000; /** Set group id */
    public static final int APR_FPROT_GREAD      = 0x0040; /** Read by group */
    public static final int APR_FPROT_GWRITE     = 0x0020; /** Write by group */
    public static final int APR_FPROT_GEXECUTE   = 0x0010; /** Execute by group */

    public static final int APR_FPROT_WSTICKY    = 0x2000; /** Sticky bit */
    public static final int APR_FPROT_WREAD      = 0x0004; /** Read by others */
    public static final int APR_FPROT_WWRITE     = 0x0002; /** Write by others */
    public static final int APR_FPROT_WEXECUTE   = 0x0001; /** Execute by others */
    public static final int APR_FPROT_OS_DEFAULT = 0x0FFF; /** use OS's default permissions */


    public static final int APR_FINFO_LINK   = 0x00000001; /** Stat the link not the file itself if it is a link */
    public static final int APR_FINFO_MTIME  = 0x00000010; /** Modification Time */
    public static final int APR_FINFO_CTIME  = 0x00000020; /** Creation or inode-changed time */
    public static final int APR_FINFO_ATIME  = 0x00000040; /** Access Time */
    public static final int APR_FINFO_SIZE   = 0x00000100; /** Size of the file */
    public static final int APR_FINFO_CSIZE  = 0x00000200; /** Storage size consumed by the file */
    public static final int APR_FINFO_DEV    = 0x00001000; /** Device */
    public static final int APR_FINFO_INODE  = 0x00002000; /** Inode */
    public static final int APR_FINFO_NLINK  = 0x00004000; /** Number of links */
    public static final int APR_FINFO_TYPE   = 0x00008000; /** Type */
    public static final int APR_FINFO_USER   = 0x00010000; /** User */
    public static final int APR_FINFO_GROUP  = 0x00020000; /** Group */
    public static final int APR_FINFO_UPROT  = 0x00100000; /** User protection bits */
    public static final int APR_FINFO_GPROT  = 0x00200000; /** Group protection bits */
    public static final int APR_FINFO_WPROT  = 0x00400000; /** World protection bits */
    public static final int APR_FINFO_ICASE  = 0x01000000; /** if dev is case insensitive */
    public static final int APR_FINFO_NAME   = 0x02000000; /** -&gt;name in proper case */

    public static final int APR_FINFO_MIN    = 0x00008170; /** type, mtime, ctime, atime, size */
    public static final int APR_FINFO_IDENT  = 0x00003000; /** dev and inode */
    public static final int APR_FINFO_OWNER  = 0x00030000; /** user and group */
    public static final int APR_FINFO_PROT   = 0x00700000; /**  all protections */
    public static final int APR_FINFO_NORM   = 0x0073b170; /**  an atomic unix apr_stat() */
    public static final int APR_FINFO_DIRENT = 0x02000000; /**  an atomic unix apr_dir_read() */



    /**
     * Open the specified file.
     * @param fname The full path to the file (using / on all systems)
     * @param flag Or'ed value of:
     * <PRE>
     * APR_FOPEN_READ              open for reading
     * APR_FOPEN_WRITE             open for writing
     * APR_FOPEN_CREATE            create the file if not there
     * APR_FOPEN_APPEND            file ptr is set to end prior to all writes
     * APR_FOPEN_TRUNCATE          set length to zero if file exists
     * APR_FOPEN_BINARY            not a text file (This flag is ignored on
     *                             UNIX because it has no meaning)
     * APR_FOPEN_BUFFERED          buffer the data.  Default is non-buffered
     * APR_FOPEN_EXCL              return error if APR_CREATE and file exists
     * APR_FOPEN_DELONCLOSE        delete the file after closing.
     * APR_FOPEN_XTHREAD           Platform dependent tag to open the file
     *                             for use across multiple threads
     * APR_FOPEN_SHARELOCK         Platform dependent support for higher
     *                             level locked read/write access to support
     *                             writes across process/machines
     * APR_FOPEN_NOCLEANUP         Do not register a cleanup with the pool
     *                             passed in on the <EM>pool</EM> argument (see below).
     *                             The apr_os_file_t handle in apr_file_t will not
     *                             be closed when the pool is destroyed.
     * APR_FOPEN_SENDFILE_ENABLED  Open with appropriate platform semantics
     *                             for sendfile operations.  Advisory only,
     *                             apr_socket_sendfile does not check this flag.
     * </PRE>
     * @param perm Access permissions for file.
     * @param pool The pool to use.
     * If perm is APR_OS_DEFAULT and the file is being created,
     * appropriate default permissions will be used.
     * @return The opened file descriptor.
     */
    public static native long open(String fname, int flag, int perm, long pool)
        throws Error;

    /**
     * Close the specified file.
     * @param file The file descriptor to close.
     */
    public static native int close(long file);

    /**
     * Flush the file's buffer.
     * @param thefile The file descriptor to flush
     */
    public static native int flush(long thefile);

    /**
     * Open a temporary file
     * @param templ The template to use when creating a temp file.
     * @param flags The flags to open the file with. If this is zero,
     *              the file is opened with
     *              APR_CREATE | APR_READ | APR_WRITE | APR_EXCL | APR_DELONCLOSE
     * @param pool The pool to allocate the file out of.
     * @return The apr file to use as a temporary file.
     *
     * This function  generates  a unique temporary file name from template.
     * The last six characters of template must be XXXXXX and these are replaced
     * with a string that makes the filename unique. Since it will  be  modified,
     * template must not be a string constant, but should be declared as a character
     * array.
     *
     */
    public static native long mktemp(String templ, int flags, long pool)
        throws Error;

    /**
     * Delete the specified file.
     * @param path The full path to the file (using / on all systems)
     * @param pool The pool to use.
     * If the file is open, it won't be removed until all
     * instances are closed.
     */
    public static native int remove(String path, long pool);

    /**
     * Rename the specified file.
     * <br><b>Warning :</b> If a file exists at the new location, then it will be
     * overwritten.  Moving files or directories across devices may not be
     * possible.
     * @param fromPath The full path to the original file (using / on all systems)
     * @param toPath The full path to the new file (using / on all systems)
     * @param pool The pool to use.
     */
    public static native int rename(String fromPath, String toPath, long pool);

    /**
     * Copy the specified file to another file.
     * The new file does not need to exist, it will be created if required.
     * <br><b>Warning :</b> If the new file already exists, its contents will be overwritten.
     * @param fromPath The full path to the original file (using / on all systems)
     * @param toPath The full path to the new file (using / on all systems)
     * @param perms Access permissions for the new file if it is created.
     *     In place of the usual or'd combination of file permissions, the
     *     value APR_FILE_SOURCE_PERMS may be given, in which case the source
     *     file's permissions are copied.
     * @param pool The pool to use.
     */
    public static native int copy(String fromPath, String toPath, int perms, long pool);

    /**
     * Append the specified file to another file.
     * The new file does not need to exist, it will be created if required.
     * @param fromPath The full path to the source file (use / on all systems)
     * @param toPath The full path to the destination file (use / on all systems)
     * @param perms Access permissions for the destination file if it is created.
     *     In place of the usual or'd combination of file permissions, the
     *     value APR_FILE_SOURCE_PERMS may be given, in which case the source
     *     file's permissions are copied.
     * @param pool The pool to use.
     */
    public static native int append(String fromPath, String toPath, int perms, long pool);

    /**
     * Write the string into the specified file.
     * @param str The string to write. Must be NUL terminated!
     * @param thefile The file descriptor to write to
     */
    public static native int puts(byte [] str, long thefile);

    /**
     * Move the read/write file offset to a specified byte within a file.
     * @param thefile The file descriptor
     * @param where How to move the pointer, one of:
     * <PRE>
     * APR_SET  --  set the offset to offset
     * APR_CUR  --  add the offset to the current position
     * APR_END  --  add the offset to the current file size
     * </PRE>
     * @param offset The offset to move the pointer to.
     * @return Offset the pointer was actually moved to.
     */
    public static native long seek(long thefile, int where, long offset)
        throws Error;

    /**
     * Write a character into the specified file.
     * @param ch The character to write.
     * @param thefile The file descriptor to write to
     */
    public static native int putc(byte ch, long thefile);

    /**
     * Put a character back onto a specified stream.
     * @param ch The character to write.
     * @param thefile The file descriptor to write to
     */
    public static native int ungetc(byte ch, long thefile);

    /**
     * Write data to the specified file.
     *
     * Write will write up to the specified number of
     * bytes, but never more.  If the OS cannot write that many bytes, it
     * will write as many as it can.  The third argument is modified to
     * reflect the * number of bytes written.
     *
     * It is possible for both bytes to be written and an error to
     * be returned.  APR_EINTR is never returned.
     * @param thefile The file descriptor to write to.
     * @param buf The buffer which contains the data.
     * @param offset Start offset in buf
     * @param nbytes The number of bytes to write; (-1) for full array.
     * @return The number of bytes written.
     */
    public static native int write(long thefile, byte[] buf, int offset, int nbytes);

    /**
     * Write data to the specified file.
     *
     * Write will write up to the specified number of
     * bytes, but never more.  If the OS cannot write that many bytes, it
     * will write as many as it can.  The third argument is modified to
     * reflect the * number of bytes written.
     *
     * It is possible for both bytes to be written and an error to
     * be returned.  APR_EINTR is never returned.
     * @param thefile The file descriptor to write to.
     * @param buf The direct Byte buffer which contains the data.
     * @param offset Start offset in buf
     * @param nbytes The number of bytes to write
     * @return The number of bytes written.
     */
    public static native int writeb(long thefile, ByteBuffer buf, int offset, int nbytes);

    /**
     * Write data to the specified file, ensuring that all of the data is
     * written before returning.
     *
     * Write will write up to the specified number of
     * bytes, but never more.  If the OS cannot write that many bytes, the
     * process/thread will block until they can be written. Exceptional
     * error such as "out of space" or "pipe closed" will terminate with
     * an error.
     *
     * It is possible for both bytes to be written and an error to
     * be returned.  And if *bytes_written is less than nbytes, an
     * accompanying error is _always_ returned.
     *
     * APR_EINTR is never returned.
     * @param thefile The file descriptor to write to.
     * @param buf The buffer which contains the data.
     * @param offset Start offset in buf
     * @param nbytes The number of bytes to write; (-1) for full array.
     * @return The number of bytes written.
     */
    public static native int writeFull(long thefile, byte[] buf, int offset, int nbytes);

    /**
     * Write data to the specified file, ensuring that all of the data is
     * written before returning.
     *
     * Write will write up to the specified number of
     * bytes, but never more.  If the OS cannot write that many bytes, the
     * process/thread will block until they can be written. Exceptional
     * error such as "out of space" or "pipe closed" will terminate with
     * an error.
     *
     * It is possible for both bytes to be written and an error to
     * be returned.  And if *bytes_written is less than nbytes, an
     * accompanying error is _always_ returned.
     *
     * APR_EINTR is never returned.
     * @param thefile The file descriptor to write to.
     * @param buf The direct ByteBuffer which contains the data.
     * @param offset Start offset in buf
     * @param nbytes The number of bytes to write.
     * @return The number of bytes written.
     */
    public static native int writeFullb(long thefile, ByteBuffer buf, int offset, int nbytes);

    /**
     * Write data from array of byte arrays to the specified file.
     *
     * It is possible for both bytes to be written and an error to
     * be returned.  APR_EINTR is never returned.
     *
     * apr_file_writev is available even if the underlying
     * operating system doesn't provide writev().
     * @param thefile The file descriptor to write to.
     * @param vec The array from which to get the data to write to the file.
     * @return The number of bytes written.
     */
    public static native int writev(long thefile, byte[][] vec);

    /**
     * Write data from array of byte arrays to the specified file,
     * ensuring that all of the data is written before returning.
     *
     * writevFull is available even if the underlying
     * operating system doesn't provide writev().
     * @param thefile The file descriptor to write to.
     * @param vec The array from which to get the data to write to the file.
     * @return The number of bytes written.
     */
    public static native int writevFull(long thefile, byte[][] vec);

    /**
     * Read data from the specified file.
     *
     * apr_file_read will read up to the specified number of
     * bytes, but never more.  If there isn't enough data to fill that
     * number of bytes, all of the available data is read.  The third
     * argument is modified to reflect the number of bytes read.  If a
     * char was put back into the stream via ungetc, it will be the first
     * character returned.
     *
     * It is not possible for both bytes to be read and an APR_EOF
     * or other error to be returned.  APR_EINTR is never returned.
     * @param thefile The file descriptor to read from.
     * @param buf The buffer to store the data to.
     * @param offset Start offset in buf
     * @param nbytes The number of bytes to read (-1) for full array.
     * @return the number of bytes read.
     */
    public static native int read(long thefile, byte[] buf,  int offset, int nbytes);

    /**
     * Read data from the specified file.
     *
     * apr_file_read will read up to the specified number of
     * bytes, but never more.  If there isn't enough data to fill that
     * number of bytes, all of the available data is read.  The third
     * argument is modified to reflect the number of bytes read.  If a
     * char was put back into the stream via ungetc, it will be the first
     * character returned.
     *
     * It is not possible for both bytes to be read and an APR_EOF
     * or other error to be returned.  APR_EINTR is never returned.
     * @param thefile The file descriptor to read from.
     * @param buf The direct Byte buffer to store the data to.
     * @param offset Start offset in buf
     * @param nbytes The number of bytes to read.
     * @return the number of bytes read.
     */
    public static native int readb(long thefile, ByteBuffer buf,  int offset, int nbytes);

    /**
     * Read data from the specified file, ensuring that the buffer is filled
     * before returning.
     *
     * Read will read up to the specified number of
     * bytes, but never more.  If there isn't enough data to fill that
     * number of bytes, then the process/thread will block until it is
     * available or EOF is reached.  If a char was put back into the
     * stream via ungetc, it will be the first character returned.
     *
     * It is possible for both bytes to be read and an error to be
     * returned.  And if *bytes_read is less than nbytes, an accompanying
     * error is _always_ returned.
     *
     * APR_EINTR is never returned.
     * @param thefile The file descriptor to read from.
     * @param buf The buffer to store the data to.
     * @param offset Start offset in buf
     * @param nbytes The number of bytes to read (-1) for full array.
     * @return the number of bytes read.
     */
    public static native int readFull(long thefile, byte[] buf,  int offset, int nbytes);

    /**
     * Read data from the specified file, ensuring that the buffer is filled
     * before returning.
     *
     * Read will read up to the specified number of
     * bytes, but never more.  If there isn't enough data to fill that
     * number of bytes, then the process/thread will block until it is
     * available or EOF is reached.  If a char was put back into the
     * stream via ungetc, it will be the first character returned.
     *
     * It is possible for both bytes to be read and an error to be
     * returned.  And if *bytes_read is less than nbytes, an accompanying
     * error is _always_ returned.
     *
     * APR_EINTR is never returned.
     * @param thefile The file descriptor to read from.
     * @param buf The direct ByteBuffer to store the data to.
     * @param offset Start offset in buf
     * @param nbytes The number of bytes to read.
     * @return the number of bytes read.
     */
    public static native int readFullb(long thefile, ByteBuffer buf,  int offset, int nbytes);

    /**
     * Read a string from the specified file.
     * The buffer will be NUL-terminated if any characters are stored.
     * @param buf The buffer to store the string in.
     * @param offset Start offset in buf
     * @param thefile The file descriptor to read from
     */
    public static native int gets(byte[] buf,  int offset, long thefile);


    /**
     * Read a character from the specified file.
     * @param thefile The file descriptor to read from
     * @return The read character
     */
    public static native int getc(long thefile)
        throws Error;

    /**
     * Are we at the end of the file
     * @param fptr The apr file we are testing.
     * @return Returns APR_EOF if we are at the end of file, APR_SUCCESS otherwise.
     */
    public static native int eof(long fptr);

    /**
     * return the file name of the current file.
     * @param thefile The currently open file.
     */
    public static native String nameGet(long thefile);

    /**
     * Set the specified file's permission bits.
     * <br><b>Warning :</b> Some platforms may not be able to apply all of the
     * available permission bits; APR_INCOMPLETE will be returned if some
     * permissions are specified which could not be set.
     * <br><b>Warning :</b> Platforms which do not implement this feature will return
     * APR_ENOTIMPL.
     * @param fname The file (name) to apply the permissions to.
     * @param perms The permission bits to apply to the file.
     *
     */
    public static native int permsSet(String fname, int perms);

    /**
     * Set attributes of the specified file.
     * This function should be used in preference to explicit manipulation
     *      of the file permissions, because the operations to provide these
     *      attributes are platform specific and may involve more than simply
     *      setting permission bits.
     * <br><b>Warning :</b> Platforms which do not implement this feature will return
     *      APR_ENOTIMPL.
     * @param fname The full path to the file (using / on all systems)
     * @param attributes Or'd combination of
     * <PRE>
     *            APR_FILE_ATTR_READONLY   - make the file readonly
     *            APR_FILE_ATTR_EXECUTABLE - make the file executable
     *            APR_FILE_ATTR_HIDDEN     - make the file hidden
     * </PRE>
     * @param mask Mask of valid bits in attributes.
     * @param pool the pool to use.
     */
    public static native int  attrsSet(String fname, int attributes, int mask, long pool);

    /**
     * Set the mtime of the specified file.
     * <br><b>Warning :</b> Platforms which do not implement this feature will return
     *      APR_ENOTIMPL.
     * @param fname The full path to the file (using / on all systems)
     * @param mtime The mtime to apply to the file in microseconds
     * @param pool The pool to use.
     */
    public static native int  mtimeSet(String fname, long mtime, long pool);

    /**
     * Establish a lock on the specified, open file. The lock may be advisory
     * or mandatory, at the discretion of the platform. The lock applies to
     * the file as a whole, rather than a specific range. Locks are established
     * on a per-thread/process basis; a second lock by the same thread will not
     * block.
     * @param thefile The file to lock.
     * @param type The type of lock to establish on the file.
     */
    public static native int lock(long thefile, int type);

    /**
     * Remove any outstanding locks on the file.
     * @param thefile The file to unlock.
     */
    public static native int unlock(long thefile);

    /**
     * Retrieve the flags that were passed into apr_file_open()
     * when the file was opened.
     * @param file The file to retrieve flags.
     * @return the flags
     */
    public static native int flagsGet(long file);

    /**
     * Truncate the file's length to the specified offset
     * @param fp The file to truncate
     * @param offset The offset to truncate to.
     */
    public static native int trunc(long fp, long offset);

    /**
     * Create an anonymous pipe.
     * @param io io[0] The file descriptors to use as input to the pipe.
     *           io[1] The file descriptor to use as output from the pipe.
     * @param pool The pool to operate on.
     */
    public static native int pipeCreate(long [] io, long pool);

    /**
     * Get the timeout value for a pipe or manipulate the blocking state.
     * @param thepipe The pipe we are getting a timeout for.
     * @return The current timeout value in microseconds.
     */
    public static native long pipeTimeoutGet(long thepipe)
        throws Error;

    /**
     * Set the timeout value for a pipe or manipulate the blocking state.
     * @param thepipe The pipe we are setting a timeout on.
     * @param timeout The timeout value in microseconds.  Values &lt; 0 mean
     *        wait forever, 0 means do not wait at all.
     */
    public static native int pipeTimeoutSet(long thepipe, long timeout);

    /**
     * Duplicate the specified file descriptor.
     * @param newFile The file to duplicate.
     * newFile must point to a valid apr_file_t, or point to NULL.
     * @param oldFile The file to duplicate.
     * @param pool The pool to use for the new file.
     * @return Duplicated file structure.
     */
    public static native long dup(long newFile, long oldFile, long pool)
        throws Error;

    /**
     * Duplicate the specified file descriptor and close the original.
     * @param newFile The old file that is to be closed and reused.
     * newFile MUST point at a valid apr_file_t. It cannot be NULL.
     * @param oldFile The file to duplicate.
     * @param pool The pool to use for the new file.
     * @return Status code.
     */
    public static native int dup2(long newFile, long oldFile, long pool);

    /**
     * Get the specified file's stats.  The file is specified by filename,
     * instead of using a pre-opened file.
     * @param finfo Where to store the information about the file, which is
     * never touched if the call fails.
     * @param fname The name of the file to stat.
     * @param wanted The desired apr_finfo_t fields, as a bit flag of APR_FINFO_ values
     * @param pool the pool to use to allocate the new file.
     */
    public static native int stat(FileInfo finfo, String fname, int wanted, long pool);

    /**
     * Get the specified file's stats.  The file is specified by filename,
     * instead of using a pre-opened file.
     * @param fname The name of the file to stat.
     * @param wanted The desired apr_finfo_t fields, as a bit flag of APR_FINFO_ values
     * @param pool the pool to use to allocate the new file.
     * @return FileInfo object.
     */
    public static native FileInfo getStat(String fname, int wanted, long pool);

    /**
     * Get the specified file's stats.
     * @param finfo Where to store the information about the file.
     * @param wanted The desired apr_finfo_t fields, as a bit flag of APR_FINFO_ values
     * @param thefile The file to get information about.
     */
    public static native int infoGet(FileInfo finfo, int wanted, long thefile);


    /**
     * Get the specified file's stats.
     * @param wanted The desired apr_finfo_t fields, as a bit flag of APR_FINFO_ values
     * @param thefile The file to get information about.
     * @return FileInfo object.
     */
    public static native FileInfo getInfo(int wanted, long thefile);

}
