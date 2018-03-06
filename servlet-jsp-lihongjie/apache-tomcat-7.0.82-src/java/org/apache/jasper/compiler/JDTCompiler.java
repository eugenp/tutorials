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

package org.apache.jasper.compiler;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.jasper.JasperException;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.internal.compiler.ClassFile;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.Compiler;
import org.eclipse.jdt.internal.compiler.DefaultErrorHandlingPolicies;
import org.eclipse.jdt.internal.compiler.ICompilerRequestor;
import org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy;
import org.eclipse.jdt.internal.compiler.IProblemFactory;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileReader;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jdt.internal.compiler.env.INameEnvironment;
import org.eclipse.jdt.internal.compiler.env.NameEnvironmentAnswer;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.problem.DefaultProblemFactory;

/**
 * JDT class compiler. This compiler will load source dependencies from the
 * context classloader, reducing dramatically disk access during 
 * the compilation process.
 *
 * @author Cocoon2
 * @author Remy Maucherat
 */
public class JDTCompiler extends org.apache.jasper.compiler.Compiler {

    private final Log log = LogFactory.getLog(JDTCompiler.class); // must not be static
   
    /** 
     * Compile the servlet from .java file to .class file
     */
    @Override
    protected void generateClass(String[] smap)
        throws FileNotFoundException, JasperException, Exception {

        long t1 = 0;
        if (log.isDebugEnabled()) {
            t1 = System.currentTimeMillis();
        }
        
        final String sourceFile = ctxt.getServletJavaFileName();
        final String outputDir = ctxt.getOptions().getScratchDir().getAbsolutePath();
        String packageName = ctxt.getServletPackageName();
        final String targetClassName = 
            ((packageName.length() != 0) ? (packageName + ".") : "") 
                    + ctxt.getServletClassName();
        final ClassLoader classLoader = ctxt.getJspLoader();
        String[] fileNames = new String[] {sourceFile};
        String[] classNames = new String[] {targetClassName};
        final ArrayList<JavacErrorDetail> problemList =
            new ArrayList<JavacErrorDetail>();
        
        class CompilationUnit implements ICompilationUnit {

            private final String className;
            private final String sourceFile;

            CompilationUnit(String sourceFile, String className) {
                this.className = className;
                this.sourceFile = sourceFile;
            }

            @Override
            public char[] getFileName() {
                return sourceFile.toCharArray();
            }
            
            @Override
            public char[] getContents() {
                char[] result = null;
                FileInputStream is = null;
                InputStreamReader isr = null;
                Reader reader = null;
                try {
                    is = new FileInputStream(sourceFile);
                    isr = new InputStreamReader(is,
                            ctxt.getOptions().getJavaEncoding());
                    reader = new BufferedReader(isr);
                    char[] chars = new char[8192];
                    StringBuilder buf = new StringBuilder();
                    int count;
                    while ((count = reader.read(chars, 0, 
                                                chars.length)) > 0) {
                        buf.append(chars, 0, count);
                    }
                    result = new char[buf.length()];
                    buf.getChars(0, result.length, result, 0);
                } catch (IOException e) {
                    log.error("Compilation error", e);
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException ioe) {/*Ignore*/}
                    }
                    if (isr != null) {
                        try {
                            isr.close();
                        } catch (IOException ioe) {/*Ignore*/}
                    }
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException exc) {/*Ignore*/}
                    }
                }
                return result;
            }
            
            @Override
            public char[] getMainTypeName() {
                int dot = className.lastIndexOf('.');
                if (dot > 0) {
                    return className.substring(dot + 1).toCharArray();
                }
                return className.toCharArray();
            }
            
            @Override
            public char[][] getPackageName() {
                StringTokenizer izer = 
                    new StringTokenizer(className, ".");
                char[][] result = new char[izer.countTokens()-1][];
                for (int i = 0; i < result.length; i++) {
                    String tok = izer.nextToken();
                    result[i] = tok.toCharArray();
                }
                return result;
            }

            @Override
            public boolean ignoreOptionalProblems() {
                return false;
            }
        }

        final INameEnvironment env = new INameEnvironment() {

                @Override
                public NameEnvironmentAnswer 
                    findType(char[][] compoundTypeName) {
                    StringBuilder result = new StringBuilder();
                    String sep = "";
                    for (int i = 0; i < compoundTypeName.length; i++) {
                        result.append(sep);
                        result.append(compoundTypeName[i]);
                        sep = ".";
                    }
                    return findType(result.toString());
                }

                @Override
                public NameEnvironmentAnswer 
                    findType(char[] typeName, 
                             char[][] packageName) {
                    StringBuilder result = new StringBuilder();
                    String sep = "";
                    for (int i = 0; i < packageName.length; i++) {
                        result.append(sep);
                        result.append(packageName[i]);
                        sep = ".";
                    }
                    result.append(sep);
                    result.append(typeName);
                    return findType(result.toString());
                }
                
                private NameEnvironmentAnswer findType(String className) {

                    InputStream is = null;
                    try {
                        if (className.equals(targetClassName)) {
                            ICompilationUnit compilationUnit = 
                                new CompilationUnit(sourceFile, className);
                            return 
                                new NameEnvironmentAnswer(compilationUnit, null);
                        }
                        String resourceName = 
                            className.replace('.', '/') + ".class";
                        is = classLoader.getResourceAsStream(resourceName);
                        if (is != null) {
                            byte[] classBytes;
                            byte[] buf = new byte[8192];
                            ByteArrayOutputStream baos = 
                                new ByteArrayOutputStream(buf.length);
                            int count;
                            while ((count = is.read(buf, 0, buf.length)) > 0) {
                                baos.write(buf, 0, count);
                            }
                            baos.flush();
                            classBytes = baos.toByteArray();
                            char[] fileName = className.toCharArray();
                            ClassFileReader classFileReader = 
                                new ClassFileReader(classBytes, fileName, 
                                                    true);
                            return 
                                new NameEnvironmentAnswer(classFileReader, null);
                        }
                    } catch (IOException exc) {
                        log.error("Compilation error", exc);
                    } catch (org.eclipse.jdt.internal.compiler.classfmt.ClassFormatException exc) {
                        log.error("Compilation error", exc);
                    } finally {
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException exc) {
                                // Ignore
                            }
                        }
                    }
                    return null;
                }

                private boolean isPackage(String result) {
                    if (result.equals(targetClassName)) {
                        return false;
                    }
                    String resourceName = result.replace('.', '/') + ".class";
                    InputStream is = null;
                    try {
                        is = classLoader.getResourceAsStream(resourceName);
                        return is == null;
                    } finally {
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e) {
                            }
                        }
                    }
                }

                @Override
                public boolean isPackage(char[][] parentPackageName, 
                                         char[] packageName) {
                    StringBuilder result = new StringBuilder();
                    String sep = "";
                    if (parentPackageName != null) {
                        for (int i = 0; i < parentPackageName.length; i++) {
                            result.append(sep);
                            result.append(parentPackageName[i]);
                            sep = ".";
                        }
                    }
                    if (Character.isUpperCase(packageName[0])) {
                        if (!isPackage(result.toString())) {
                            return false;
                        }
                    }
                    result.append(sep);
                    result.append(packageName);
                    return isPackage(result.toString());
                }

                @Override
                public void cleanup() {
                }

            };

        final IErrorHandlingPolicy policy = 
            DefaultErrorHandlingPolicies.proceedWithAllProblems();

        final Map<String,String> settings = new HashMap<String,String>();
        settings.put(CompilerOptions.OPTION_LineNumberAttribute,
                     CompilerOptions.GENERATE);
        settings.put(CompilerOptions.OPTION_SourceFileAttribute,
                     CompilerOptions.GENERATE);
        settings.put(CompilerOptions.OPTION_ReportDeprecation,
                     CompilerOptions.IGNORE);
        if (ctxt.getOptions().getJavaEncoding() != null) {
            settings.put(CompilerOptions.OPTION_Encoding,
                    ctxt.getOptions().getJavaEncoding());
        }
        if (ctxt.getOptions().getClassDebugInfo()) {
            settings.put(CompilerOptions.OPTION_LocalVariableAttribute,
                         CompilerOptions.GENERATE);
        }

        // Source JVM
        if(ctxt.getOptions().getCompilerSourceVM() != null) {
            String opt = ctxt.getOptions().getCompilerSourceVM();
            if(opt.equals("1.1")) {
                settings.put(CompilerOptions.OPTION_Source,
                             CompilerOptions.VERSION_1_1);
            } else if(opt.equals("1.2")) {
                settings.put(CompilerOptions.OPTION_Source,
                             CompilerOptions.VERSION_1_2);
            } else if(opt.equals("1.3")) { 
                settings.put(CompilerOptions.OPTION_Source,
                             CompilerOptions.VERSION_1_3);
            } else if(opt.equals("1.4")) {
                settings.put(CompilerOptions.OPTION_Source,
                             CompilerOptions.VERSION_1_4);
            } else if(opt.equals("1.5")) {
                settings.put(CompilerOptions.OPTION_Source,
                             CompilerOptions.VERSION_1_5);
            } else if(opt.equals("1.6")) {
                settings.put(CompilerOptions.OPTION_Source,
                             CompilerOptions.VERSION_1_6);
            } else if(opt.equals("1.7")) {
                settings.put(CompilerOptions.OPTION_Source,
                             CompilerOptions.VERSION_1_7);
            } else if(opt.equals("1.8")) {
                settings.put(CompilerOptions.OPTION_Source,
                             CompilerOptions.VERSION_1_8);
            } else {
                log.warn("Unknown source VM " + opt + " ignored.");
                settings.put(CompilerOptions.OPTION_Source,
                        CompilerOptions.VERSION_1_6);
            }
        } else {
            // Default to 1.6
            settings.put(CompilerOptions.OPTION_Source,
                    CompilerOptions.VERSION_1_6);
        }
        
        // Target JVM
        if(ctxt.getOptions().getCompilerTargetVM() != null) {
            String opt = ctxt.getOptions().getCompilerTargetVM();
            if(opt.equals("1.1")) {
                settings.put(CompilerOptions.OPTION_TargetPlatform,
                             CompilerOptions.VERSION_1_1);
            } else if(opt.equals("1.2")) {
                settings.put(CompilerOptions.OPTION_TargetPlatform,
                             CompilerOptions.VERSION_1_2);
            } else if(opt.equals("1.3")) { 
                settings.put(CompilerOptions.OPTION_TargetPlatform,
                             CompilerOptions.VERSION_1_3);
            } else if(opt.equals("1.4")) {
                settings.put(CompilerOptions.OPTION_TargetPlatform,
                             CompilerOptions.VERSION_1_4);
            } else if(opt.equals("1.5")) {
                settings.put(CompilerOptions.OPTION_TargetPlatform,
                             CompilerOptions.VERSION_1_5);
                settings.put(CompilerOptions.OPTION_Compliance,
                        CompilerOptions.VERSION_1_5);
            } else if(opt.equals("1.6")) {
                settings.put(CompilerOptions.OPTION_TargetPlatform,
                             CompilerOptions.VERSION_1_6);
                settings.put(CompilerOptions.OPTION_Compliance,
                        CompilerOptions.VERSION_1_6);
            } else if(opt.equals("1.7")) {
                settings.put(CompilerOptions.OPTION_TargetPlatform,
                             CompilerOptions.VERSION_1_7);
                settings.put(CompilerOptions.OPTION_Compliance,
                        CompilerOptions.VERSION_1_7);
            } else if(opt.equals("1.8")) {
                settings.put(CompilerOptions.OPTION_TargetPlatform,
                             CompilerOptions.VERSION_1_8);
                settings.put(CompilerOptions.OPTION_Compliance,
                        CompilerOptions.VERSION_1_8);
            } else {
                log.warn("Unknown target VM " + opt + " ignored.");
                settings.put(CompilerOptions.OPTION_TargetPlatform,
                        CompilerOptions.VERSION_1_6);
            }
        } else {
            // Default to 1.6
            settings.put(CompilerOptions.OPTION_TargetPlatform,
                    CompilerOptions.VERSION_1_6);
            settings.put(CompilerOptions.OPTION_Compliance,
                    CompilerOptions.VERSION_1_6);
        }

        final IProblemFactory problemFactory = 
            new DefaultProblemFactory(Locale.getDefault());
        
        final ICompilerRequestor requestor = new ICompilerRequestor() {
                @Override
                public void acceptResult(CompilationResult result) {
                    try {
                        if (result.hasProblems()) {
                            IProblem[] problems = result.getProblems();
                            for (int i = 0; i < problems.length; i++) {
                                IProblem problem = problems[i];
                                if (problem.isError()) {
                                    String name = 
                                        new String(problems[i].getOriginatingFileName());
                                    try {
                                        problemList.add(ErrorDispatcher.createJavacError
                                                (name, pageNodes, new StringBuilder(problem.getMessage()), 
                                                        problem.getSourceLineNumber(), ctxt));
                                    } catch (JasperException e) {
                                        log.error("Error visiting node", e);
                                    }
                                }
                            }
                        }
                        if (problemList.isEmpty()) {
                            ClassFile[] classFiles = result.getClassFiles();
                            for (int i = 0; i < classFiles.length; i++) {
                                ClassFile classFile = classFiles[i];
                                char[][] compoundName = 
                                    classFile.getCompoundName();
                                StringBuilder classFileName = new StringBuilder(outputDir).append('/');
                                for (int j = 0; 
                                     j < compoundName.length; j++) {
                                    if(j > 0) {
                                        classFileName.append('/');
                                    }
                                    classFileName.append(compoundName[j]);
                                }
                                byte[] bytes = classFile.getBytes();
                                classFileName.append(".class");
                                FileOutputStream fout = null;
                                BufferedOutputStream bos = null;
                                try {
                                    fout = new FileOutputStream(classFileName.toString());
                                    bos = new BufferedOutputStream(fout);
                                    bos.write(bytes);
                                } finally {
                                    if (bos != null) {
                                        try {
                                            bos.close();
                                        } catch (IOException e) {
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException exc) {
                        log.error("Compilation error", exc);
                    }
                }
            };

        ICompilationUnit[] compilationUnits = 
            new ICompilationUnit[classNames.length];
        for (int i = 0; i < compilationUnits.length; i++) {
            String className = classNames[i];
            compilationUnits[i] = new CompilationUnit(fileNames[i], className);
        }
        CompilerOptions cOptions = new CompilerOptions(settings);
        cOptions.parseLiteralExpressionsAsConstants = true;
        Compiler compiler = new Compiler(env,
                                         policy,
                                         cOptions,
                                         requestor,
                                         problemFactory);
        compiler.compile(compilationUnits);

        if (!ctxt.keepGenerated()) {
            File javaFile = new File(ctxt.getServletJavaFileName());
            javaFile.delete();
        }
    
        if (!problemList.isEmpty()) {
            JavacErrorDetail[] jeds = 
                problemList.toArray(new JavacErrorDetail[0]);
            errDispatcher.javacError(jeds);
        }
        
        if( log.isDebugEnabled() ) {
            long t2=System.currentTimeMillis();
            log.debug("Compiled " + ctxt.getServletJavaFileName() + " "
                      + (t2-t1) + "ms");
        }

        if (ctxt.isPrototypeMode()) {
            return;
        }

        // JSR45 Support
        if (! options.isSmapSuppressed()) {
            SmapUtil.installSmap(smap);
        }
        
    }
    
    
}
