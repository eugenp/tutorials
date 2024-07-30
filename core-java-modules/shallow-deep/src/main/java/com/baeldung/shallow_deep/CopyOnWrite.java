package com.baeldung.shallow_deep;

class CopyDocument { 
    private Document doc; 
    private boolean copied; 
    
    public Integer getDocId() { 
        return this.doc.getDocId(); 
    } 
    
    public Integer setDocId(Integer id) { 
        if (!copied) { 
            this.doc = deepCopy(this.doc); // create new deepcopy obj when mutated 
            copied = true; 
        } 
        this.doc.setDocId(id); 
    }
 }