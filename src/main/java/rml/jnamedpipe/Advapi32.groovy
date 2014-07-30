package rml.jnamedpipe

import com.sun.jna.Native
import com.sun.jna.ptr.IntByReference
import com.sun.jna.ptr.LongByReference
import com.sun.jna.win32.StdCallLibrary

/**
 * Created by Renato.Lorenzi on 23/07/2014.
 */
public interface Advapi32 extends StdCallLibrary {

    Advapi32 INSTANCE = (Advapi32) Native.loadLibrary("advapi32",
            Advapi32.class, JNAKernel32.DEFAULT_OPTIONS);

    int ConvertStringSecurityDescriptorToSecurityDescriptor(String StringSecurityDescriptor,
                                                            int StringSDRevision,
                                                            IntByReference SecurityDescriptor,
                                                            LongByReference SecurityDescriptorSize);
}
