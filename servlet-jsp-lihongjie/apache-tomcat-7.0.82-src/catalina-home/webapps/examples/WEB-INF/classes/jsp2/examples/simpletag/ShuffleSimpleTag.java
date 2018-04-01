/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package jsp2.examples.simpletag;

import java.io.IOException;
import java.util.Random;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * SimpleTag handler that accepts takes three attributes of type
 * JspFragment and invokes then in a random order.
 */
public class ShuffleSimpleTag extends SimpleTagSupport {
    // No need for this to use SecureRandom
    private static Random random = new Random();

    private JspFragment fragment1;
    private JspFragment fragment2;
    private JspFragment fragment3;

    @Override
    public void doTag() throws JspException, IOException {
        switch(random.nextInt(6)) {
            case 0:
                fragment1.invoke( null );
                fragment2.invoke( null );
                fragment3.invoke( null );
                break;
            case 1:
                fragment1.invoke( null );
                fragment3.invoke( null );
                fragment2.invoke( null );
                break;
            case 2:
                fragment2.invoke( null );
                fragment1.invoke( null );
                fragment3.invoke( null );
                break;
            case 3:
                fragment2.invoke( null );
                fragment3.invoke( null );
                fragment1.invoke( null );
                break;
            case 4:
                fragment3.invoke( null );
                fragment1.invoke( null );
                fragment2.invoke( null );
                break;
            case 5:
                fragment3.invoke( null );
                fragment2.invoke( null );
                fragment1.invoke( null );
                break;
        }
    }

    public void setFragment1( JspFragment fragment1 ) {
        this.fragment1 = fragment1;
    }

    public void setFragment2( JspFragment fragment2 ) {
        this.fragment2 = fragment2;
    }

    public void setFragment3( JspFragment fragment3 ) {
        this.fragment3 = fragment3;
    }
}
