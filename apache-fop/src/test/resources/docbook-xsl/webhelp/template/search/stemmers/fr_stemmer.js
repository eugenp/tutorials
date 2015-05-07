/*
 * Author: Kasun Gajasinghe
 * E-Mail: kasunbg AT gmail DOT com
 * Date: 09.08.2010
 *
 * usage: stemmer(word);
 * ex: var stem = stemmer(foobar);
 * Implementation of the stemming algorithm from http://snowball.tartarus.org/algorithms/french/stemmer.html
 *
 * LICENSE:
 *
 * Copyright (c) 2010, Kasun Gajasinghe. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice,
 *       this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *
 *
 * THIS SOFTWARE IS PROVIDED BY KASUN GAJASINGHE ''AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL KASUN GAJASINGHE BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

var stemmer = function(word){
//    Letters in French include the following accented forms,
//        â   à   ç   ë   é   ê   è   ï   î   ô   û   ù
//    The following letters are vowels:
//        a   e   i   o   u   y   â   à   ë   é   ê   è   ï   î   ô   û   ù
    
    word = word.toLowerCase();
    var oriWord = word;
    word = word.replace(/qu/g, 'qU');   //have to perform first, as after the operation, capital U is not treated as a vowel
    word = word.replace(/([aeiouyâàëéêèïîôûù])u([aeiouyâàëéêèïîôûù])/g, '$1U$2');
    word = word.replace(/([aeiouyâàëéêèïîôûù])i([aeiouyâàëéêèïîôûù])/g, '$1I$2');
    word = word.replace(/([aeiouyâàëéêèïîôûù])y/g, '$1Y');
    word = word.replace(/y([aeiouyâàëéêèïîôûù])/g, 'Y$1');
 
    var rv='';
    var rvIndex = -1;
    if(word.search(/^(par|col|tap)/) != -1 || word.search(/^[aeiouyâàëéêèïîôûù]{2}/) != -1){
        rv = word.substring(3);
        rvIndex = 3;
    } else {
        rvIndex = word.substring(1).search(/[aeiouyâàëéêèïîôûù]/);
        if(rvIndex != -1){
            rvIndex +=2;   //+2 is to supplement the substring(1) used to find rvIndex
            rv = word.substring(rvIndex);
        } else {
            rvIndex = word.length;
        }
    }

//    R1 is the region after the first non-vowel following a vowel, or the end of the word if there is no such non-vowel.
//    R2 is the region after the first non-vowel following a vowel in R1, or the end of the word if there is no such non-vowel
    var r1Index = word.search(/[aeiouyâàëéêèïîôûù][^aeiouyâàëéêèïîôûù]/);
    var r1 = '';
    if (r1Index != -1) {
        r1Index += 2;
        r1 = word.substring(r1Index);
    } else {
        r1Index = word.length;        
    }

    var r2Index = -1;
    var r2 = '';
    if (r1Index != -1) {
        r2Index = r1.search(/[aeiouyâàëéêèïîôûù][^aeiouyâàëéêèïîôûù]/);
        if (r2Index != -1) {
            r2Index += 2;
            r2 = r1.substring(r2Index);
            r2Index += r1Index;
        } else {
            r2 = '';
            r2Index = word.length;            
        }
    }
    if (r1Index != -1 && r1Index < 3) {
        r1Index = 3;
        r1 = word.substring(r1Index);
    }

    /*
    Step 1: Standard suffix removal
    */
    var a1Index = word.search(/(ance|iqUe|isme|able|iste|eux|ances|iqUes|ismes|ables|istes)$/);
    var a2Index = word.search(/(atrice|ateur|ation|atrices|ateurs|ations)$/);
    var a3Index = word.search(/(logie|logies)$/);
    var a4Index = word.search(/(usion|ution|usions|utions)$/);
    var a5Index = word.search(/(ence|ences)$/);
    var a6Index = word.search(/(ement|ements)$/);
    var a7Index = word.search(/(ité|ités)$/);
    var a8Index = word.search(/(if|ive|ifs|ives)$/);
    var a9Index = word.search(/(eaux)$/);
    var a10Index = word.search(/(aux)$/);
    var a11Index = word.search(/(euse|euses)$/);
    var a12Index = word.search(/[^aeiouyâàëéêèïîôûù](issement|issements)$/);
    var a13Index = word.search(/(amment)$/);
    var a14Index = word.search(/(emment)$/);
    var a15Index = word.search(/[aeiouyâàëéêèïîôûù](ment|ments)$/);

    if(a1Index != -1 && a1Index >= r2Index){
        word = word.substring(0,a1Index);
    } else if(a2Index != -1 && a2Index >= r2Index){
        word = word.substring(0,a2Index);
        var a2Index2 = word.search(/(ic)$/);
        if(a2Index2 != -1 && a2Index2 >= r2Index){
            word = word.substring(0, a2Index2);        //if preceded by ic, delete if in R2,
        } else {                                //else replace by iqU
            word = word.replace(/(ic)$/,'iqU');
        }
    } else if(a3Index != -1 && a3Index >= r2Index){
        word = word.replace(/(logie|logies)$/,'log');  //replace with log if in R2
    } else if(a4Index != -1 && a4Index >= r2Index){
        word = word.replace(/(usion|ution|usions|utions)$/,'u');  //replace with u if in R2
    } else if(a5Index != -1 && a5Index >= r2Index){
        word = word.replace(/(ence|ences)$/,'ent');  //replace with ent if in R2
    } else if(a6Index != -1 && a6Index >= rvIndex){
        word = word.substring(0,a6Index);
        if(word.search(/(iv)$/) >= r2Index){
            word = word.replace(/(iv)$/, '');
            if(word.search(/(at)$/) >= r2Index){
                word = word.replace(/(at)$/, '');
            }
        } else if(word.search(/(eus)$/) != -1){
            var a6Index2 = word.search(/(eus)$/);
            if(a6Index2 >=r2Index){
                word = word.substring(0, a6Index2);    
            } else if(a6Index2 >= r1Index){
                word = word.substring(0,a6Index2)+"eux";
            }
        } else if(word.search(/(abl|iqU)$/) >= r2Index){
            word = word.replace(/(abl|iqU)$/,'');   //if preceded by abl or iqU, delete if in R2,
        } else if(word.search(/(ièr|Ièr)$/) >= rvIndex){
            word = word.replace(/(ièr|Ièr)$/,'i');   //if preceded by abl or iqU, delete if in R2,
        } 
    } else if(a7Index != -1 && a7Index >= r2Index){
        word = word.substring(0,a7Index);   //delete if in R2
        if(word.search(/(abil)$/) != -1){   //if preceded by abil, delete if in R2, else replace by abl, otherwise,
            var a7Index2 = word.search(/(abil)$/);
            if(a7Index2 >=r2Index){
                word = word.substring(0, a7Index2);
            } else {
                word = word.substring(0,a7Index2)+"abl";
            }
        } else if(word.search(/(ic)$/) != -1){
            var a7Index3 = word.search(/(ic)$/);
            if(a7Index3 != -1 && a7Index3 >= r2Index){
                word = word.substring(0, a7Index3);        //if preceded by ic, delete if in R2,
            } else {                                //else replace by iqU
                word = word.replace(/(ic)$/,'iqU');
            }
        } else if(word.search(/(iv)$/) != r2Index){
            word = word.replace(/(iv)$/,'');                        
        }
    } else if(a8Index != -1 && a8Index >= r2Index){
        word = word.substring(0,a8Index);
        if(word.search(/(at)$/) >= r2Index){
            word = word.replace(/(at)$/, '');
            if(word.search(/(ic)$/) >= r2Index){
                word = word.replace(/(ic)$/, '');
            } else { word = word.replace(/(ic)$/, 'iqU'); }
        }
    } else if(a9Index != -1){ word = word.replace(/(eaux)/,'eau')
    } else if(a10Index >= r1Index){ word = word.replace(/(aux)/,'al')
    } else if(a11Index != -1 ){
        var a11Index2 = word.search(/(euse|euses)$/);
        if(a11Index2 >=r2Index){
            word = word.substring(0, a11Index2);
        } else if(a11Index2 >= r1Index){
            word = word.substring(0, a11Index2)+"eux";
        }
    } else if(a12Index!=-1 && a12Index>=r1Index){
        word = word.substring(0,a12Index+1);    //+1- amendment to non-vowel
    } else if(a13Index!=-1 && a13Index>=rvIndex){
        word = word.replace(/(amment)$/,'ant');
    } else if(a14Index!=-1 && a14Index>=rvIndex){
        word = word.replace(/(emment)$/,'ent');
    } else if(a15Index!=-1 && a15Index>=rvIndex){
        word = word.substring(0,a15Index+1);
    }

    /* Step 2a: Verb suffixes beginning i*/
    var wordStep1 = word;
    var step2aDone = false;
    if(oriWord == word.toLowerCase() || oriWord.search(/(amment|emment|ment|ments)$/) != -1){
        step2aDone = true;
        var b1Regex = /([^aeiouyâàëéêèïîôûù])(îmes|ît|îtes|i|ie|ies|ir|ira|irai|iraIent|irais|irait|iras|irent|irez|iriez|irions|irons|iront|is|issaIent|issais|issait|issant|issante|issantes|issants|isse|issent|isses|issez|issiez|issions|issons|it)$/i;
        if(word.search(b1Regex) >= rvIndex){
            word = word.replace(b1Regex,'$1');
        }
    }

    /* Step 2b:  Other verb suffixes*/
    if (step2aDone && wordStep1 == word) {
        if (word.search(/(ions)$/) >= r2Index) {
            word = word.replace(/(ions)$/, '');
        } else {
            var b2Regex = /(é|ée|ées|és|èrent|er|era|erai|eraIent|erais|erait|eras|erez|eriez|erions|erons|eront|ez|iez)$/i;
            if (word.search(b2Regex) >= rvIndex) {
                word = word.replace(b2Regex, '');
            } else {
                var b3Regex = /e(âmes|ât|âtes|a|ai|aIent|ais|ait|ant|ante|antes|ants|as|asse|assent|asses|assiez|assions)$/i;
                if (word.search(b3Regex) >= rvIndex) {
                    word = word.replace(b3Regex, '');
                } else {
                    var b3Regex2 = /(âmes|ât|âtes|a|ai|aIent|ais|ait|ant|ante|antes|ants|as|asse|assent|asses|assiez|assions)$/i;
                    if (word.search(b3Regex2) >= rvIndex) {
                        word = word.replace(b3Regex2, '');
                    }
                }
            }
        }
    }
    
    if(oriWord != word.toLowerCase()){
        /* Step 3 */
        var rep = '';
        if(word.search(/Y$/) != -1) {
            word = word.replace(/Y$/, 'i');
        } else if(word.search(/ç$/) != -1){
            word = word.replace(/ç$/, 'c');
        }
    } else {
        /* Step 4 */
        //If the word ends s, not preceded by a, i, o, u, è or s, delete it.
        if (word.search(/([^aiouès])s$/) >= rvIndex) {
            word = word.replace(/([^aiouès])s$/, '$1');
        }
        var e1Index = word.search(/ion$/);
        if (e1Index >= r2Index && word.search(/[st]ion$/) >= rvIndex) {
            word = word.substring(0, e1Index);
        } else {
            var e2Index = word.search(/(ier|ière|Ier|Ière)$/);
            if (e2Index != -1 && e2Index >= rvIndex) {
                word = word.substring(0, e2Index) + "i";
            } else {
                if (word.search(/e$/) >= rvIndex) {
                    word = word.replace(/e$/, '');   //delete last e
                } else if (word.search(/guë$/) >= rvIndex) {
                    word = word.replace(/guë$/, 'gu');
                }
            }
        }
    }
    
    /* Step 5: Undouble */
    //word = word.replace(/(en|on|et|el|eil)(n|t|l)$/,'$1');
    word = word.replace(/(en|on)(n)$/,'$1');
    word = word.replace(/(ett)$/,'et');
    word = word.replace(/(el|eil)(l)$/,'$1');

    /* Step 6: Un-accent */
    word = word.replace(/[éè]([^aeiouyâàëéêèïîôûù]+)$/,'e$1');
    word = word.toLowerCase();
    return word;
};

var eqOut = new Array();
var noteqOut = new Array();
var eqCount = 0;
/*
To test the stemming, create two arrays named "voc" and "COut" which are for vocabualary and the stemmed output.
Then add the vocabulary strings and output strings. This method will generate the stemmed output for "voc" and will
compare the output with COut.
 (I used porter's voc and out files and did a regex to convert them to js objects. regex: /");\nvoc.push("/g . This
  will add strings to voc array such that output would look like: voc.push("foobar"); ) drop me an email for any help.
 */
function testFr(){
    var start = new Date().getTime(); //execution time
    eqCount = 0;
    eqOut = new Array();
    noteqOut = new Array();
    for(var k=0;k<voc.length;k++){
        if(COut[k]==stemmer(voc[k])){
            eqCount++;
            eqOut.push("v: "+voc[k]+" c: "+COut[k]);    
        } else {
            noteqOut.push(voc[k]+", c: "+COut[k]+" s:"+stemmer(voc[k]));
        }
    }
    var end = new Date().getTime(); //execution time
    var time = end-start;
    alert("equal count= "+eqCount+" out of "+voc.length+" words. time= "+time+" ms");
    //console.log("equal count= "+eqCount+" out of "+voc.length+" words. time= "+time+" ms");
}


