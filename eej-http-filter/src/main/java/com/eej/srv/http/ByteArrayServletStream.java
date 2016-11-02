/**
 * 
 */
package com.eej.srv.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;

/**
 * @author jlumietu - Mikel Ibiricu Alfaro
 *
 */
public class ByteArrayServletStream extends ServletOutputStream {
	 
    ByteArrayOutputStream baos;
 
    ByteArrayServletStream(ByteArrayOutputStream baos) {
      this.baos = baos;
    }
 
    public void write(int param) throws IOException {
      baos.write(param);
    }
  }