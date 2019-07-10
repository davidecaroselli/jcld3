#include "javah/com_github_davidecaroselli_jcld3_CLD3.h"
#include "base.h"
#include "nnet_language_identifier.h"
#include "jniutils.h"
#include "LanguageRegistry.h"
#include <math.h>

using namespace std;
using namespace chrome_lang_id;

void CopyResult(JNIEnv *jvm, const NNetLanguageIdentifier::Result &source, jobject output) {
    jclass XLanguageResultClass = jvm->GetObjectClass(output);

    jfieldID languageField = jvm->GetFieldID(XLanguageResultClass, "language", "I");
    jfieldID probabilityField = jvm->GetFieldID(XLanguageResultClass, "probability", "F");
    jfieldID reliableField = jvm->GetFieldID(XLanguageResultClass, "reliable", "Z");
    jfieldID proportionField = jvm->GetFieldID(XLanguageResultClass, "proportion", "F");

    jvm->SetIntField(output, languageField, (jint) LanguageRegistry::GetLanguageId(source.language));
    jvm->SetFloatField(output, probabilityField, (jfloat) source.probability);
    jvm->SetBooleanField(output, reliableField, (jboolean) source.is_reliable);
    jvm->SetFloatField(output, proportionField, (jfloat) source.proportion);
}

void CopyResults(JNIEnv *jvm, const vector<NNetLanguageIdentifier::Result> &sources, jobjectArray output) {
    auto size = static_cast<size_t>(jvm->GetArrayLength(output));
    jclass XLanguageResultClass = jvm->GetObjectClass(jvm->GetObjectArrayElement(output, 0));

    jfieldID languageField = jvm->GetFieldID(XLanguageResultClass, "language", "I");
    jfieldID probabilityField = jvm->GetFieldID(XLanguageResultClass, "probability", "F");
    jfieldID reliableField = jvm->GetFieldID(XLanguageResultClass, "reliable", "Z");
    jfieldID proportionField = jvm->GetFieldID(XLanguageResultClass, "proportion", "F");

    for (jsize i = 0; i < min(sources.size(), size); ++i) {
        const NNetLanguageIdentifier::Result &source = sources[i];
        jobject dest = jvm->GetObjectArrayElement(output, i);

        jvm->SetIntField(dest, languageField, (jint) LanguageRegistry::GetLanguageId(source.language));
        jvm->SetFloatField(dest, probabilityField, (jfloat) source.probability);
        jvm->SetBooleanField(dest, reliableField, (jboolean) source.is_reliable);
        jvm->SetFloatField(dest, proportionField, (jfloat) source.proportion);
    }
}

/*
 * Class:     com_github_davidecaroselli_jcld3_CLD3
 * Method:    init
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_github_davidecaroselli_jcld3_CLD3_init(JNIEnv *jvm, jobject jself) {
    return (jlong) new NNetLanguageIdentifier(
            /*min_num_bytes=*/0,
            /*max_num_bytes=*/com_github_davidecaroselli_jcld3_CLD3_MAX_NUM_BYTES
    );
}

/*
 * Class:     com_github_davidecaroselli_jcld3_CLD3
 * Method:    findLanguage
 * Signature: (J[BLcom/github/davidecaroselli/jcld3/XLanguageResult;)V
 */
JNIEXPORT void JNICALL Java_com_github_davidecaroselli_jcld3_CLD3_findLanguage
        (JNIEnv *jvm, jobject, jlong handle, jbyteArray jtext, jobject jresult) {
    auto *self = reinterpret_cast<NNetLanguageIdentifier *>(handle);
    string text = jni_jbytestostr(jvm, jtext);

    const NNetLanguageIdentifier::Result result = self->FindLanguage(text);
    CopyResult(jvm, result, jresult);
}

/*
 * Class:     com_github_davidecaroselli_jcld3_CLD3
 * Method:    findTopNMostFreqLanguage
 * Signature: (J[B[Lcom/github/davidecaroselli/jcld3/XLanguageResult;)V
 */
JNIEXPORT void JNICALL Java_com_github_davidecaroselli_jcld3_CLD3_findTopNMostFreqLanguage
        (JNIEnv *jvm, jobject jself, jlong handle, jbyteArray jtext, jobjectArray jresults) {
    auto *self = reinterpret_cast<NNetLanguageIdentifier *>(handle);
    string text = jni_jbytestostr(jvm, jtext);

    jsize size = jvm->GetArrayLength(jresults);
    const vector<NNetLanguageIdentifier::Result> results = self->FindTopNMostFreqLangs(text, size);
    CopyResults(jvm, results, jresults);
}

/*
 * Class:     com_github_davidecaroselli_jcld3_CLD3
 * Method:    dispose
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_github_davidecaroselli_jcld3_CLD3_dispose(JNIEnv *jvm, jobject jself, jlong ptr) {
    if (ptr != 0) {
        auto *self = (NNetLanguageIdentifier *) ptr;
        delete self;
    }

    return 0;
}