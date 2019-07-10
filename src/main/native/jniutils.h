//
// Created by Davide Caroselli on 2019-07-10.
//

#ifndef JCLD3_JNIUTILS_H
#define JCLD3_JNIUTILS_H

#include <jni.h>
#include <string>

inline std::string jni_jbytestostr(JNIEnv *jvm, jbyteArray jbytes) {
    jboolean is_copy;
    int jsize = jvm->GetArrayLength(jbytes);
    jbyte *chars = jvm->GetByteArrayElements(jbytes, &is_copy);

    std::string string(reinterpret_cast<char const *>(chars), (size_t) jsize);

    jvm->ReleaseByteArrayElements(jbytes, chars, JNI_ABORT);

    return string;
}

#endif //JCLD3_JNIUTILS_H
