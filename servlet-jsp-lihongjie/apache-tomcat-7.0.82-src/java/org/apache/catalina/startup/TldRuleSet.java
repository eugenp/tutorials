/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.apache.catalina.startup;


import org.apache.tomcat.util.digester.Digester;
import org.apache.tomcat.util.digester.Rule;
import org.apache.tomcat.util.digester.RuleSetBase;


/**
 * <p><strong>RuleSet</strong> for processing the contents of a tag library
 * descriptor resource.</p>
 *
 * @author Craig R. McClanahan
 */
public class TldRuleSet extends RuleSetBase {


    // ----------------------------------------------------- Instance Variables


    /**
     * The matching pattern prefix to use for recognizing our elements.
     */
    protected String prefix = null;


    // ------------------------------------------------------------ Constructor


    /**
     * Construct an instance of this <code>RuleSet</code> with the default
     * matching pattern prefix.
     */
    public TldRuleSet() {

        this("");

    }


    /**
     * Construct an instance of this <code>RuleSet</code> with the specified
     * matching pattern prefix.
     *
     * @param prefix Prefix for matching pattern rules (including the
     *  trailing slash character)
     */
    public TldRuleSet(String prefix) {

        super();
        this.namespaceURI = null;
        this.prefix = prefix;

    }


    // --------------------------------------------------------- Public Methods


    /**
     * <p>Add the set of Rule instances defined in this RuleSet to the
     * specified <code>Digester</code> instance, associating them with
     * our namespace URI (if any).  This method should only be called
     * by a Digester instance.</p>
     *
     * @param digester Digester instance to which the new Rule instances
     *  should be added.
     */
    @Override
    public void addRuleInstances(Digester digester) {

        // Note the sharing of state between rules
        TaglibUriRule taglibUriRule = new TaglibUriRule(); 

        digester.addRule(prefix + "taglib", new TaglibRule(taglibUriRule));
        
        digester.addRule(prefix + "taglib/uri", taglibUriRule);

        digester.addRule(prefix + "taglib/listener/listener-class",
                new TaglibListenerRule(taglibUriRule));

    }


}

/*
 * This rule only exists to reset the duplicateUri flag on the TaglibUriRule.
 */
final class TaglibRule extends Rule {
    private final TaglibUriRule taglibUriRule;
    
    public TaglibRule(TaglibUriRule taglibUriRule) {
        this.taglibUriRule = taglibUriRule;
    }
    
    @Override
    public void body(String namespace, String name, String text)
    throws Exception {
        taglibUriRule.setDuplicateUri(false);
    }

}

final class TaglibUriRule extends Rule {
    
    // This is set to false for each file processed by the TaglibRule
    private boolean duplicateUri;
    
    public TaglibUriRule() {
    }

    @Override
    public void body(String namespace, String name, String text)
            throws Exception {
        TldConfig tldConfig =
            (TldConfig) digester.peek(digester.getCount() - 1);
        if (tldConfig.isKnownTaglibUri(text)) {
            // Already seen this URI
            duplicateUri = true;
            // This is expected if the URI was defined in web.xml
            // Log message at debug in this case
            if (tldConfig.isKnownWebxmlTaglibUri(text)) {
                if (digester.getLogger().isDebugEnabled()) {
                    digester.getLogger().debug(
                            "TLD skipped. URI: " + text + " is already defined");
                }
            } else {
                digester.getLogger().info(
                        "TLD skipped. URI: " + text + " is already defined");
            }
        } else {
            // New URI. Add it to known list and carry on
            tldConfig.addTaglibUri(text);
        }
    }
    
    public boolean isDuplicateUri() {
        return duplicateUri;
    }

    public void setDuplicateUri(boolean duplciateUri) {
        this.duplicateUri = duplciateUri;
    }

}

final class TaglibListenerRule extends Rule {
    
    private final TaglibUriRule taglibUriRule;
    
    public TaglibListenerRule(TaglibUriRule taglibUriRule) {
        this.taglibUriRule = taglibUriRule;
    }

    @Override
    public void body(String namespace, String name, String text)
            throws Exception {
        TldConfig tldConfig =
            (TldConfig) digester.peek(digester.getCount() - 1);
        
        // Only process the listener if the URI is not a duplicate
        if (!taglibUriRule.isDuplicateUri()) {
            tldConfig.addApplicationListener(text.trim());
        }
    }
    
}