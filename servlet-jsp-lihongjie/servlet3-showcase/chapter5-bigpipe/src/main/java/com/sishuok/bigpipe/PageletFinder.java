/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.bigpipe;

import com.sishuok.bigpipe.Pagelet;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-8-3 下午8:49
 * <p>Version: 1.0
 */
public interface PageletFinder {

    Pagelet find(String pageletName);
}
