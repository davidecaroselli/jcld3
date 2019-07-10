//
// Created by Davide Caroselli on 2019-07-10.
//

#ifndef JCLD3_LANGUAGEREGISTRY_H
#define JCLD3_LANGUAGEREGISTRY_H

#include <string>
#include <unordered_map>

class LanguageRegistry {
public:
    inline static int GetLanguageId(const std::string &language) {
        if (kUnknown == language)
            return -1;
        else
            return kLanguages[language];
    }

private:
    static std::string kUnknown;
    static std::unordered_map<std::string, int> kLanguages;
};


#endif //JCLD3_LANGUAGEREGISTRY_H
