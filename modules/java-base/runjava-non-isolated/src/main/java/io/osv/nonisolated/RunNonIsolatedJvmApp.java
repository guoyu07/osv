package io.osv.nonisolated;

/*
 * Copyright (C) 2016 Waldemar Kozaczuk
 *
 * This work is open source software, licensed under the terms of the
 * BSD license as described in the LICENSE file in the top-level directory.
 */

import io.osv.Jvm;
import io.osv.util.ClassDiagnostics;

import static io.osv.RunJvmAppHelper.runSync;
import static io.osv.RunJvmAppHelper.JvmFactory;

public class RunNonIsolatedJvmApp {
    private static native void onVMStop();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                onVMStop();
            }
        });
    }

    public static void main(String[] args) {
        if(ClassDiagnostics.showDiagnostics(args)) {
            System.out.println("------ Run Java class information --------");
            System.out.println("Classloader: " + ClassDiagnostics.showClassLoaderHierarchy(RunNonIsolatedJvmApp.class,false));
            System.out.println("Security: " + ClassDiagnostics.showClassSecurity(RunNonIsolatedJvmApp.class));
        }

        runSync(new JvmFactory() {
            public Jvm getJvm() {
                return NonIsolatedJvm.getInstance();
            }
        }, args);
    }
}
