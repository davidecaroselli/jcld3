cmake_minimum_required(VERSION 3.9)
project(jcld3)

add_definitions(-fPIC) # Position Independant Code
add_definitions(-D_GLIBCXX_USE_CXX11_ABI=0)
add_definitions(-std=c++11) # Needed for std::to_string(), ...

## Protobuf
set(Protobuf_USE_STATIC_LIBS ON)
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
