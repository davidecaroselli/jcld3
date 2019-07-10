cmake_minimum_required(VERSION 3.13)
project(jcld3)

set(CMAKE_CXX_STANDARD 17)

## Protobuf
find_package(Protobuf REQUIRED)
include_directories(${Protobuf_INCLUDE_DIRS})

## JNI
find_package(JNI REQUIRED)
include_directories(${JNI_INCLUDE_DIRS})

## CLD3
add_subdirectory(cld3)
include_directories(${CMAKE_SOURCE_DIR}/cld3/src)
include_directories(${CMAKE_CURRENT_BINARY_DIR}/cld3) # needed to include generated pb headers

include_directories(${CMAKE_SOURCE_DIR})
add_library(jcld3 SHARED
        jniutils.h
        javah/com_github_davidecaroselli_jcld3_CLD3.h com_github_davidecaroselli_jcld3_CLD3.cpp
        LanguageRegistry.cpp LanguageRegistry.h)

target_link_libraries(jcld3 cld3 ${Protobuf_LITE_LIBRARIES})

install(TARGETS ${PROJECT_NAME}
        LIBRARY DESTINATION ${CMAKE_INSTALL_PREFIX})
